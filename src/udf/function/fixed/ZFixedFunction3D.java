/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function.fixed;

import udf.function.SmoothFunction;
import udf.mathType.RealVector3D;


/**
 *
 * @author DELL
 */
public class ZFixedFunction3D extends OneFixedFunction3D {
    private double zValue = 0;
    
    public ZFixedFunction3D(){
        
    }
    
    public ZFixedFunction3D(SmoothFunction<RealVector3D> function){
        super(function);
    }
    
    public ZFixedFunction3D(SmoothFunction<RealVector3D> function, double zValue){
        super(function);
        this.zValue = zValue;
    }
    

    @Override
    public Double evaluate(double x, double y) {
        return function.evaluate(new RealVector3D(x, y, zValue));
    }

    @Override
    public ZFixedFunction3D xDerivative() {
        return new ZFixedFunction3D(function.derivative(1), zValue);
    }
    
    @Override
    public ZFixedFunction3D yDerivative() {
        return new ZFixedFunction3D(function.derivative(2), zValue);
    }
    
}
