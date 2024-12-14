• GitHub: https://github.com/hcyss10/CW2024.git

• Compilation Instructions:
- Ensure you have Maven and JavaFX configured on your system.
- Use the provided "pom.xml" to handle dependencies.
- Compile and run the project using IntelliJ IDEA or a compatible IDE.

• Implemented and Working Properly: 
-infinite levels with increasing difficulty
-kill counter
-boss health bar
-level title
-background animation
-horizontal movement
-boss variable difficulty
-in game menus
-CSS styling
-start menu
-total kill counter
-added logic to handle 2 key inputs at the same time
-added shoot cooldown

• Implemented but Not Working Properly:


• Features Not Implemented:
-level select
-enemy variety
-user plane variety
-power ups
-use total kills as ingame currency
-split level class into smaller single issue manager classes

• New Java Classes:
-BossHealthBar: extends progressbar, displayed at the bottom in boos battles, Binded to boss health property
-Difficulty: used as parameter for level constructor to set the level difficulty, allows for infinte levels with ever increasing difficulty
-GameLoop: seperated from level class for refactoring
-InGameMenuController: Controller logic for InGameMenu.fxml, multipurpose pause menu, win menu, and defeat menu
-KillCounter: extends text node, displayed at the top, binded to user's number of kills property, and displays kills needed 
-LevelTitle: displays current level, extends text node
-Pause: shows IngameMenu in pause state, and stops gameloop
-StartMenuController: Controller logic for StartMenu.fxml, displays total kills and highest level won at the top right corner both binded to respective properties

• Modified Java Classes:
-Controller: removed deprecated observer, added goToMenu method, set screen dimensions
-Main: removed stage dimensions, now the dimensions are automatically set according to screen dimensions
-Boss: Added variable initial health, use IntegerProperty for health so it can be binded to progressbar
-FighterPlane:  use IntegerProperty for health and kills so it can be binded to heartdisplay and killcounter
-HeartDisplay: added sethearts method handled in health property listener, (can be used to increase hearts for future power up)
-Level(formerly named LevelParent): no longer abstract, replaces levelone and leveltwo, has variable properties set bu difficuty parameter, infinite levels
-LevelView: added many nodes to root such as killcounter, bosshealthbar, pause, etc
-ShieldImage: binded visibilty property to boss shielded property
-UserPlane: added horizontal movement, and fire projectile cooldown time

• Unexpected Problems:

