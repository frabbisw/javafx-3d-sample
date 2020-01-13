package jumping_ball;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.ArrayList;


public class BallMain extends Application {
    Group root;
    double width = 1000, height = 800, depth = 400;
    double frameWidth = width, frameHeight = height;
    double cameraX = -300, cameraY = 2000, cameraZ = -400;
    double cameraView = 30;
    double posX = 0, posY = 0, posZ = 0;
    double cameraAngle = 80;
    double rootAngle = 60;
    double dr = 1, dx = 10, dy = 10, dz = 10, dc=1;
    double radius=50;
    Stage primaryStage;
    ArrayList<Ball>balls;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        balls = new ArrayList<>();
        build();

        animate();
    }

    private void build() {
        prepareRoot();

        int dw = 110, lw = 1000, w = 100, r = 50;
        int x = 100, y = 100, z = 20;
        int dis = 250;

        balls.add(drawSphere(posX - dis, posY, posZ, radius, Color.RED));
        balls.add(drawSphere(posX + dis, posY, posZ, radius, Color.BLUE));
        balls.add(drawSphere(posX, posY + dis, posZ, radius, Color.YELLOW));
        balls.add(drawSphere(posX, posY - dis , posZ, radius, Color.GOLD));

        drawBox(posX, posY, posZ + dw * 6, Color.GREEN, lw + x * 2, lw + x * 2, z);

        addLight(posX + 500, posY, posZ - 100, Color.WHITE);

        prepareScene();
    }

    private void prepareRoot() {
        root = new Group();
        root.setRotationAxis(Rotate.Z_AXIS);
        root.setRotate(rootAngle);
    }
    private void animate() {
        double maxZ = posZ + 110 * 6 - radius;
        double minZ = 0;

        new Thread(){
            @Override
            public void run() {
                super.run();

                while (true){
                    try {
                        for(Ball ball : balls){
                            if(ball.getZ() < minZ | ball.getZ() > maxZ)
                                ball.changeDirection();

                            ball.move();
                        }
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
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
    private Ball drawSphere(double x, double y, double z, double radius, Color color) {
        Ball ball = new Ball(radius, x, y, z, color);
        ball.setDt(.1);
        ball.setVelocity(0);

        ball.getSphere().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ball.reset();
            }
        });

        root.getChildren().add(ball.getSphere());

        return ball;
    }
    private void addLight(double x, double y, double z, Color color) {
        PointLight light = new PointLight(color);
        light.setTranslateX(x);
        light.setTranslateY(y);
        light.setTranslateZ(z);

        root.getChildren().add(light);
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
