package four_pillars;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;


public class PillarsMain extends Application {
    Group root;
    double width = 1000, height = 800, depth = 400;
    double frameWidth = width, frameHeight = height;
    double cameraX = 0, cameraY = 2000, cameraZ = -400;
    double cameraView = 30;
    double posX = 0, posY = 0, posZ = 0;
    double cameraAngle = 70;
    double rootAngle = 60;
    double dr = 1, dx = 10, dy = 10, dz = 10, dc=1;

    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        build();
    }

    private void build() {
        prepareRoot();

        int dw = 110, lw = 1000, w = 100, r = 50;
        int x = 100, y = 100, z = 100;


        drawSphere(posX , posY, posZ, Color.BROWN);
        drawSphere(posX + lw, posY, posZ, Color.BLUE);
        drawSphere(posX , posY + lw, posZ, Color.RED);
        drawSphere(posX + lw, posY + lw, posZ, Color.ORANGE);

        for(int i=1; i<=5; i++){
            drawBox(posX , posY, posZ + dw * i, Color.BROWN);
            drawBox(posX + lw, posY, posZ + dw * i, Color.BLUE);
            drawBox(posX, posY + lw, posZ + dw * i, Color.RED);
            drawBox(posX + lw, posY + lw, posZ + dw * i, Color.ORANGE);
        }

        drawBox(posX + lw/2, posY + lw/2, posZ + dw * 6, Color.GREEN, lw + x * 2, lw + x * 2, z);

        prepareScene();
    }

    private void prepareRoot() {
        root = new Group();
        root.setRotationAxis(Rotate.Z_AXIS);
        root.setRotate(rootAngle);
    }

    private void drawBox(double x, double y, double z, Color color) {
        drawBox(x, y, z, color,100, 100, 100);
    }
    private void drawBox(double x, double y, double z, Color color, double width, double height, double depth) {
        Box box = new Box(width, height, depth);
        box.setTranslateX(x);
        box.setTranslateY(y);
        box.setTranslateZ(z);

        PhongMaterial material = new PhongMaterial(color);
        box.setMaterial(material);

        root.getChildren().add(box);
    }
    private void drawSphere(double x, double y, double z, Color color) {
        Sphere sphere = new Sphere(50);
        sphere.setTranslateX(x);
        sphere.setTranslateY(y);
        sphere.setTranslateZ(z);

        PhongMaterial material = new PhongMaterial(color);
        sphere.setMaterial(material);

        root.getChildren().add(sphere);
    }

    private PerspectiveCamera prepareCamera() {
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateX(cameraX);
        camera.setTranslateY(cameraY);
        camera.setTranslateZ(cameraZ);

        camera.setRotationAxis(Rotate.X_AXIS);
        camera.setRotate(cameraAngle);

        camera.setFieldOfView(cameraView);

        return camera;
    }
    private void prepareScene() {
        primaryStage.setScene(new Scene(root, frameWidth, frameHeight, true));
        primaryStage.setTitle("Four Pillars");
        primaryStage.getScene().setCamera(prepareCamera());

        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.LEFT)) {
                    root.setRotate(rootAngle += dr);
                }
                else if (event.getCode().equals(KeyCode.RIGHT)) {
                    root.setRotate(rootAngle -= dr);
                }

                else if (event.getCode().equals(KeyCode.UP)) {
                    primaryStage.getScene().getCamera().setRotate(cameraAngle+=dc);
                }
                else if (event.getCode().equals(KeyCode.DOWN)) {
                    primaryStage.getScene().getCamera().setRotate(cameraAngle-=dc);
                }

                else if (event.getCode().equals(KeyCode.A)) {
                    primaryStage.getScene().getCamera().setTranslateX(cameraX-=dx);
                }
                else if (event.getCode().equals(KeyCode.D)) {
                    primaryStage.getScene().getCamera().setTranslateX(cameraX+=dx);
                }

                else if (event.getCode().equals(KeyCode.W)) {
                    primaryStage.getScene().getCamera().setTranslateY(cameraY-=dy);
                }
                else if (event.getCode().equals(KeyCode.S)) {
                    primaryStage.getScene().getCamera().setTranslateY(cameraY+=dy);
                }

                else if (event.getCode().equals(KeyCode.Q)) {
                    primaryStage.getScene().getCamera().setTranslateZ(cameraZ-=dz);
                }
                else if (event.getCode().equals(KeyCode.E)) {
                    primaryStage.getScene().getCamera().setTranslateZ(cameraZ+=dz);
                }
            }
        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
