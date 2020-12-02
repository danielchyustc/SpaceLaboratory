/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function;

import java.util.ArrayList;
import java.util.Arrays;
import udf.mathType.RealVector;
/**
 *
 * @author DELL
 */
public class ProductFunction<E extends RealVector> extends SmoothFunction<E> {
    final private ArrayList<SmoothFunction<E>> functionList = new ArrayList<>();
    
    public ProductFunction(){
        super();
        filtrate();
    }
    
    public ProductFunction(ArrayList<SmoothFunction<E>> functionList){
        this.functionList.addAll(functionList);
        filtrate();
    }
    
    public ProductFunction(SmoothFunction<E>... args){
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
        double product = 1;
        if(isNull()) return 1.0;
        for(int i = 0; i < functionList.size(); i++) {
            if(functionList.get(i).evaluate(x) == 0) return 0.0;
            product *= functionList.get(i).evaluate(x);
        }
        return product;
    }
    
    @Override
    public SumFunction derivative(int index){
        ArrayList<SmoothFunction> sumFunctionList = new ArrayList<>();
        for(int i = 0; i < functionList.size(); i++){
            ArrayList<SmoothFunction> productFunctionList = new ArrayList<>();
            for(int j = 0; j < functionList.size(); j++)
                if(j!=i) productFunctionList.add(functionList.get(j));
                else productFunctionList.add(functionList.get(i).derivative(index));
            sumFunctionList.add(new ProductFunction(productFunctionList));
        }
        return(new SumFunction(sumFunctionList));
    }

}
