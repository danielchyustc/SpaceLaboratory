/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.physics.field;

import udf.mathType.RealVector3D;
import udf.physics.model.pointModel.PointModel;

/**
 *
 * @author DELL
 */
public class GravField extends Field<PointModel> {
    final public static double g = 9.80665;

    @Override
    public RealVector3D acceleration(PointModel A) {
        return new RealVector3D(0, -g, 0);
    }

    @Override
    public double potential(PointModel A) {
        return g * A.getPosition().y();
    }
    
}
