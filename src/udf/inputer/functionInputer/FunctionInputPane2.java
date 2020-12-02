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
import udf.function.StringToFunction;
import udf.mathType.RealVector;

/**
 *
 * @author DELL
 */
public class FunctionInputPane2<E extends RealVector> extends FIP<E> {
    private GridPane basicButtonPane = new GridPane();
    private VBox functionPane = new VBox();
    private ComboBox<String> functionBox = new ComboBox<>();
    private HBox buttonPane = new HBox(20);
    private TextField f1InputBox = new TextField();
    private TextField f2InputBox = new TextField();
    private Button enterButton = new Button("Enter");
    private Button foldButton = new Button("Unfold");
    private String f1EnterString;
    private String f2EnterString;
    private StringToFunction<E> f1;
    private StringToFunction<E> f2;
    private ObjectProperty<StringToFunction<E>[]> functionProperty = new SimpleObjectProperty<>();
    private int f1InsertIndex = 0;
    private int f2InsertIndex = 0;
    private int insertOne = 1;
    private ArrayList<String> names = new ArrayList<>();
    private String f1Name = "f1";
    private String f2Name = "f2";
    private Text f1Text = new Text("f1(x)=");
    private Text f2Text = new Text("f2(x)=");
    private Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
    
    public FunctionInputPane2(){
        setLayout();  
    }
    
    public FunctionInputPane2(String f1Name, String f2Name, String... args){
        this.names.addAll(Arrays.asList(args));
        this.f1Name = f1Name;
        this.f2Name = f2Name;
        setLayout();  
    }
    
    private void setLayout(){
        setWidth(390);
        setHeight(310);
        setMaxWidth(390);
        setMaxHeight(310);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 10, 10));
        setSpacing(20);
        setInputBox();
        setEnterButton();
        setFoldButton();
        setBasicButtonPane();
        setFunctionPane();
        StringToFunction[] function = {f1, f2};
        functionProperty.setValue(function);
    }
    
    private void setInputBox(){
        String str = names.get(0);
        for(int i = 1; i < names.size(); i++) str += "," + names.get(i);
        f1Text.setText(f1Name + "(" + str + ") = ");
        f2Text.setText(f2Name + "(" + str + ") = ");
        f1Text.setFont(font);
        f2Text.setFont(font);
        setF1InputBox();
        setF2InputBox();
        this.getChildren().add(new HBox(20, new VBox(10, new HBox(f1Text, f1InputBox), new HBox(f2Text, f2InputBox)),
                            new VBox(10, enterButton, foldButton)));
    }
    
    private void setF1InputBox(){
        f1InputBox.setPrefSize(150, 40);
        f1InsertIndex = f1InputBox.getLength();
        f1InputBox.setOnMouseClicked(e -> {
            f1InsertIndex = f1InputBox.getCaretPosition();
            insertOne = 1;
        });
        f1InputBox.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) enterAction();
        });
    }
    
    private void setF2InputBox(){
        f2InputBox.setPrefSize(150, 40);
        f2InsertIndex = f2InputBox.getLength();
        f2InputBox.setOnMouseClicked(e -> {
            f2InsertIndex = f2InputBox.getCaretPosition();
            insertOne = 2;
        });
        f2InputBox.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) enterAction();
        });
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
        bt.setOnAction(e -> {
            if(insertOne == 1) f1InputBox.insertText(f1InsertIndex++, text);
            if(insertOne == 2) f2InputBox.insertText(f2InsertIndex++, text);
                });
        basicButtonPane.add(bt, columnIndex, rowIndex);
    }
    
    private Button deleteButton(){
        Button bt = new Button("delete");
        bt.setPrefSize(120, 40);
        bt.setStyle("-fx-background-color: #FF3333;");
        bt.setOnAction(e -> {
            if(insertOne == 1 && f1InsertIndex > 0) f1InputBox.deleteText(f1InsertIndex - 1, f1InsertIndex--);
            if(insertOne == 2 && f2InsertIndex > 0) f2InputBox.deleteText(f2InsertIndex - 1, f2InsertIndex--);
                });
        return bt;
    }
    
    private void setFunctionPane(){
        functionPane.setSpacing(10);
        functionPane.getChildren().add(deleteButton());
        setFunctionBox();
        functionPane.getChildren().add(functionBox);
        for(int i = 0; i < names.size(); i++) functionPane.getChildren().add(varButton(i));
        buttonPane.getChildren().addAll(basicButtonPane, functionPane);
    }
    
    private void setFunctionBox(){
        functionBox.setPrefSize(120, 40);
        functionBox.setValue("function");
        functionBox.getItems().addAll(StringToFunction.functionNames);
        functionBox.setOnAction(e -> {
            if(insertOne == 1) {
                f1InputBox.insertText(f1InsertIndex, functionBox.getValue() + "()");
                f1InsertIndex += functionBox.getValue().length() + 1;
            }
            if(insertOne == 2) {
                f2InputBox.insertText(f2InsertIndex, functionBox.getValue() + "()");
                f2InsertIndex += functionBox.getValue().length() + 1;
            }
            
                });
    }
    
    private Button varButton(int i){
        Button bt = new Button(names.get(i));
        bt.setPrefSize(40, 40);
        bt.setOnAction(e -> {
            if(insertOne == 1) f1InputBox.insertText(f1InsertIndex++, names.get(i));
            if(insertOne == 2) f2InputBox.insertText(f2InsertIndex++, names.get(i));
                });
        return bt;
        
    }
    
    private void enterAction(){
        f1EnterString = f1InputBox.getText().toLowerCase();
        f1 = new StringToFunction(f1EnterString, names);
        f2EnterString = f2InputBox.getText().toLowerCase();
        f2 = new StringToFunction(f2EnterString, names);
        StringToFunction[] function = {f1, f2};
        functionProperty.setValue(function);
    }
    
    
    public StringToFunction<E> getF1(){
        return f1;
    }
    
    public StringToFunction<E> getF2(){
        return f2;
    }
    
    public ObjectProperty<StringToFunction<E>[]> functionProperty(){
        return functionProperty;
    }
    
    public String getF1EnterString(){
        return f1EnterString;
    }
    
    public String getF2EnterString(){
        return f2EnterString;
    }
}
