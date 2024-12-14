package com.example.demo;

import javafx.scene.text.Text;

public class LevelTitle extends Text{
	public LevelTitle(int level, int xPosition, int yPosition) {
		super("level: " + level);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
        this.getStyleClass().add("Text");
	}
}
