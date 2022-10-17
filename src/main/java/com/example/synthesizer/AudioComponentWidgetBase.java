package com.example.synthesizer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class AudioComponentWidgetBase extends Pane {
    public AudioComponent audiocomponent_;
    AnchorPane parent_;
    private HBox baseLayout;
    public String name_;
    private Line line_;
    public Label nameLabel_;
    double mouseStartDragX_, mouseStartDragY_, widgetStartDragX_, widgetStartDragY_;
    public VBox center;
    public boolean connected; // this widget is connected or not
    public AudioComponentWidgetBase(AudioComponent ac, AnchorPane parent, String name) {
        audiocomponent_ = ac;
        parent_ = parent;
        name_ = name;
        connected = false;

        baseLayout = new HBox();
        baseLayout.setStyle("-fx-border-color:black; -fx-border-image-width:8");

        // Right side
        VBox rightSide = new VBox();
        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(5));
        rightSide.setSpacing(5);

        Button closeBtn = new Button("x");
        closeBtn.setOnAction(e -> closeWidget());
        Circle outputCircle = new Circle(10);
        outputCircle.setFill(Color.PURPLE);
        Cable cable = new Cable(parent, this);
        outputCircle.setOnMousePressed(e -> cable.startConnection(e, outputCircle));
        outputCircle.setOnMouseDragged(cable::moveConnection);
        Circle speaker = SynthesizeApplication.speaker_;
        outputCircle.setOnMouseReleased(e -> cable.endConnection(e, speaker));


        rightSide.getChildren().add(closeBtn);
        rightSide.getChildren().add(outputCircle);


        // Center portion of widget
        center = new VBox();
        center.setAlignment(Pos.CENTER);

        nameLabel_ = new Label();
        nameLabel_.setMouseTransparent(true);

        center.setOnMousePressed(e -> startDrag(e));
        center.setOnMouseDragged(e -> handleDrag(e));

        baseLayout.getChildren().add(center);
        baseLayout.getChildren().add(rightSide);
        this.getChildren().add(baseLayout);
        this.setMinWidth(500);
        // widget location
        this.setLayoutX(50);
        this.setLayoutY(100);

        parent_.getChildren().add(this);
    }
    private void startDrag(MouseEvent e) {
        mouseStartDragX_ = e.getSceneX();
        mouseStartDragY_ = e.getSceneY();

        widgetStartDragX_ = this.getLayoutX();
        widgetStartDragY_ = this.getLayoutY();
    }
    private void handleDrag(MouseEvent e) {
        double mouseDelX = e.getSceneX() - mouseStartDragX_;
        double mouseDelY = e.getSceneY() - mouseStartDragY_;
        this.relocate(widgetStartDragX_ + mouseDelX, widgetStartDragY_ + mouseDelY);
    }
    private void closeWidget() {
        parent_.getChildren().remove(this);
        SynthesizeApplication.widget_.remove(this);
        connected = false;
    }
    public AudioComponent getAudioComponent() {
        return audiocomponent_;
    }
}
