# SwiftBot Object Detection Game

A Java program for the **SwiftBot** robot (Raspberry Pi based) that turns object detection into a small game. The robot reads a QR code to choose a behaviour mode, wanders around using its ultrasound sensor, reacts when it finds an object, takes a photo with its camera, and shows a summary log when the game ends.

> 📅 **Project context:** This project was built during my **first year of BSc Computer Science at Brunel University London** and was completed between **February and March 2025**.

---

## Features

- **QR code mode selection** – scan a QR code with the SwiftBot camera to pick the mode (`curious`, `scaredy` or `dubious`).
- **Three behaviour modes:**
  | Mode | Behaviour |
  |------|-----------|
  | 🟡 **Curious** | Wanders randomly until it finds an object, then moves forwards/backwards until it sits exactly **30 cm** away. Underlights turn green and it takes and saves a photo. |
  | 🟢 **Scaredy** | Wanders until an object comes within **50 cm**, takes a photo, flashes red underlights, backs away and turns in a random direction. If nothing is found within 5 seconds, it follows a set path in a different direction. |
  | 🔴 **Dubious** | Randomly picks either Curious or Scaredy behaviour each time. |
- **Underlight feedback** – blue while searching, green when positioned, red when "scared".
- **Camera capture** – photos are saved as `capturedImage.png` on the robot.
- **Button controls** – Button **A** starts the game; Button **X** ends it.
- **Execution log** – when the game ends, press **Y** to view the log (mode, number of objects detected, image and log file paths) or **X** to exit.
- **Coloured ASCII-art console UI** using ANSI escape codes.

## Project structure

```
SwiftBot_Object_detect_Year1/
├── pom.xml                         # Maven build file (SwiftBot API dependency)
└── src/main/java/Object_Detect/
    ├── Main_code.java              # Entry point, QR mode selection, Curious/Scaredy/Dubious logic
    └── Termination2.java           # End-of-game screen and execution log
```

## Tech stack

- **Java**
- **Maven**
- **SwiftBot API** `5.1.3` (`com.swiftbotlabs:SwiftBot-API`), from Brunel's SwiftBot Maven repository

## How to run

1. Clone the repository:
   ```bash
   git clone <repo-url>
   cd SwiftBot_Object_detect_Year1
   ```
2. Build the project with Maven:
   ```bash
   mvn clean package
   ```
3. Copy the built JAR to the SwiftBot and run it there:
   ```bash
   java -cp <your-jar>.jar Object_Detect.Main_code
   ```
4. Show the robot a QR code that reads `curious`, `scaredy` or `dubious`, then press **Button A** to start.

> ⚠️ The program needs the physical SwiftBot hardware (camera, ultrasound sensor, underlights and buttons) and will not run on a normal PC. Photos and logs are saved under `/data/home/pi/` on the robot.

## Results

This project was part of the work assessed in my first-year (FHEQ Level 4) Software Engineering modules, academic year 2024/25:

| Module | Title | Grade | Credits |
|--------|-------|:-----:|:-------:|
| CS1812 | Professional Reflection | **A** | 15 |
| CS1813 | Software Design | **A-** | 15 |
| CS1814 | Software Implementation | **A** | 15 |

All three modules were passed on the first attempt.

## Author

**Oleksandra** – BSc Computer Science, Brunel University London
