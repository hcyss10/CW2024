package com.example.demo.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.example.demo.Difficulty;
import com.example.demo.Level;
import com.example.demo.StartMenuController;

public class Controller {

	private final Stage stage;
	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	String css = getClass().getResource("/com/example/demo/styling.css").toExternalForm();

	private StartMenuController startMenuController;
	
	private final IntegerProperty kills = new SimpleIntegerProperty();
	private final IntegerProperty level = new SimpleIntegerProperty();

	public void addKills(int kills) {
		this.kills.set(this.kills.get() + kills);
	}

	public void unlockLevel(int level) {
		if (level > this.level.get())
			this.level.set(level);
	}
	
	public int getLevel() {
		return this.level.get();
	}
	
	public IntegerProperty killsProperty() {
        return kills;
    }
	
	public IntegerProperty levelProperty() {
        return level;
    }


	public Controller(Stage stage) {
		this.stage = stage;
		this.kills.set(0);
		this.level.set(0);
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			goToStartMenu();
	}

	public void goToLevel(int level) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Level myLevel = new Level(new Difficulty(level), SCREEN_WIDTH, SCREEN_HEIGHT, this);
		Scene scene = myLevel.initializeScene();
	    scene.getStylesheets().add(css);
		stage.setScene(scene);
		myLevel.startGame();
	}
	
	public void goToStartMenu(){
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/StartMenu.fxml"));
		try {
			Parent root = loader.load();
	        startMenuController = loader.getController();
	        if (startMenuController != null) {
	        	startMenuController.initialize(this);
	        } else {
	            System.err.println("Failed to load startMenuController");
	        }
			Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		    scene.getStylesheets().add(css);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
