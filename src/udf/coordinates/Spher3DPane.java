/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.coordinates;

import javafx.scene.paint.Color;

/**
 *
 * @author DELL
 */
public class Spher3DPane extends Coordinates3D{
    
    public Spher3DPane(){
    }

   @Override
    public double[] transform(double r, double theta, double phi) {
        double[] result = new double[3];
        result[0] = r * Math.sin(theta) * Math.sin(phi);
        result[1] = r * Math.cos(theta);
        result[2] = r * Math.sin(theta) * Math.cos(phi);
        return result;
    }

    @Override
    protected void layoutPoints(){
        drawDot(0, 0, 0, 5, "O", Color.BLACK, false);
        for(int i = 1; i <= 5; i++){
            drawDot(i * 5, 0, 0, i * 5 + "");
            drawDot(i * 5, Math.PI, 0, -i * 5 + "");
            drawDot(i * 5, Math.PI / 2, 0, i * 5 + "");
            drawDot(i * 5, Math.PI / 2, Math.PI / 2, i * 5 + "");
            drawDot(i * 5, Math.PI / 2, Math.PI, -i * 5 + "");
            drawDot(i * 5, Math.PI / 2, Math.PI * 3 / 2, -i * 5 + "");
        }
    }

}
