/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.mathType;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class RealNumber extends RealVector {
    public final static int DIM = 1;
    public final static RealNumber NULL = new RealNumber();
    public final static RealNumber ZERO = new RealNumber(0);
    public final static RealNumber E1 = new RealNumber(1);
    
    public RealNumber(){
        
    }
    
    public RealNumber(double xValue){
        super(xValue);
    }
    
    @Override
    public int dimension(){
        return 1;
    }
    
    public double x(){
        return getComponent(0);
    }
    
    public boolean equals(RealVector3D other){
        if(isNull() || other.isNull()) return false;
        return x() == other.x();
    }
    
    public RealNumber add(RealNumber other){
        if(isNull() || other.isNull()) return NULL;
        return new RealNumber(x() + other.x());
    }
    
    public double innerProduct(RealNumber other){
        if(isNull() || other.isNull()) return Double.NaN;
        return x() * other.x();
    }
    
    @Override
    public RealNumber minus(){
        if(isNull()) return NULL;
        return new RealNumber(-x());
    }
    
    @Override
    public RealNumber multiply(double number){
        if(isNull()) return NULL;
        return new RealNumber(x() * number);
    }
    
    public RealNumber subtract(RealNumber other){
        return add(other.minus());
    }
    
    @Override
    public double normSquare(){
        return x() * x();
    }
    
    @Override
    public String toString(){
        if(isNull()) return "null";
        return Math.round(100 * x()) / 100.0 + "";
    }
}
