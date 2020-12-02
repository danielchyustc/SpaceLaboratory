/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.mathType;

/**
 *
 * @author DELL
 */
public class Interval {
    final public static Interval R = new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    final public static Interval ZERO = new Interval();
    
    private double startNumber = 0;
    private double endNumber = 0;
    
    public Interval(){
        
    }
    
    public Interval(double number){
        setInterval(number, number);
    }
    
    public Interval(double startNumber, double endNumber){
        setInterval(Math.min(startNumber, endNumber), Math.max(startNumber, endNumber));
    }
    
    private void setInterval(double startNumber, double endNumber){
        this.startNumber = startNumber;
        this.endNumber = endNumber;
    }
    
    public double getStartNumber(){
        return startNumber;
    }
    
    public double getEndNumber(){
        return endNumber;
    }
    
    public boolean isNull(){
        return measure() == 0;
    }
    
    public double measure(){
        return endNumber - startNumber;
    }
    
    
    public boolean isPoint(){
        return startNumber == endNumber;
    }
    
    public boolean equals(Interval other){
        return(startNumber == other.startNumber && endNumber == other.endNumber);
    }
    
    public boolean contains(Double number){
        return(number > startNumber && number < endNumber);
    }
    
    public boolean contains(Interval other){
        return(other.startNumber > startNumber && other.endNumber < endNumber);
    }
    
    @Override
    public String toString(){
        return "[" + startNumber + ',' + endNumber + ']';
    }
    
}