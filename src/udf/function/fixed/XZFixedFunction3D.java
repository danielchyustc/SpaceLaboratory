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
public class XZFixedFunction3D extends TwoFixedFunction3D {
    private double xValue = 0;
    private double zValue = 0;
    
    public XZFixedFunction3D(){
        
    }
    
    public XZFixedFunction3D(SmoothFunction<RealVector3D> function){
        super(function);
    }
    
    public XZFixedFunction3D(SmoothFunction<RealVector3D> function, double xValue, double zValue){
        super(function);
        this.xValue = xValue;
        this.zValue = zValue;
    }
    

    @Override
    public Double evaluate(Double x) {
        return function.evaluate(new RealVector3D(xValue, x, zValue));
    }

    @Override
    public XZFixedFunction3D derivative() {
        return new XZFixedFunction3D(function.derivative(2), xValue, zValue);
    }
    
}
