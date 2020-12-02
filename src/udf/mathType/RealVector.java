/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.mathType;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class RealVector {
    public final static int DIM = 0;
    public final static RealVector NULL = new RealVector();
    
    protected ArrayList<Double> componentList = new ArrayList<>();
    
    public RealVector(double...args){
        for(int i = 0; i < args.length; i++)
            componentList.add(args[i]);
    }
    
    public RealVector(ArrayList<Double> list){
        componentList = list;
    }
    
    public ArrayList<Double> getComponentList(){
        return componentList;
    }
    
    public double getComponent(int index){
        return componentList.get(index);
    }
    
    public int dimension(){
        return componentList.size();
    }
    
    public boolean isNull(){
        return dimension() == 0;
    }
    
    public boolean sameDimensionWith(RealVector other){
        return dimension() == other.dimension();
    }
    
    public boolean equals(RealVector other){
        if(!sameDimensionWith(other)) return false;
        for(int i = 0; i< dimension(); i++)
            if(componentList.get(i) != other.componentList.get(i)) return false;
        return true;
    }
    
    public RealVector add(RealVector other){
        if(!sameDimensionWith(other)) return null;
        ArrayList<Double> newList = new ArrayList<>();
        for(int i = 0; i < dimension(); i++)
            newList.add(componentList.get(i) + other.componentList.get(i));
        return new RealVector(newList);
    }
    
    public double innerProduct(RealVector other){
        if(!sameDimensionWith(other)) return Double.NaN;
        double sum = 0;
        for(int i = 0; i < dimension(); i++)
            sum += componentList.get(i) * other.componentList.get(i);
        return sum;
    }
    
    public RealVector minus(){
        ArrayList<Double> newList = new ArrayList<>();
        for(int i = 0; i< dimension(); i++)
            newList.add(-componentList.get(i));
        return new RealVector(newList);
    }
    
    public RealVector subtract(RealVector other){
        return add(other.minus());
    }
    
    public RealVector multiply(double number){
        ArrayList<Double> newList = new ArrayList<>();
        for(int i = 0; i < dimension(); i++)
            newList.add(componentList.get(i) * number);
        return new RealVector(newList);
    }
    
    public RealVector divide(double number){
        return multiply(1 / number);
    }
    
    public RealVector normalize(){
        return divide(norm());
    }
    
    public double normSquare(){
        double sum = 0;
        for(int i = 0; i < dimension(); i++)
            sum += Math.pow(componentList.get(i), 2);
        return sum;
    }
    
    public double norm(){
        return Math.sqrt(normSquare());
    }
    
    public double distance(RealVector other){
        return this.subtract(other).norm();
    }
    
    public RealNumber toRealNumber(){
        return new RealNumber(componentList.get(0));
    }
    
    public RealVector2D toRealVector2D(){
        return new RealVector2D(getComponent(0), getComponent(1));
    }
    
    public RealVector3D toRealVector3D(){
        return new RealVector3D(getComponent(0), getComponent(1), getComponent(2));
    }
    
    @Override
    public String toString(){
        if(isNull()) return "null";
        else{
            String str = "(" + componentList.get(0);
            for(int i = 1; i < dimension(); i++)
                str += ", " + componentList.get(i);
            str += ")";
            return str;
        }
    }
}
