package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HeartDisplay {
	
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 50;
	private static final int INDEX_OF_FIRST_ITEM = 0;
	private HBox container;
	private double containerXPosition;
	private double containerYPosition;
	private int hearts = 0;
	
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		initializeContainer();
		setHearts(heartsToDisplay);
	}
	
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);		
	}
	
	public void setHearts(int changed) {
		if (changed > hearts) {
			for(int i = hearts; i < changed; i++) {
				ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));

				heart.setFitHeight(HEART_HEIGHT);
				heart.setPreserveRatio(true);
				container.getChildren().add(heart);
			}
		}else if(changed < hearts) {
			for(int i = hearts; i > changed; i--) {
				if (!container.getChildren().isEmpty())
					container.getChildren().remove(INDEX_OF_FIRST_ITEM);
				
			}
		}
		hearts = changed;
	}
	
	public HBox getContainer() {
		return container;
	}

}
