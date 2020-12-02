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
public abstract class OneFixedFunction2D extends SmoothFunction<RealNumber>{
    protected SmoothFunction<RealVector2D> function  ;
    
    public OneFixedFunction2D(){
        super();
    }
    
    public OneFixedFunction2D(SmoothFunction<RealVector2D> function){
        this.function = function;
    }
    
    public SmoothFunction<RealVector2D> getFunction(){
        return function;
    }

    @Override
    public Double evaluate(RealNumber x){
        return evaluate(x.x());
    }
    
    public abstract Double evaluate(double x);

    @Override
    public SmoothFunction<RealNumber> derivative(int index){
        if(index == 1) return derivative();
        else return ConstantFunction.ZERO;
    }
    
    public abstract OneFixedFunction2D derivative();
    
}
