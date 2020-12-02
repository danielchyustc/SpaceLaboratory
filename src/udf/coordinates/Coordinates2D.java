/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.coordinates;

import java.util.ArrayList;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import udf.function.SmoothFunction;
import udf.function.fixed.XFixedFunction2D;
import udf.function.simple.BasicFunction;
import udf.mathType.*;
import udf.mathType.Interval;

/**
 *
 * @author DELL
 */
public abstract class Coordinates2D extends Coordinates {
    final public static int CURVE_PARTITION = 1000;
    
    public Coordinates2D(){
        drawCoordinates();
    }
    
    @Override
    protected void drawCoordinates(){
        layoutAxes();
        layoutPoints();
        layoutGrids();
        getChildren().add(dotGroup);
        getChildren().add(curveGroup);
    }
    
    protected abstract void layoutAxes();
    
    protected abstract void layoutPoints();
    
    protected abstract void layoutGrids();
    
    public abstract double[] transform(double u1, double u2);
    
    public RealVector2D transform(RealVector2D vector){
        return new RealVector2D(transform(vector.x(), vector.y()));
    }
    
    public void drawDot(double u1Value, double u2Value, String label){
        drawDot(u1Value, u2Value, 3, label, Color.BLACK, false);
    }
    
    public void drawDot(Color color, double...args){
        drawDot(args[0], args[1], 3, null, color, true);
    }
    
    public void drawDot(RealVector2D vector, Color color){
        drawDot(vector.x(), vector.y(), 3, null, color, true);
    }
    
    public void drawDot(double u1Value, double u2Value, double radius, String label, Color color, Boolean isExtra){
        double[] pos = transform(u1Value, u2Value);
        Circle dot = new Circle(400 + pos[0] * UNIT, 400 - pos[1] * UNIT, radius, color);
        Text dotLabel = new Text(400 + pos[0] * UNIT + 5, 400 - pos[1] * UNIT - 5, label);
        if(isExtra){
            dotGroup.getChildren().add(dot);
            dotCountProperty.setValue(getDotCount() + 1);
        }
        else{
            getChildren().add(dot);
            getChildren().add(dotLabel);
        }
    }
    
    
    public void drawExplicitCurve1(Interval interval, SmoothFunction<RealNumber> function, Color color){
        drawParametricCurve(interval, new BasicFunction(1), function, color);
    }
    
    public void drawExplicitCurve2(Interval interval, SmoothFunction<RealNumber> function, Color color){
        drawParametricCurve(interval, function, new BasicFunction(1), color);
    }
    
    @Override
    public void drawParametricCurve(Interval interval, SmoothFunction<RealNumber>...args){
        drawParametricCurve(interval, args[0], args[1], Color.BLACK);
    }
    
    public void drawParametricCurve(Interval interval, SmoothFunction<RealNumber> u1Function, SmoothFunction<RealNumber> u2Function, Color color){
        Group curveSet = new Group();
        for(int i = 0; i < CURVE_PARTITION; i++){
            RealNumber number1 = new RealNumber(interval.getStartNumber() + interval.measure() / CURVE_PARTITION * i);
            RealNumber number2 = new RealNumber(interval.getStartNumber() + interval.measure() / CURVE_PARTITION * (i + 1));
            double[] pos1 = transform(u1Function.evaluate(number1), u2Function.evaluate(number1));
            double[] pos2 = transform(u1Function.evaluate(number2), u2Function.evaluate(number2));
            Line curve = new Line(400 + pos1[0] * UNIT, 400 - pos1[1] * UNIT, 400 + pos2[0] * UNIT, 400 - pos2[1] * UNIT);
            curve.setStroke(color);
            curveSet.getChildren().add(curve);
        }
        curveGroup.getChildren().add(curveSet);
        curveCountProperty.setValue(getCurveCount() + 1);
    }
    
    
    public void drawImplicitCurve1(Interval xInterval, Interval yInterval, SmoothFunction<RealVector2D> function, Color color){
        Group curveSet = new Group();
        for(int i = 0; i < CURVE_PARTITION; i++){
            double x1 = xInterval.getStartNumber() + xInterval.measure() / CURVE_PARTITION * i;
            double x2 = xInterval.getStartNumber() + xInterval.measure() / CURVE_PARTITION * (i + 1);
            double[] pos1 = transform(x1, MathUdf.root(new XFixedFunction2D(function, x1), yInterval).get(0));
            double[] pos2 = transform(x2, MathUdf.root(new XFixedFunction2D(function, x2), yInterval).get(0));
            Line curve = new Line(400 + pos1[0] * UNIT, 400 - pos1[1] * UNIT, 400 + pos2[0] * UNIT, 400 - pos2[1] * UNIT);
            curve.setStroke(color);
            curveSet.getChildren().add(curve);
        }
        curveGroup.getChildren().add(curveSet);
        curveCountProperty.setValue(getCurveCount() + 1);
    }
    
    public void drawImplicitCurve2(Interval xInterval, Interval yInterval, SmoothFunction<RealVector2D> function, Color color){
        Group curveSet = new Group();
        for(int i = 0; i <= CURVE_PARTITION; i++){
            double x = xInterval.getStartNumber() + xInterval.measure() / CURVE_PARTITION * i;
            ArrayList<Double> yList = MathUdf.root(new XFixedFunction2D(function, x), yInterval);
            for(int j = 0; j < yList.size(); j++){
                double[] pos = transform(x, yList.get(j));
                Circle point = new Circle(400 + pos[0] * UNIT, 400 - pos[1] * UNIT, 1, color);
                curveSet.getChildren().add(point);
            }
        }
        curveGroup.getChildren().add(curveSet);
        curveCountProperty.setValue(getCurveCount() + 1);
    }
    
}
