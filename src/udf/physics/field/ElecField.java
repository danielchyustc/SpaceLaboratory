/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.physics.field;

import java.util.ArrayList;
import java.util.Arrays;
import udf.mathType.RealVector3D;
import static udf.physics.field.UniGravField.G;
import udf.physics.model.pointModel.PointModel;

/**
 *
 * @author DELL
 */
public class ElecField extends Field<PointModel> {
    public final static double E0 = 8.854187817e-3;
    private ArrayList<PointModel> chargePointSet = new ArrayList<>();

    public ElecField(PointModel... args){
        chargePointSet.addAll(Arrays.asList(args));
    }
    
    public ElecField(ArrayList<PointModel> set){
        chargePointSet = set;
    }
    
    public ArrayList<PointModel> getChargePointSet(){
        return chargePointSet;
    }
    
    public PointModel getChargePoint(int index){
        return chargePointSet.get(index);
    }
    
    public void removeChargePoint(int index){
        chargePointSet.remove(index);
    }
    
    public void addChargePoint(PointModel A){
        chargePointSet.add(A);
    }
    
    public int ChargePointNumber(){
        return chargePointSet.size();
    }
    
    @Override
    public RealVector3D force(PointModel A) {
        RealVector3D force = RealVector3D.ZERO;
        for(int i = 0; i < ChargePointNumber(); i++){
            RealVector3D r = A.getPosition().subtract(getChargePoint(i).getPosition());
            force = force.add(r.multiply(getChargePoint(i).getCharge() * A.getCharge() / (4 * Math.PI * E0 *Math.pow(r.norm(), 3))));
        }
        return force;
    }
    
    @Override
    public RealVector3D acceleration(PointModel A){
        return force(A).multiply(1 / A.getMass());
    }
    
    @Override
    public double potential(PointModel A) {
        double potential = 0;
        for(int i = 0; i < ChargePointNumber(); i++){
            RealVector3D r = A.getPosition().subtract(getChargePoint(i).getPosition());
            potential += -getChargePoint(i).getMass() / (4 * Math.PI * E0 * r.norm());
        }
        return potential;
    }
    
    
    @Override
    public double potentialEnergy(PointModel A){
        return potential(A) * A.getCharge();
    }
    
}
