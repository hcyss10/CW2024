package com.example.demo.controller;

//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;

import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import com.example.demo.Difficulty;
import com.example.demo.Level;

public class Controller /*implements PropertyChangeListener*/ {

	private final Stage stage;
	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			goToLevel(0);
	}

	public void goToLevel(int level) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Level myLevel = new Level(new Difficulty(level), SCREEN_HEIGHT, SCREEN_WIDTH, this);
		//myLevel.addPropertyChangeListener(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();
	}
	
	public void goToMenu() {
		//load menu fxml
	}

	/*@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try {
			goToLevel((int) evt.getNewValue());
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}*/

}
