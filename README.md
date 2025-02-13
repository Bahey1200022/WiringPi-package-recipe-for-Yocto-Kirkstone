# WiringPi GPIO Library Yocto Recipe for Raspberry Pi

This repository provides a Yocto package recipe for building and installing the [WiringPi](https://github.com/WiringPi/WiringPi) GPIO library on Raspberry Pi devices.

## 📌 Overview
WiringPi is a C library that allows users to easily interface with the GPIO pins of a Raspberry Pi. This Yocto recipe enables seamless integration of WiringPi into a Yocto-based Linux system.

## 📂 Recipe Structure
The WiringPi recipe follows the standard Yocto recipe format and includes:
- **Fetching the source** from the WiringPi GitHub repository.
- **Building the library and CLI tool (`gpio`)**.
- **Installing the required headers and binaries** into the target filesystem.

## ⚙️ How to Use

### 1️⃣ Add the Layer (If Needed)
If this recipe is part of a custom layer, ensure that the layer is included in your Yocto build environment:
```sh
bitbake-layers add-layer /path/to/your-layer
```

### 2️⃣ Build the WiringPi Package
Run the following command to build the package:
```sh
bitbake wiringpi
```

### 3️⃣ Integrate the Package into Your Image
To include WiringPi in your custom Yocto image, add the package to your image recipe:
```sh
IMAGE_INSTALL += "wiringpi"
```
Then rebuild your image:
```sh
bitbake your-image-name
```

### 4️⃣ Verify the Installation
After installation, verify that WiringPi is working by running:
```sh
gpio -v
gpio readall
```

## 🛠️ Customization
If you need to modify the recipe (e.g., change the version or dependencies), edit the `wiringpi.bb` file and update the `SRC_URI` or dependencies accordingly.

## 📝 Notes
- Ensure that your Yocto build is configured for **Raspberry Pi**.
- If using an older Raspberry Pi, additional kernel configurations may be required.
- WiringPi is **deprecated** by the original author but still works with Raspberry Pi models up to the Raspberry Pi 4.

## 📜 License
This package follows the **LGPL-3.0-only** license as per the WiringPi project.

## 🔗 References
- WiringPi GitHub: [https://github.com/WiringPi/WiringPi](https://github.com/WiringPi/WiringPi)
- Yocto Project Documentation: [https://www.yoctoproject.org/](https://www.yoctoproject.org/)


https://github.com/user-attachments/assets/f0d75627-b46d-463b-993b-5dd4bb09ef7a


