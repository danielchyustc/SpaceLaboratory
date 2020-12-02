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
public class SinFunction<E extends RealVector> extends CompositeFunction<E> {
    
    public SinFunction(){
    }
    
    public SinFunction(SmoothFunction<E> function){
        super(function);
    }
    
    @Override
    public Double evaluate(E x){
        return Math.sin(getFunction().evaluate(x));
    }
    
    @Override
    public SmoothFunction<E> derivative(int index){
        return(new ProductFunction<E>(new CosFunction<E>(function), function.derivative(index)));
    }
}
