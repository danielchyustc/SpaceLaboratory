/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.applet.physics;

import udf.physics.model.PhysicsPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import udf.coordinates.Coordinates2D;
import udf.coordinates.Polar2DPane;
import udf.coordinates.Rect2DPane;
import udf.function.StringToFunction;
import udf.mathType.Interval;
import udf.mathType.RealVector3D;
import udf.physics.field.*;
import udf.physics.model.pointModel.MassPoint;
import udf.physics.model.pointModel.PointModel;

/**
 *
 * @author DELL
 */
public class PhysicsShowEx {
    private Color[] colorSet = {Color.BLUE, Color.CORAL, Color.GOLD, Color.CHARTREUSE, Color.AQUA, Color.BLUEVIOLET};
    private ObservableList<Color> colorList = FXCollections.observableArrayList(colorSet);
    final private ObservableList<String> items = FXCollections.observableArrayList(
            "Example 1", "Example 2", "Example 3", "Example 4");
    
    private BorderPane bdPane = new BorderPane();
    private ListView listView = new ListView(items);
    private PhysicsPane pane = new PhysicsPane();
    
    public PhysicsShowEx(){
        setListView();
        setPane1();
        bdPane.setLeft(listView);
        bdPane.setCenter(pane);
        bdPane.setRight(pane.getParticleVBox());
    }
    
    public BorderPane getPane(){
        return bdPane;
    }
    
    private void setListView(){
        listView.setPrefWidth(100);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.getSelectionModel().selectFirst();
        listView.getSelectionModel().selectedItemProperty().addListener(ov -> {
            setPane(listView.getSelectionModel().getSelectedIndex());
            bdPane.setCenter(pane);
            bdPane.setRight(pane.getParticleVBox());
        });
    }
    
    private void setPane(int index){
        switch(index){
            case 0: setPane1(); break;
            case 1: setPane2(); break;
            case 2: setPane3(); break;
            case 3: setPane4(); break;
            default:
        }
    }
    
    private void setPane1(){
        pane = new PhysicsPane(new UniGravField(new MassPoint(2000)));
        pane.addAll(colorList, true, new MassPoint(1, new RealVector3D(3, 5, 4), new RealVector3D(-18, 15, -14)),
                new MassPoint(1, new RealVector3D(4, 5, -6), new RealVector3D(15, -17, 16)),
                new MassPoint(1, new RealVector3D(-8, 7, 9), new RealVector3D(12, 9, -10)),
                new MassPoint(1, new RealVector3D(11, -3, 13), new RealVector3D(-10, -10, 9)));
        
    }
    
    private void setPane2(){
        pane = new PhysicsPane(new UniGravField(new MassPoint(1000)));
        pane.addAll(colorList, true, new MassPoint(1, new RealVector3D(10, 0, 10), new RealVector3D(8, 10, 0)),
                new MassPoint(1, new RealVector3D(0, 6, 10), new RealVector3D(10, 7, -0.3)),
                new MassPoint(1, new RealVector3D(8, 4, 2), new RealVector3D(9, -12, 0.2)));
    }
    
    private void setPane3(){
        pane = new PhysicsPane(new GravField());
        pane.addAll(colorList, false, new MassPoint(1, new RealVector3D(1, 3, 4)),
                new MassPoint(10, new RealVector3D(-1, 3, -4)),
                new MassPoint(5, new RealVector3D(2, 2, 2), new RealVector3D(1, 2, 3)));
    }
    
    private void setPane4(){
        pane = new PhysicsPane(new UniAccelField(new RealVector3D(3, 3, 3)));
        pane.addAll(colorList, false, new MassPoint(1, new RealVector3D(1, 3, 4)),
                new MassPoint(10, new RealVector3D(-1, 3, -4)),
                new MassPoint(5, new RealVector3D(2, 2, 2), new RealVector3D(1, 2, 3)));
    }

}
