package com.example.synthesizer;

import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Cable{

    public Line line_;

    private final AnchorPane parent_;

    AudioComponentWidgetBase widget;
    public Cable(AnchorPane parent, AudioComponentWidgetBase widget) {
        line_ = new Line();
        this.parent_ = parent;
        this.widget = widget;
        line_.setOnMouseClicked(this::removeLine);
    }

    public void removeLine(MouseEvent e) {
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
            SynthesizeApplication.connectedWidgets.remove(widget);
            widget.connected = false;
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

    public void endConnection(MouseEvent e, Circle speaker) {
        Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());
        double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getSceneX(), 2) +
                Math.pow(speakerBounds.getCenterY() - e.getSceneY(), 2));

        if (distance < 10) {
            if (!widget.name_.equals("Volume Control")) {
                SynthesizeApplication.connectedWidgets.add(widget);
            }
            widget.connected = true;

        }
        else {
            parent_.getChildren().remove(line_);
            line_ = null;
        }
    }

}
