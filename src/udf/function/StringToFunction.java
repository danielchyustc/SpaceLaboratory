/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.function;

import udf.function.composite.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import udf.function.simple.BasicFunction;
import udf.function.simple.ConstantFunction;
import udf.mathType.RealVector;

/**
 *
 * @author DELL
 */
public class StringToFunction<E extends RealVector> extends SmoothFunction<E> {
    public static String[] functionNames = {"exp", "log", "sqrt", "sin", "cos", "tan", "asin", "acos", "atan", "sinh", "cosh", "tanh"};
    public static ArrayList<String> functionList = new ArrayList<>(Arrays.asList(functionNames));
    
    private SmoothFunction<E> function;
    private ArrayList<String> names = new ArrayList<>();
    
    public StringToFunction(String string, String...args){
        this.names.addAll(Arrays.asList(args));
        function = process1(string);
    }
    
    public StringToFunction(String string, ArrayList<String> list){
        this.names = list;
        function = process1(string);
    }

    @Override
    public Double evaluate(E x) {
        if(function == null) return Double.NaN;
        return function.evaluate(x);
    }

    @Override
    public SmoothFunction derivative(int index) {
        if(function == null) return new ConstantFunction<>(Double.NaN);
        return function.derivative(index);
    }
    
    public SmoothFunction getFunction(){
        return function;
    }
    
    
    private SmoothFunction process1(String string){
        ArrayList<SmoothFunction> funcList = new ArrayList<>();
        string = string.trim();
        if(string.equals(null) || string.equals("")) return null;
        if(!checkChar(string)) return null;
        if(!checkBrackets(string)) return null; 
        ArrayList<String> sumList = sumPartition(string);
        if(sumList.size() == 0) return null;
        for(int k = 0; k < sumList.size(); k++) funcList.add(process2(sumList.get(k)));
        return new SumFunction(funcList);
    }
    
    private ArrayList<String> sumPartition(String string){
        ArrayList<String> list = new ArrayList<>();
        string = string.trim();
        int i = 0;
        while(i < string.length() && string.charAt(i) != '+' && string.charAt(i) != '-')
            if(string.charAt(i) == '(') i += transBracketPos(string.substring(i)) + 1;
            else i++;
        if(i != 0) list.add(string.substring(0, i).trim());
        if(i != string.length())
            if(string.charAt(i) == '+') list.addAll(sumPartition(string.substring(i + 1).trim()));
            else list.addAll(sumPartition("_" + string.substring(i + 1).trim()));
        return list;
    }
    
    private SmoothFunction process2(String string){
        string = string.trim();
        if(string.charAt(0) == '_') return process2(string.substring(1)).minus();
        ArrayList<String> productList = productPartition(string);
        if(productList.size() == 0) return null;
        ArrayList<SmoothFunction> funcList = new ArrayList<>();
        for(int k = 0; k < productList.size(); k++) funcList.add(process3(productList.get(k)));
        return new ProductFunction(funcList);
    }
    
    private ArrayList<String> productPartition(String string){
        ArrayList<String> list = new ArrayList<>();
        string = string.trim();
        int i = 0;
        while(i < string.length() && string.charAt(i) != '*' && string.charAt(i) != '/') 
            if(string.charAt(i) == '(') i += transBracketPos(string.substring(i)) + 1;
            else i++;
        if(i != 0) list.add(string.substring(0, i).trim());
        if(i != string.length())
            if(string.charAt(i) == '*') list.addAll(productPartition(string.substring(i + 1).trim()));
            else list.addAll(productPartition("\\" + string.substring(i + 1).trim()));
        return list;
    }
    
    private SmoothFunction process3(String string){
        string = string.trim();
        if(string.charAt(0) == '\\') return process3(string.substring(1)).reciprocal();
        ArrayList<String> powerList = powerPartition(string);
        if(powerList.size() != 1 && powerList.size() !=2 ) return null;
        if(powerList.size() == 1) return process4(powerList.get(0));
        ArrayList<SmoothFunction> funcList = new ArrayList<>();
        funcList.add(process4(powerList.get(0)));
        funcList.add(process4(powerList.get(1)));
        return new PowerFunction(funcList.get(0), funcList.get(1));
    }
    
    private ArrayList<String> powerPartition(String string){
        ArrayList<String> list = new ArrayList<>();
        string = string.trim();
        int i = 0;
        while(i < string.length() && string.charAt(i) != '^') 
            if(string.charAt(i) == '(') i += transBracketPos(string.substring(i)) + 1;
            else i++;
        if(i != 0) list.add(string.substring(0, i).trim());
        if(i != string.length()) list.addAll(powerPartition(string.substring(i + 1).trim()));
        return list;
    }
    
    private SmoothFunction process4(String string){
        int i = 0;
        string = string.trim();
        for(int j = 0; j < names.size(); j++)
            if(string.equals(names.get(j))) return new BasicFunction<>(j + 1);
        if(string.equals("e")) return new ConstantFunction<>(Math.E);
        if(string.equals("pi") || string.equals("\u03C0")) return new ConstantFunction<>(Math.PI);
        if(string.charAt(0) == '(') return process1(string.substring(1, string.length() - 1));
        while(i < string.length() && Character.isLetter(string.charAt(i))) i++;
        if(i == 0) return new ConstantFunction<>(Double.parseDouble(string));
        String function1 = string.substring(0, i);
        if(!functionList.contains(function1) || string.charAt(i) != '(') return null;
        int j =  i + transBracketPos(string.substring(i));
        return genFunction(functionList.indexOf(function1), process1(string.substring(i + 1, j)));
    }
    
    private Boolean checkChar(String string){
        for(int i = 0; i < string.length(); i++){
            char c = string.charAt(i);
            if(!Character.isLetter(c) && !Character.isDigit(c))
                if(c!=' ' && c!='.' && c!='(' && c!=')')
                    if(c!='+' && c!='-' && c!='*' && c!='/' && c!='^')
                        return false;
        }
        return true;
    }
    
    private Boolean checkBrackets(String string){
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < string.length(); i++)
            if(string.charAt(i) == '(') stack.add('(');
            else if(string.charAt(i) == ')')
                if(stack.empty()) return false;
                else stack.pop();
        return stack.empty();
    }
    
    private int transBracketPos(String string){
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < string.length(); i++)
            if(string.charAt(i) == '(') stack.add('(');
            else if(string.charAt(i) == ')')
                if(stack.size() != 1) stack.pop();
                else return i;
        return 0;
    }
    
    private SmoothFunction genFunction(int index, SmoothFunction function){
        switch(index){
            case 0: return new ExpFunction<>(function);
            case 1: return new LogFunction<>(function);
            case 2: return new SqrtFunction<>(function);
            case 3: return new SinFunction<>(function);
            case 4: return new CosFunction<>(function);
            case 5: return new TanFunction<>(function);
            case 6: return new AsinFunction<>(function);
            case 7: return new AcosFunction<>(function);
            case 8: return new AtanFunction<>(function);
            case 9: return new SinhFunction<>(function);
            case 10: return new CoshFunction<>(function);
            case 11: return new TanhFunction<>(function);
            default: return null;
        }
    }
    
}
