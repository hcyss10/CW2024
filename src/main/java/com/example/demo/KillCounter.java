package com.example.demo;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;

public class KillCounter extends Label{
	

	public KillCounter(UserPlane user, int killsToAdvance, int xPosition, int yPosition) {
		super("Kills: 0/" + killsToAdvance);
		this.textProperty().bind(
			    Bindings.createStringBinding(
			        () -> "Kills: " + user.numberOfKills.get() + "/" + killsToAdvance,
			        user.numberOfKills
			    )
			);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setPrefSize(300, 10);
		
	}

}
