package com.example.synthesizer;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class AudioComponentWidgetBase extends Pane {
    public AudioComponent audiocomponent_;
    AnchorPane parent_;
    public HBox baseLayout;
    public String name_;
    private Line line_;
    public Label nameLabel_;
    double mouseStartDragX_, mouseStartDragY_, widgetStartDragX_, widgetStartDragY_;
    public VBox center;
    public VBox leftSide;

    public Circle inputCircle;
    public Circle outputCircle;
    public boolean connected; // this widget is connected or not


    public ArrayList<AudioComponentWidgetBase> sourceWidget = new ArrayList<>();
    public AudioComponentWidgetBase targetWidget;
    public boolean isSoundSource = true;
    public AudioComponentWidgetBase(AudioComponent ac, AnchorPane parent, String name) {
        audiocomponent_ = ac;
        parent_ = parent;
        name_ = name;
        connected = false;

        baseLayout = new HBox();
        baseLayout.setStyle("-fx-border-color:black; -fx-border-image-width:8; -fx-background-color: #D3D3D3;");

        // Right side
        VBox rightSide = new VBox();
        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(5));
        rightSide.setSpacing(5);

        Button closeBtn = new Button("x");
        closeBtn.setOnAction(e -> closeWidget());
        outputCircle = new Circle(10);
        outputCircle.setFill(Color.PURPLE);

        leftSide = new VBox();
        leftSide.setAlignment(Pos.CENTER);
        inputCircle = new Circle(10);
        inputCircle.setFill(Color.PURPLE);
        leftSide.setPadding(new Insets(4));
        if(hasInput()) {
            leftSide.getChildren().add(inputCircle);
        }
//        Cable cable = new Cable(parent, this);
//        line_ = cable.line_;
        line_ = new Line();
        line_.setOnMouseClicked(e -> removeLine(e));
        outputCircle.setOnMousePressed(e -> startConnection(e, outputCircle));
        outputCircle.setOnMouseDragged(e -> moveConnection(e));
        outputCircle.setOnMouseReleased(e -> endConnection(e));

        rightSide.getChildren().add(closeBtn);
        rightSide.getChildren().add(outputCircle);


        // Center portion of widget
        center = new VBox();
        center.setAlignment(Pos.CENTER);

        nameLabel_ = new Label();
        nameLabel_.setMouseTransparent(true);

        center.setOnMousePressed(e -> startDrag(e));
        center.setOnMouseDragged(e -> handleDrag(e));

        baseLayout.getChildren().add(leftSide);
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
        Bounds parentBounds = parent_.getBoundsInParent();

        if (line_ != null) {
            Bounds bounds = outputCircle.localToScene(outputCircle.getBoundsInLocal());
            line_.setStartX(bounds.getCenterX() - parentBounds.getMinX());
            line_.setStartY(bounds.getCenterY() - parentBounds.getMinY());
        }

        if (this.sourceWidget != null) {
            Bounds bounds = inputCircle.localToScene(inputCircle.getBoundsInLocal());
            for(AudioComponentWidgetBase source: this.sourceWidget) {
                source.line_.setEndX(bounds.getCenterX() - parentBounds.getMinX());
                source.line_.setEndY(bounds.getCenterY() - parentBounds.getMinY());
            }
        }

    }
    private void closeWidget() {
        parent_.getChildren().remove(this);
        SynthesizeApplication.widget_.remove(this);
        connected = false;
    }
    public AudioComponent getAudioComponent() {
        return audiocomponent_;
    }

    public boolean hasInput() {
        return true;
    }

    public void removeLine(MouseEvent e) {
        System.out.println("line click");
        double x = e.getX();
        double y = e.getY();
        if (line_ != null) {
            if (line_.contains(x, y)) {
                parent_.getChildren().remove(line_);
            }
        }
    }

    public void startConnection(MouseEvent e, Circle outputCircle) {
        // only one line can draw
        if (line_ != null) {
            parent_.getChildren().remove(line_);
            SynthesizeApplication.connectedWidgets.remove(this);
            this.connected = false;
        }

        Bounds parentBounds = parent_.getBoundsInParent();
        Bounds bounds = outputCircle.localToScene(outputCircle.getBoundsInLocal());

        line_ = new Line();
        line_.setStrokeWidth(4);
        line_.setStartX(bounds.getCenterX() - parentBounds.getMinX());
        line_.setStartY(bounds.getCenterY() - parentBounds.getMinY());
        line_.setEndX(e.getSceneX());
        line_.setEndY(e.getSceneY());
        parent_.getChildren().add(line_);
    }

    public void moveConnection(MouseEvent e) {
        Bounds parentBounds = parent_.getBoundsInParent();
        line_.setEndX(e.getSceneX() - parentBounds.getMinX());
        line_.setEndY(e.getSceneY() - parentBounds.getMinY());
    }

    public void endConnection(MouseEvent e) {
        Circle speaker = SynthesizeApplication.speaker_;
        Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());

        double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getSceneX(), 2) +
                Math.pow(speakerBounds.getCenterY() - e.getSceneY(), 2));
        if (distance < 10) {
            SynthesizeApplication.connectedWidgets.add(this);
            this.connected = true;
        } else {
            for (AudioComponentWidgetBase singleWidget: SynthesizeApplication.widget_) {
                Bounds inputCircleBounds = singleWidget.inputCircle.localToScene(singleWidget.inputCircle.getBoundsInLocal());
                double distanceInput = Math.sqrt(Math.pow(inputCircleBounds.getCenterX() - e.getSceneX(), 2) +
                        Math.pow(inputCircleBounds.getCenterY() - e.getSceneY(), 2));
                if (distanceInput < 10) {
                    if (!this.name_.equals("Volume Control")) {
                        SynthesizeApplication.connectedWidgets.add(this);
                    }
                    this.targetWidget = singleWidget;
                    singleWidget.sourceWidget.add(this);
                    this.connected = true;
                    return;
//                    }
                }
            }
            if (line_!=null) {
                parent_.getChildren().remove(line_);
                line_ = null;
            }
        }
    }

}
