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
public class XYFixedFunction3D extends TwoFixedFunction3D {
    private double xValue = 0;
    private double yValue = 0;
    
    public XYFixedFunction3D(){
        
    }
    
    public XYFixedFunction3D(SmoothFunction<RealVector3D> function){
        super(function);
    }
    
    public XYFixedFunction3D(SmoothFunction<RealVector3D> function, double xValue, double yValue){
        super(function);
        this.xValue = xValue;
        this.yValue = yValue;
    }
    

    @Override
    public Double evaluate(Double x) {
        return function.evaluate(new RealVector3D(xValue, yValue, x));
    }

    @Override
    public XYFixedFunction3D derivative() {
        return new XYFixedFunction3D(function.derivative(3), xValue, yValue);
    }
    
}
