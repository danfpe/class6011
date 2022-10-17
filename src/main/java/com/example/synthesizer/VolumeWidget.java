package com.example.synthesizer;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

import javafx.scene.input.MouseEvent;

import java.text.DecimalFormat;

public class VolumeWidget extends AudioComponentWidgetBase{
    double sliderValue;
    public VolumeWidget(AudioComponent ac, AnchorPane parent, String name){
        super(ac, parent, name);
        sliderValue = 1.0;
        nameLabel_.setText("Volume: 1.0");
        Slider slider = new Slider(0.0,5.5,1.0);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(0.5);
        slider.setBlockIncrement(0.1);
        slider.setOnMouseDragged(e -> handleSlider(e, slider, nameLabel_));
        center.getChildren().add(nameLabel_);
        center.getChildren().add(slider);
        this.setLayoutX(50);
        this.setLayoutY(200);
    }

    public void handleSlider(MouseEvent e, Slider slider, Label title) {
        sliderValue = Math.round(slider.getValue() * 10) / 10.0;
        title.setText("Volume: " + sliderValue);
    }

}
