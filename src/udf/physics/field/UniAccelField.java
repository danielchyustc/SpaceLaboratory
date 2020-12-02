/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.physics.field;

import udf.mathType.RealVector3D;
import udf.physics.model.pointModel.PointModel;

/**
 *
 * @author DELL
 */
public class UniAccelField extends Field{
    private RealVector3D acceleration = RealVector3D.ZERO;
    
    public UniAccelField(){
        
    }
    
    public UniAccelField(RealVector3D acceleration){
        this.acceleration = acceleration;
    }

    @Override
    public RealVector3D acceleration(PointModel A) {
        return acceleration;
    }

    @Override
    public double potential(PointModel A) {
        return -A.getPosition().innerProduct(acceleration);
    }
    
}
