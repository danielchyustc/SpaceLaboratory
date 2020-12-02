/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function;

import java.util.ArrayList;
import java.util.Arrays;
import udf.mathType.*;

/**
 *
 * @author DELL
 * @param <E>
 */
public class SumFunction<E extends RealVector> extends SmoothFunction<E> {
    final private ArrayList<SmoothFunction<E>> functionList = new ArrayList<>();
    
    public SumFunction(){
        super();
        filtrate();
    }
    
    public SumFunction(ArrayList<SmoothFunction<E>> functionList){
        this.functionList.addAll(functionList);
        filtrate();
    }
    
    public SumFunction(SmoothFunction<E>... args){
        functionList.addAll(Arrays.asList(args));
        filtrate();
    }
    
    private void filtrate(){
        for(int i = 0; i < functionList.size(); i++)
            if(functionList.get(i) == null)
                functionList.remove(i--);
    }
    
    public ArrayList<SmoothFunction<E>> getFunctionList(){
        return functionList;
    }
    
    public Boolean isNull(){
        return functionList == null || functionList.size() == 0;
    }
    
    @Override
    public Double evaluate(E x){
        double sum = 0;
        if(isNull()) return 0.0;
        for(int i = 0; i < functionList.size(); i++) 
            sum += functionList.get(i).evaluate(x);
        return sum;
    }
    
    @Override
    public SumFunction<E> derivative(int index){
        ArrayList<SmoothFunction<E>> newFunctionList = new ArrayList<>();
        if(isNull()) return null;
        for(int i = 0; i < functionList.size(); i++)
            newFunctionList.add(functionList.get(i).derivative(index));
        return(new SumFunction<E>(newFunctionList));
    }
    
    @Override
    public String toString(){
        String string = "";
        if(isNull()) return "0";
        for(int i = 0; i < functionList.size() - 1; i++)
            string = string + functionList.get(i).toString() + " + ";
        string = string + functionList.get(functionList.size() - 1).toString();
        return string;
    }

}
