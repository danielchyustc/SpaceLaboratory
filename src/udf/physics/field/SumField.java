/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.physics.field;

import java.util.ArrayList;
import java.util.Arrays;
import udf.mathType.RealVector3D;
import udf.physics.model.pointModel.PointModel;

/**
 *
 * @author DELL
 */
public class SumField extends Field {
    private ArrayList<Field> fieldList = new ArrayList<>();
    
    public SumField(ArrayList<Field> fieldList){
        this.fieldList = fieldList;
    }
    
    public SumField(Field...args){
        fieldList.addAll(Arrays.asList(args));
    }
    
    public Field getField(int index){
        return fieldList.get(index);
    }
    
    public ArrayList<Field> getFieldList(){
        return fieldList;
    }

    @Override
    public RealVector3D acceleration(PointModel A) {
        RealVector3D result = RealVector3D.ZERO;
        for(int i = 0; i < fieldList.size(); i++)
            result = result.add(getField(i).acceleration(A));
        return result;
    }

    @Override
    public double potential(PointModel A) {
        double result = 0;
        for(int i = 0; i < fieldList.size(); i++)
            result += getField(i).potential(A);
        return result;
    }
    
}
