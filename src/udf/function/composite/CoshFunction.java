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
public class CoshFunction<E extends RealVector> extends CompositeFunction<E> {
    
    public CoshFunction(){
    }
    
    public CoshFunction(SmoothFunction<E> function){
        super(function);
    }
    
    @Override
    public Double evaluate(E x){
        return Math.cosh(getFunction().evaluate(x));
    }
    
    @Override
    public SmoothFunction derivative(int index){
        return(new ProductFunction<>(new SinhFunction<>(function), function.derivative(index)));
    }
}
