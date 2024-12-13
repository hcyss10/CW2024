package com.example.demo;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.controller.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class StartMenuController {
	private Controller myController;
	

    @FXML
    private Text killsLabel;
    @FXML
    private Text levelLabel;
    


	public void initialize(Controller myController) {
        this.myController = myController;
        killsLabel.textProperty().bind(myController.killsProperty().asString());
        levelLabel.textProperty().bind(myController.levelProperty().asString());
    }
	@FXML
    private void playButton(ActionEvent event){
    	try {
			myController.goToLevel(myController.getLevel());
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
    }

}
