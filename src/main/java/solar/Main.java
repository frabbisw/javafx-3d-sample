package solar;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {
    Group root;
    double width = 1000, height = 800;
    double frameWidth = width, frameHeight = height;
    double cameraX = -500, cameraY = 3500, cameraZ = -2000;
    double cameraView = 30;
    double posX = 0, posY = 0, posZ = 0;
    double cameraAngle = 60;
    double rootAngle = 60;
    double dr = 1, dx = 10, dy = 10, dz = 10, dc=1;

    double standardSunRadius = 100;
    double lengthConstant = 1;

    long timeUnit = 25;
    double timeUnitInSecond=timeUnit/1000.0;

    Stage primaryStage;

    Planet sun;
    ArrayList<Planet>planets=new ArrayList<>();
    AmbientLight light;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        prepare();
        build();
        buildBoundary();
        animate();
    }

    private void prepare() {
        sun = createSuperPlanet(Constants.SUN_RADIUS, Color.YELLOW, posX, posY, posZ);

        Planet earth = createPlanet(Constants.EARTH_RADIUS, Constants.EARTH_DISTANCE, Constants.EARTH_REVOLUTION_TIME, sun, new Color(1.0 / 0xFF * 0x39, 1.0 / 0xFF * 0x54, 1.0 / 0xFF * 0x6D, 1), 1);
        planets.add(earth);

        Planet moon = createPlanet(Constants.MOON_RADIUS, Constants.MOON_DISTANCE, Constants.MOON_REVOLUTION_TIME, earth, new Color(1.0 / 0xFF * 0x9E, 1.0 / 0xFF * 0x96, 1.0 / 0xFF * 0x94, 1), 1);
        moon.makeUpward();
        planets.add(moon);

        Planet jupiter = createPlanet(Constants.JUPITER_RADIUS, Constants.JUPITER_DISTANCE, Constants.JUPITER_REVOLUTION_TIME, sun, new Color(1.0 / 0xFF * 0x79, 1.0 / 0xFF * 0x70, 1.0 / 0xFF * 0x6B, 1), 1);
        planets.add(jupiter);

//        Planet europa = createPlanet(Constants.EUROPA_RADIUS, Constants.EUROPA_DISTANCE, Constants.EUROPA_REVOLUTION_TIME, jupiter, Color.BLUE, 1);
//        planets.add(europa);

        Planet venus = createPlanet(Constants.VENUS_RADIUS, Constants.VENUS_DISTANCE, Constants.VENUS_REVOLUTION_TIME, sun, new Color(1.0 / 0xFF * 0xA9, 1.0 / 0xFF * 0x60, 1.0 / 0xFF * 0x17, 1), -1);
        planets.add(venus);

        Planet mars = createPlanet(Constants.MARS_RADIUS, Constants.MARS_DISTANCE, Constants.MARS_REVOLUTION_TIME, sun, new Color(1.0 / 0xFF * 0xBB, 1.0 / 0xFF * 0x55, 1.0 / 0xFF * 0x23, 1), 1);
        planets.add(mars);

        Planet saturn = createPlanet(Constants.SATURN_RADIUS, Constants.SATURN_DISTANCE, Constants.SATURN_REVOLUTION_TIME, sun, new Color(1.0 / 0xFF * 0xCF, 1.0 / 0xFF * 0xC5, 1.0 / 0xFF * 0xAC, 1), 1);
        planets.add(saturn);

        Planet uranus = createPlanet(Constants.URANUS_RADIUS, Constants.URANUS_DISTANCE, Constants.URANUS_REVOLUTION_TIME, sun, new Color(1.0 / 0xFF * 0xC9, 1.0 / 0xFF * 0xEF, 1.0 / 0xFF * 0xF1, 1), -1);
        planets.add(uranus);

        Planet neptune = createPlanet(Constants.NEPTUNE_RADIUS, Constants.NEPTUNE_DISTANCE, Constants.NEPTUNE_REVOLUTION_TIME, sun, new Color(1.0 / 0xFF * 0x77, 1.0 / 0xFF * 0x9A, 1.0 / 0xFF * 0xE2, 1), 1);
        planets.add(neptune);

        Planet mercury = createPlanet(Constants.MERCURY_RADIUS, Constants.MERCURY_DISTANCE, Constants.MERCURY_REVOLUTION_TIME, sun, new Color(1.0 / 0xFF * 0x59, 1.0 / 0xFF * 0x59, 1.0 / 0xFF * 0x59, 1), 1);
        planets.add(mercury);
    }

    private void build() {
        prepareRoot();

        addPlanet(sun);
        for(Planet planet : planets){
            addPlanet(planet);
        }
//        addLight();

        prepareScene();
    }

    private void buildBoundary() {
        double max = 0;
        for(Planet planet : planets){
            max = Math.max(max, planet.getOrbitalDistance());
        }
        max = max*1.1;

        Cylinder cylinder = new Cylinder(max,1);
        cylinder.setRotationAxis(Rotate.X_AXIS);
        cylinder.setRotate(90);

        cylinder.setTranslateX(posX);
        cylinder.setTranslateY(posY);
        cylinder.setTranslateZ(posZ);

        cylinder.setMaterial(new PhongMaterial(new Color(.5, .5, .5, 0.5)));

        root.getChildren().add(cylinder);
    }

    private void addLight() {
        light = new AmbientLight();
        light.setColor(new Color(1, 1, 1, 1));
        light.setTranslateX(sun.getPosX());
        light.setTranslateY(sun.getPosY());
        light.setTranslateZ(sun.getPosZ());
        root.getChildren().add(light);
    }

    private void animate() {
        new Thread(){
            @Override
            public void run() {
                super.run();

                while(true){
                    for(Planet planet : planets){
                        planet.move();
                    }
                    try {
                        sleep(timeUnit);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    private void prepareRoot() {
        root = new Group();
        root.setRotationAxis(Rotate.Z_AXIS);
        root.setRotate(rootAngle);
    }

    private Planet createPlanet(double radius, double distance, double revolutionTime, Planet superPlanet, Color color, int direction) {
        Planet planet = new Planet(radius, distance, revolutionTime, superPlanet);
        planet.setColor(color);

        planet.setLengthConstant(this.lengthConstant);
        planet.setDirection(direction);
        planet.setTimeUnit(timeUnitInSecond);

        return planet;
    }

    private Planet createSuperPlanet(double radius, Color color, double posX, double posY, double posZ) {
        Planet sun = new Planet(radius, 0, 0, null);
        sun.setPositions(posX, posY, posZ);
        sun.setColor(color);

        this.lengthConstant = this.standardSunRadius / sun.getLoggedRadius();
        sun.setLengthConstant(this.lengthConstant);

        return sun;
    }

    private void addPlanet(Planet planet) {
        root.getChildren().add(planet.getSphere());
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
        primaryStage.setTitle("Solar System");
        primaryStage.getScene().setCamera(prepareCamera());

        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(cameraAngle);

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
