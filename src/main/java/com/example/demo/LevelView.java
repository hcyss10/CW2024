package com.example.demo;

import javafx.scene.Group;
//import javafx.scene.control.Button;

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 5;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSISITION = -375;
	private static final int SHIELD_X_POSITION = 1245;
	private static final int SHIELD_Y_POSITION = 5;
	private final Group root;
	private final GameOverImage gameOverImage;
	protected final HeartDisplay heartDisplay;
	protected final ShieldImage shieldImage;
	//private final Button pause;
	//private final PauseMenu pauseMenu;
	
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

	public void showShield() {
		root.getChildren().add(shieldImage);
	}
	
	/*public void showPauseMenu() {
		//stop_timeline
		root.getChildren().add(pauseMenu);
	}*/
	

}
