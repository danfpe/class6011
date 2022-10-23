package com.example.synthesizer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.ArrayList;

public class SynthesizeApplication extends Application {
    private AnchorPane maincavas_;
    public static Circle speaker_;
    public static ArrayList<AudioComponentWidgetBase> widget_ = new ArrayList<>();
    public static ArrayList<AudioComponentWidgetBase> connectedWidgets = new ArrayList<>();

    private VolumeWidget volumeWidget;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("My world");


        // right
        VBox rightPanel = new VBox();
        rightPanel.setPadding(new Insets(4));
        rightPanel.setSpacing(10);
        rightPanel.setStyle("-fx-background-color: lightgray");

        Button sineWaveBtn = new Button("Sine Wave");
        Button filterBtn = new Button("Filters");
        Button vfBtn = new Button("VF Sine Wave");
        Button mixerBtn = new Button("Mixer");
        sineWaveBtn.setMinWidth(100);
        filterBtn.setMinWidth(100);
        vfBtn.setMinWidth(100);
        vfBtn.setMinWidth(100);
        mixerBtn.setMinWidth(100);
        rightPanel.getChildren().add(sineWaveBtn);
        rightPanel.getChildren().add(filterBtn);
        rightPanel.getChildren().add(vfBtn);
        rightPanel.getChildren().add(mixerBtn);
        sineWaveBtn.setOnAction(e -> createComponent());
        filterBtn.setOnAction(e -> createFilterComponent());
        vfBtn.setOnAction(e -> createVFSineWaveComponent());
        mixerBtn.setOnAction(e -> createMixerComponent());

        // central pane
        maincavas_ = new AnchorPane();
        maincavas_.setStyle("-fx-background-color: white");
        SpeakerWidget speakerWidget = new SpeakerWidget();
        speaker_ = speakerWidget.getSpeaker();
        maincavas_.getChildren().add(speaker_);

        //bottom panel
        HBox bottomPanel = new HBox();
        Button playBtn = new Button("Play");
        playBtn.setOnAction(e -> playNetwork());
        bottomPanel.getChildren().add(playBtn);

        // Put the panels into the borderPane(root)
        root.setRight(rightPanel);
        root.setCenter(maincavas_);
        root.setBottom(bottomPanel);
        stage.setScene(scene);
        stage.show();
    }
    private void playNetwork() {
        if (connectedWidgets.size() == 0) {
            return;
        }
        for (AudioComponentWidgetBase acw : widget_){
            if (!acw.connected) {
                return;
            }
        }
        try {
            Clip c = AudioSystem.getClip();
            AudioListener listener = new AudioListener(c);
            Mixers mixer = new Mixers();
            for (AudioComponentWidgetBase acw : connectedWidgets){
                System.out.println(acw.name_);
                AudioComponent ac = acw.getAudioComponent();
                if (acw.isSoundSource) {
                    mixer.connectInput(ac);
                }
            }
            byte [] data = mixer.getClip().getData();
            
            if (volumeWidget != null) {
                if (volumeWidget.connected)
                {
                    Filters filter = new Filters(volumeWidget.sliderValue);
                    System.out.println("volume " + volumeWidget.sliderValue);
                    AudioClip mixClip = mixer.getClip();
                    AudioClip filteredSound = filter.filterSound(mixClip);
                    data = filteredSound.getData();
                }
            }

            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            c.open(format, data, 0, data.length);
            c.start();
//            c.loop( 2 );
            c.addLineListener(listener);

        }  catch (LineUnavailableException e) {
            throw new RuntimeException(e);
            }
    }

    private void createComponent() {
        AudioComponent sinewave = new SineWave(440);
        SineWaveWidget acw = new SineWaveWidget(sinewave, maincavas_, "sine wave");
        widget_.add(acw);
    }

    private void createFilterComponent() {
        AudioComponent sinewave = new SineWave(440);
        volumeWidget = new VolumeWidget(sinewave, maincavas_, "Volume Control");
        widget_.add(volumeWidget);
    }
    private void createMixerComponent() {
        AudioComponent sinewave = new SineWave(440);
        MixerWidget mixerWidget = new MixerWidget(sinewave, maincavas_, "Mixer");
        widget_.add(mixerWidget);
    }
    private void createVFSineWaveComponent() {
        AudioComponent linearRamp = new LinearRamp(50, 2000);
        AudioComponent vfgen = new VFSineWave();
        vfgen.connectInput(linearRamp);
        VFSineWaveWidget acw = new VFSineWaveWidget(vfgen, maincavas_, "VF Sine Wave");
        widget_.add(acw);
    }

    public static void main(String[] args) {
        launch();
    }
}