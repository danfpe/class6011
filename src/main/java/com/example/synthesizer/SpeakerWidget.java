package com.example.synthesizer;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SpeakerWidget {

    public static Circle speaker_;
    public SpeakerWidget() {
        speaker_ = new Circle(500,200, 15);
        speaker_.setFill(Color.BLUE);
    }

    public Circle getSpeaker() {
        return speaker_;
    }

}
