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
public class TanFunction<E extends RealVector> extends CompositeFunction<E> {
    
    public TanFunction(){
    }
    
    public TanFunction(SmoothFunction<E> function){
        super(function);
    }
    
    @Override
    public Double evaluate(E x){
        return Math.tan(getFunction().evaluate(x));
    }
    
    @Override
    public SmoothFunction<E> derivative(int index){
        return(new ProductFunction<E>(new CosFunction<E>(function).power(2), function.derivative(index)));
    }
}
