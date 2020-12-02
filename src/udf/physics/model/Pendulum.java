/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.physics.model;

import java.util.LinkedList;
import java.util.Queue;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import udf.coordinates.Coordinates;
import udf.coordinates.Coordinates2D;
import udf.coordinates.Rect2DPane;
import udf.function.StringToFunction;
import udf.mathType.RealVector3D;
import udf.physics.field.Field;
import udf.physics.field.GravField;
import udf.physics.field.SpringField;
import udf.physics.field.SumField;
import udf.physics.model.pointModel.MassPoint;

/**
 *
 * @author DELL
 */
public class Pendulum {
    final private static double K = 2.0;
    final private static double ORG_LENGTH = 5;
    final private static double FRAME = 0.01;
    final private Font font = Font.font("Times New Roman", 25);
    
    private VBox pendulumVBox = new VBox(20);
    private Coordinates2D frame = new Rect2DPane();
    private MassPoint mass = new MassPoint(1);
    private Circle ball = new Circle(15);
    private Line rope = new Line();
    
    private Queue<Line> trace = new LinkedList();
    private Group traceGroup = new Group();
    private DoubleProperty traceLength = new SimpleDoubleProperty(5);
    
    private Timeline animation;
    private Boolean changed = false;
    
    public Pendulum(MassPoint mass){
        this.mass = mass;
        this.mass.setField(new SumField(new GravField(), new SpringField(K, ORG_LENGTH)));
        layout();
        setAnimation();
    }
    
    public Pendulum(MassPoint mass, Field field){
        this.mass = mass;
        this.mass.setField(new SumField(new GravField(), field));
        layout();
        setAnimation();
    }
    
    public Pendulum(MassPoint mass, Field field, String limit){
        this.mass = mass;
        this.mass.setField(new SumField(new GravField(), field));
        this.mass.setLimit(new StringToFunction<>(limit, "x", "y", "z"));
        layout();
        setAnimation();
    }
    
    public Coordinates2D getFrame(){
        return frame;
    }
    
    public void setFrame(Coordinates2D frame){
        this.frame = frame;
        layout();
    }
    
    private void layout(){
        ball.setCenterX(frame.getWidth() / 2 + mass.getPosition().x() * Coordinates.UNIT);
        ball.setCenterY(frame.getHeight() / 2 - mass.getPosition().y() * Coordinates.UNIT);
        ball.setFill(Color.RED);
        ball.setOnMouseDragged( me ->{
                ball.setCenterX(me.getSceneX());
                ball.setCenterY(me.getSceneY());
                mass.setPosition(new RealVector3D(
                        (ball.getCenterX() - frame.getWidth() / 2) / Coordinates.UNIT, 
                        (-ball.getCenterY() + frame.getHeight() / 2) / Coordinates.UNIT, 0));
                mass.setVelocity(RealVector3D.ZERO);
                clearTrace(); changed = true;
        });
        rope.startXProperty().bind(ball.centerXProperty());
        rope.startYProperty().bind(ball.centerYProperty());
        rope.endXProperty().bind(frame.widthProperty().divide(2));
        rope.endYProperty().bind(frame.heightProperty().divide(2));
        rope.setStrokeWidth(5);
        Button pauseButton = new Button("pause");
        pauseButton.setLayoutX(680);
        pauseButton.setLayoutY(20);
        pauseButton.setPrefSize(100, 40);
        pauseButton.setOnAction(e -> {
            if(pauseButton.getText().equals("pause")){
                animation.pause(); pauseButton.setText("play");
            }
            else{
                if(changed) changed = false;
                animation.play(); 
                pauseButton.setText("pause");
            }
        });
        frame.getChildren().addAll(rope, ball, traceGroup, pauseButton);
        HBox hBox = new HBox(10);
        setHBox(hBox);
        pendulumVBox.getChildren().add(hBox);
    }
    
    private void setHBox(HBox hBox){
        Text particleText = new Text("pendulum:");
        particleText.setFont(font);
        Circle circle = new Circle(15);
        circle.setFill(Color.RED);
        Text infoText = new Text("mass: " + mass.getMass());
        infoText.setFont(font);
        Slider traceSlider = new Slider(0, 20, 5);
        traceSlider.setOrientation(Orientation.HORIZONTAL);
        traceSlider.setBlockIncrement(0.01);
        traceSlider.valueProperty().bindBidirectional(traceLength);
        Text traceText = new Text();
        traceSlider.valueProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    traceText.setText((double)Math.round(newValue.doubleValue() * 100) / 100 + "");
        });
        Label label = new Label("trace: ", new HBox(10, traceSlider, traceText));
        label.setContentDisplay(ContentDisplay.RIGHT);
        label.setFont(font);
        
        Text posText = new Text("Position:");
        posText.setFont(font);
        Text pos = new Text();
        pos.textProperty().bind(mass.positionProperty().asString());
        
        Text vText = new Text("Velocity:");
        vText.setFont(font);
        Text v = new Text();
        v.textProperty().bind(mass.velocityProperty().asString());
        
        Text momentText = new Text("Momentum:");
        momentText.setFont(font);
        Text moment = new Text();
        moment.textProperty().bind(mass.momentumProperty().asString());
        
        Text angMomentText = new Text("Angular Momentum:");
        angMomentText.setFont(font);
        Text angMoment = new Text();
        angMoment.textProperty().bind(mass.angularMomentumProperty().asString());
        
        Text kEngText = new Text("Kinetic Energy:");
        kEngText.setFont(font);
        Text kEng = new Text();
        kEng.textProperty().bind(mass.kineticEnergyProperty().asString());
        
        Text pEngText = new Text("Potential Energy:");
        pEngText.setFont(font);
        Text pEng = new Text();
        pEng.textProperty().bind(mass.potentialEnergyProperty().asString());
        
        Text engText = new Text("Energy:");
        engText.setFont(font);
        Text eng = new Text();
        eng.textProperty().bind(mass.energyProperty().asString());
        hBox.getChildren().addAll(new VBox(10, new HBox(10, particleText, circle), 
                infoText, label, new HBox(posText, pos), new HBox(vText, v), 
                new HBox(momentText, moment), new HBox(angMomentText, angMoment), 
                new HBox(10, kEngText, kEng), new HBox(10, pEngText, pEng), new HBox(10, engText, eng)));
    }
    
    
    public Timeline getAnimation(){
        return animation;
    }
    
    public VBox getPendulumVBox(){
        return pendulumVBox;
    }
    
    public MassPoint getPendulum(){
        return mass;
    }
    
    public void clearTrace(){
        traceGroup.getChildren().clear();
        trace.clear();
    }
    
    private void setAnimation(){
        animation = new Timeline(new KeyFrame(Duration.millis(1000 * FRAME), e -> develop()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }
    
    
    private void develop(){
        RealVector3D pos1 = mass.getPosition();
        mass.develop(FRAME);
        RealVector3D pos2 = mass.getPosition();
        ball.setCenterX(frame.getWidth() / 2 + pos2.x() * Coordinates.UNIT);
        ball.setCenterY(frame.getHeight() / 2 - pos2.y() * Coordinates.UNIT);
        if(traceLength.get() > 0){
            Line curve = new Line(
                    frame.getWidth() / 2 + pos1.x() * Coordinates.UNIT,
                    frame.getHeight() / 2 - pos1.y() * Coordinates.UNIT,
                    frame.getWidth() / 2 + pos2.x() * Coordinates.UNIT,
                    frame.getHeight() / 2 - pos2.y() * Coordinates.UNIT);
            curve.setStroke(Color.BLUE);
            traceGroup.getChildren().add(curve);
            trace.add(curve);
        }
        while(trace.size() > traceLength.get() / FRAME){
            traceGroup.getChildren().remove(trace.peek());
            trace.remove();
        }
    }
}
