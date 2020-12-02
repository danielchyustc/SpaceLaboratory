/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function;

import java.util.ArrayList;
import java.util.Arrays;
import udf.function.simple.ConstantFunction;
import udf.mathType.RealVector;

/**
 *
 * @author DELL
 * @param <E>
 */
public class SmoothMapping<E extends RealVector> {
    public final static SmoothMapping NULL = new SmoothMapping();
    
    protected ArrayList<SmoothFunction<E>> componentList = new ArrayList<>();
    
    public SmoothMapping(SmoothFunction<E>...args){
        componentList.addAll(Arrays.asList(args));
    }
    
    public SmoothMapping(ArrayList<SmoothFunction<E>> list){
        componentList = list;
    }
    
    public ArrayList<SmoothFunction<E>> getComponentList(){
        return componentList;
    }
    
    public SmoothFunction<E> getComponent(int index){
        return componentList.get(index);
    }
    
    public int dimension(){
        return componentList.size();
    }
    
    public boolean isNull(){
        return dimension() == 0;
    }
    
    public boolean sameDimensionWith(SmoothMapping<E> other){
        return dimension() == other.dimension();
    }
    
    public boolean equals(SmoothMapping<E> other){
        if(!sameDimensionWith(other)) return false;
        for(int i = 0; i< dimension(); i++)
            if(componentList.get(i) != other.componentList.get(i)) return false;
        return true;
    }
    
    public SmoothMapping<E> add(SmoothMapping<E> other){
        if(!sameDimensionWith(other)) return null;
        ArrayList<SmoothFunction<E>> newList = new ArrayList<>();
        for(int i = 0; i < dimension(); i++)
            newList.add(componentList.get(i).add(other.componentList.get(i)));
        return new SmoothMapping<>(newList);
    }
    
    public SmoothFunction<E> innerProduct(SmoothMapping<E> other){
        if(!sameDimensionWith(other)) return SmoothFunction.NULL;
        SmoothFunction<E> sum = ConstantFunction.ZERO;
        for(int i = 0; i < dimension(); i++)
            sum = sum.add(componentList.get(i).multiply(other.componentList.get(i)));
        return sum;
    }
    
    public SmoothMapping<E> minus(){
        ArrayList<SmoothFunction<E>> newList = new ArrayList<>();
        for(int i = 0; i< dimension(); i++)
            newList.add(componentList.get(i).minus());
        return new SmoothMapping<>(newList);
    }
    
    public SmoothMapping<E> subtract(SmoothMapping<E> other){
        return add(other.minus());
    }
    
    public SmoothMapping<E> multiply(double number){
        ArrayList<SmoothFunction<E>> newList = new ArrayList<>();
        for(int i = 0; i < dimension(); i++)
            newList.add(componentList.get(i).multiply(number));
        return new SmoothMapping<>(newList);
    }
    
    public SmoothFunction<E> normSquare(){
        SmoothFunction<E> sum = ConstantFunction.ZERO;
        for(int i = 0; i < dimension(); i++)
            sum = sum.add(componentList.get(i).add(2));
        return sum;
    }
    
    public SmoothFunction<E> norm(){
        return normSquare().power(0.5);
    }
    
    public SmoothFunction<E> distance(SmoothMapping<E> other){
        return this.subtract(other).norm();
    }
    
    @Override
    public String toString(){
        if(isNull()) return "null";
        else{
            String str = "(" + componentList.get(0).toString();
            for(int i = 1; i < dimension(); i++)
                str += ", " + componentList.get(i).toString();
            str += ")";
            return str;
        }
    }
}
