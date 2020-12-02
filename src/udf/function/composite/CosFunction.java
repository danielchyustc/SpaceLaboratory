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
public class CosFunction<E extends RealVector> extends CompositeFunction<E> {
    
    public CosFunction(){
    }
    
    public CosFunction(SmoothFunction function){
        super(function);
    }
    
    @Override
    public Double evaluate(E x){
        return Math.cos(getFunction().evaluate(x));
    }
    
    @Override
    public SmoothFunction<E> derivative(int index){
        return(new ProductFunction<>(new SinFunction<>(getFunction()), function.derivative(index)));
    }
}
