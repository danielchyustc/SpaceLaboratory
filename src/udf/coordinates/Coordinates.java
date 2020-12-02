/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.coordinates;

import udf.Xform;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import udf.function.SmoothFunction;
import udf.mathType.RealNumber;
import udf.mathType.Interval;

/**
 *
 * @author DELL
 */
public abstract class Coordinates extends Pane {
    final static protected int AXISLENGTH = 20;
    final static public double UNIT = 20;
    
    protected Group curveGroup = new Xform();
    protected Group dotGroup = new Xform();
    protected IntegerProperty dotCountProperty = new SimpleIntegerProperty();
    protected IntegerProperty curveCountProperty = new SimpleIntegerProperty();
    
    public Coordinates(){
        setWidth(800);
        setHeight(800);
        setMaxWidth(800);
        setMaxHeight(800);
    }
    
    public Group getDotGroup(){
        return dotGroup;
    }
    
    public Node getDot(int index){
        return dotGroup.getChildren().get(index);
    }
    
    public int getDotCount(){
        return dotCountProperty.getValue();
    }
    
    public IntegerProperty dotCountProperty(){
        return dotCountProperty;
    }
    
    public Group getCurveSet(){
        return curveGroup;
    }
    
    public Node getCurve(int index){
        return curveGroup.getChildren().get(index);
    }
    
    public int getCurveCount(){
        return curveCountProperty.getValue();
    }
    
    public IntegerProperty curveCountProperty(){
        return curveCountProperty;
    }
    
    protected abstract void drawCoordinates();
    
    public abstract void drawParametricCurve(Interval interval, SmoothFunction<RealNumber>...args);
    
    public void deleteCurve(int index){
        curveGroup.getChildren().remove(index);
        curveCountProperty.setValue(getCurveCount() - 1);
    }
    
    public void deleteDot(int index){
        dotGroup.getChildren().remove(index);
        dotCountProperty.setValue(getDotCount() - 1);
    }
    
    public void clearCurve(){
        curveGroup.getChildren().clear();
        curveCountProperty.setValue(0);
    }
    
    public void clearDot(){
        dotGroup.getChildren().clear();
        dotCountProperty.setValue(0);
    }
    
    public void clear(){
        clearCurve();
        clearDot();
    }
    
}
