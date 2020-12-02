/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.coordinates;

/**
 *
 * @author DELL
 */
import javafx.beans.binding.DoubleBinding;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class Polar2DPane extends Coordinates2D {
    
    public Polar2DPane(){
    }
    
    @Override
    public double[] transform(double u1, double u2){
        double[] result = new double[2];
        result[0] = u1 * Math.cos(u2);
        result[1] = u1 * Math.sin(u2);
        return result;
    }
    
    
    @Override    
    protected void layoutAxes(){
        drawRAxis(0, Color.BLACK);
        drawThetaAxis(20, Color.BLACK);
    }
    
    @Override
    protected void layoutPoints(){
        drawDot(0, 0, 5, "O", Color.BLACK, false);
        for(int i = 1; i <= 4; i++){
            drawDot(i * 5, 0, 3, i * 5 + "", Color.BLACK, false);
        }
    }
    
    @Override
    protected void layoutGrids(){
        for(int i = 1; i < 24; i++) drawRAxis(Math.PI / 12 * i, Color.LIGHTGRAY);
        for(int i = 1; i < 5; i++) drawThetaAxis(5 * i, Color.LIGHTGRAY);
    }
    
    private void drawRAxis(double theta, Color color){
        Line[] line = new Line[AXISLENGTH];
        for(int i = 0; i < AXISLENGTH; i++){
            double[] pos1 = transform(i, theta);
            double[] pos2 = transform(i + 1, theta);
            line[i] = new Line(400 + pos1[0] * UNIT, 400 - pos1[1] * UNIT, 400 + pos2[0] * UNIT, 400 - pos2[1] * UNIT);
            line[i].setStroke(color);
        }
        getChildren().addAll(line);
    }
    
    private void drawThetaAxis(double r, Color color){
        Line[] line = new Line[60];
        for(int i = 0; i < 60; i++){
            double[] pos1 = transform(r, Math.PI / 30 * i);
            double[] pos2 = transform(r, Math.PI / 30 * (i + 1));
            line[i] = new Line(400 + pos1[0] * UNIT, 400 - pos1[1] * UNIT, 400 + pos2[0] * UNIT, 400 - pos2[1] * UNIT);
            line[i].setStroke(color);
        }
        getChildren().addAll(line);
    }
    
}
