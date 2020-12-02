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
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;


public class Rect3DPane extends Coordinates3D {
    
    public Rect3DPane(){
    }
    
    @Override
    public double[] transform(double x, double y, double z) {
        double[] result = new double[3];
        result[0] = x;
        result[1] = y;
        result[2] = z;
        return result;
    }
    
    @Override
    protected void layoutPoints(){
        drawDot(0, 0, 0, 5, "O", Color.BLACK, false);
        for(int i = 1; i <= 5; i++){
            drawDot(-5 * i, 0, 0, -5 * i + "");
            drawDot(5 * i, 0, 0, 5 * i + "");
            drawDot(0, -5 * i, 0, -5 * i + "");
            drawDot(0, 5 * i, 0, 5 * i + "");
            drawDot(0, 0, -5 * i, -5 * i + "");
            drawDot(0, 0, 5 * i, 5 * i + "");
        }
    }
    
}
