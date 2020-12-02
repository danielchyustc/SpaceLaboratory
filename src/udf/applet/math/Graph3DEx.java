/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.applet.math;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import udf.coordinates.*;
import udf.function.StringToFunction;
import udf.function.composite.CosFunction;
import udf.function.composite.SinFunction;
import udf.function.simple.BasicFunction;
import udf.mathType.Interval;
import udf.mathType.RealNumber;

/**
 *
 * @author DELL
 */
public class Graph3DEx {
    final private ObservableList<String> items = FXCollections.observableArrayList(
            "Example 1", "Example 2", "Example 3", "Example 4", "Example 5", "Example 6", "Example 7", "Exsample 8");
    
    private BorderPane bdPane = new BorderPane();
    private ListView listView = new ListView(items);
    private Coordinates3D pane;
    
    public Graph3DEx(){
        setListView();
        setPane1();
        bdPane.setLeft(listView);
        bdPane.setCenter(pane);
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
        });
    }
    
    private void setPane(int index){
        switch(index){
            case 0: setPane1(); break;
            case 1: setPane2(); break;
            case 2: setPane3(); break;
            case 3: setPane4(); break;
            case 4: setPane5(); break;
            case 5: setPane6(); break;
            case 6: setPane7(); break;
            case 7: setPane8(); break;
            default:
        }
    }
    
    private void setPane1(){
        pane = new Rect3DPane();
        pane.drawParametricCurve(new Interval(-25, 25), new StringToFunction<>("t", "t"), 
                new StringToFunction<>("t", "t"), new StringToFunction<>("t", "t"), Color.BLUE);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
        Text text = new Text(600, 80, "x(t) = t");
        text.setFont(font);
        Text text1 = new Text(600, 115, "y(t) = t");
        text1.setFont(font);
        Text text2 = new Text(600, 150, "z(t) = t");
        text2.setFont(font);
        Text text3 = new Text(550, 130, "{");
        text3.setFont(Font.font("Times New Roman", FontWeight.EXTRA_LIGHT, 100));
        Group textGroup = new Group(text, text1, text2, text3);
        pane.getChildren().add(textGroup);
    }
    
    private void setPane2(){
        pane = new Cylin3DPane();
        pane.drawParametricCurve(new Interval(-25, 25), new StringToFunction<>("t", "t"), 
                new StringToFunction<>("t", "t"), new StringToFunction<>("t", "t"), Color.DEEPPINK);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
        Text text = new Text(600, 80, "r(t) = t");
        text.setFont(font);
        Text text1 = new Text(600, 115, "\u03B8(t) = t");
        text1.setFont(font);
        Text text2 = new Text(600, 150, "z(t) = t");
        text2.setFont(font);
        Text text3 = new Text(550, 130, "{");
        text3.setFont(Font.font("Times New Roman", FontWeight.EXTRA_LIGHT, 100));
        Group textGroup = new Group(text, text1, text2, text3);
        pane.getChildren().add(textGroup);
    }
    
    private void setPane3(){
        pane = new Spher3DPane();
        pane.drawParametricCurve(new Interval(-25, 25), new StringToFunction<>("t", "t"), 
                new StringToFunction<>("t", "t"), new StringToFunction<>("t", "t"), Color.BLUEVIOLET);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
        Text text = new Text(600, 80, "r(t) = t");
        text.setFont(font);
        Text text1 = new Text(600, 115, "\u03B8(t) = t");
        text1.setFont(font);
        Text text2 = new Text(600, 150, "\u03C6(t) = t");
        text2.setFont(font);
        Text text3 = new Text(550, 130, "{");
        text3.setFont(Font.font("Times New Roman", FontWeight.EXTRA_LIGHT, 100));
        Group textGroup = new Group(text, text1, text2, text3);
        pane.getChildren().add(textGroup);
    }
    
    private void setPane4(){
        pane = new Rect3DPane();
        pane.drawParametricCurve(new Interval(-25, 25), new BasicFunction(1), 
                new StringToFunction<>("2*cos(4*t+1)+3", "t"), 
                new StringToFunction<>("3*sin(6*t+5)+2", "t"), Color.BROWN);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
        Text text = new Text(400, 80, "x(t) = t");
        text.setFont(font);
        Text text1 = new Text(400, 115, "y(t) = 2 * cos(4 * t + 1) + 1");
        text1.setFont(font);
        Text text2 = new Text(400, 150, "z(t) = 3 * sin(6 * t + 5) + 2");
        text2.setFont(font);
        Text text3 = new Text(350, 130, "{");
        text3.setFont(Font.font("Times New Roman", FontWeight.EXTRA_LIGHT, 100));
        Group textGroup = new Group(text, text1, text2, text3);
        pane.getChildren().add(textGroup);
    }
    
    private void setPane5(){
        pane = new Rect3DPane();
        pane.drawExplicitSurface2(new Interval(-10, 10), new Interval(-10, 10),
                new StringToFunction<>("10+3*sin(x+y)", "x", "y"), Color.BLUE);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
        Text text = new Text(400, 80, "z(x,y) = 10+3sin(x+y)");
        text.setFont(font);
        pane.getChildren().add(text);
    }
    
    private void setPane6(){
        pane = new Rect3DPane();
        pane.drawParametricSurface(
                new Interval(0, Math.PI), new Interval(0, 2 * Math.PI),
                new StringToFunction<>("5*cos(u)", "u", "v"),
                new StringToFunction<>("5*sin(u)*cos(v)", "u", "v"), 
                new StringToFunction<>("5*sin(u)*sin(v)", "u", "v"), Color.RED);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
        Text text = new Text(500, 80, "x(u,v) = 5cos(u)");
        text.setFont(font);
        Text text1 = new Text(500, 115, "y(u,v) = 5sin(u)cos(v)");
        text1.setFont(font);
        Text text2 = new Text(500, 150, "z(u,v) = 5sin(u)sin(v)");
        text2.setFont(font);
        Text text3 = new Text(450, 130, "{");
        text3.setFont(Font.font("Times New Roman", FontWeight.EXTRA_LIGHT, 100));
        Group textGroup = new Group(text, text1, text2, text3);
        pane.getChildren().add(textGroup);
    }
    
    private void setPane7(){
        pane = new Rect3DPane();
        pane.drawImplicitSurface(new Interval(-25, 25), new Interval(-25, 25), new Interval(-100, 100),
                new StringToFunction<>("x^2 + y^2 - z^2 + 25", "x", "y", "z"), Color.CORNFLOWERBLUE);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
        Text text = new Text(400, 80, "x^2+y^2-z^2+25=0");
        text.setFont(font);
        pane.getChildren().add(text);
    }
    
    private void setPane8(){
        pane = new Rect3DPane();
        pane.drawParametricCurve(new Interval(0, 12*Math.PI), new BasicFunction<RealNumber>(1).add(-6 * Math.PI),
                new CosFunction<RealNumber>().multiply(3), new SinFunction<RealNumber>().multiply(4), Color.CHOCOLATE);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
        Text text = new Text(600, 80, "x(t) = t-6\u03C0");
        text.setFont(font);
        Text text1 = new Text(600, 115, "y(t) = 3cos(t)");
        text1.setFont(font);
        Text text2 = new Text(600, 150, "z(t) = 4sin(t)");
        text2.setFont(font);
        Text text3 = new Text(550, 130, "{");
        text3.setFont(Font.font("Times New Roman", FontWeight.EXTRA_LIGHT, 100));
        Group textGroup = new Group(text, text1, text2, text3);
        pane.getChildren().add(textGroup);
    }   
    
}
