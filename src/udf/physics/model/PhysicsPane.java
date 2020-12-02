/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.physics.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import udf.Xform;
import udf.coordinates.*;
import udf.mathType.RealVector3D;
import udf.physics.field.*;
import udf.physics.model.pointModel.PointModel;

/**
 *
 * @author DELL
 */
public class PhysicsPane extends Rect3DPane {
    final private double FRAME = 0.01; 
    final private Font font = Font.font("Times New Roman", 25);
    
    private VBox particleVBox = new VBox(20);
    private ArrayList<PointModel> particleSet = new ArrayList<>();
    private Xform particleXform = new Xform();
    private ArrayList<Xform> particleXformSet = new ArrayList<>();
    private ArrayList<Queue<Xform>> traceSet = new ArrayList<>();
    private Xform traceXform = new Xform();
    private ArrayList<Xform> traceXformSet = new ArrayList<>();
    private ArrayList<DoubleProperty> traceLengthSet = new ArrayList<>();
    private ArrayList<Color> colorSet = new ArrayList<>();
    private ArrayList<Material> materialSet = new ArrayList<>();
    private IntegerProperty particleCountProperty = new SimpleIntegerProperty();
    private Field field = new FreeSpace();
    private ArrayList<IntegerProperty> particleSequence = new ArrayList<>();
    
    public PhysicsPane(){
        getWorld().getChildren().addAll(particleXform, traceXform);
        setAnimation();
        particleVBox.setMaxWidth(200);
    }
    
    public PhysicsPane(Field field){
        this.field = field;
        getWorld().getChildren().addAll(particleXform, traceXform);
        setAnimation();
        particleVBox.setMaxWidth(200);
    }
    
    public void addAll(ObservableList<Color> colorList, Boolean addAng, PointModel...args){
        for(int i = 0; i < args.length; i++)
            addParticle(args[i], colorList.get(i), addAng);
    }
    
    public void addParticle(PointModel particle, Color color, Boolean addAng){
        particle.setField(field);
        Sphere point = new Sphere(10);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        material.setSpecularColor(color);
        point.setMaterial(material);
        Xform pointXform = new Xform();
        pointXform.getChildren().add(point);
        RealVector3D pos = particle.getPosition();
        pointXform.setTx(pos.x() * UNIT);
        pointXform.setTy(pos.y() * UNIT);
        pointXform.setTz(pos.z() * UNIT);
        particleXform.getChildren().add(pointXform);
        particleXformSet.add(pointXform);
        particleSet.add(particle);
        traceSet.add(new LinkedList<>());
        Xform xform = new Xform();
        traceXform.getChildren().add(xform);
        traceXformSet.add(xform);
        traceLengthSet.add(new SimpleDoubleProperty(2.0));
        colorSet.add(color);
        materialSet.add(material);
        HBox hBox = new HBox(10);
        setHBox(hBox, particleSet.size() - 1, addAng);
        particleVBox.getChildren().add(hBox);
        particleCountProperty.setValue(getParticleCount() + 1);
    }
    
    private void setHBox(HBox hBox, int i, Boolean addAng){
        Text particleText = new Text("particle");
        particleText.setFont(font);
        IntegerProperty particleSequenceElem = new SimpleIntegerProperty(i + 1);
        particleSequence.add(particleSequenceElem);
        Text sequenceText = new Text();
        sequenceText.setFont(font);
        sequenceText.textProperty().bind(particleSequenceElem.asString());
        Text qtText = new Text(":");
        qtText.setFont(font);
        Circle circle = new Circle(15);
        circle.setFill(colorSet.get(i));
        String string1 = "mass: " + particleSet.get(i).getMass();
        String string2 = "charge: " + particleSet.get(i).getCharge();
        Button dlButton = new Button("delete");
        dlButton.setStyle("-fx-background-color: #FF3333;");
        dlButton.setOnAction(e -> deleteParticle(particleSequenceElem.get() - 1));
        Text infoText = new Text(string1 + " , " + string2);
        infoText.setFont(font);
        Slider traceSlider = new Slider(0, 5, 2);
        traceSlider.setOrientation(Orientation.HORIZONTAL);
        traceSlider.setBlockIncrement(0.01);
        traceSlider.valueProperty().bindBidirectional(traceLengthSet.get(i));
        Text traceText = new Text();
        traceSlider.valueProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    traceText.setText((double)Math.round(newValue.doubleValue() * 100) / 100 + "");
        });
        Label label = new Label("trace: ", new HBox(10, traceSlider, traceText));
        label.setContentDisplay(ContentDisplay.RIGHT);
        label.setFont(font);
        
        Text engText = new Text("energy:");
        engText.setFont(font);
        Text eng = new Text();
        eng.textProperty().bind(particleSet.get(i).energyProperty().asString());
        
        Text angMomentText = new Text("angularMomentum");
        angMomentText.setFont(font);
        Text angMoment = new Text();
        HBox angMomentHBox = new HBox(angMomentText, angMoment);
        angMomentHBox.setMaxWidth(200);
        angMoment.textProperty().bind(particleSet.get(i).angularMomentumProperty().asString());
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(new HBox(10, particleText, sequenceText, qtText, circle), 
                infoText, label, new HBox(10, engText, eng));
        if(addAng) vBox.getChildren().add(angMomentHBox);
        vBox.getChildren().add(new Text("__________________________________________"));
        hBox.getChildren().addAll(dlButton, vBox);
    }
    
    private void setAnimation(){
            Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000 * FRAME), e -> develop()));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
    }
    
    private void develop(){
        for(int i = 0; i < particleSet.size(); i++){
            RealVector3D position1 = transform(particleSet.get(i).getPosition());
            particleSet.get(i).develop(FRAME);
            RealVector3D position2 = transform(particleSet.get(i).getPosition());
            particleXformSet.get(i).setTx(position2.x() * UNIT);
            particleXformSet.get(i).setTy(position2.y() * UNIT);
            particleXformSet.get(i).setTz(position2.z() * UNIT);
            if(traceLengthSet.get(i).getValue() > 0){
                RealVector3D curveCenterPos = position1.add(position2).divide(2);
                RealVector3D difference = position2.subtract(position1);
                Xform curveXform = new Xform();
                Cylinder curve = new Cylinder(1, difference.norm() * UNIT);
                curve.setMaterial(materialSet.get(i));
                curveXform.getChildren().add(curve);
                curveXform.setTx(curveCenterPos.x() * UNIT);
                curveXform.setTy(curveCenterPos.y() * UNIT);
                curveXform.setTz(curveCenterPos.z() * UNIT);
                curveXform.setRx(Math.atan2(difference.projection(0, 2).norm(), difference.y()) / Math.PI * 180);
                curveXform.setRy(Math.atan2(difference.x(), difference.z()) / Math.PI * 180);
                traceXformSet.get(i).getChildren().add(curveXform);
                traceSet.get(i).add(curveXform);
            }
            while(traceSet.get(i).size() > traceLengthSet.get(i).getValue() / FRAME){
                traceXformSet.get(i).getChildren().remove(traceSet.get(i).peek());
                traceSet.get(i).remove();
            }
        }
    }
    
    public VBox getParticleVBox(){
        return particleVBox;
    }
    
    public void deleteParticle(int index){
        particleVBox.getChildren().remove(index);
        particleSet.remove(index);
        particleXform.getChildren().remove(index);
        particleXformSet.remove(index);
        traceSet.remove(index);
        traceXform.getChildren().remove(index);
        traceXformSet.remove(index);
        traceLengthSet.remove(index);
        colorSet.remove(index);
        materialSet.remove(index);
        particleSequence.remove(index);
        particleCountProperty.setValue(getParticleCount() - 1);
        for(int i = index; i < getParticleCount(); i++)
            particleSequence.get(i).setValue(i + 1);
    }
    
    public int getParticleCount(){
        return particleCountProperty.get();
    }
    
    public IntegerProperty particleCountProperty(){
        return particleCountProperty;
    }
    
}
