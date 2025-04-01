PoCoTo – Virtual Pet Simulation Game

Group 50 – COMPSCI 2212B - Winter 2025

**Group 50**:

- Adit Bhimani
- Jayansh Bagga
- Bhavya Sharma
- Krish Patel
- Jeremy Ro


**Description**:

PoCoTo is a Java-based virtual pet simulation game where players care for one of three unique bears: Po (Panda), Co (Polar Bear), and To (Grizzly Bear). Each bear has different traits and needs. Players interact by feeding, playing, healing, and putting their bear to sleep. The goal is to maintain the bear’s happiness, hunger, sleep, and health. The game includes save/load features, parental controls, a settings menu, and optional mini-games for added interaction.


**Required Libraries and Tools**:


**Languages and Build Tools**:
- Java SE 11
- Apache Maven 3.9.6



**APIs and Libraries**:
JUnit 5.10.0 (via Guardian API for testing)
GSON 2.10.1 (used to convert Java objects to/from JSON)
Jackson Databind 2.15.2 (for JSON parsing and writing)
JavaFX SDK (optional, used if GUI is implemented)

**IDE**:
Visual Studio Code with Java Extension Pack
How to Build (From Source)
Install Java SE 11 or higher.
Install Apache Maven 3.6+.
Install Visual Studio Code (optional, but recommended).
Extract the ZIP file or clone the Git repository.
Open the project folder in your IDE.
Open the terminal and run the following command:
mvn clean install
This will download dependencies and compile the project.


**How to Run the Game**:
After building, double-click the file: runPoCoTo.bat. 
This will launch the game automatically using the compiled files.
No other commands or configuration are required.


**User Guide**:
Launch the game using the batch file.
Use the Main Menu to start a new game or load a saved one.
Select your bear: Po, Co, or To.

**Interact with your bear by choosing actions**:
- Feed
- Play
- Sleep
- Heal


Monitor the bear's status bars: hunger, happiness, sleep, and health.

**Use the Settings menu to**:
- Adjust volume
- Change language
- Enable or disable dark mode

Save your progress anytime from the in-game menu.

**Parental Controls**:
- To access parental controls:
- Go to the Settings menu.
- Select "Parental Controls".


When prompted, enter the password:
- admin

From here, you can:
- Set a daily playtime limit
- Enable or disable auto-revive for bears

**Debugs**:
- Pause Button in the game is missing
- Character Revive was not implemented

**Javadocs**:
- Our Javadoc documentation is located inside the docs folder. From there, there is a file named index-html, which contains Javadocs for all of the Javadocs compiled.



No separate installation is required. This feature is built into the main game.