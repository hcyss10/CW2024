package com.example.demo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = 715;
	private static final double X_UPPER_BOUND = 1170;
	private static final double X_LOWER_BOUND = 0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 35;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION_OFFSET = 70;
	private static final int PROJECTILE_Y_POSITION_OFFSET = -45;
	private boolean movingUp;
	private boolean movingDown;
	private boolean movingRight;
	private boolean movingLeft;
	private boolean firing;
	private long lastFiredTime = 0;
	private static final long COOLDOWN_TIME = 200;
	protected final IntegerProperty numberOfKills = new SimpleIntegerProperty(0);

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		movingUp = false;
		movingDown = false;
		movingRight = false;
		movingLeft = false;
	}
	
	@Override
	public void updatePosition() {
		if (movingUp && !movingDown) {
		    double newPosition = getLayoutY() + getTranslateY() - VERTICAL_VELOCITY;
		    if (newPosition >= Y_UPPER_BOUND) {
		        this.moveVertically(-VERTICAL_VELOCITY);
		    }
		} else if (!movingUp && movingDown) {
		    double newPosition = getLayoutY() + getTranslateY() + VERTICAL_VELOCITY;
		    if (newPosition <= Y_LOWER_BOUND) {
		        this.moveVertically(VERTICAL_VELOCITY);
		    }
		}
		if (movingRight && !movingLeft) {
		    double newPosition = getLayoutX() + getTranslateX() + HORIZONTAL_VELOCITY;
		    if (newPosition <= X_UPPER_BOUND) {
		        this.moveHorizontally(HORIZONTAL_VELOCITY);
		    }
		} else if (!movingRight && movingLeft) {
		    double newPosition = getLayoutX() + getTranslateX() - HORIZONTAL_VELOCITY;
		    if (newPosition >= X_LOWER_BOUND) {
		        this.moveHorizontally(-HORIZONTAL_VELOCITY);
		    }
		}
	}
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFiredTime > COOLDOWN_TIME && firing) {
            lastFiredTime = currentTime;
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new UserProjectile(projectileXPosition, projectileYPostion);
        }
		return null;
	}

	public void setMovingUp(boolean moveUp) {
		this.movingUp = moveUp;
	}

	public void setMovingDown(boolean moveDown) {
		this.movingDown = moveDown;
	}

	public void setMovingRight(boolean moveRight) {
		this.movingRight = moveRight;
	}

	public void setMovingLeft(boolean moveLeft) {
		this.movingLeft = moveLeft;
	}
	public void setFiring(boolean fire) {
		this.firing = fire;
	}

	public int getNumberOfKills() {
		return numberOfKills.get();
	}

	public void incrementKillCount(int hits) {
		numberOfKills.set(getNumberOfKills() + hits);
	}
	
	public IntegerProperty numberOfKillsProperty() {
	    return numberOfKills;
	}

}
