package curved_ball;

import javafx.scene.shape.Box;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;

public class Physics {
    ArrayList<Ball>balls;
    public Physics(){
        balls = new ArrayList<>();
    }
    public void addObject(Ball ball){
        balls.add(ball);
    }
}
