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
public class FreeSpace extends Field<PointModel> {

    @Override
    public RealVector3D acceleration(PointModel A) {
        return RealVector3D.ZERO;
    }

    @Override
    public double potential(PointModel A) {
        return 0;
    }
    
}
