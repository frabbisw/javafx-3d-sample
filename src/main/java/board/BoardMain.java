package board;

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


public class BoardMain extends Application {
    Group root;
    double frameWidth = 1000, frameHeight = 800;
    double cameraX = 0, cameraY = 1000, cameraZ = -600;
    double cameraView = 30;
    double posX = 0, posY = 0, posZ = 0;
    double cameraAngle = 60;
    double rootAngle = 60;
    double dr = 1, dx = 10, dy = 10, dz = 10, dc=1;

    double boardWidth=100, boardHeight=100, boardDepth=20;

    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        build();
    }

    private void build() {
        prepareRoot();

        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                Color color;
                if((i+j)%2==0)
                    color = Color.BLACK;
                else
                    color= Color.WHITE;

                drawBox(posX + i* boardWidth, posY + j * boardHeight, posZ, color);
            }
        }
        prepareScene();
    }

    private void prepareRoot() {
        root = new Group();
        root.setRotationAxis(Rotate.Z_AXIS);
        root.setRotate(rootAngle);
    }

    private void drawBox(double x, double y, double z, Color color) {
        drawBox(x, y, z, color,boardWidth, boardHeight, boardDepth);
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
