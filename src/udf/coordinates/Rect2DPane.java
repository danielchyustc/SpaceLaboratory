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


public class Rect2DPane extends Coordinates2D {
    
    public Rect2DPane(){
    }
    
    @Override
    public double[] transform(double u1, double u2){
        double[] result = new double[2];
        result[0] = u1;
        result[1] = u2;
        return result;
    }
    
    @Override
    protected void layoutAxes(){
        drawXAxis(0, Color.BLACK);
        drawYAxis(0, Color.BLACK);
    }
    
    @Override
    protected void layoutPoints(){
        drawDot(0, 0, 5, "O", Color.BLACK, false);
        for(int i = 1; i <= 4; i++){
            drawDot(-5 * i, 0, 3, -5 * i + "", Color.BLACK, false);
            drawDot(5 * i, 0, 3, 5 * i + "", Color.BLACK, false);
            drawDot(0, -5 * i, 3, -5 * i + "", Color.BLACK, false);
            drawDot(0, 5 * i, 3, 5 * i + "", Color.BLACK, false);
        }
    }
    
    @Override
    protected void layoutGrids(){
        for(int i = 1; i <= 4; i++){
            drawXAxis(-5 * i, Color.LIGHTGRAY);
            drawXAxis(5 * i, Color.LIGHTGRAY);
            drawYAxis(-5 * i, Color.LIGHTGRAY);
            drawYAxis(5 * i, Color.LIGHTGRAY);
        }
    }
    
    
    private void drawXAxis(double y, Color color){
        Line[] pLine = new Line[AXISLENGTH];
        Line[] nLine = new Line[AXISLENGTH];
        for(int i = 0; i < AXISLENGTH; i++){
            double[] pPos1 = transform(i, y);
            double[] pPos2 = transform(i + 1, y);
            pLine[i] = new Line(400 + pPos1[0] * UNIT, 400 - pPos1[1] * UNIT, 400 + pPos2[0] * UNIT, 400 - pPos2[1] * UNIT);
            pLine[i].setStroke(color);
            double[] nPos1 = transform(-i, y);
            double[] nPos2 = transform(-i - 1, y);
            nLine[i] = new Line(400 + nPos1[0] * UNIT, 400 - nPos1[1] * UNIT, 400 + nPos2[0] * UNIT, 400 - nPos2[1] * UNIT);
            nLine[i].setStroke(color);
        }
        getChildren().addAll(pLine);
        getChildren().addAll(nLine);
    }
    
    
    private void drawYAxis(double x, Color color){
        Line[] pLine = new Line[AXISLENGTH];
        Line[] nLine = new Line[AXISLENGTH];
        for(int i = 0; i < AXISLENGTH; i++){
            double[] pPos1 = transform(x, i);
            double[] pPos2 = transform(x, i + 1);
            pLine[i] = new Line(400 + pPos1[0] * UNIT, 400 - pPos1[1] * UNIT, 400 + pPos2[0] * UNIT, 400 - pPos2[1] * UNIT);
            pLine[i].setStroke(color);
            double[] nPos1 = transform(x, -i);
            double[] nPos2 = transform(x, -i - 1);
            nLine[i] = new Line(400 + nPos1[0] * UNIT, 400 - nPos1[1] * UNIT, 400 + nPos2[0] * UNIT, 400 - nPos2[1] * UNIT);
            nLine[i].setStroke(color);
        }
        getChildren().addAll(pLine);
        getChildren().addAll(nLine);
    }
}
