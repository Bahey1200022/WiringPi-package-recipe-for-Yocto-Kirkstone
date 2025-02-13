DESCRIPTION = "A library to control Raspberry Pi GPIO channels"
HOMEPAGE = "https://github.com/WiringPi/WiringPi"
SECTION = "devel/libs"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://COPYING.LESSER;md5=e6a600fd5e1d9cbde2d983680233ad02"

SRC_URI = "git://github.com/WiringPi/WiringPi.git;protocol=https;branch=master"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "raspberrypi"

DEPENDS = "virtual/libc libxcrypt"
inherit pkgconfig

do_configure() {
    # Prevent WiringPi from auto-detecting system properties
    echo "" > ${S}/wiringPi/cfg.mk
}
do_compile() {
    # Build WiringPi library first
    oe_runmake -C wiringPi \
        CC="${CC}" \
        CXX="${CXX}" \
        LD="${LD}" \
        AR="${AR}" \
        CFLAGS="${CFLAGS} -fPIC -I${S}/wiringPi" \
        LDFLAGS="${LDFLAGS} -Wl,--hash-style=gnu"

    # Build WiringPiDev library next
    oe_runmake -C devLib \
        CC="${CC}" \
        CXX="${CXX}" \
        LD="${LD}" \
        AR="${AR}" \
        CFLAGS="${CFLAGS} -fPIC -I${S}/wiringPi -I${S}/devLib" \
        LDFLAGS="${LDFLAGS} -L${S}/wiringPi -Wl,--hash-style=gnu"

    # Create symbolic links for the libraries
    cd ${S}/wiringPi
    ln -sf libwiringPi.so.3.14 libwiringPi.so
    cd ${S}/devLib
    ln -sf libwiringPiDev.so.3.14 libwiringPiDev.so
    cd ${S}

    # Finally build gpio
    oe_runmake -C gpio \
        CC="${CC}" \
        CXX="${CXX}" \
        LD="${LD}" \
        AR="${AR}" \
        CFLAGS="${CFLAGS} -fPIC -I${S}/wiringPi -I${S}/devLib -I${S}/gpio" \
        LDFLAGS="${LDFLAGS} -L${S}/wiringPi -L${S}/devLib -Wl,--hash-style=gnu"
}

do_install() {
    install -d ${D}${libdir} ${D}${includedir}/wiringPi ${D}${includedir}/devLib ${D}${bindir}

    # Install WiringPi library
    install -m 0755 ${S}/wiringPi/libwiringPi.so.3.14 ${D}${libdir}/
    ln -sf libwiringPi.so.3.14 ${D}${libdir}/libwiringPi.so

    # Install WiringPiDev library
    install -m 0755 ${S}/devLib/libwiringPiDev.so.3.14 ${D}${libdir}/
    ln -sf libwiringPiDev.so.3.14 ${D}${libdir}/libwiringPiDev.so

    # Install headers
    install -m 0644 ${S}/wiringPi/*.h ${D}${includedir}/wiringPi/
    install -m 0644 ${S}/devLib/*.h ${D}${includedir}/devLib/

    # Install gpio binary
    install -m 0755 ${S}/gpio/gpio ${D}${bindir}/
}


FILES:${PN} += "${libdir}/libwiringPi.so.* ${libdir}/libwiringPiDev.so.* ${bindir}/gpio"
FILES:${PN}-dev += "${includedir}/wiringPi/* ${includedir}/devLib/*"

TARGET_CC_ARCH += "${LDFLAGS}"
INSANE_SKIP:${PN} = "ldflags"