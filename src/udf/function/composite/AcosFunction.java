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
public class AcosFunction<E extends RealVector> extends CompositeFunction<E> {
    
    public AcosFunction(){
    }
    
    public AcosFunction(SmoothFunction<E> function){
        super(function);
    }
    
    @Override
    public Double evaluate(E x){
        return Math.acos(getFunction().evaluate(x));
    }
    
    @Override
    public SmoothFunction<E> derivative(int index){
        return(new ProductFunction<>(function.power(2).minus().add(1).power(-0.5).minus(), function.derivative(index)));
    }
}
