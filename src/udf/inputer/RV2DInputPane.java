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
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import udf.mathType.MathUdf;
import udf.mathType.RealVector2D;

/**
 *
 * @author DELL
 */
public class RV2DInputPane extends VBox {
    private GridPane basicButtonPane = new GridPane();
    private Text xText = new Text("x:");
    private Text yText = new Text("y:");
    private Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
    private TextField xInputBox = new TextField();
    private TextField yInputBox = new TextField();
    private Button enterButton = new Button("Enter");
    private Button deleteButton = new Button("Delete");
    private Button foldButton = new Button("Unfold");
    private int xInsertIndex = 0;
    private int yInsertIndex = 0;
    private int insertOne = 1;
    private RealVector2D vector;
    private ObjectProperty<RealVector2D> vectorProperty = new SimpleObjectProperty<>();
    private String xName = "x";
    private String yName = "y";
    
    public RV2DInputPane(){
        setLayout();  
    }
    
    public RV2DInputPane(String xName, String yName){
        this.xName = xName;
        this.yName = yName;
        setLayout();  
    }
    
    private void setLayout(){
        setWidth(260);
        setHeight(350);
        setMaxWidth(260);
        setMaxHeight(350);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setSpacing(20);
        setEnterButton();
        setDeleteButton();
        setFoldButton();
        setInputBox();
        setBasicButtonPane();
    }
    
    private void setInputBox(){
        setXInputBox();
        setYInputBox();
        xText.setFont(font);
        xText.setText(xName + ":");
        yText.setFont(font);
        yText.setText(yName + ":");
        this.getChildren().add(new HBox(10, new VBox(10, new HBox(10, xText, xInputBox), new HBox(10, yText, yInputBox)), 
                new VBox(10, enterButton, deleteButton, foldButton)));
    }
    
    private void setXInputBox(){
        xInputBox.setPrefSize(120, 40);
        xInsertIndex = xInputBox.getLength();
        xInputBox.setOnMouseClicked(e -> {
            xInsertIndex = xInputBox.getCaretPosition();
            insertOne = 1;
        });
        xInputBox.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) enterAction();
        });
    }
    
    private void setYInputBox(){
        yInputBox.setPrefSize(120, 40);
        yInsertIndex = yInputBox.getLength();
        yInputBox.setOnMouseClicked(e -> {
            yInsertIndex = yInputBox.getCaretPosition();
            insertOne = 2;
        });
        yInputBox.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) enterAction();
        });
    }
    
    private void setEnterButton(){
        enterButton.setPrefSize(80, 25);
        enterButton.setOnAction(e -> enterAction());
    }
    
    private void setDeleteButton(){
        deleteButton.setPrefSize(80, 25);
        deleteButton.setStyle("-fx-background-color: #FF3333;");
        deleteButton.setOnAction(e -> {
            if(insertOne == 1 && xInsertIndex > 0) xInputBox.deleteText(xInsertIndex - 1, xInsertIndex--);
            if(insertOne == 2 && yInsertIndex > 0) yInputBox.deleteText(yInsertIndex - 1, yInsertIndex--);
                });
    }
    
    private void setFoldButton(){
        foldButton.setPrefSize(80, 25);
        foldButton.setOnAction(e -> {
            if(foldButton.getText().equals("Unfold")){
                this.getChildren().add(basicButtonPane);
                foldButton.setText("Fold");
            }
            else{
                this.getChildren().remove(basicButtonPane);
                foldButton.setText("Unfold");
            }
        });
    }
    
    private void setBasicButtonPane(){
        basicButtonPane.setAlignment(Pos.CENTER);
        basicButtonPane.setHgap(10);
        basicButtonPane.setVgap(10);
        for(int i = 1; i < 10; i++)
            addBasicButton(i + "", (i - 1) % 3, 2 - (i - 1) / 3);
        addBasicButton("0", 0, 3);
        addBasicButton(".", 1, 3);
        addBasicButton("\u03C0", 2, 3);
        addBasicButton("+", 4, 0);
        addBasicButton("-", 4, 1);
        addBasicButton("*", 4, 2);
        addBasicButton("/", 4, 3);
        addBasicButton("^", 3, 0);
        addBasicButton("(", 3, 1);
        addBasicButton(")", 3, 2);
        addBasicButton("e", 3, 3);
    }
    
    private void addBasicButton(String text, int columnIndex, int rowIndex){
        Button bt = new Button(text);
        bt.setPrefSize(40, 40);
        bt.setOnAction(e -> {
            if(insertOne == 1) xInputBox.insertText(xInsertIndex++, text);
            if(insertOne == 2) yInputBox.insertText(yInsertIndex++, text);
                });
        basicButtonPane.add(bt, columnIndex, rowIndex);
    }
    
    private void enterAction(){
        vector = new RealVector2D(MathUdf.stringToDouble(xInputBox.getText()), MathUdf.stringToDouble(yInputBox.getText()));
        vectorProperty.setValue(vector);
    }
    
    public RealVector2D getVector(){
        return vector;
    }
    
    public ObjectProperty<RealVector2D> vectorProperty(){
        return vectorProperty;
    }
}
