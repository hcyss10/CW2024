package com.example.demo;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
public class GameLoop {
    private final Timeline timeline;
    public GameLoop(Runnable action, int intervalMilliseconds) {
        this.timeline = new Timeline(new KeyFrame(Duration.millis(intervalMilliseconds), e -> action.run()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }
    public void start() {
        timeline.play();
    }
    public void stop() {
        timeline.stop();
    }
}