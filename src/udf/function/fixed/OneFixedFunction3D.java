/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function.fixed;

import udf.function.SmoothFunction;
import udf.function.simple.ConstantFunction;
import udf.mathType.*;


/**
 *
 * @author DELL
 */
public abstract class OneFixedFunction3D extends SmoothFunction<RealVector2D>{
    protected SmoothFunction<RealVector3D> function;
    
    public OneFixedFunction3D(){
        super();
    }
    
    public OneFixedFunction3D(SmoothFunction<RealVector3D> function){
        this.function = function;
    }
    
    public SmoothFunction<RealVector3D> getFunction(){
        return function;
    }

    @Override
    public Double evaluate(RealVector2D vector){
        return evaluate(vector.x(), vector.y());
    }
    
    public abstract Double evaluate(double x, double y);

    @Override
    public SmoothFunction<RealVector2D> derivative(int index){
        if(index == 1) return xDerivative();
        if(index == 2) return yDerivative();
        else return ConstantFunction.ZERO;
    }
    
    public abstract OneFixedFunction3D xDerivative();
    
    public abstract OneFixedFunction3D yDerivative();
    
}
