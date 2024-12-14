package com.example.demo;

import javafx.scene.control.ProgressBar;

public class BossHealthBar extends ProgressBar{

	public BossHealthBar(Boss boss, int xPosition, int yPosition) {
		super(1);
	    this.progressProperty().bind(
	            boss.healthProperty().divide((double) boss.getInitialHealth())
	        );
	    this.setLayoutX(xPosition);
	    this.setLayoutY(yPosition);
	    this.setPrefSize(300, 30);
        this.getStyleClass().add("boss-health-bar");
	}

}
