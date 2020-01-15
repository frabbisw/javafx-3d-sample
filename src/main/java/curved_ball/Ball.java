package curved_ball;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Ball {
    double x, y, z, radius;
    Sphere sphere;
    PhongMaterial material;
    double horizontalVelocity=0;
    double verticalVelocity=0;
    double verticalAcceleration = 9.8;
    double horizontalAcceleration = 0;
    double dt = .1;

    double horizontalCollisionConstant  = .9;
    double verticalCollisionConstant  = .9;

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

    public void horizontalCollision(){
        horizontalVelocity = - horizontalCollisionConstant * horizontalVelocity;
    }

    public void verticalCollision(){
        verticalVelocity = - verticalCollisionConstant * verticalVelocity;
    }

    public Ball(Sphere sphere) {
        this.sphere = sphere;
        this.x = sphere.getTranslateX();
        this.y = sphere.getTranslateY();
        this.z = sphere.getTranslateZ();
        this.radius = sphere.getRadius();
    }
    public void reset(double x, double y, double z){
        verticalVelocity = 0;
        horizontalVelocity = 0;
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public void setHorizontalVelocity(double horizontalVelocity){
        this.horizontalVelocity=horizontalVelocity;
    }

    public void setVerticalVelocity(double verticalVelocity){
        this.verticalVelocity=verticalVelocity;
    }

    public Sphere getSphere() {
        return sphere;
    }

    public void move(){
        double verticalDistance = this.verticalVelocity * this.dt + .5 * this.verticalAcceleration * this.dt * this.dt;
        double horizontalDistance = this.horizontalVelocity * this.dt + .5 * this.horizontalAcceleration * this.dt * this.dt;

        this.x = this.x + horizontalDistance;
        this.z = this.z + verticalDistance;

        sphere.setTranslateZ(this.z);
        sphere.setTranslateX(this.x);
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
