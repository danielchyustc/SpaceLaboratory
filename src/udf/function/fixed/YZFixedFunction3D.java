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
public class YZFixedFunction3D extends TwoFixedFunction3D {
    private double yValue = 0;
    private double zValue = 0;
    
    public YZFixedFunction3D(){
        
    }
    
    public YZFixedFunction3D(SmoothFunction<RealVector3D> function){
        super(function);
    }
    
    public YZFixedFunction3D(SmoothFunction<RealVector3D> function, double yValue, double zValue){
        super(function);
        this.yValue = yValue;
        this.zValue = zValue;
    }
    

    @Override
    public Double evaluate(Double x) {
        return function.evaluate(new RealVector3D(x, yValue, zValue));
    }

    @Override
    public YZFixedFunction3D derivative() {
        return new YZFixedFunction3D(function.derivative(1), yValue, zValue);
    }
    
}
