/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.physics.field;

import udf.function.SmoothFunction;
import udf.function.simple.ConstantFunction;
import udf.mathType.RealVector3D;
import udf.physics.model.pointModel.PointModel;

/**
 *
 * @author DELL
 */
public class UdfField extends Field {
    private SmoothFunction<RealVector3D> function1;
    private SmoothFunction<RealVector3D> function2;
    private SmoothFunction<RealVector3D> function3;
    
    public UdfField(SmoothFunction<RealVector3D> function1, SmoothFunction<RealVector3D> function2, SmoothFunction<RealVector3D> function3){
        this.function1 = function1;
        this.function2 = function2;
        this.function3 = function3;
    }

    @Override
    public RealVector3D acceleration(PointModel A) {
        return new RealVector3D(function1.evaluate(A.getPosition()),
                function2.evaluate(A.getPosition()),
                function3.evaluate(A.getPosition())
        );
    }

    @Override
    public double potential(PointModel A) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
