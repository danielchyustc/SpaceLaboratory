/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function;

import java.util.ArrayList;
import udf.function.simple.ConstantFunction;
import udf.mathType.MathUdf;
import udf.mathType.RealNumber;
import udf.mathType.RealVector;

/**
 *
 * @author DELL
 * @param <E>
 */
public abstract class SmoothFunction<E extends RealVector> {
    final public static SmoothFunction NULL = new ConstantFunction<>(Double.NaN);
    
    public SmoothFunction(){
        
    }
    
    public SmoothFunction<E> linearOperation(double linearTerm, double constantTerm){
        return new SumFunction<>(new ProductFunction(this, new ConstantFunction<>(linearTerm)), new ConstantFunction<>(constantTerm));
    }
    
    public SmoothFunction<E> add(SmoothFunction<E> other){
        return new SumFunction<>(this, other);
    }
    
    public SmoothFunction<E> add(double number){
        return new SumFunction<>(this, new ConstantFunction(number));
    }
    
    public SmoothFunction<E> multiply(SmoothFunction<E> other){
        return new ProductFunction(this, other);
    }
    
    public SmoothFunction<E> multiply(double number){
        return new ProductFunction<>(this, new ConstantFunction<>(number));
    }
    
    public SmoothFunction<E> power(double number){
        return new PowerFunction<>(this, new ConstantFunction(number));
    }
    
    public SmoothFunction<E> minus(){
        return new ProductFunction<>(new ConstantFunction<>(-1), this);
    }
    
    public SmoothFunction<E> reciprocal(){
        return new QuotientFunction<>(ConstantFunction.ONE, this);
    }
    
    public abstract Double evaluate(E x);
    
    
    public abstract SmoothFunction<E> derivative(int index);
    
    public RealVector normalVector(E x){
        ArrayList<Double> list = new ArrayList<>();
        for(int i = 0; i < x.dimension(); i++)
            list.add(derivative(i).evaluate(x));
        return new RealVector(list);
    }
    
    public boolean hasRoot(E x){
        return MathUdf.isInfinitesimal(evaluate(x));
    }
    
}
