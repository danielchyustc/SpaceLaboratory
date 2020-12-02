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
public class Cylin3DPane extends Coordinates3D {
    
    public Cylin3DPane(){
    }
    
    @Override
    public double[] transform(double r, double theta, double z) {
        double[] result = new double[3];
        result[0] = r * Math.sin(theta);
        result[1] = z;
        result[2] = r * Math.cos(theta);
        return result;
    }

    @Override
    protected void layoutPoints(){
        drawDot(0, 0, 0, 5, "O", Color.BLACK, false);
        for(int i = 1; i <= 5; i++){
            drawDot(i * 5, 0, 0, i * 5 + "");
            drawDot(i * 5, Math.PI / 2, 0, i * 5 + "");
            drawDot(0, 0, i * 5, i * 5 + "");
            drawDot(0, 0, -i * 5, -i * 5 + "");
        }
    }

}
