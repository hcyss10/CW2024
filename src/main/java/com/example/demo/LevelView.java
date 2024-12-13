package com.example.demo;

import java.io.IOException;

import com.example.demo.InGameMenuController.MenuState;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.layout.VBox;

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 5;
	private static final int SHIELD_X_POSITION = 1245;
	private static final int SHIELD_Y_POSITION = 5;
	private static final int LEVEL_TITLE_X_POSITION = 500;
	private static final int LEVEL_TITLE_Y_POSITION = 5;
	private static final int KILL_COUNTER_X_POSITION = 500;
	private static final int KILL_COUNTER_Y_POSITION = 20;
	private static final int BOSS_HEALTH_BAR_X_POSITION = 500;
	private static final int BOSS_HEALTH_BAR_Y_POSITION = 700;
	private static final int PAUSE_X_POSITION = 475;
	private static final int PAUSE_Y_POSITION = 5;
	private final Group root;
	private final Level level;
	private final GameLoop gameLoop;
	private final HeartDisplay heartDisplay;
	private final ShieldImage shieldImage;
	private final LevelTitle levelTitle;
	private final KillCounter killCounter;
	private final BossHealthBar bossHealthBar;
	private final Pause pause;
	private VBox inGameMenu;
    private InGameMenuController inGameMenuController;
	
	public LevelView(Group root, int heartsToDisplay, Level level, GameLoop gameLoop){
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.shieldImage = new ShieldImage(level.getBoss(), SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.bossHealthBar = new BossHealthBar(level.getBoss(), BOSS_HEALTH_BAR_X_POSITION, BOSS_HEALTH_BAR_Y_POSITION);
		this.levelTitle = new LevelTitle(level.getCurrentLevel() + 1, LEVEL_TITLE_X_POSITION, LEVEL_TITLE_Y_POSITION);
		this.killCounter = new KillCounter(level.getUser(), level.getKillsToAdvance(), KILL_COUNTER_X_POSITION, KILL_COUNTER_Y_POSITION);
		this.pause = new Pause(this, PAUSE_X_POSITION, PAUSE_Y_POSITION); 
		this.level = level;
		this.gameLoop = gameLoop;
		loadInGameMenu();
	}
	
	private void loadInGameMenu() {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/InGameMenu.fxml"));
	    try {
	        inGameMenu = loader.load();
	        inGameMenuController = loader.getController();
	        if (inGameMenuController != null) {
	            inGameMenuController.initialize(level, gameLoop, this);
	        } else {
	            System.err.println("Failed to load inGameMenuController");
	        }
	    } catch (IOException e) {
	        System.err.println("Error loading InGameMenu: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	
	public void addNode(javafx.scene.Node node) {
		if (!root.getChildren().contains(node))
			root.getChildren().add(node);
	}

	
	public void removeNode(javafx.scene.Node node) {
		if (root.getChildren().contains(node))
			root.getChildren().remove(node);
	}

	protected HeartDisplay getHeartDisplay() {
		return heartDisplay;
	}
	
	public void showHeartDisplay() {
		addNode(heartDisplay.getContainer());
	}

	public void showShield() {
		addNode(shieldImage);
		
	}
	
	public void showInGameMenu(MenuState state) {
		pause.setDisable(true);
		gameLoop.stop();
		inGameMenuController.setMenuState(state);
		addNode(inGameMenu);
	}
	
	public void hideInGameMenu() {
		pause.setDisable(false);
		removeNode(inGameMenu);
	}
	
	public void showPause() {
		addNode(pause);
	}
	
	public void showKillCount() {
		addNode(killCounter);

	}
	
	public void showTitle() {
		addNode(levelTitle);
	}
	
	public void showBossHealthBar() {
		addNode(bossHealthBar);
	}
	

}
