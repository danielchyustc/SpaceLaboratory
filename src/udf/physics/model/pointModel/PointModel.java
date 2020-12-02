/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.physics.model.pointModel;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import udf.function.SmoothFunction;
import udf.mathType.RealVector;
import udf.mathType.RealVector3D;
import udf.physics.field.*;

/**
 *
 * @author DELL
 */
public class PointModel {
    protected double mass = 1;
    protected double charge = 0;
    protected RealVector3D position = RealVector3D.ZERO;
    protected RealVector3D velocity = RealVector3D.ZERO;
    protected ObjectProperty<RealVector3D> positionProperty = new SimpleObjectProperty<>(RealVector3D.ZERO);
    protected ObjectProperty<RealVector3D> velocityProperty = new SimpleObjectProperty<>(RealVector3D.ZERO);
    protected ObjectProperty<RealVector3D> momentumProperty = new SimpleObjectProperty<>(RealVector3D.ZERO);
    protected ObjectProperty<RealVector3D> angularMomentumProperty = new SimpleObjectProperty<>(RealVector3D.ZERO);
    protected DoubleProperty energyProperty = new SimpleDoubleProperty();
    protected DoubleProperty kineticEnergyProperty = new SimpleDoubleProperty();
    protected DoubleProperty potentialEnergyProperty = new SimpleDoubleProperty();
    protected Field field = new FreeSpace();
    protected ArrayList<SmoothFunction<RealVector3D>> limitList = new ArrayList<>();
    protected int limitNumber = 0;
    
    
    public PointModel(){
        setProperties();
    }
    
    public PointModel(double mass){
        this.mass = mass;
        setProperties();
    }
    
    public PointModel(double mass, double charge){
        this.mass = mass;
        this.charge = charge;
        setProperties();
    }
    
    public PointModel(double mass, RealVector3D position){
        this.mass = mass;
        this.position = position;
        setProperties();
    }
    
    public PointModel(double mass, double charge, RealVector3D position){
        this.mass = mass;
        this.charge = charge;
        this.position = position;
        setProperties();
    }
    
    public PointModel(double mass, RealVector3D position, RealVector3D velocity){
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
        setProperties();
    }
    
    public PointModel(double mass, double charge, RealVector3D position, RealVector3D velocity){
        this.mass = mass;
        this.charge = charge;
        this.position = position;
        this.velocity = velocity;
        setProperties();
    }
    
    public void setProperties(){
        positionProperty.setValue(position);
        velocityProperty.setValue(velocity);
        momentumProperty.setValue(velocity.multiply(mass));
        angularMomentumProperty.setValue(position.crossProduct(velocity).multiply(mass));
        kineticEnergyProperty.setValue((double)Math.round(mass * velocity.normSquare() / 2 * 100) / 100);
        potentialEnergyProperty.setValue((double)Math.round(field.potentialEnergy(this) * 100) / 100);
        energyProperty.setValue(kineticEnergy() + potentialEnergy());
    }
    
    public double getMass(){
        return mass;
    }
    
    public void setMass(double mass){
        this.mass = mass;
    }
    
    public double getCharge(){
        return charge;
    }
    
    public void setCharge(double charge){
        this.charge = charge;
    }
    
    public void setPosition(RealVector3D position){
        this.position = position;
    }
    
    public RealVector3D getPosition(){
        return position;
    }
    
    public ObjectProperty<RealVector3D> positionProperty(){
        return positionProperty;
    }
    
    public void displace(RealVector3D displacement){
        position = position.add(displacement);
    }
    
    public void accelerate(RealVector3D acceleration){
        velocity = velocity.add(acceleration);
    }
    
    public void setVelocity(RealVector3D velocity){
        this.velocity = velocity;
    }
    
    
    public RealVector3D getVelocity(){
        return velocity;
    }
    
    public ObjectProperty<RealVector3D> velocityProperty(){
        return velocityProperty;
    }
    
    public RealVector3D force(){
        return field.force(this);
    }
    
    public Field getField(){
        return field;
    }
    
    public void setField(Field field){
        this.field = field;
    }
    
    public void setLimit(SmoothFunction<RealVector3D>...args){
        limitList.clear();
        for (SmoothFunction<RealVector3D> arg : args) limitList.add(arg);
        limitNumber = args.length;
    }
    
    public RealVector3D momentum(){
        return momentumProperty.getValue();
    }
    
    public ObjectProperty<RealVector3D> momentumProperty(){
        return momentumProperty;
    }
    
    public RealVector3D angularMomentum(){
        return angularMomentumProperty.getValue();
    }
    
    public ObjectProperty<RealVector3D> angularMomentumProperty(){
        return angularMomentumProperty;
    }
    
    public double kineticEnergy(){
        return kineticEnergyProperty.getValue();
    }
    
    public DoubleProperty kineticEnergyProperty(){
        return kineticEnergyProperty;
    }
    
    public double potentialEnergy(){
        return potentialEnergyProperty.getValue();
    }
    
    public DoubleProperty potentialEnergyProperty(){
        return potentialEnergyProperty;
    }
    
    public double energy(){
        return energyProperty.getValue();
    }
    
    public DoubleProperty energyProperty(){
        return energyProperty;
    }
    
    public RealVector3D acceleration(){
        return field.acceleration(this);
    }
    
    public void develop(double timeInterval){
        for(int i = 0; i < 100; i++) linearDevelop(timeInterval / 100);
        setProperties();
    }
    
    public void develop(){
        develop(0.001);
    }
    
    private void linearDevelop(double timeInterval){
        correctVelocity();
        displace(velocity.multiply(timeInterval));
        accelerate(acceleration().multiply(timeInterval));
    }
    
    private void correctVelocity(){
        
        if(limitNumber == 1) {
            RealVector3D v1 = limitList.get(0).normalVector(position).toRealVector3D();
            RealVector3D v2 = velocity.projection(v1);
            RealVector3D v3 = velocity.subtract(v2);
            velocity = v3;
        }
        else if(limitNumber == 2)
            velocity = velocity.projection(limitList.get(0).normalVector(position).toRealVector3D()
                    .crossProduct(limitList.get(1).normalVector(position).toRealVector3D()));
        
        if(Math.abs(position.x()) >= 25) velocity = new RealVector3D(-velocity.x(), velocity.y(), velocity.z());
        if(Math.abs(position.y()) >= 25) velocity = new RealVector3D(velocity.x(), -velocity.y(), velocity.z());
        if(Math.abs(position.z()) >= 25) velocity = new RealVector3D(velocity.x(), velocity.y(), -velocity.z());
    }
    
}
