/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.physics.model.pointModel;

import udf.mathType.RealVector3D;
import udf.physics.field.Field;

/**
 *
 * @author DELL
 */
public class MassPoint extends PointModel {
    private final double charge = 0;
    
    public MassPoint(){
        
    }
    
    public MassPoint(double mass){
        super(mass);
    }
    
    public MassPoint(double mass, RealVector3D position){
        super(mass, position);
    }
    
    public MassPoint(double mass, RealVector3D position, RealVector3D velocity){
        super(mass, position, velocity);
    }
}
