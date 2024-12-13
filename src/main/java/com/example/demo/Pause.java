package com.example.demo;

import static com.example.demo.InGameMenuController.MenuState.PAUSE;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pause extends Button{

	public Pause(LevelView levelView, int xPosition, int yPosition) {
		super();
		Image image = new Image(getClass().getResource("/com/example/demo/images/pause.png").toExternalForm());
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(20);
		imageView.setFitHeight(20);
		imageView.setPreserveRatio(true);
		this.setGraphic(imageView);
		this.setOnAction(event -> levelView.showInGameMenu(PAUSE));
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setPrefSize(20, 20);
	}

}
