package jumping_ball;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Ball {
    double x, y, z, radius;
    //    double dz;
    Sphere sphere;
    PhongMaterial material;
    double velocity=0;
    double g = 9.8;
    double dt = .1;
    public Ball(double radius, double x, double y, double z, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.material=new PhongMaterial(color);

        this.sphere = new Sphere(radius);
        this.sphere.setTranslateX(x);
        this.sphere.setTranslateY(y);
        this.sphere.setTranslateZ(z);
        this.sphere.setMaterial(new PhongMaterial(color));
    }

    public void changeDirection(){
        velocity = -.8 * velocity;
    }

    public Ball(Sphere sphere) {
        this.sphere = sphere;
        this.x = sphere.getTranslateX();
        this.y = sphere.getTranslateY();
        this.z = sphere.getTranslateZ();
        this.radius = sphere.getRadius();
    }
    public void reset(){
        velocity = 0;
        z = 0;
    }
    public void setVelocity(double velocity){
        this.velocity=velocity;
    }

    public Sphere getSphere() {
        return sphere;
    }

    public void move(){
        double s = this.velocity * this.dt + .5 * this.g * this.dt * this.dt;
        this.velocity = this.velocity + this.g * this.dt;
        this.z = z + s;
        sphere.setTranslateZ(this.z);

//        System.out.println(this.z);
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public void setSphere(Sphere sphere) {
        this.sphere = sphere;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
