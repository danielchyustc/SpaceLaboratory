/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf;

import udf.applet.physics.PendulumEx;
import udf.applet.physics.PendulumUdf;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import udf.applet.math.Graph2DEx;
import udf.applet.math.Graph3DEx;
import udf.coordinates.Coordinates3D;
import udf.coordinates.Cylin3DPane;
import udf.coordinates.Polar2DPane;
import udf.coordinates.Rect3DPane;
import udf.coordinates.Spher3DPane;
import udf.applet.math.graphUdf.Graph2DUdf;
import udf.applet.math.graphUdf.Graph3DUdf;
import udf.mathType.*;
import udf.applet.physics.AstrophysicsShow;
import udf.applet.physics.PhysicsShowEx;
import udf.physics.model.pointModel.MassPoint;
import udf.physics.field.*;

/**
 *
 * @author DELL
 */
public class SpaceLaboratory extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Coordinates3D coPane = new Rect3DPane();
        coPane.setLayoutX(120);
        coPane.setLayoutY(0);
        //Polar2DPane pane = new Polar2Dpane(true);
        //pane.drawExplicitCurve(new SinFunction(10, 0, new LinearFunction(5, 0)), new SingleInterval(0, Math.PI * 2));
        
        ImageView rect2DUdfImageView = new ImageView("file:image/rect2DUdfGraph.png");
        rect2DUdfImageView.setFitWidth(200);
        rect2DUdfImageView.setFitHeight(200);
        Button rect2DUdfButton = new Button("Udf Rectangular 2D Graph", rect2DUdfImageView);
        rect2DUdfButton.setContentDisplay(ContentDisplay.TOP);
        rect2DUdfButton.setOnAction(e -> 
                {
                    Stage stage = new Stage();
                    stage.setTitle("Rectangular 2D Coordinates with Udf Graph");
                    stage.setScene(new Scene(new Graph2DUdf().getPane()));
                    stage.show();
                }
        );
        
        ImageView polar2DUdfImageView = new ImageView("file:image/polar2DUdfGraph.png");
        polar2DUdfImageView.setFitWidth(200);
        polar2DUdfImageView.setFitHeight(200);
        Button polar2DUdfButton = new Button("Udf Polar 2D Graph", polar2DUdfImageView);
        polar2DUdfButton.setContentDisplay(ContentDisplay.TOP);
        polar2DUdfButton.setOnAction(e -> 
                {
                    Stage stage = new Stage();
                    stage.setTitle("Polar 2D Coordinates with Udf Graph");
                    stage.setScene(new Scene(new Graph2DUdf("r", "\u03B8", new Polar2DPane()).getPane()));
                    stage.show();
                }
        );
        
        ImageView rect2DExImageView = new ImageView("file:image/rect2DEx1Graph.png");
        rect2DExImageView.setFitWidth(200);
        rect2DExImageView.setFitHeight(200);
        Button rect2DExButton = new Button("2D Graph Examples", rect2DExImageView);
        rect2DExButton.setContentDisplay(ContentDisplay.TOP);
        rect2DExButton.setOnAction(e -> {
                    Stage stage = new Stage();
                    stage.setTitle("2D Coordinates Examples");
                    stage.setScene(new Scene(new Graph2DEx().getPane()));
                    stage.show();
                }
        );
        
        ImageView rect3DUdfImageView = new ImageView("file:image/rect3DUdfGraph.png");
        rect3DUdfImageView.setFitWidth(200);
        rect3DUdfImageView.setFitHeight(200);
        Button rect3DUdfButton = new Button("Udf Rectangular 3D Graph", rect3DUdfImageView);
        rect3DUdfButton.setContentDisplay(ContentDisplay.TOP);
        rect3DUdfButton.setOnAction(e -> 
                {
                    Stage stage = new Stage();
                    stage.setTitle("Rectangular 3D Coordinates with Udf Graph");
                    stage.setScene(new Scene(new Graph3DUdf().getPane()));
                    stage.show();
                }
        );
        
        ImageView cylin3DUdfImageView = new ImageView("file:image/cylin3DUdfGraph.png");
        cylin3DUdfImageView.setFitWidth(200);
        cylin3DUdfImageView.setFitHeight(200);
        Button cylin3DUdfButton = new Button("Udf Cylindrical 3D Graph", cylin3DUdfImageView);
        cylin3DUdfButton.setContentDisplay(ContentDisplay.TOP);
        cylin3DUdfButton.setOnAction(e -> 
                {
                    Stage stage = new Stage();
                    stage.setTitle("Cylindrical 3D Coordinates with Udf Graph");
                    stage.setScene(new Scene(new Graph3DUdf("r", "\u03B8", "z", new Cylin3DPane()).getPane()));
                    stage.show();
                }
        );
        
        ImageView spher3DUdfImageView = new ImageView("file:image/spher3DUdfGraph.png");
        spher3DUdfImageView.setFitWidth(200);
        spher3DUdfImageView.setFitHeight(200);
        Button spher3DUdfButton = new Button("Udf Spherical 3D Graph", spher3DUdfImageView);
        spher3DUdfButton.setContentDisplay(ContentDisplay.TOP);
        spher3DUdfButton.setOnAction(e -> 
                {
                    Stage stage = new Stage();
                    stage.setTitle("spherical 3D Coordinates with Udf Graph");
                    stage.setScene(new Scene(new Graph3DUdf("r", "\u03B8", "\u03C6", new Spher3DPane()).getPane()));
                    stage.show();
                }
        );
        
        ImageView rect3DExImageView = new ImageView("file:image/rect3DEx1Graph.png");
        rect3DExImageView.setFitWidth(200);
        rect3DExImageView.setFitHeight(200);
        Button rect3DExButton = new Button("3D Graph Examples", rect3DExImageView);
        rect3DExButton.setContentDisplay(ContentDisplay.TOP);
        rect3DExButton.setOnAction(e -> {
                    Stage stage = new Stage();
                    stage.setTitle("3D Coordinates Examples");
                    stage.setScene(new Scene(new Graph3DEx().getPane()));
                    stage.show();
                }
        );
        
        
        ImageView physicsExImageView = new ImageView("file:image/physicsExGraph.png");
        physicsExImageView.setFitWidth(200);
        physicsExImageView.setFitHeight(200);
        Button physicsExButton = new Button("3D Physics Examples", physicsExImageView);
        physicsExButton.setContentDisplay(ContentDisplay.TOP);
        physicsExButton.setOnAction(e -> {
                    Stage stage = new Stage();
                    stage.setTitle("3D Physics Examples");
                    stage.setScene(new Scene(new PhysicsShowEx().getPane()));
                    stage.show();
                }
        );
        
        ImageView pendulumImageView = new ImageView("file:image/pendulumGraph.png");
        pendulumImageView.setFitWidth(200);
        pendulumImageView.setFitHeight(200);
        Button pendulumButton = new Button("Pendulum Examples", pendulumImageView);
        pendulumButton.setContentDisplay(ContentDisplay.TOP);
        pendulumButton.setOnAction(e -> 
                {
                    Stage stage = new Stage();
                    stage.setTitle("Pendulum Examples");
                    stage.setScene(new Scene(new PendulumEx().getPane()));
                    stage.show();
                }
        );
        
        ImageView pendulumUdfImageView = new ImageView("file:image/pendulumUdfGraph.png");
        pendulumUdfImageView.setFitWidth(200);
        pendulumUdfImageView.setFitHeight(200);
        Button pendulumUdfButton = new Button("Pendulum on Udf Curve", pendulumUdfImageView);
        pendulumUdfButton.setContentDisplay(ContentDisplay.TOP);
        pendulumUdfButton.setOnAction(e -> 
                {
                    Stage stage = new Stage();
                    stage.setTitle("Pendulum on User-Defined Curve");
                    stage.setScene(new Scene(new PendulumUdf().getPane()));
                    stage.show();
                }
        );
        
        ImageView astrophysicsImageView = new ImageView("file:image/astrophysicsGraph.png");
        astrophysicsImageView.setFitWidth(200);
        astrophysicsImageView.setFitHeight(200);
        Button astrophysicsButton = new Button("AstroPhysics Simulator", astrophysicsImageView);
        astrophysicsButton.setContentDisplay(ContentDisplay.TOP);
        astrophysicsButton.setOnAction(e -> 
                {
                    Stage stage = new Stage();
                    stage.setTitle("Astrophysics Simulator");
                    stage.setScene(new Scene(new AstrophysicsShow(new UniGravField(new MassPoint(2000))).getPane()));
                    stage.show();
                }
        );
        
        Font textFont = Font.font("SansSerif", FontWeight.BLACK, FontPosture.ITALIC, 40);
        Text sketch2D = new Text("2D Sketchpads");
        sketch2D.setUnderline(true);
        sketch2D.setFont(textFont);
        Text sketch3D = new Text("3D Sketchpads");
        sketch3D.setUnderline(true);
        sketch3D.setFont(textFont);
        Text egs = new Text("Examples");
        egs.setUnderline(true);
        egs.setFont(textFont);
        VBox graphVBox = new VBox(20);
        graphVBox.setLayoutX(10);
        graphVBox.setLayoutY(10);
        graphVBox.getChildren().addAll(sketch2D, new HBox(20, rect2DUdfButton, polar2DUdfButton),
                                        sketch3D, new HBox(20, rect3DUdfButton, cylin3DUdfButton, spher3DUdfButton),
                                        egs, new HBox(20, rect2DExButton, rect3DExButton));
        
        Text spaceLab = new Text("Space Laboratory");
        spaceLab.setUnderline(true);
        spaceLab.setFont(textFont);
        Text egs2 = new Text("Examples");
        egs2.setUnderline(true);
        egs2.setFont(textFont);
        VBox physicsVBox = new VBox(20);
        physicsVBox.setLayoutX(750);
        physicsVBox.setLayoutY(160);
        physicsVBox.getChildren().addAll(spaceLab, new HBox(20, astrophysicsButton, pendulumUdfButton),
                                            egs2, new HBox(20, physicsExButton, pendulumButton));
        
        pane.getChildren().addAll(coPane, graphVBox, physicsVBox);
        
        Scene scene = new Scene(pane, 1240, 1000);
        primaryStage.setTitle("Space Laboratory");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
