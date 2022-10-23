package com.example.synthesizer;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.text.DecimalFormat;

public class MixerWidget extends AudioComponentWidgetBase{
    double sliderValue;
    public MixerWidget(AudioComponent ac, AnchorPane parent, String name){
        super(ac, parent, name);
        // left side

        sliderValue = 1.0;
        nameLabel_.setText("   Sound Mixer  ");
        center.getChildren().add(nameLabel_);
        this.setLayoutX(400);
        this.setLayoutY(300);
        isSoundSource = false;
    }


}

