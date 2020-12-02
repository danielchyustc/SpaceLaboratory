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
public class SpringField extends Field {
    private double k = 1;
    private double orgLength = 5;
    private RealVector3D fixPoint = RealVector3D.ZERO;
    
    public SpringField(){
        
    }
    
    public SpringField(double k, double orgLength){
        this.orgLength = Math.abs(orgLength);
        this.k = k;
    }
    
    public SpringField(double k, double orgLength, RealVector3D fixPoint){
        this.k = k;
        this.orgLength = Math.abs(orgLength);
        this.fixPoint = fixPoint;
    }

    @Override
    public RealVector3D force(PointModel A) {
        return A.getPosition().normalize().multiply(-k * ((A.getPosition().subtract(fixPoint)).norm() - orgLength));
    }
    
    @Override
    public RealVector3D acceleration(PointModel A) {
        return force(A).divide(A.getMass());
    }

    @Override
    public double potentialEnergy(PointModel A) {
        return 0.5 * k * Math.pow(A.getPosition().subtract(fixPoint).norm() - orgLength, 2);
    }
    
    @Override
    public double potential(PointModel A){
        return potentialEnergy(A) / A.getMass();
    }   
}
