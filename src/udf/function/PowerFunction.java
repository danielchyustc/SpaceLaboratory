/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function;

import udf.function.composite.LogFunction;
import udf.function.simple.ConstantFunction;
import udf.mathType.RealVector;


/**
 *
 * @author DELL
 */
public class PowerFunction<E extends RealVector> extends SmoothFunction<E> {
    private SmoothFunction<E> base = ConstantFunction.ONE;
    private SmoothFunction<E> exponential = ConstantFunction.ONE;
    
    public PowerFunction(){
        
    }
    
    public PowerFunction(SmoothFunction<E> base, SmoothFunction<E> exponential){
        if(base != null) this.base = base;
        if(exponential != null) this.exponential = exponential;
    }
    
    public SmoothFunction<E> getBase(){
        return base;
    }
    
    public SmoothFunction<E> getExponential(){
        return exponential;
    }
    
    @Override
    public Double evaluate(E x){
        return Math.pow(base.evaluate(x), exponential.evaluate(x));
    }
    
    @Override
    public ProductFunction derivative(int index){
        return(new ProductFunction(this, new SumFunction(
                new ProductFunction(exponential, base.derivative(index), base.reciprocal()), 
                new ProductFunction(exponential.derivative(index), new LogFunction(base)))));
    }
    
}
