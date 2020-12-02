/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.applet.physics;

import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import udf.coordinates.*;
import udf.function.*;
import udf.mathType.*;
import udf.physics.model.Pendulum;
import udf.physics.field.*;
import udf.physics.model.pointModel.MassPoint;

/**
 *
 * @author DELL
 */
public class PendulumEx {
    final private String[] coordTypeSet = new String[2];
    final private String[] ropeTypeSet = new String[3];
    final private Font font = Font.font("Times New Roman", 25);
    
    private Pendulum pendulum = new Pendulum(new MassPoint(1, new RealVector3D(10, 5, 0)));
    final private BorderPane bdPane = new BorderPane();
    private VBox vBox = new VBox(10);
    private ComboBox<String> ctBox = new ComboBox<>();
    private ComboBox<String> rtBox = new ComboBox<>();
    private Text introText = new Text("Pendulum with String Rope");
    private Group infoGroup = new Group();
    
    public PendulumEx(){
        
        layout();
    }
    
    public BorderPane getPane(){
        return bdPane;
    }
    
    private void layout(){
        coordTypeSet[0] = "Rectangular";
        coordTypeSet[1] = "Polar";
        ropeTypeSet[0] = "Spring Rope";
        ropeTypeSet[1] = "Elastic Rope";
        ropeTypeSet[2] = "Pole";
        bdPane.setCenter(pendulum.getFrame());
        introText.setFont(Font.font("Times New Roman", 40));
        Text coordType = new Text("Coordinates Type:");
        coordType.setFont(Font.font("Times New Roman", 30));
        ctBox.setPrefSize(220, 40);
        ctBox.setValue(coordTypeSet[0]);
        ctBox.getItems().addAll(coordTypeSet);
        ctBox.setOnAction(e -> {
            switch(FXCollections.observableArrayList(coordTypeSet).indexOf(ctBox.getValue())){
                case 0: pendulum.setFrame(new Rect2DPane()); break;
                case 1: pendulum.setFrame(new Polar2DPane());  break;
                default:
            }
            bdPane.setCenter(pendulum.getFrame());
        });
        rtBox.setPrefSize(220, 40);
        rtBox.setValue(ropeTypeSet[0]);
        rtBox.getItems().addAll(ropeTypeSet);
        rtBox.setOnAction(e -> {
            introText.setText("Pendulum with " + rtBox.getValue());
            vBox.getChildren().remove(2);
            switch(FXCollections.observableArrayList(ropeTypeSet).indexOf(rtBox.getValue())){
                case 0: pendulum.getPendulum().setField(new SpringField(2, 5)); break;
                case 1: pendulum.getPendulum().setField(new ElasticRopeField(2, 5)); break;
                case 2: pendulum.getPendulum().setLimit(new StringToFunction<>("x^2 + y^2", "x", "y", "z")); break;
                default:
            }
            vBox.getChildren().add(pendulum.getPendulumVBox());
        });
        vBox.getChildren().addAll(introText, new HBox(10, coordType, ctBox), pendulum.getPendulumVBox());
        bdPane.setRight(vBox);
        pendulum.getFrame().getChildren().add(rtBox);
        rtBox.setLayoutX(20);
        rtBox.setLayoutY(20);
    }
}
