package com.example.synthesizer;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SineWaveWidget extends AudioComponentWidgetBase{

    public SineWaveWidget(AudioComponent ac, AnchorPane parent, String name) {
        // invoke parent class constructor
        super(ac, parent, name);
        nameLabel_.setText("Sine Wave 440 Hz");

        Slider slider = new Slider(100,1200,440);
        slider.setOnMouseDragged(e -> handleSlider(e, slider, nameLabel_));
        center.getChildren().add(nameLabel_);
        center.getChildren().add(slider);
    }

    public void handleSlider(MouseEvent e, Slider slider, Label title) {
        int value = (int) slider.getValue();
        title.setText("Sine Wave: " + value + " Hz");
        super.audiocomponent_.setFrequency(value);
    }

    public boolean hasInput() {
        return false;
    }

}
