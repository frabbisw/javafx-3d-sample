package sample;

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


public class Main extends Application {
    Group root;
    double width = 1000, height = 800, depth = 400;
    double frameWidth = width, frameHeight = height;
    double cameraX = -450, cameraY = 600, cameraZ = -700;
    double cameraView = 30, dcv = 5;
    double posX = 0, posY = 0, posZ = 0;
    double cameraAngle = 70;
    double rootAngle = 45;
    double dr = 1, dx = 10, dy = 10, dz = 10, dc=1;

    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        build();
    }

    private void build() {
        prepareRoot();

        drawBox(posX, posY, posZ, Color.GREEN);
        drawBox(posX, posY, posZ - 110, Color.YELLOW);
        drawSphere(posX , posY , posZ - 220, Color.RED);


        drawBox(posX + 200, posY , posZ , Color.BLUE);
        drawBox(posX + 200, posY , posZ - 110, Color.BROWN);
        drawSphere(posX + 200, posY , posZ - 220, Color.ORANGE);

        prepareScene();
    }

    private void prepareRoot() {
        root = new Group();
        root.setRotationAxis(Rotate.Z_AXIS);
        root.setRotate(rootAngle);
    }

    private void drawBox(double x, double y, double z, Color color) {
        Box box = new Box(100, 100, 100);
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
        primaryStage.setTitle("Title");
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
