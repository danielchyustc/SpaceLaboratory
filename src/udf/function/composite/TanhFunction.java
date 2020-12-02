/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function.composite;

import udf.function.*;
import udf.mathType.*;

/**
 *
 * @author DELL
 */
public class TanhFunction<E extends RealVector> extends CompositeFunction<E> {
    
    public TanhFunction(){
    }
    
    public TanhFunction(SmoothFunction function){
        super(function);
    }
    
    @Override
    public Double evaluate(E x){
        return Math.tanh(getFunction().evaluate(x));
    }
    
    @Override
    public SmoothFunction<E> derivative(int index){
        return(new ProductFunction<>(new CoshFunction<>(function).power(2), function.derivative(index)));
    }
}