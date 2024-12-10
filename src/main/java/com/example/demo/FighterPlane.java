package com.example.demo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class FighterPlane extends ActiveActorDestructible {

	private final IntegerProperty health = new SimpleIntegerProperty();

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health.set(health);
	}

	public abstract ActiveActorDestructible fireProjectile();
	
	@Override
	public void takeDamage() {
		int newHealth = getHealth() - 1;
		if (newHealth <= 0) {
			this.destroy();
		}
		health.set(newHealth);
	}

	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/*private boolean healthAtZero() {
		return health.get() == 0;
	}*/

	public int getHealth() {
		return health.get();
	}
	
	public IntegerProperty healthProperty() {
        return health;
    }
		
}
