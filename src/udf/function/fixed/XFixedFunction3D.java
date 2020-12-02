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
public class XFixedFunction3D extends OneFixedFunction3D {
    private double xValue = 0;
    
    public XFixedFunction3D(){
        
    }
    
    public XFixedFunction3D(SmoothFunction<RealVector3D> function){
        super(function);
    }
    
    public XFixedFunction3D(SmoothFunction<RealVector3D> function, double xValue){
        super(function);
        this.xValue = xValue;
    }
    

    @Override
    public Double evaluate(double x, double y) {
        return function.evaluate(new RealVector3D(xValue, x, y));
    }

    @Override
    public XFixedFunction3D xDerivative() {
        return new XFixedFunction3D(function.derivative(2), xValue);
    }
    
    @Override
    public XFixedFunction3D yDerivative() {
        return new XFixedFunction3D(function.derivative(3), xValue);
    }
    
}
