package com.example.demo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.binding.NumberExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;

public class Level {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
    private PropertyChangeSupport support;
	
	private int currentLevel;
	private int maxEnemies;
	private int killsToAdvance;
	private double enemySpawnProbability;
	private boolean isBossBattle;

	private final Group root;
	private final GameLoop gameLoop;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	
	private LevelView levelView;

	public Level(Difficulty difficulty, double screenHeight, double screenWidth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.gameLoop = new GameLoop(this::updateScene, MILLISECOND_DELAY);
		this.user = new UserPlane(PLAYER_INITIAL_HEALTH);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
        support = new PropertyChangeSupport(this);

		this.background = new ImageView(new Image(getClass().getResource(difficulty.getBackground()).toExternalForm()));
		this.currentLevel = difficulty.getLevel();
		this.maxEnemies = difficulty.getMaxEnemies();
		this.killsToAdvance = difficulty.getKillsToAdvance();
		this.enemySpawnProbability = difficulty.getEnemySpawnProbability();
		this.isBossBattle = difficulty.isBossBattle();
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		friendlyUnits.add(user);
		boss = new Boss();
	}
	
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= killsToAdvance;
	}

	protected void spawnEnemyUnits() {
		int enemiesToBeSpawned = this.maxEnemies - enemyUnits.size();
		for (int i = 0; i < enemiesToBeSpawned; i++) {
			double x = Math.random();
			if (isBossBattle) {
				addEnemyUnit(boss);
			}else if (x < enemySpawnProbability) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		levelView.showTitle(currentLevel + 1);
		levelView.showKillCount(killsToAdvance);
		if (isBossBattle) {
			levelView.showShield();
			levelView.showBossHealthBar();
		}
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		gameLoop.start();
		levelView.shieldImage.visibleProperty().bind(boss.shieldedProperty());
		levelView.bossHealthBar.progressProperty().bind((DoubleExpression) boss.healthProperty().divide(100.0));
		levelView.killCounter.textProperty().bind(
			    Bindings.createStringBinding(
			        () -> "Kills: " + user.numberOfKills.get() + "/" + killsToAdvance,
			        user.numberOfKills
			    )
			);
		user.healthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (user.isDestroyed())
					loseGame();
				levelView.heartDisplay.setHearts(newValue.intValue());
			}
		});
		
	}

	public void goToNextLevel() {
		gameLoop.stop();
		support.firePropertyChange("currentLevel", currentLevel, currentLevel + 1);
	}
	
	public void updateBackground() {
		if(background.getLayoutX() <= -screenWidth)
			background.setLayoutX(0);
		background.setLayoutX(background.getLayoutX() + background.getTranslateX());
	}

	private void updateScene() {
		updateBackground();
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
	}

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setTranslateX(-3);
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.SPACE) fireProjectile();
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
			}
		});
		root.getChildren().add(background);
	}

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handleUserProjectileCollisions() {
		user.incrementKillCount(handleCollisions(userProjectiles, enemyUnits));
		if (userHasReachedKillTarget())
			goToNextLevel();
		
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	private int handleCollisions(List<ActiveActorDestructible> actors1,
			List<ActiveActorDestructible> actors2) {
		int hits = 0;
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
					if(actor.isDestroyed())
						hits++;
				}
			}
		}
		return hits;
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}	

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}

	protected void loseGame() {
		gameLoop.stop();
		levelView.showGameOverImage();
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}
	
}
