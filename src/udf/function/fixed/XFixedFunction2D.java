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
public class XFixedFunction2D extends OneFixedFunction2D {
    private double xValue = 0;
    
    public XFixedFunction2D(){
        
    }
    
    public XFixedFunction2D(SmoothFunction<RealVector2D> function){
        super(function);
    }
    
    public XFixedFunction2D(SmoothFunction<RealVector2D> function, double xValue){
        super(function);
        this.xValue = xValue;
    }
    

    @Override
    public Double evaluate(double x) {
        return function.evaluate(new RealVector2D(xValue, x));
    }

    @Override
    public XFixedFunction2D derivative() {
        return new XFixedFunction2D(function.derivative(2), xValue);
    }
    
}
