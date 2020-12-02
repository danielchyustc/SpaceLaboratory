/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function.composite;

import udf.function.*;
import udf.function.simple.ConstantFunction;
import udf.mathType.*;

/**
 *
 * @author DELL
 */
public class LogFunction<E extends RealVector> extends CompositeFunction<E> {
    
    public LogFunction(){
    }
    
    public LogFunction(SmoothFunction<E> function){
        super(function);
    }
    
    @Override
    public Double evaluate(E x){
        return Math.log(getFunction().evaluate(x));
    }
    
    @Override
    public SmoothFunction<E> derivative(int index){
        return(new ProductFunction<>(function.reciprocal(), function.derivative(index)));
    }
}
