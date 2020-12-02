/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.inputer.functionInputer;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import udf.function.SmoothFunction;
import udf.inputer.functionInputer.FIP;
import udf.function.StringToFunction;
import udf.mathType.RealNumber;
import udf.mathType.RealVector;
import udf.mathType.RealVector2D;

/**
 *
 * @author DELL
 */
public class FunctionInputPane<E extends RealVector> extends FIP<E> {
    private GridPane basicButtonPane = new GridPane();
    private VBox functionPane = new VBox();
    private ComboBox<String> functionBox = new ComboBox<>();
    private HBox buttonPane = new HBox(20);
    private TextField inputBox = new TextField();
    private Button enterButton = new Button("Enter");
    private Button foldButton = new Button("Unfold");
    private String enterString;
    private StringToFunction<E> function;
    private ObjectProperty<StringToFunction<E>> functionProperty = new SimpleObjectProperty<>();
    private int insertIndex = 0;
    private ArrayList<String> names = new ArrayList<>();
    private String fName = "f";
    
    public FunctionInputPane(){
        setLayout();  
    }
    
    public FunctionInputPane(String fName, String...args){
        this.fName = fName;
        this.names.addAll(Arrays.asList(args));
        setLayout();  
    }
    
    private void setLayout(){
        setWidth(390);
        setHeight(260);
        setMaxWidth(390);
        setMaxHeight(260);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(20);
        setInputBox();
        setEnterButton();
        setFoldButton();
        setBasicButtonPane();
        setFunctionPane();
        functionProperty.setValue(function);
    }
    
    private void setInputBox(){
        String str = names.get(0);
        for(int i = 1; i < names.size(); i++) str += "," + names.get(i);
        Text fxText = new Text(fName + "(" + str + ") = ");
        fxText.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 30));
        inputBox.setPrefSize(150, 40);
        insertIndex = inputBox.getLength();
        inputBox.setOnMouseClicked(e -> {
            insertIndex = inputBox.getCaretPosition();
        });
        inputBox.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) enterAction();
        });
        this.getChildren().add(new HBox(20, new HBox(10, fxText, inputBox), 
                new VBox(10, enterButton, foldButton)));
    }
    
    private void setEnterButton(){
        enterButton.setPrefSize(120, 40);
        enterButton.setOnAction(e -> enterAction());
    }
    
    private void setFoldButton(){
        foldButton.setPrefSize(120, 40);
        foldButton.setOnAction(e -> {
            if(foldButton.getText().equals("Unfold")){
                this.getChildren().add(buttonPane);
                foldButton.setText("Fold");
            }
            else{
                this.getChildren().remove(buttonPane);
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
        addBasicButton("^", 3, 0);
        addBasicButton("(", 3, 1);
        addBasicButton(")", 3, 2);
        addBasicButton("e", 3, 3);
        addBasicButton("+", 4, 0);
        addBasicButton("-", 4, 1);
        addBasicButton("*", 4, 2);
        addBasicButton("/", 4, 3);
    }
    
    private void addBasicButton(String text, int columnIndex, int rowIndex){
        Button bt = new Button(text);
        bt.setPrefSize(40, 40);
        bt.setOnAction(e -> inputBox.insertText(insertIndex++, text));
        basicButtonPane.add(bt, columnIndex, rowIndex);
    }
    
    private Button deleteButton(){
        Button bt = new Button("delete");
        bt.setPrefSize(120, 40);
        bt.setStyle("-fx-background-color: #FF3333;");
        bt.setOnAction(e -> {
            if(insertIndex > 0) inputBox.deleteText(insertIndex - 1, insertIndex--);
                });
        return bt;
    }
    
    private void setFunctionPane(){
        functionPane.setSpacing(10);
        setFunctionBox();
        functionPane.getChildren().addAll(deleteButton(), functionBox);
        for(int i = 0; i < names.size(); i++) functionPane.getChildren().add(varButton(i));
        buttonPane.getChildren().addAll(basicButtonPane, functionPane);
    }
    
    private void setFunctionBox(){
        functionBox.setPrefSize(120, 40);
        functionBox.setValue("function");
        functionBox.getItems().addAll(StringToFunction.functionNames);
        functionBox.setOnAction(e -> {
            inputBox.insertText(insertIndex, functionBox.getValue() + "()");
            insertIndex += functionBox.getValue().length() + 1;
                });
    }
    
    private Button varButton(int i){
        Button bt = new Button(names.get(i));
        bt.setPrefSize(40, 40);
        bt.setOnAction(e -> inputBox.insertText(insertIndex++, names.get(i)));
        return bt; 
    }
    
    private void enterAction(){
        enterString = inputBox.getText().toLowerCase();
        function = new StringToFunction<>(enterString, names);
        functionProperty.setValue(function);
    }
    
    
    public StringToFunction<E> getFunction(){
        return function;
    }
    
    public ObjectProperty<StringToFunction<E>> functionProperty(){
        return functionProperty;
    }
    
    public String getEnterString(){
        return enterString;
    }
}
