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
public class ExpFunction<E extends RealVector> extends CompositeFunction<E> {
    
    public ExpFunction(){
    }
    
    public ExpFunction(SmoothFunction<E> function){
        super(function);
    }
    
    @Override
    public Double evaluate(E x){
        return Math.exp(getFunction().evaluate(x));
    }
    
    @Override
    public SmoothFunction derivative(int index){
        return(new ProductFunction<>( new ExpFunction<>(function), function.derivative(index)));
    }
}
