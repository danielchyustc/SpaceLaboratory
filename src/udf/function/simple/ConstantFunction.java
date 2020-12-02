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
 * @param <E>
 */
public class ConstantFunction<E extends RealVector> extends SmoothFunction<E> {
    final public static ConstantFunction ZERO = new ConstantFunction(0);
    final public static ConstantFunction ONE = new ConstantFunction(1);
    final public static ConstantFunction MINUSONE = new ConstantFunction(-1);
    
    private double value = 0;
    
    public ConstantFunction(){
    }
    
    public ConstantFunction(double value){
        this.value = value;
    }
    
    public double getValue(){
        return value;
    }
    
    @Override
    public Double evaluate(E x){
        return value;
    }
    
    @Override
    public ConstantFunction derivative(int index){
        return ConstantFunction.ZERO;
    }
    
    @Override
    public String toString(){
        return super.toString() + getValue();
    }
    
}
