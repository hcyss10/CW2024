package com.example.demo;

import javafx.scene.Group;
//import javafx.scene.control.Button;

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSISITION = -375;
	private final Group root;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	//private final Button pause;
	//private final PauseMenu pauseMenu;
	

	private static final int SHIELD_X_POSITION = 1000;
	private static final int SHIELD_Y_POSITION = 5;
	private final ShieldImage shieldImage;
	
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		//this.pause = new Button("Pause");  
        //root.getChildren().add(pause);
		//this.pauseMenu = new PauseMenu();
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION);
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
	}
	
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}
	
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}
	
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/*public void showPauseMenu() {
		//stop_timeline
		root.getChildren().add(pauseMenu);
	}*/
	
	public void showShield() {
		if (!root.getChildren().contains(shieldImage)) {
			root.getChildren().add(shieldImage); // Add shield to root if not already added
		}
		shieldImage.showShield();
	}

	public void hideShield() {
		shieldImage.hideShield();
	}

}
