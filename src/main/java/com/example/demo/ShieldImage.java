package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {
	
	private static final String IMAGE_LOCATION_NAME = "/com/example/demo/images/shield.png";
	private static final int SHIELD_SIZE = 50;
	
	public ShieldImage(Boss boss, double xPosition, double yPosition) {
		this.visibleProperty().bind(boss.shieldedProperty());
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION_NAME).toExternalForm()));
		this.setFitWidth(SHIELD_SIZE);
		this.setPreserveRatio(true);
	}

}
