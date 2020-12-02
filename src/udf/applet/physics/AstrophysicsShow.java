/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.applet.physics;

import udf.physics.model.PhysicsPane;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import udf.physics.field.*;
import udf.physics.model.pointModel.PointModel;
import udf.inputer.PointModelInputPane;
import udf.physics.model.pointModel.MassPoint;

/**
 *
 * @author DELL
 */
public class AstrophysicsShow {
    final private Font font = Font.font("Times New Roman", 25);
    
    private BorderPane bdPane = new BorderPane();
    private PhysicsPane frame = new PhysicsPane(new UniGravField(new MassPoint(2000)));
    private VBox vBox = new VBox(20);
    private PointModelInputPane PMInputer = new PointModelInputPane();
    private ColorPicker colorPicker = new ColorPicker(Color.BLUE);
    
    public AstrophysicsShow(Field field){
        frame = new PhysicsPane(field);
        layout();    
    }
    
    public BorderPane getPane(){
        return bdPane;
    }
    
    
    private void layout(){
        bdPane.setCenter(frame);
        PMInputer = new PointModelInputPane();
        Text inputParticleText = new Text("Input Particles:");
        inputParticleText.setFont(Font.font("Times New Roman", 40));
        PMInputer.pointModelProperty().addListener(
                (ObservableValue<? extends PointModel> observable, PointModel oldValue, PointModel newValue) -> {
                    if(newValue != null && frame.getDotCount() < 6){
                        frame.addParticle(newValue, colorPicker.getValue(), true);
                    }
        });
        
         Text particleNumText = new Text("particle number:");
         particleNumText.setFont(font);
         Text particleNumber = new Text();
         particleNumber.setFont(font);
         particleNumber.textProperty().bind(frame.particleCountProperty().asString());
         HBox particleNumHBox = new HBox(10);
         particleNumHBox.setPadding(new Insets(10, 10, 10, 10));
         particleNumHBox.getChildren().addAll(particleNumText, particleNumber);
         VBox vBox = new VBox(20);
         vBox.getChildren().addAll(inputParticleText, PMInputer, colorPicker, particleNumHBox, frame.getParticleVBox());
         bdPane.setRight(vBox);
    }
}
