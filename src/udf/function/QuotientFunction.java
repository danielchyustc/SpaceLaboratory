/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function;

import udf.function.simple.ConstantFunction;
import udf.mathType.RealVector;


/**
 *
 * @author DELL
 */
public class QuotientFunction<E extends RealVector> extends SmoothFunction<E> {
    private SmoothFunction<E> numerator = ConstantFunction.ONE;
    private SmoothFunction<E> denominator = ConstantFunction.ONE;
    
    public QuotientFunction(){
        
    }
    
    public QuotientFunction(SmoothFunction<E> numerator, SmoothFunction<E> denominator){
        if(numerator != null) this.numerator = numerator;
        if(denominator != null) this.denominator = denominator;
    }
    
    public SmoothFunction<E> getNumerator(){
        return numerator;
    }
    
    public SmoothFunction<E> getDenominator(){
        return denominator;
    }
    
    @Override
    public Double evaluate(E x){
        return numerator.evaluate(x) / denominator.evaluate(x);
    }
    
    @Override
    public QuotientFunction<E> derivative(int index){
        return(new QuotientFunction<>(
                new SumFunction<>(new ProductFunction<>(numerator.derivative(index), denominator), 
                        new ProductFunction<>(denominator.derivative(index), numerator).derivative(index).minus()), 
                new PowerFunction<>(denominator, new ConstantFunction<>(2))));
    }
    
}
