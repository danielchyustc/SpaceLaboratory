/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.applet.math.graphUdf;

import udf.inputer.functionInputer.FunctionInputPane2;
import udf.inputer.functionInputer.FunctionInputPane;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import udf.coordinates.Coordinates2D;
import udf.coordinates.Rect2DPane;
import udf.inputer.functionInputer.FIP;
import udf.function.StringToFunction;
import udf.inputer.RV2DInputPane;
import udf.mathType.RealNumber;
import udf.mathType.RealVector2D;
import udf.mathType.Interval;

/**
 *
 * @author DELL
 */
public class Graph2DUdf {
    final private String[] curveTypeSet = new String[4];
    final private Font font = Font.font("Times New Roman", 25);
    
    private Coordinates2D pane = new Rect2DPane();
    final private BorderPane bdPane = new BorderPane();
    private ComboBox<String> ctBox = new ComboBox<>();
    private FIP curveInputer;
    private RV2DInputPane dotInputer;
    private VBox curveVBox = new VBox(10);
    private VBox dotVBox = new VBox(10);
    private ArrayList<IntegerProperty> curveSequence = new ArrayList<>();
    private ArrayList<IntegerProperty> dotSequence = new ArrayList<>();
    private ArrayList<Color> curveColorList = new ArrayList<>();
    private ArrayList<Color> dotColorList = new ArrayList<>();
    private ColorPicker curveCP = new ColorPicker(Color.BLUE);
    private ColorPicker dotCP = new ColorPicker(Color.BLACK);
    
    private String u1Name = "x";
    private String u2Name = "y";
    
    public Graph2DUdf(){
        layout();
    }
    
    public Graph2DUdf(String u1Name, String u2Name, Coordinates2D pane){
        this.u1Name = u1Name;
        this.u2Name = u2Name;
        this.pane = pane;
        layout();
    }
    
    public BorderPane getPane(){
        return bdPane;
    }
    
    private void layout(){
        bdPane.setCenter(pane);
        curvePart();
        dotPart();
    }
    
    private void curvePart(){
        curveTypeSet[0] = "Explicit Curve " + u2Name + "(" + u1Name + ")";
        curveTypeSet[1] = "Explicit Curve " + u1Name + "(" + u2Name + ")";
        curveTypeSet[2] = "Parametric Curve " + u1Name + "(t), " + u2Name + "(t)";
        curveTypeSet[3] = "Implicit Curve f(" + u1Name + "," + u2Name + ") = 0";
        Text drawCurveText = new Text("Draw Curves:");
        drawCurveText.setFont(Font.font("Times New Roman", 40));
        Text curveType = new Text("Curve Type:");
        curveType.setFont(Font.font("Times New Roman", 30));
        ctBox.setPrefSize(220, 40);
        ctBox.setValue(curveTypeSet[0]);
        ctBox.getItems().addAll(curveTypeSet);
        ctBox.setOnAction(e -> {
            curveVBox.getChildren().remove(2);
            switch(FXCollections.observableArrayList(curveTypeSet).indexOf(ctBox.getValue())){
                case 0: curveProcess1(); break;
                case 1: curveProcess2(); break;
                case 2: curveProcess3(); break;
                case 3: curveProcess4(); break;
                default:
            }
            curveVBox.getChildren().add(2, curveInputer);
        });
        curveProcess1();
        Text curveNumText = new Text("curve number:");
        curveNumText.setFont(font);
        Text curveNumber = new Text();
        curveNumber.setFont(font);
        curveNumber.textProperty().bind(pane.curveCountProperty().asString());
        HBox curveNumHBox = new HBox(10);
        curveNumHBox.setPadding(new Insets(10, 10, 10, 10));
        curveNumHBox.getChildren().addAll(curveNumText, curveNumber);
        curveVBox.getChildren().addAll(drawCurveText, new HBox(10, curveType, ctBox), curveInputer, curveCP, curveNumHBox);
        bdPane.setRight(curveVBox);
        
    }
    
    private void curveProcess1(){
        curveInputer = new FunctionInputPane<>(u2Name, u1Name);
        ((FunctionInputPane<RealNumber>)curveInputer).functionProperty().addListener(
            (ObservableValue<? extends StringToFunction<RealNumber>> observable, StringToFunction<RealNumber> oldValue, StringToFunction<RealNumber> newValue) -> {
                if(newValue.getFunction() != null && pane.getCurveCount() < 15){
                    pane.drawExplicitCurve1(new Interval(-25, 25), newValue, curveCP.getValue());
                    curveColorList.add(curveCP.getValue());
                    addCurveProcess(pane.getCurveCount());
                }
        }); 
    }
    
    private void curveProcess2(){
        curveInputer = new FunctionInputPane<>(u1Name, u2Name);
        ((FunctionInputPane<RealNumber>)curveInputer).functionProperty().addListener(
            (ObservableValue<? extends StringToFunction<RealNumber>> observable, StringToFunction<RealNumber> oldValue, StringToFunction<RealNumber> newValue) -> {
                if(newValue.getFunction() != null && pane.getCurveCount() < 15){
                    pane.drawExplicitCurve2(new Interval(-25, 25), newValue, curveCP.getValue());
                    curveColorList.add(curveCP.getValue());
                    addCurveProcess(pane.getCurveCount());
                }
        });     
    }
    
    private void curveProcess3(){
        curveInputer = new FunctionInputPane2<>(u1Name, u2Name, "t");
        ((FunctionInputPane2<RealNumber>)curveInputer).functionProperty().addListener(
            (ObservableValue<? extends StringToFunction<RealNumber>[]> observable, StringToFunction<RealNumber>[] oldValue, StringToFunction<RealNumber>[] newValue) -> {
                if(newValue != null && pane.getCurveCount() < 15){
                    pane.drawParametricCurve(new Interval(-25, 25), newValue[0], newValue[1], curveCP.getValue());
                    curveColorList.add(curveCP.getValue());
                    addCurveProcess(pane.getCurveCount());
                }
        }); 
    }
    
    private void curveProcess4(){
        curveInputer = new FunctionInputPane<>("f", u1Name, u2Name);
        ((FunctionInputPane<RealVector2D>)curveInputer).functionProperty().addListener(
            (ObservableValue<? extends StringToFunction<RealVector2D>> observable, StringToFunction<RealVector2D> oldValue, StringToFunction<RealVector2D> newValue) -> {
                if(newValue.getFunction() != null && pane.getCurveCount() < 15){
                    pane.drawImplicitCurve2(new Interval(-25, 25), new Interval(-25, 25), newValue, curveCP.getValue());
                    curveColorList.add(curveCP.getValue());
                    addCurveProcess(pane.getCurveCount());
                }
        }); 
    }
    
    private void addCurveProcess(int index){
        HBox hBox = new HBox(5);
        hBox.setPadding(new Insets(0, 10, 0, 10));
        Text curveText = new Text("curve");
        curveText.setFont(font);
        IntegerProperty sequenceElem = new SimpleIntegerProperty(index);
        curveSequence.add(sequenceElem);
        Text sequenceText = new Text();
        sequenceText.setFont(font);
        sequenceText.textProperty().bind(sequenceElem.asString());
        Text qtText = new Text(":");
        qtText.setFont(font);
        Circle circle = new Circle(10);
        circle.setFill(curveColorList.get(index - 1));
        Text stringText = new Text();
        switch(FXCollections.observableArrayList(curveTypeSet).indexOf(ctBox.getValue())){
            case 0: stringText.setText(u2Name + "(" + u1Name + ") = " + ((FunctionInputPane<RealNumber>)curveInputer).getEnterString()); break;
            case 1: stringText.setText(u1Name + "(" + u2Name + ") = " + ((FunctionInputPane<RealNumber>)curveInputer).getEnterString()); break;
            case 2: stringText.setText(
                    u1Name + "(t) = " + ((FunctionInputPane2<RealNumber>)curveInputer).getF1EnterString() + "\n" +
                    u2Name + "(t) = " + ((FunctionInputPane2<RealNumber>)curveInputer).getF2EnterString()
            ); break;
            case 3: stringText.setText(((FunctionInputPane<RealVector2D>)curveInputer).getEnterString() + " = 0");
        }
        stringText.setFont(font);
        Button dlButton = new Button("delete");
        dlButton.setStyle("-fx-background-color: #FF3333;");
        dlButton.setOnAction(e -> deleteCurve(sequenceElem.getValue() - 1));
        hBox.getChildren().addAll(dlButton, curveText, sequenceText, qtText, circle, stringText);
        curveVBox.getChildren().add(hBox);
    }
    
    private void deleteCurve(int index){
        pane.deleteCurve(index);
        curveVBox.getChildren().remove(index + 5);
        curveColorList.remove(index);
        curveSequence.remove(index);
        for(int i = index; i < pane.getCurveCount(); i++)
            curveSequence.get(i).setValue(i + 1);
    }
    
    private void dotPart(){
        dotInputer = new RV2DInputPane(u1Name, u2Name);
        Text drawDotText = new Text("Draw Dots:");
        drawDotText.setFont(Font.font("Times New Roman", 40));
        dotInputer.vectorProperty().addListener(
                (ObservableValue<? extends RealVector2D> observable, RealVector2D oldValue, RealVector2D newValue) -> {
                    if(newValue != null && pane.getDotCount() < 15){
                        pane.drawDot(newValue, dotCP.getValue());
                        dotColorList.add(dotCP.getValue());
                        addDotInfo(pane.getDotCount());
                    }
        });
        Text dotNumText = new Text("dot number:");
        dotNumText.setFont(font);
        Text dotNumber = new Text();
        dotNumber.setFont(font);
        dotNumber.textProperty().bind(pane.dotCountProperty().asString());
        HBox dotNumHBox = new HBox(10);
        dotNumHBox.setPadding(new Insets(10, 10, 10, 10));
        dotNumHBox.getChildren().addAll(dotNumText, dotNumber);
        dotVBox.getChildren().addAll(drawDotText, dotInputer, dotCP, dotNumHBox);
        bdPane.setLeft(dotVBox);
    }
    
    private void addDotInfo(int index){
        HBox hBox = new HBox(5);
        hBox.setPadding(new Insets(0, 10, 0, 10));
        Text dotText = new Text("dot");
        dotText.setFont(font);
        IntegerProperty dotSequenceElem = new SimpleIntegerProperty(index);
        dotSequence.add(dotSequenceElem);
        Text sequenceText = new Text();
        sequenceText.setFont(font);
        sequenceText.textProperty().bind(dotSequenceElem.asString());
        Text qtText = new Text(":");
        qtText.setFont(font);
        Circle circle = new Circle(10);
        circle.setFill(dotColorList.get(index - 1));
        Text stringText = new Text(dotInputer.getVector().toString());
        stringText.setFont(font);
        Button dlButton = new Button("delete");
        dlButton.setStyle("-fx-background-color: #FF3333;");
        dlButton.setOnAction(e -> deleteDot(dotSequenceElem.getValue() - 1));
        hBox.getChildren().addAll(dlButton, dotText, sequenceText, qtText, circle, stringText);
        dotVBox.getChildren().add(hBox);
    }
    
    private void deleteDot(int index){
        pane.deleteDot(index);
        dotVBox.getChildren().remove(index + 4);
        dotColorList.remove(index);
        dotSequence.remove(index);
        for(int i = index; i < pane.getDotCount(); i++)
            dotSequence.get(i).setValue(i + 1);
    }
}
