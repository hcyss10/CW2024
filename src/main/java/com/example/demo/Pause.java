package com.example.demo;

import static com.example.demo.InGameMenuController.MenuState.PAUSE;

import javafx.scene.control.Button;

public class Pause extends Button{

	public Pause(LevelView levelView, int xPosition, int yPosition) {
		super();
		this.setOnAction(event -> levelView.showInGameMenu(PAUSE));
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setPrefSize(20, 20);
	}

}
