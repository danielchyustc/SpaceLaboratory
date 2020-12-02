/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.mathType;

import java.util.ArrayList;
import java.util.Stack;
import udf.function.SmoothFunction;

/**
 *
 * @author DELL
 */
public class MathUdf {
    final public static double BIGENOUGH = 10;
    final public static double INFINITESIMAL = 1e-3;
    
    public static boolean sameSign(double a, double b){
        return (a > 0 && b> 0) || (a < 0 && b < 0);
    }
    
    public static boolean isInfinitesimal(double a){
        return Math.abs(a) <= INFINITESIMAL;
    }
    
    public static double polynomial(double x, double...args){
        double sum = 0;
        for(int i = 0; i < args.length; i++)  sum += args[i] * Math.pow(x, i);
        return sum;
    }
    
    public static double sum(ArrayList<Double> list){
        double sum = 0;
        for(int i = 0; i < list.size(); i++) sum += list.get(i);
        return sum;
    }    
    
    public static double sum(double...args){
        double sum = 0;
        for(int i = 0; i < args.length; i++) sum += args[i];
        return sum;
    }
    
    public static double product(ArrayList<Double> list){
        double product = 1;
        for(int i = 0; i < list.size(); i++) product *= list.get(i);
        return product;
    }    
    
    public static double product(double...args){
        double product = 1;
        for(int i = 0; i < args.length; i++) product *= args[i];
        return product;
    }
    
    
    public static double oneRoot(SmoothFunction<RealNumber> function, Interval interval){
        double start = interval.getStartNumber();
        double end = interval.getEndNumber();
        if(start == Double.NEGATIVE_INFINITY) return oneRoot(function, new Interval(-MathUdf.BIGENOUGH, end));
        if(end == Double.POSITIVE_INFINITY) return oneRoot(function, new Interval(start, MathUdf.BIGENOUGH));
        if(MathUdf.sameSign(function.evaluate(new RealNumber(start)), function.evaluate(new RealNumber(end)))) return Double.NaN;
        if(MathUdf.isInfinitesimal(function.evaluate(new RealNumber(start)))) return start;
        if(MathUdf.isInfinitesimal(function.evaluate(new RealNumber(end)))) return end;
        double mid = (start + end) / 2;
        while(!MathUdf.isInfinitesimal(function.evaluate(new RealNumber(mid)))){
            if(MathUdf.sameSign(function.evaluate(new RealNumber(mid)), function.evaluate(new RealNumber(end)))){
                end = mid;
                mid = (start + end) / 2;
            }
            else{
                start = mid;
                mid = (start + end) / 2;
            }
        }
        return mid;
    }
    
    public static ArrayList<Double> root(SmoothFunction<RealNumber> function, Interval interval){
        ArrayList<Double> rootSet = new ArrayList<>();
        if(interval.getStartNumber() == Double.NEGATIVE_INFINITY) return root(function, new Interval(-MathUdf.BIGENOUGH, interval.getEndNumber()));
        if(interval.getEndNumber() == Double.POSITIVE_INFINITY) return root(function, new Interval(interval.getStartNumber(), MathUdf.BIGENOUGH));
        for(int i = 0; i < 100; i++){
            double start = interval.getStartNumber() + interval.measure() / 100 * i;
            double end = interval.getStartNumber() + interval.measure() / 100 * (i + 1);
            if(!MathUdf.sameSign(function.evaluate(new RealNumber(start)), function.evaluate(new RealNumber(end))))
                rootSet.add(oneRoot(function, new Interval(start, end)));
        }
        //if(rootSet.isEmpty()) rootSet.add(Double.NaN);
        return rootSet;
    }
    
    public static ArrayList<Double> root(SmoothFunction<RealNumber> function){
        return root(function, Interval.R);
    }
    
    public static double stringToDouble(String string){
        return process1(string.replaceAll("pi", "\u03C0"));
    }
    
    
    private static double process1(String string){
        ArrayList<Double> numList = new ArrayList<>();
        string = string.trim();
        if(string.equals(null) || string.equals("")) return Double.NaN;
        if(!checkChar(string)) return Double.NaN;
        if(!checkBrackets(string)) return Double.NaN; 
        ArrayList<String> sumList = sumPartition(string);
        if(sumList.size() == 0) return Double.NaN;
        for(int k = 0; k < sumList.size(); k++) numList.add(process2(sumList.get(k)));
        return sum(numList);
    }
    
    private static ArrayList<String> sumPartition(String string){
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
    
    private static double process2(String string){
        string = string.trim();
        if(string.charAt(0) == '_') return -process2(string.substring(1));
        ArrayList<String> productList = productPartition(string);
        if(productList.size() == 0) return Double.NaN;
        ArrayList<Double> numList = new ArrayList<>();
        for(int k = 0; k < productList.size(); k++) numList.add(process3(productList.get(k)));
        return product(numList);
    }
    
    private static ArrayList<String> productPartition(String string){
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
    
    private static double process3(String string){
        string = string.trim();
        if(string.charAt(0) == '\\') return 1/process3(string.substring(1));
        ArrayList<String> powerList = powerPartition(string);
        if(powerList.size() != 1 && powerList.size() !=2 ) return Double.NaN;
        if(powerList.size() == 1) return process4(powerList.get(0));
        ArrayList<Double> numList = new ArrayList<>();
        numList.add(process4(powerList.get(0)));
        numList.add(process4(powerList.get(1)));
        return Math.pow(numList.get(0), numList.get(1));
    }
    
    private static ArrayList<String> powerPartition(String string){
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
    
    private static double process4(String string){
        string = string.trim();
        if(string.equals("e")) return Math.E;
        if(string.equals("\u03C0")) return Math.PI;
        if(string.charAt(0) == '(') return process1(string.substring(1, string.length() - 1));
        return Double.parseDouble(string);
    }
    
    private static Boolean checkChar(String string){
        for(int i = 0; i < string.length(); i++){
            char c = string.charAt(i);
            if(!Character.isDigit(c))
                if(c!='e' && c!='\u03C0')
                    if(c!=' ' && c!='.' && c!='(' && c!=')')
                        if(c!='+' && c!='-' && c!='*' && c!='/' && c!='^')
                            return false;
        }
        return true;
    }
    
    private static Boolean checkBrackets(String string){
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < string.length(); i++)
            if(string.charAt(i) == '(') stack.add('(');
            else if(string.charAt(i) == ')')
                if(stack.empty()) return false;
                else stack.pop();
        return stack.empty();
    }
    
    private static int transBracketPos(String string){
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < string.length(); i++)
            if(string.charAt(i) == '(') stack.add('(');
            else if(string.charAt(i) == ')')
                if(stack.size() != 1) stack.pop();
                else return i;
        return 0;
    }
    
}
