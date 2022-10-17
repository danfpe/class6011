package com.example.synthesizer;

import javafx.scene.layout.AnchorPane;

public class VFSineWaveWidget extends AudioComponentWidgetBase{
    public VFSineWaveWidget(AudioComponent ac, AnchorPane parent, String name) {
        super(ac, parent, name);
        nameLabel_.setText("VF Sine Wave");
        center.getChildren().add(nameLabel_);

        this.setLayoutX(50);
        this.setLayoutY(300);
    }
}
