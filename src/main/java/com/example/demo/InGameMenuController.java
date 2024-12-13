package com.example.demo;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class InGameMenuController {
    private GameLoop gameLoop;
    private Level level;
    private LevelView levelView;

    public void initialize(Level level, GameLoop gameLoop, LevelView levelView) {
        this.level = level;
        this.gameLoop = gameLoop;
        this.levelView = levelView;
    }
    
    @FXML
    private Text menuTitle;
    
    @FXML
    private BorderPane resumeButtonPane;
    
    @FXML
    private BorderPane nextButtonPane;
    
    @FXML
    private BorderPane retryButtonPane;
    
    @FXML
    private BorderPane exitButtonPane;


    @FXML
    private void resume(ActionEvent event) {
    	gameLoop.start();
    	levelView.hideInGameMenu();
    }

    @FXML
    private void retry(ActionEvent event) {
    	level.goToLevel(level.getCurrentLevel());
    }

    @FXML
    private void exit(ActionEvent event){
    	level.exit();
    }

    @FXML
    private void next(ActionEvent event) {
    	level.goToLevel(level.getCurrentLevel() + 1);
    }
    
    public enum MenuState {
    	PAUSE,
    	WIN,
    	DEFEAT
    }
    
    public void setMenuState(MenuState state) {
        switch (state) {
            case PAUSE:
                menuTitle.setText("Paused");

                setPaneState(resumeButtonPane, true);
                setPaneState(retryButtonPane, true);
                setPaneState(nextButtonPane, false);
                setPaneState(exitButtonPane, true);
                break;

            case WIN:
                menuTitle.setText("You Win!");

                setPaneState(resumeButtonPane, false);
                setPaneState(retryButtonPane, true);
                setPaneState(nextButtonPane, true);
                setPaneState(exitButtonPane, true);
                break;

            case DEFEAT:
                menuTitle.setText("Game Over");

                setPaneState(resumeButtonPane, false);
                setPaneState(retryButtonPane, true);
                setPaneState(nextButtonPane, false);
                setPaneState(exitButtonPane, true);
                break;
        }
    }

    private void setPaneState(BorderPane pane, boolean isVisible) {
        pane.setVisible(isVisible);
        pane.setManaged(isVisible);
    }
}
