/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function.simple;

import udf.function.SmoothFunction;
import udf.mathType.RealVector;


/**
 *
 * @author DELL
 */
public class BasicFunction<E extends RealVector> extends SmoothFunction<E> {
    // f(x) = l * x + c;
    final public static BasicFunction X = new BasicFunction();
    
    private int i;
    
    public BasicFunction(){
    }
    
    public BasicFunction(int i){
        this.i = i;
    }
    
    @Override
    public Double evaluate(E x){
        return x.getComponent(i - 1);
    }
    
    @Override
    public ConstantFunction<E> derivative(int index){
        if(index == i - 1) return ConstantFunction.ONE;
        else return ConstantFunction.ZERO;
    }
    
}
