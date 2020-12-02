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
public abstract class TwoFixedFunction3D extends SmoothFunction<RealNumber>{
    protected SmoothFunction<RealVector3D> function ;
    
    public TwoFixedFunction3D(){
        super();
    }
    
    public TwoFixedFunction3D(SmoothFunction<RealVector3D> function){
        this.function = function;
    }
    
    public SmoothFunction<RealVector3D> getFunction(){
        return function;
    }

    @Override
    public Double evaluate(RealNumber x){
        return evaluate(x.x());
    }
    
    public abstract Double evaluate(Double x);

    @Override
    public SmoothFunction<RealNumber> derivative(int index){
        if(index == 1) return derivative();
        else return ConstantFunction.ZERO;
    }
    public abstract TwoFixedFunction3D derivative();
    
}
