package com.example.demo;

import javafx.scene.control.Label;

public class LevelTitle extends Label{
	public LevelTitle(int level, int xPosition, int yPosition) {
		super("level: " + level);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setPrefSize(300, 10);
	}
}
