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
public class ElasticRopeField extends Field {
    private double k = 1;
    private double orgLength = 5;
    private RealVector3D fixPoint = RealVector3D.ZERO;
    
    public ElasticRopeField(){
        
    }
    
    public ElasticRopeField(double k, double orgLength){
        this.orgLength = Math.abs(orgLength);
        this.k = k;
    }
    
    public ElasticRopeField(double k, double orgLength, RealVector3D fixPoint){
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
        if(A.getPosition().subtract(fixPoint).norm() > orgLength)
            return force(A).divide(A.getMass());
        else return RealVector3D.ZERO;
    }

    @Override
    public double potentialEnergy(PointModel A) {
        if(A.getPosition().subtract(fixPoint).norm() > orgLength)
            return 0.5 * k * Math.pow(A.getPosition().subtract(fixPoint).norm() - orgLength, 2);
        else return 0;
    }
    
    @Override
    public double potential(PointModel A){
        return potentialEnergy(A) / A.getMass();
    }   
}
