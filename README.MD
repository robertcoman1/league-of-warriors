Java 2D Grid RPG – Turn‑Based Combat
This is a Java game (with a Swing GUI) where you can log in with one of the accounts defined in accounts.json.
Each account is linked to multiple legends with unique abilities.

📋 Game Overview
Accounts & Legends:
The file accounts.json contains multiple accounts.
When you log in with one of them, you can play as one of the associated legend, each having different stats and special abilities.

World & Movement:
The map is a 2D grid.
You can move around the grid, exploring various special tiles:

Finish – the goal tile to complete a level.

Portal – instantly teleports you to a different map.

Sanctuary – restores your health when you step on it.

Encounters & Combat:
When you encounter an enemy on the grid, the game switches to a turn‑based combat mode:

On your turn you can perform a normal attack or use a special ability.

Defeat enemies to progress through the map.

✅ Requirements
Java 17+ installed.

A graphical environment (GUI) is required to run the game:

On Linux, make sure you run it in a desktop environment (e.g., GNOME/KDE) or with an X server.

Running in a headless environment (no GUI) will result in a HeadlessException.

🚀 How to Run
Build the project or just run :
javac -cp .:lib/json_simple.jar -d out src/*.java
java -cp "out:lib/*" Game