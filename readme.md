# PoCoTo – Virtual Pet Simulation Game

**A JavaFX desktop game developed for COMPSCI 2212B at Western University, Winter 2025.**

Originally created as Group 50's course project, PoCoTo has since received improvements to its gameplay systems, user interface, persistence, parental controls, and automated testing.

## Development Team

**Group 50**

- Adit Bhimani
- Jayansh Bagga
- Bhavya Sharma
- Krish Patel
- Jeremy Ro

## About the Game

PoCoTo is a Java-based virtual pet simulation game where players adopt and care for one of three bears:

- **Po:** Panda Bear
- **Co:** Polar Bear
- **To:** Grizzly Bear

Each bear has unique stat-decay characteristics and requires regular care to remain healthy and happy.

Players manage four essential attributes: **Hunger, Health, Sleep, and Happiness**.

The game combines pet-care mechanics with inventory management, scoring, a minigame, and parental controls.

## Features

### Virtual Pet Care

Players can interact with their selected bear through four primary actions:

- **Feed:** Restore Hunger.
- **Play:** Restore Happiness.
- **Sleep:** Restore energy.
- **Heal:** Restore Health.

Each bear's statistics automatically decrease as time passes, with different decay rates depending on the bear type.

### Inventory System

Each bear has an independent inventory containing food and other care items.

Players can consume items to restore their bear's statistics. Item quantities decrease when used, and exhausted items cannot be consumed again.

Inventories are preserved when saving and loading a game.

### Scoring System

Players earn points by caring for their bears and participating in the minigame.

- Restoring bear statistics earns points based on the actual improvement.
- Winning the minigame awards **30 points**.
- Drawing the minigame awards **10 points**.
- Losing does not award points.

Scores are saved with each bear's progress.

### Minigame

PoCoTo includes *Pic, Pac, Toe*, a Tic-Tac-Toe-style minigame.

Players can access the minigame from the game interface. When launched during gameplay, the bear's automatic stat decay is paused until the player returns.

### Save and Load

Players can save and restore their bear's progress, including:

- Bear type and name
- Hunger, Health, Sleep, and Happiness
- Inventory quantities
- Accumulated score

The primary gameplay Save/Load system uses Java object serialization.

### Parental Controls

Parental controls are accessible directly from the Main Menu.

The system supports:

- Password-protected settings changes
- Configurable playtime limits of **1, 2, or 3 hours**
- Unlimited gameplay
- Persistent session and playtime statistics
- Playtime-limit enforcement
- Enabling or disabling bear revival
- Reviving a deceased bear when permitted

The parental-control settings are stored separately from individual bear save files.

### User Interface

PoCoTo features a JavaFX graphical interface with:

- Main Menu and bear selection
- Gameplay statistics and status indicators
- Inventory screen
- Pause menu
- Save and Load controls
- Parental Controls
- Settings and Help screens
- Resizable and maximizable windows

## Technologies

| Technology | Purpose |
|---|---|
| Java | Application and gameplay logic |
| JavaFX | Desktop graphical user interface |
| Gson 2.12.1 | JSON support |
| Java Serialization | Gameplay Save/Load |
| JUnit 5 | Automated testing |
| Windows Batch Scripts | Compilation, execution, and testing |

The application follows a Model-View-Controller-oriented architecture, separating bear models, gameplay controllers, and JavaFX screens.

## Requirements

For the current Windows development setup:

- Java Development Kit (JDK) 21
- JavaFX SDK 23.0.2
- Windows 10 or 11
- Required JAR dependencies in the project's `lib/` directory

The current launcher expects JavaFX at:

```text
C:\Program Files\Java\javafx-sdk-23.0.2\lib
```

If JavaFX is installed elsewhere, update the `JAVAFX_LIB` variable in the batch scripts.

## Installation and Running

### 1. Clone the Repository

```bash
git clone https://github.com/adit1110/PoCoTo.git
cd PoCoTo
```

### 2. Configure JavaFX

Install the required JavaFX SDK and confirm that the path in `runPoCoTo.bat` matches your installation.

### 3. Launch PoCoTo

From PowerShell in the project directory:

```powershell
.\runPoCoTo.bat
```

The script compiles the Java source files and launches the application.

The current build process uses `javac` and Windows batch scripts rather than requiring Maven.

## How to Play

1. Launch PoCoTo.
2. Select **New Game** from the Main Menu.
3. Choose Po, Co, or To.
4. Monitor your bear's Hunger, Health, Sleep, and Happiness.
5. Use Feed, Play, Sleep, and Heal to maintain its well-being.
6. Visit the Inventory to consume available items.
7. Play the minigame to earn additional points.
8. Save your progress using **Save Game**.
9. Return later and use **Load Game** to continue.

If your bear's Health reaches zero, revival is available only when permitted by the parental-control settings.

## Parental Controls

1. Open **Parental Controls** from the Main Menu.
2. Choose the desired playtime limit.
3. Enable or disable bear revival.
4. Enter the administrator password.
5. Click **Save**.

For the current project version, the default administrator password is `admin`.

Parental settings and playtime statistics are persisted locally in `parental_controls.properties`.

> **Note:** This password is currently hardcoded for the course-project application. The parental controls should not be treated as a security-hardened access-control system.

## Automated Testing

PoCoTo includes automated unit, regression, and selected JavaFX integration tests.

### Regression Tests

Run:

```powershell
.\runPoCoToTests.bat
```

The regression suite covers:

- Bear stat calculations
- Automatic stat-decay lifecycle
- Save/Load behavior
- Inventory management
- Gameplay scoring
- Parental settings persistence
- Bear revival restrictions
- Playtime-limit policies
- Selected JavaFX Save/Load interactions

**Latest verified result: 45 tests passing.**

### Original Course Tests

Run:

```powershell
.\runPoCoToLegacyTests.bat
```

The original CS2212 tests cover:

- Bear models and individual decay behavior
- BearController
- Player and Inventory
- GameController and DatabaseHelper
- NotificationService
- ParentalControl
- Pause menu callbacks

**Latest verified result: 41 tests passing.**

**Total: 86 automated tests passing across both suites.**

These tests validate application logic and selected UI interactions. They do not provide exhaustive end-to-end GUI coverage.

## Modernization Since the Original Submission

Following the Winter 2025 course submission, development continued to address bugs and improve the application.

Major improvements include:

- Fixed gameplay buttons and layout clipping
- Improved window resizing and navigation
- Corrected automatic stat-decay timer lifecycle
- Fixed bear Happiness and Health restoration calculations
- Improved game serialization and recovery handling
- Introduced independent inventories with item quantities
- Added persistent gameplay scoring
- Connected minigame results to score rewards
- Implemented persistent parental settings and playtime tracking
- Added parental playtime-limit policies and revival functionality
- Restored and expanded the automated test suites

The original course submission is preserved under the Git tag `v1.0-course-submission`.

## Project Documentation

The repository includes Javadoc documentation for the project's Java classes in the `docs/` directory.

Open `docs/index.html` in a browser to browse the generated documentation, if present.

## Known Limitations

- The Settings screen exposes volume and dark-mode controls, but these preferences are not currently applied to the running application.
- The current build scripts use a Windows-specific JavaFX installation path.
- The default parental-control password is hardcoded.
- Automated tests do not cover every JavaFX screen transition or interaction.

## Project History

PoCoTo was originally developed by Group 50 for COMPSCI 2212B at Western University during Winter 2025.

The project has since been revisited and expanded with additional features, bug fixes, and regression testing while preserving the original team's contributions.
