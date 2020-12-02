/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.mathType;

import java.util.ArrayList;
import javafx.geometry.Point3D;

/**
 *
 * @author DELL
 */
public class RealVector3D extends RealVector {
    public final static int DIM = 3;
    public final static RealVector3D NULL = new RealVector3D();
    public final static RealVector3D ZERO = new RealVector3D(0, 0, 0);
    public final static RealVector3D E1 = new RealVector3D(1, 0, 0);
    public final static RealVector3D E2 = new RealVector3D(0, 1, 0);
    public final static RealVector3D E3 = new RealVector3D(0, 0, 1);
    
    public RealVector3D(){
        
    }
    
    public RealVector3D(double xValue, double yValue, double zValue){
        super(xValue, yValue, zValue);
    }
    
    public RealVector3D(double...args){
        super(args[0], args[1], args[2]);
    }
    
    @Override
    public int dimension(){
        return 3;
    }
    
    public double x(){
        return getComponent(0);
    }
    
    public double y(){
        return getComponent(1);
    }
    
    public double z(){
        return getComponent(2);
    }
    
    public boolean equals(RealVector3D other){
        if(isNull() || other.isNull()) return false;
        return x() == other.x() && y() == other.y() && z() == other.z();
    }
    
    public RealVector3D add(RealVector3D other){
        if(isNull() || other.isNull()) return NULL;
        return new RealVector3D(x() + other.x(), y() + other.y(), z() + other.z());
    }
    
    public double innerProduct(RealVector3D other){
        if(isNull() || other.isNull()) return Double.NaN;
        return x() * other.x() + y() * other.y() + z() * other.z();
    }
    
    public RealVector3D crossProduct(RealVector3D other){
        if(isNull() || other.isNull()) return null;
        return new RealVector3D(y() * other.z() - z() * other.y(),
                                z() * other.x() - x() * other.z(),
                                x() * other.y() - y() * other.x());
    }
    
    @Override
    public RealVector3D minus(){
        if(isNull()) return NULL;
        return new RealVector3D(-x(), -y(), -z());
    }
    
    public RealVector3D subtract(RealVector3D other){
        return add(other.minus());
    }
    
    @Override
    public RealVector3D multiply(double number){
        if(isNull()) return NULL;
        return new RealVector3D(x() * number, y() * number, z() * number);
    }
    
    @Override
    public RealVector3D divide(double number){
        return multiply(1 / number);
    }
    
    @Override
    public RealVector3D normalize(){
        if(norm() == 0) return RealVector3D.ZERO;
        return divide(norm());
    }
    
    public RealVector2D projection(int index1, int index2){
        return new RealVector2D(getComponent(index1), getComponent(index2));
    }
    
    public RealVector3D projection(RealVector3D other){
        RealVector3D vector = other.normalize();
        return vector.multiply(innerProduct(vector));
    }
    
    @Override
    public double normSquare(){
        return x() * x() + y() * y() + z() * z();
    }
    
    public Point3D toPoint3D(){
        return new Point3D(x(), y(), z());
    }
    
    @Override
    public String toString(){
        if(isNull()) return "null";
        return "(" + Math.round(100 * x()) / 100.0 + ", " + Math.round(100 * y()) / 100.0 + ", " +  Math.round(100 * z()) / 100.0 + ")";
    }
}
