/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function.fixed;

import udf.function.SmoothFunction;
import udf.mathType.*;


/**
 *
 * @author DELL
 */
public class YFixedFunction3D extends OneFixedFunction3D {
    private double yValue = 0;
    
    public YFixedFunction3D(){
        
    }
    
    public YFixedFunction3D(SmoothFunction<RealVector3D> function){
        super(function);
    }
    
    public YFixedFunction3D(SmoothFunction<RealVector3D> function, double yValue){
        super(function);
        this.yValue = yValue;
    }
    

    @Override
    public Double evaluate(double x, double y) {
        return function.evaluate(new RealVector3D(x, yValue, y));
    }

    @Override
    public YFixedFunction3D xDerivative() {
        return new YFixedFunction3D(function.derivative(1), yValue);
    }
    
    @Override
    public YFixedFunction3D yDerivative() {
        return new YFixedFunction3D(function.derivative(3), yValue);
    }
    
}
