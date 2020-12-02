/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.physics.field;

import udf.function.SmoothFunction;
import udf.mathType.RealVector3D;
import udf.physics.model.pointModel.PointModel;

/**
 *
 * @author DELL
 */
public class UdfPotentialField extends Field {
    private SmoothFunction<RealVector3D> potentialFunction;
    
    public UdfPotentialField(SmoothFunction<RealVector3D> potentialFunction){
        this.potentialFunction = potentialFunction;
    }

    @Override
    public RealVector3D acceleration(PointModel A) {
        return potentialFunction.normalVector(A.getPosition()).toRealVector3D().minus();
    }

    @Override
    public double potential(PointModel A) {
        return potentialFunction.evaluate(A.getPosition());
    }   
}
