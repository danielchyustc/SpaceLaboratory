/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.physics.field;

import java.util.ArrayList;
import java.util.Arrays;
import udf.mathType.RealVector3D;
import udf.physics.model.pointModel.MassPoint;
import udf.physics.model.pointModel.PointModel;

/**
 *
 * @author DELL
 */
public class UniGravField extends Field<PointModel> {
    public final static double G = 6.67384;
    private ArrayList<PointModel> pointModelSet = new ArrayList<>();

    public UniGravField(PointModel... args){
        pointModelSet.addAll(Arrays.asList(args));
    }
    
    public UniGravField(ArrayList<PointModel> set){
        pointModelSet = set;
    }
    
    public ArrayList<PointModel> getPointModelSet(){
        return pointModelSet;
    }
    
    public PointModel getPointModel(int index){
        return pointModelSet.get(index);
    }
    
    public void addPointModel(PointModel A){
        pointModelSet.add(A);
    }
    
    public int pointModelNumber(){
        return pointModelSet.size();
    }
    
    @Override
    public RealVector3D acceleration(PointModel A) {
        RealVector3D force = RealVector3D.ZERO;
        for(int i = 0; i < pointModelNumber(); i++){
            RealVector3D r = A.getPosition().subtract(getPointModel(i).getPosition());
            force = force.add(r.multiply(-1).multiply(G * getPointModel(i).getMass() / Math.pow(r.norm(), 3)));
        }
        return force;
    }
    
    @Override
    public double potential(PointModel A) {
        double potential = 0;
        for(int i = 0; i < pointModelNumber(); i++){
            RealVector3D r = A.getPosition().subtract(getPointModel(i).getPosition());
            potential += -G * getPointModel(i).getMass() / r.norm();
        }
        return potential;
    }
    
}
