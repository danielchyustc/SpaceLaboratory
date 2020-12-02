/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udf.coordinates;

import udf.Xform;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;
import javafx.scene.text.Text;
import javafx.util.Duration;
import udf.function.SmoothFunction;
import udf.function.fixed.*;
import udf.function.simple.BasicFunction;
import udf.mathType.*;
import udf.mathType.Interval;

/**
 *
 * @author DELL
 */
public abstract class Coordinates3D extends Coordinates {
    private Timeline animation;
    final Group root = new Group();
    final protected Xform axisGroup = new Xform();
    final Xform basicDotGroup = new Xform();
    protected int surfaceCount = 0;
    protected Group surfaceGroup = new Xform();
    private Button pauseButton = new Button("pause");
    private ComboBox typeBox = new ComboBox();
    final private Xform world = new Xform();
    final private PerspectiveCamera camera = new PerspectiveCamera(true);
    final private Xform cameraXform = new Xform();
    final private Xform cameraXform2 = new Xform();
    final private Xform cameraXform3 = new Xform();
    private static final int SURFACE_PARTITION = 200;
    private static final double CAMERA_INITIAL_DISTANCE = -1500;
    private static final double CAMERA_INITIAL_X_ANGLE = 30.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 180.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    protected static final double AXIS_LENGTH = 1000.0;
    private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
    private static final double MOUSE_SPEED = 0.1;
    private static final double ROTATION_SPEED = 2.0;
    private static final double TRACK_SPEED = 0.3;
    
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;
    
    public Coordinates3D(){
        drawCoordinates();
    }
    
    public Xform getWorld(){
        return world;
    }
    
    public Group getSurfaceSet(){
        return surfaceGroup;
    }
    
    public Node getSurface(int index){
        return surfaceGroup.getChildren().get(index);
    }
    
    public int getSurfaceCount(){
        return surfaceCount;
    }
    
    public Timeline getAnimation(){
        return animation;
    }
    
    @Override
    protected final void drawCoordinates(){
        setWidth(800);
        setHeight(800);
        setMaxWidth(800);
        setMaxHeight(800);
        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);
        buildCamera();
        buildAxes();
        world.getChildren().addAll(basicDotGroup, axisGroup, dotGroup, curveGroup, surfaceGroup);
        axisGroup.setVisible(true);
        SubScene scene = new SubScene(root, 800, 800);
        handleKeyboard(scene, world);
        handleMouse(scene, world);
        scene.setCamera(camera);
        layoutPoints();
        setAnimation();
        setButtons();
        setLight();
        getChildren().add(scene);
    }
    
    private void setLight(){
        PointLight light = new PointLight();
        light.setColor(Color.WHITE);
        Xform lightXform = new Xform();
        lightXform.getChildren().add(light);
        lightXform.setTy(500);
        lightXform.setTx(200);
        lightXform.setTz(200);
        root.getChildren().add(lightXform);
    }
    
    protected abstract void layoutPoints();
    
    
    private void setAnimation(){
        animation = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            cameraXform.setRy(cameraXform.ry.getAngle()+ 1);
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }
    
    
    public abstract double[] transform(double u1, double u2, double u3);
    
    public RealVector3D transform(RealVector3D vector){
        return new RealVector3D(transform(vector.x(), vector.y(), vector.z()));
    };
    
    public void setButtons(){
        pauseButton.setLayoutX(600);
        pauseButton.setLayoutY(10);
        pauseButton.setPrefSize(80, 40);
        pauseButton.setOnAction(e -> {
            if(pauseButton.getText().equals("pause")){
                animation.pause();
                pauseButton.setText("rotate");
            }
            else{
                animation.play();
                pauseButton.setText("pause");
            }
        });
        this.getChildren().add(pauseButton);
    }
    
    public void drawDot(double u1Value, double u2Value, double u3Value, String label){
        drawDot(u1Value, u2Value, u3Value, 3, label, Color.BLACK, false);
    }
    
    public void drawDot(RealVector3D vector, Color color){
        drawDot(vector.x(), vector.y(), vector.z(), 3, null, color, true);
    }
    
    public void drawDot(double u1Value, double u2Value, double u3Value, double radius, String label, Color color, Boolean isExtra){
        double[] coordinates = transform(u1Value, u2Value, u3Value);
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        material.setSpecularColor(color);
        Xform dotXform = new Xform();
        Sphere dot = new Sphere(radius);
        dot.setMaterial(material);
        Text dotLabel = new Text(label);
        Xform dotLabelXform = new Xform();
        dotLabelXform.getChildren().add(dotLabel);
        dotLabelXform.setRx(180);
        dotLabelXform.setTx(5);
        dotLabelXform.setTy(5);
        dotLabelXform.setTz(5);
        dotLabel.setTranslateX(5);
        dotLabel.setTranslateY(5);
        dotXform.getChildren().addAll(dot, dotLabelXform);
        dotXform.setTx(coordinates[0] * UNIT);
        dotXform.setTy(coordinates[1] * UNIT);
        dotXform.setTz(coordinates[2] * UNIT);
        if(isExtra){
            dotGroup.getChildren().add(dotXform);
            dotCountProperty.setValue(getDotCount() + 1);
        }
        else basicDotGroup.getChildren().add(dotXform);
    }
    
    @Override
    public void drawParametricCurve(Interval interval, SmoothFunction<RealNumber>...args){
        drawParametricCurve(interval, args[0], args[1], args[2], Color.BLACK);
    }
    
    public void drawParametricCurve(Interval interval, SmoothFunction<RealNumber> u1Function, SmoothFunction<RealNumber> u2Function, SmoothFunction<RealNumber> u3Function){
        drawParametricCurve(interval, u1Function, u2Function, u3Function, Color.BLACK);
    }
    
    public void drawParametricCurve(Interval interval, SmoothFunction<RealNumber> u1Function, SmoothFunction<RealNumber> u2Function, SmoothFunction<RealNumber> u3Function, Color color){
        Xform curveSet = new Xform();
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        material.setSpecularColor(color);
        material.setSpecularPower(0.5);
        for(int i = 0; i < 1000; i++){
            RealNumber number1 = new RealNumber(interval.getStartNumber() + interval.measure() / 1000 * i);
            RealNumber number2 = new RealNumber(interval.getStartNumber() + interval.measure() / 1000 * (i + 1));
            double[] coordinates1 = transform(u1Function.evaluate(number1), u2Function.evaluate(number1), u3Function.evaluate(number1));
            double[] coordinates2 = transform(u1Function.evaluate(number2), u2Function.evaluate(number2), u3Function.evaluate(number2));
            RealVector3D curveCenterPos = new RealVector3D(coordinates1).add(new RealVector3D(coordinates2)).divide(2);
            RealVector3D difference = new RealVector3D(coordinates2).subtract(new RealVector3D(coordinates1));
            Xform curveXform = new Xform();
            Cylinder curve = new Cylinder(1, difference.norm() * UNIT);
            curve.setMaterial(material);
            curveXform.getChildren().add(curve);
            curveXform.setTx(curveCenterPos.x() * UNIT);
            curveXform.setTy(curveCenterPos.y() * UNIT);
            curveXform.setTz(curveCenterPos.z() * UNIT);
            curveXform.setRx(Math.atan2(difference.projection(0, 2).norm(), difference.y()) / Math.PI * 180);
            curveXform.setRy(Math.atan2(difference.x(), difference.z()) / Math.PI * 180);
            curveSet.getChildren().add(curveXform);
        }
        curveGroup.getChildren().add(curveSet);
        curveCountProperty.setValue(getCurveCount() + 1);
    }
    
    public void drawExplicitCurve1(Interval interval, SmoothFunction<RealNumber> u2Function, SmoothFunction<RealNumber> u3Function, Color color){
        drawParametricCurve(interval, new BasicFunction<>(1), u2Function, u3Function, color);
    }
    
    public void drawExplicitCurve2(Interval interval, SmoothFunction<RealNumber> u1Function, SmoothFunction<RealNumber> u3Function, Color color){
        drawParametricCurve(interval, u1Function, new BasicFunction<>(1), u3Function, color);
    }
    
    public void drawExplicitCurve3(Interval interval, SmoothFunction<RealNumber> u1Function, SmoothFunction<RealNumber> u2Function, Color color){
        drawParametricCurve(interval, u2Function, u2Function, new BasicFunction<>(1), color);
    }
    
    public void drawParametricSurface(Interval v1Interval, Interval v2Interval, SmoothFunction<RealVector2D> u1Function, SmoothFunction<RealVector2D> u2Function, SmoothFunction<RealVector2D> u3Function, Color color){
        TriangleMesh mesh = new TriangleMesh(VertexFormat.POINT_TEXCOORD);
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        material.setSpecularColor(color);
        for(int i = 0; i <= SURFACE_PARTITION; i++)
            for(int j = 0; j <= SURFACE_PARTITION; j++){
                double v1 = v1Interval.getStartNumber() + v1Interval.measure() / SURFACE_PARTITION * i;
                double v2 = v2Interval.getStartNumber() + v2Interval.measure() / SURFACE_PARTITION * j;
                RealVector2D vector = new RealVector2D(v1, v2);
                double[] result = transform(u1Function.evaluate(vector), u2Function.evaluate(vector), u3Function.evaluate(vector));
                mesh.getPoints().addAll((float)(result[0] * UNIT), (float)(result[1] * UNIT), (float)(result[2] * UNIT));
            }
        mesh.getTexCoords().addAll(0, 0, 1, 0, 1, 1, 0, 1);
        for(int i = 0; i < SURFACE_PARTITION; i++)
            for(int j = 0; j < SURFACE_PARTITION; j++){
                int n = (SURFACE_PARTITION + 1) * i + j;
                mesh.getFaces().addAll(n, 0, n + SURFACE_PARTITION + 2, 2, n + SURFACE_PARTITION + 1, 1,
                        n + SURFACE_PARTITION + 2, 2, n, 0, n + 1, 3);
                //mesh.getFaceSmoothingGroups().addAll(0, 0);
            }
        MeshView meshView = new MeshView(mesh);
        meshView.setDrawMode(DrawMode.FILL);
        meshView.setMaterial(material);
        meshView.setCullFace(CullFace.NONE);
        surfaceGroup.getChildren().add(meshView);
        surfaceCount++;
    }
    
    public void drawExplicitSurface1(Interval u2Interval, Interval u3Interval, SmoothFunction<RealVector2D> function, Color color){
        drawParametricSurface(u2Interval, u3Interval, function, new BasicFunction<>(1), new BasicFunction<>(2), color);
    }
    
    public void drawExplicitSurface2(Interval u1Interval, Interval u3Interval, SmoothFunction<RealVector2D> function, Color color){
        drawParametricSurface(u1Interval, u3Interval, new BasicFunction<>(1), function, new BasicFunction<>(2), color);
    }
    
    public void drawExplicitSurface3(Interval u1Interval, Interval u2Interval, SmoothFunction<RealVector2D> function, Color color){
        drawParametricSurface(u1Interval, u2Interval, new BasicFunction<>(1), new BasicFunction<>(2), function, color);
    }
    
    
    public void drawImplicitSurface(Interval xInterval, Interval yInterval, Interval zInterval, SmoothFunction<RealVector3D> f, Color color){
        Group surfaceSet = new Xform();
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        material.setSpecularColor(color);
        for(int i = 0; i <= SURFACE_PARTITION; i++)
            for(int j = 0; j <= SURFACE_PARTITION; j++){
                double x = xInterval.getStartNumber() + xInterval.measure() / SURFACE_PARTITION * i;
                double y = yInterval.getStartNumber() + yInterval.measure() / SURFACE_PARTITION * j;
                ArrayList<Double> zList = MathUdf.root(new XYFixedFunction3D(f, x, y));
                for(int k = 0; k < zList.size(); k++)
                    if(zInterval.contains(zList.get(k))){
                        Sphere point = new Sphere(5);
                        point.setMaterial(material);
                        Xform pointXform = new Xform();
                        pointXform.getChildren().add(point);
                        pointXform.setTx(x * UNIT);
                        pointXform.setTy(y * UNIT);
                        pointXform.setTz(zList.get(k) * UNIT);
                        surfaceSet.getChildren().add(pointXform);
                    }
        }
        surfaceGroup.getChildren().add(surfaceSet);
        surfaceCount++;
    }

    private void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }

    private void buildAxes() {
        final PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BLACK);
        material.setSpecularColor(Color.BLACK);
        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);
        xAxis.setMaterial(material);
        yAxis.setMaterial(material);
        zAxis.setMaterial(material);
        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
    }

    private void handleMouse(SubScene scene, final Node root) {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }
        });
        setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX); 
                mouseDeltaY = (mousePosY - mouseOldY); 
                
                double modifier = 1.0;
                
                if (me.isControlDown()) {
                    modifier = CONTROL_MULTIPLIER;
                } 
                if (me.isShiftDown()) {
                    modifier = SHIFT_MULTIPLIER;
                }     
                if (me.isPrimaryButtonDown()) {
                    cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX*MOUSE_SPEED*modifier*ROTATION_SPEED);  
                    cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);  
                }
                else if (me.isSecondaryButtonDown()) {
                    double z = camera.getTranslateZ();
                    double newZ = z + mouseDeltaX*MOUSE_SPEED*modifier;
                    camera.setTranslateZ(newZ);
                }
                else if (me.isMiddleButtonDown()) {
                    cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX*MOUSE_SPEED*modifier*TRACK_SPEED);  
                    cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY*MOUSE_SPEED*modifier*TRACK_SPEED);  
                }
            }
        });
    }
    
    private void handleKeyboard(SubScene scene, final Node root) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case Z:
                        cameraXform2.t.setX(0.0);
                        cameraXform2.t.setY(0.0);
                        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
                        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
                        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
                        break;
                    case X:
                        axisGroup.setVisible(!axisGroup.isVisible());
                        break;
                    case V:
                        //moleculeGroup.setVisible(!moleculeGroup.isVisible());
                        break;
                }
            }
        });
    }
    
}
