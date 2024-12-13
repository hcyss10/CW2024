package com.example.demo;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.controller.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import static com.example.demo.InGameMenuController.MenuState.*;


public class Level {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	
	private int currentLevel;
	private int maxEnemies;
	private int killsToAdvance;
	private double enemySpawnProbability;
	private boolean isBossBattle;

	private final Group root;
	protected final GameLoop gameLoop;
	protected final Controller myController;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	
	private LevelView levelView;

	public Level(Difficulty difficulty, double screenHeight, double screenWidth, Controller myController) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.gameLoop = new GameLoop(this::updateScene, MILLISECOND_DELAY);
		this.user = new UserPlane(PLAYER_INITIAL_HEALTH);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
        //support = new PropertyChangeSupport(this);
		this.myController = myController;

		this.background = new ImageView(new Image(getClass().getResource(difficulty.getBackground()).toExternalForm()));
		this.currentLevel = difficulty.getLevel();
		this.maxEnemies = difficulty.getMaxEnemies();
		this.killsToAdvance = difficulty.getKillsToAdvance();
		this.enemySpawnProbability = difficulty.getEnemySpawnProbability();
		this.isBossBattle = difficulty.isBossBattle();
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		friendlyUnits.add(user);
		boss = new Boss(difficulty);
		this.levelView = instantiateLevelView();
	}

	public int getKillsToAdvance() {
		return killsToAdvance;
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
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, this, gameLoop);
	}

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		levelView.showPause();
		levelView.showTitle();
		levelView.showKillCount();
		if (isBossBattle) {
			levelView.showShield();
			levelView.showBossHealthBar();
		}
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		gameLoop.start();
		user.healthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (user.isDestroyed())
					loseGame();
				levelView.getHeartDisplay().setHearts(newValue.intValue());
			}
		});
		
	}
	public void goToLevel(int level) {
		try {
			myController.goToLevel(level);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public void exit() {
		myController.goToMenu();
	}
	
	public int getCurrentLevel() {
		return currentLevel;
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
		generateUserFire();
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
				if (kc == KeyCode.UP) user.setMovingUp(true);
				if (kc == KeyCode.DOWN) user.setMovingDown(true);
				if (kc == KeyCode.RIGHT) user.setMovingRight(true);
				if (kc == KeyCode.LEFT) user.setMovingLeft(true);
				if (kc == KeyCode.SPACE) user.setFiring(true);
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.setMovingUp(false);
				if (kc == KeyCode.DOWN) user.setMovingDown(false);
				if (kc == KeyCode.RIGHT) user.setMovingRight(false);
				if (kc == KeyCode.LEFT) user.setMovingLeft(false);
				if (kc == KeyCode.SPACE) user.setFiring(false);
			}
		});
		root.getChildren().add(background);
	}

	private void generateUserFire() {
		friendlyUnits.forEach(user -> spawnUserProjectile(((FighterPlane) user).fireProjectile()));
	}

	private void spawnUserProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			userProjectiles.add(projectile);
		}
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
			winGame();
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
		levelView.showInGameMenu(DEFEAT);
	}

	protected void winGame() {
		levelView.showInGameMenu(WIN);
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Boss getBoss() {
		return boss;
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
