package com.example.demo;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
	protected final Label title;
	protected final Label killCounter;
	protected final ProgressBar bossHealthBar;
	//private final Button pause;
	//private final PauseMenu pauseMenu;
	
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION);
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		this.bossHealthBar = new ProgressBar(1);
		this.title = new Label();
		this.killCounter = new Label();
		//this.pause = new Button("Pause");  
        //root.getChildren().add(pause);
		//this.pauseMenu = new PauseMenu();
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
	
	public void showKillCount(int killsToAdvance) {
		killCounter.setText("Kills: 0/" + killsToAdvance);
		killCounter.setLayoutX(500);
		killCounter.setLayoutY(20);
		killCounter.setPrefSize(300, 10);
		killCounter.setVisible(true);
		root.getChildren().add(killCounter);
	}
	
	public void showTitle(int level) {
		title.setText("Level: " + level);
		title.setLayoutX(500);
		title.setLayoutY(5);
		title.setPrefSize(300, 10);
		title.setVisible(true);
		root.getChildren().add(title);
	}
	
	public void showBossHealthBar() {
		bossHealthBar.setLayoutX(500);
		bossHealthBar.setLayoutY(700);
		bossHealthBar.setPrefSize(300, 30);
		bossHealthBar.setVisible(true);
		root.getChildren().add(bossHealthBar);
	}
	
	/*public void showPauseMenu() {
		//stop_timeline
		root.getChildren().add(pauseMenu);
	}*/
	

}
