/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.applet.math;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import udf.coordinates.*;
import udf.function.StringToFunction;
import udf.mathType.Interval;

/**
 *
 * @author DELL
 */
public class Graph2DEx {
    final private ObservableList<String> items = FXCollections.observableArrayList(
            "Example 1", "Example 2", "Example 3", "Example 4", "Example 5", "Example 6");
    
    private BorderPane bdPane = new BorderPane();
    private ListView listView = new ListView(items);
    private Coordinates2D pane;
    
    public Graph2DEx(){
        setListView();
        setPane1();
        bdPane.setLeft(listView);
        bdPane.setCenter(pane);
    }
    
    public BorderPane getPane(){
        return bdPane;
    }
    
    private void setListView(){
        listView.setPrefWidth(150);
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
            default:
        }
    }
    
    private void setPane1(){
        pane = new Rect2DPane();
        pane.drawDot(-2, 9, "(-2,9)");  pane.drawDot(6, -3, "(6,-3)");
        pane.drawDot(-10, -3, "(-10,-3)");  pane.drawDot(14, 9, "(14,9)");
        pane.drawDot(-18, 9, "(-18,9)");  pane.drawDot(22, -3, "(22,-3)");
        pane.drawExplicitCurve1(new Interval(-25, 25), new StringToFunction<>("6*cos((x+2)*pi/8)+3", "x"), Color.BLACK);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 40);
        Text text = new Text(50, 80, "y(x) = 6 cos\u2013(x + 2) + 3");
        text.setFont(font);
        Text text1 = new Text(248, 60, "\u03C0");
        text1.setFont(font);
        Text text2 = new Text(238, 105, "8");
        text2.setFont(font);
        pane.getChildren().addAll(text, text1, text2);
        /*pane.drawImplicitCurve1(new Interval(-10, 10), new Interval(-10, 10),new SumFunction2D(
                new PowFunction2D(XFunction2D.X, 2), new PowFunction2D(1, -100, YFunction2D.Y, 2)));
        pane.drawImplicitCurve2(new Interval(-15, 15), new Interval(-15, 15),new SumFunction2D(
                new PowFunction2D(XFunction2D.X, 2), new PowFunction2D(1, -225, YFunction2D.Y, 2)));*/
    }
    
    private void setPane2(){
        pane = new Polar2DPane();
        pane.drawExplicitCurve2(new Interval(-25, 25), new StringToFunction<>("20*sin(3*t)", "t"), Color.GREEN);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
        Text text = new Text(800, 80, "r(\u03B8) = 20sin(3\u03B8)");
        text.setFont(font);
        Group textGroup = new Group(text);
        pane.getChildren().add(textGroup);
    }
    
    private void setPane3(){
        pane = new Rect2DPane();
        pane.drawParametricCurve(new Interval(0, Math.PI * 2), new StringToFunction<>("8*cos(t)", "t"), 
                new StringToFunction<>("10*sin(t)", "t"), Color.BLUEVIOLET);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
        Text text = new Text(500, 80, "x(t) = 8cos(t)");
        text.setFont(font);
        Text text1 = new Text(500, 115, "y(t) = 10sin(t)");
        text1.setFont(font);
        Text text3 = new Text(450, 110, "{");
        text3.setFont(Font.font("Times New Roman", FontWeight.EXTRA_LIGHT, 60));
        Group textGroup = new Group(text, text1, text3);
        pane.getChildren().add(textGroup);
    }
    
    private void setPane4(){
        pane = new Rect2DPane();
        pane.drawParametricCurve(new Interval(-25, 25), new StringToFunction<>("8*cosh(t)", "t"), 
                new StringToFunction<>("10*sinh(t)", "t"), Color.BLUEVIOLET);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
        Text text = new Text(500, 80, "x(t) = 8cosh(t)");
        text.setFont(font);
        Text text1 = new Text(500, 115, "y(t) = 10sinh(t)");
        text1.setFont(font);
        Text text3 = new Text(450, 110, "{");
        text3.setFont(Font.font("Times New Roman", FontWeight.EXTRA_LIGHT, 60));
        Group textGroup = new Group(text, text1, text3);
        pane.getChildren().add(textGroup);
    }
    
    private void setPane5(){
        pane = new Rect2DPane();
        pane.drawImplicitCurve2(new Interval(-25, 25), new Interval(-25, 25), 
                new StringToFunction<>("x^2+y^2-100", "x", "y"), Color.ORANGE);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
        Text text = new Text(500, 80, "x^2+y^2-100=0");
        text.setFont(font);
        Group textGroup = new Group(text);
        pane.getChildren().add(textGroup);
    }
    private void setPane6(){
        pane = new Rect2DPane();
        pane.drawImplicitCurve2(new Interval(-25, 25), new Interval(-25, 25), 
                new StringToFunction<>("x^2-y^2-100", "x", "y"), Color.ORANGE);
        Font font = Font.font("Times New Roman", FontPosture.ITALIC, 30);
        Text text = new Text(500, 80, "x^2-y^2-100=0");
        text.setFont(font);
        Group textGroup = new Group(text);
        pane.getChildren().add(textGroup);
    }
    
}
