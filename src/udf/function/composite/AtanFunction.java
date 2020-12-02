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
public class AtanFunction<E extends RealVector> extends CompositeFunction<E> {
    
    public AtanFunction(){
    }
    
    public AtanFunction(SmoothFunction function){
        super(function);
    }
    
    @Override
    public Double evaluate(E x){
        return Math.atan(getFunction().evaluate(x));
    }
    
    @Override
    public SmoothFunction<E> derivative(int index){
        return(new ProductFunction<>(function.power(2).add(1).reciprocal(), function.derivative(index)));
    }
}
