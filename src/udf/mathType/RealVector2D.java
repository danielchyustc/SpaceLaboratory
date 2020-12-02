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
public class RealVector2D extends RealVector {
    public final static int DIM = 2;
    public final static RealVector2D NULL = new RealVector2D();
    public final static RealVector2D ZERO = new RealVector2D(0, 0);
    public final static RealVector2D E1 = new RealVector2D(1, 0);
    public final static RealVector2D E2 = new RealVector2D(0, 1);
    
    public RealVector2D(){
        
    }
    
    public RealVector2D(double xValue, double yValue){
        super(xValue, yValue);
    }
    
    public RealVector2D(double...args){
        super(args[0], args[1]);
    }
    
    @Override
    public int dimension(){
        return 2;
    }
    
    public double x(){
        return getComponent(0);
    }
    
    public double y(){
        return getComponent(1);
    }
    
    public boolean equals(RealVector3D other){
        if(isNull() || other.isNull()) return false;
        return x() == other.x() && y() == other.y();
    }
    
    public double innerProduct(RealVector2D other){
        if(isNull() || other.isNull()) return Double.NaN;
        return x() * other.x() + y() * other.y();
    }
    
    @Override
    public RealVector2D minus(){
        if(isNull()) return NULL;
        return new RealVector2D(-x(), -y());
    }
    
    public RealVector2D add(RealVector2D other){
        if(isNull() || other.isNull()) return NULL;
        return new RealVector2D(x() + other.x(), y() + other.y());
    }
    
    public RealVector2D subtract(RealVector2D other){
        return add(other.minus());
    }
    
    @Override
    public RealVector2D multiply(double number){
        if(isNull()) return NULL;
        return new RealVector2D(x() * number, y() * number);
    }
    
    @Override
    public RealVector2D divide(double number){
        return multiply(1 / number);
    }
    
    @Override
    public RealVector2D normalize(){
        return divide(norm());
    }
    
    @Override
    public double normSquare(){
        return x() * x() + y() * y();
    }
    
    @Override
    public String toString(){
        if(isNull()) return "null";
        return "(" + Math.round(100 * x()) / 100.0 + ", " + Math.round(100 * y()) / 100.0 + ")";
    }
}
