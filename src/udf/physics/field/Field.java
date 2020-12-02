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
 * @param <E>
 */
public abstract class Field<E extends PointModel> {
    public RealVector3D force(E A){
        return acceleration(A).multiply(A.getMass());
    }
    
    public abstract RealVector3D acceleration(E A);
    
    public double potentialEnergy(E A){
        return potential(A) * A.getMass();
    }
    
    public abstract double potential(E A);
}
