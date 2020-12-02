/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.inputer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import udf.mathType.*;
import udf.physics.model.pointModel.PointModel;

/**
 *
 * @author DELL
 */
public class PointModelInputPane extends GridPane {
    private Font font = Font.font("Times New Roman", FontPosture.ITALIC, 25);
    private TextField xInputBox = new TextField();
    private TextField yInputBox = new TextField();
    private TextField zInputBox = new TextField();
    private TextField vxInputBox = new TextField();
    private TextField vyInputBox = new TextField();
    private TextField vzInputBox = new TextField();
    private TextField mInputBox = new TextField();
    private TextField eInputBox = new TextField();
    private Button enterButton = new Button("Enter");
    private PointModel A;
    private ObjectProperty<PointModel> pointModelProperty = new SimpleObjectProperty<>();
    
    public PointModelInputPane(){
        setLayout();  
    }
    
    private void setLayout(){
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 10, 10));
        setHgap(10);
        setVgap(10);
        setEnterButton();
        setInputBox();
    }
    
    private void setInputBox(){
        Text xText = new Text("x:");    Text yText = new Text("y:");    Text zText = new Text("z:");    
        Text vxText = new Text("vx:");  Text vyText = new Text("vy:");  Text vzText = new Text("vz:");
        Text mText = new Text("m:");    Text eText = new Text("e:");
        xText.setFont(font);    yText.setFont(font);    zText.setFont(font);
        vxText.setFont(font);   vyText.setFont(font);   vzText.setFont(font);
        mText.setFont(font);    eText.setFont(font);
        setInputBox(xInputBox); setInputBox(yInputBox); setInputBox(zInputBox);
        setInputBox(vxInputBox); setInputBox(vyInputBox); setInputBox(vzInputBox);
        setInputBox(mInputBox); setInputBox(eInputBox);
        this.add(mText, 0, 0);  this.add(mInputBox, 1, 0);  this.add(eText, 2, 0);  this.add(eInputBox, 3, 0);  this.add(enterButton, 5, 0);
        this.add(xText, 0, 1);  this.add(xInputBox, 1, 1);  this.add(yText, 2, 1);  this.add(yInputBox, 3, 1);  this.add(zText, 4, 1);  this.add(zInputBox, 5, 1);
        this.add(vxText, 0, 2); this.add(vxInputBox, 1, 2); this.add(vyText, 2, 2); this.add(vyInputBox, 3, 2); this.add(vzText, 4, 2); this.add(vzInputBox, 5, 2);
    }
    
    private void setInputBox(TextField inputBox){
        inputBox.setPrefSize(60, 30);
        inputBox.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) enterAction();
        });
    }
    
    private void setEnterButton(){
        enterButton.setPrefSize(60, 30);
        enterButton.setOnAction(e -> enterAction());
    }
    
    private void enterAction(){
        A = new PointModel(MathUdf.stringToDouble(mInputBox.getText()), MathUdf.stringToDouble(eInputBox.getText()), 
        new RealVector3D(MathUdf.stringToDouble(xInputBox.getText()), MathUdf.stringToDouble(yInputBox.getText()), MathUdf.stringToDouble(zInputBox.getText())),
        new RealVector3D(MathUdf.stringToDouble(vxInputBox.getText()), MathUdf.stringToDouble(vyInputBox.getText()), MathUdf.stringToDouble(vzInputBox.getText())));
        pointModelProperty.setValue(A);
    }
    
    public PointModel getPointModel(){
        return A;
    }
    
    public ObjectProperty<PointModel> pointModelProperty(){
        return pointModelProperty;
    }
}
