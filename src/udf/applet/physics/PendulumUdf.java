/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.applet.physics;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import udf.coordinates.*;
import udf.function.*;
import udf.inputer.functionInputer.FIP;
import udf.inputer.functionInputer.FunctionInputPane;
import udf.mathType.*;
import udf.physics.model.Pendulum;
import udf.physics.field.*;
import udf.physics.model.pointModel.MassPoint;
import udf.mathType.Interval;

/**
 *
 * @author DELL
 */
public class PendulumUdf {
    final private String[] ropeTypeSet = new String[2];
    final private Font font = Font.font("Times New Roman", 25);
    
    private Pendulum pendulum = new Pendulum(new MassPoint(1, new RealVector3D(10, 5, 0)));
    final private BorderPane bdPane = new BorderPane();
    private VBox vBox = new VBox(10);
    private FunctionInputPane<RealVector2D> limitInputer = new FunctionInputPane<>("f", "x", "y");
    private ComboBox<String> rtBox = new ComboBox<>();
    private Text introText = new Text("Pendulum with String Rope");
    private Group infoGroup = new Group();
    
    public PendulumUdf(){
        
        layout();
    }
    
    public BorderPane getPane(){
        return bdPane;
    }
    
    private void layout(){
        ropeTypeSet[0] = "Spring Rope";
        ropeTypeSet[1] = "Elastic Rope";
        bdPane.setCenter(pendulum.getFrame());
        introText.setFont(Font.font("Times New Roman", 40));
        Text ropeType = new Text("Rope Type:");
        ropeType.setFont(Font.font("Times New Roman", 30));
        rtBox.setPrefSize(220, 40);
        rtBox.setValue(ropeTypeSet[0]);
        rtBox.getItems().addAll(ropeTypeSet);
        rtBox.setOnAction(e -> {
            introText.setText("Pendulum with " + rtBox.getValue());
            vBox.getChildren().remove(3);
            switch(FXCollections.observableArrayList(ropeTypeSet).indexOf(rtBox.getValue())){
                case 0: pendulum.getPendulum().setField(new SpringField(2, 5)); break;
                case 1: pendulum.getPendulum().setField(new ElasticRopeField(2, 5)); break;
                default:
            }
            vBox.getChildren().add(pendulum.getPendulumVBox());
        });
        vBox.getChildren().addAll(introText, new HBox(10, ropeType, rtBox), limitInputer, pendulum.getPendulumVBox());
        bdPane.setRight(vBox);
        setLimitInputer();
    }
    
    private void setLimitInputer(){
        limitInputer.functionProperty().addListener(
            (ObservableValue<? extends StringToFunction<RealVector2D>> observable, StringToFunction<RealVector2D> oldValue, StringToFunction<RealVector2D> newValue) -> {
                if(newValue.getFunction() != null){
                    pendulum.clearTrace();
                    pendulum.getFrame().clearCurve();
                    pendulum.getFrame().drawImplicitCurve2(new Interval(-25, 25), new Interval(-25, 25), 
                            newValue.add(-newValue.evaluate(pendulum.getPendulum().getPosition().projection(0, 1))), Color.DEEPPINK);
                    pendulum.getPendulum().setLimit(new StringToFunction<>(limitInputer.getEnterString(), "x", "y", "z"));
                }
        }); 
    }
    
    private void addInfo(SmoothFunction<RealVector2D> function){
        infoGroup.getChildren().clear();
    }
}
