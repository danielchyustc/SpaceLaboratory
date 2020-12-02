/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function.composite;

import udf.function.SmoothFunction;
import udf.function.simple.BasicFunction;
import udf.mathType.*;

/**
 *
 * @author DELL
 */
public abstract class CompositeFunction<E extends RealVector> extends SmoothFunction<E> {   
    protected SmoothFunction<E> function  = new BasicFunction<>(1);
    
    public CompositeFunction(){
        super();
    }
    
    public CompositeFunction(SmoothFunction<E> function){
        this.function = function;
    }
    
    public SmoothFunction<E> getFunction(){
        return function;
    }
}
