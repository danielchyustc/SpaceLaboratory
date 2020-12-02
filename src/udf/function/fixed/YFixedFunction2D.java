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
public class YFixedFunction2D extends OneFixedFunction2D {
    private double yValue = 0;
    
    public YFixedFunction2D(){
        
    }
    
    public YFixedFunction2D(SmoothFunction<RealVector2D> function){
        super(function);
    }
    
    public YFixedFunction2D(SmoothFunction<RealVector2D> function, double yValue){
        super(function);
        this.yValue = yValue;
    }
    

    @Override
    public Double evaluate(double x) {
        return function.evaluate(new RealVector2D(x, yValue));
    }

    @Override
    public YFixedFunction2D derivative() {
        return new YFixedFunction2D(function.derivative(1), yValue);
    }
    
}
