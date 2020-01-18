package solar;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import static solar.Scalar.scaleMeter;
import static solar.Scalar.scaleSecond;

public class Planet {
    Sphere sphere;
    double radius, loggedRadius;
    double distance, loggedDistance;
    double posX, posY, posZ;
    double angularVelocity, loggedAngularVelocity;
    double linearVelocity, loggedLinearVelocity;
    double revolutionTime, loggedRevolutionTime;
    double timeUnit=.1, totalUnit=10;
    int currentUnit=0;
    double horizontalAngle=0, verticalAngle=0;

    double orbitalDistance=0;

    int direction=1;
    boolean upward=false;

    final double angularCircumference = 2 * Math.PI;
    double linearCircumference, loggedLinearCircumference;

    double lengthConstant=1, distanceConstant=1, timeConstant=1;

    Planet superPlanet;

    public Planet(double radius, double distance, double revolutionTime, Planet superPlanet) {
        try{
            this.superPlanet = superPlanet;

            this.radius = radius;
            this.loggedRadius = scaleMeter(radius);

            this.sphere = new Sphere(loggedRadius);

            if(superPlanet != null){
                this.distance = distance;
                this.loggedDistance = scaleMeter(distance);

                this.revolutionTime = revolutionTime;
                this.loggedRevolutionTime = scaleSecond(revolutionTime);

                this.linearCircumference = angularCircumference * radius;
                this.loggedLinearCircumference = scaleMeter(linearCircumference);

                this.angularVelocity = angularCircumference / revolutionTime;
                this.loggedAngularVelocity = angularCircumference / loggedRevolutionTime;

                this.linearVelocity = linearCircumference / revolutionTime;
                this.loggedLinearVelocity = loggedLinearCircumference / loggedRevolutionTime;

                this.orbitalDistance = superPlanet.getPosX() + superPlanet.getLoggedRadius() + this.getLoggedDistance() + this.getLoggedRadius();
                this.posX = this.orbitalDistance;
                this.posY = superPlanet.getPosY();
                this.posZ = superPlanet.getPosZ();
            }
            else {
                this.posX = 0;
                this.posY = 0;
                this.posZ = 0;
            }
            this.sphere.setTranslateX(this.posX);
            this.sphere.setTranslateY(this.posY);
            this.sphere.setTranslateZ(this.posZ);
        }
        catch (Exception e){

        }
    }

    public void makeUpward(){
        this.upward = true;
        this.orbitalDistance = superPlanet.getPosZ() + superPlanet.getLoggedRadius() + this.getLoggedDistance() + this.getLoggedRadius();
        this.posZ = this.orbitalDistance;
        this.posY = superPlanet.getPosY();
        this.posX = superPlanet.getPosX();
    }

    public void move() {
        if(this.superPlanet != null){
            this.currentUnit++;
            if(this.currentUnit>=totalUnit) currentUnit=0;

            this.horizontalAngle = this.direction * this.angularCircumference / totalUnit * currentUnit;

            setPosY(superPlanet.getPosY() + this.orbitalDistance * Math.sin(this.horizontalAngle));
            this.sphere.setTranslateY(posY);

            if(upward){
                setPosZ(superPlanet.getPosZ() + this.orbitalDistance * Math.cos(this.horizontalAngle));
                this.sphere.setTranslateZ(posZ);
                setPosX(superPlanet.getPosX());
                this.sphere.setTranslateX(posX);
            }else {
                setPosX(superPlanet.getPosX() + this.orbitalDistance * Math.cos(this.horizontalAngle));
                this.sphere.setTranslateX(posX);
                setPosZ(superPlanet.getPosZ());
                this.sphere.setTranslateZ(posZ);
            }
        }


//        System.out.println(this.sphere.getTranslateX());
//        System.out.println(this.sphere.getTranslateY());
//        System.out.println();
    }

    public void setPositions(double x, double y, double z) {
        this.sphere.setTranslateX(x);
        this.sphere.setTranslateY(y);
        this.sphere.setTranslateZ(z);
    }

    public double getLoggedRadius() {
        return loggedRadius;
    }

    public void setTimeUnit(double timeUnit){
        this.timeUnit = timeUnit;
        this.totalUnit = getLoggedRevolutionTime() / timeUnit;
    }

    public void setLoggedRadius(double loggedRadius) {
        this.loggedRadius = loggedRadius;
    }

    public double getLoggedDistance() {
        return loggedDistance;
    }

    public void setLoggedDistance(double loggedDistance) {
        this.loggedDistance = loggedDistance;
    }

    public double getLoggedRevolutionTime() {
        return loggedRevolutionTime;
    }

    public void setLoggedRevolutionTime(double loggedRevolutionTime) {
        this.loggedRevolutionTime = loggedRevolutionTime;
    }

    public int getDirection() {
        return direction;
    }

    public double getOrbitalDistance() {
        return orbitalDistance;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public double getAngularCircumference() {
        return angularCircumference;
    }

    public double getLinearCircumference() {
        return linearCircumference;
    }

    public void setLinearCircumference(double linearCircumference) {
        this.linearCircumference = linearCircumference;
    }

    public double getDistanceConstant() {
        return distanceConstant;
    }

    public void setDistanceConstant(double distanceConstant) {
        this.distanceConstant = distanceConstant;
    }

    public double getTimeConstant() {
        return timeConstant;
    }

    public void setTimeConstant(double timeConstant) {
        this.timeConstant = timeConstant;
    }

    public void setColor(Color color) {
        this.sphere.setMaterial(new PhongMaterial(color));
    }

    public Sphere getSphere() {
        return sphere;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(double angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public double getLoggedAngularVelocity() {
        return loggedAngularVelocity;
    }

    public void setLoggedAngularVelocity(double loggedAngularVelocity) {
        this.loggedAngularVelocity = loggedAngularVelocity;
    }

    public double getLinearVelocity() {
        return linearVelocity;
    }

    public void setLinearVelocity(double linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public double getLoggedLinearVelocity() {
        return loggedLinearVelocity;
    }

    public void setLoggedLinearVelocity(double loggedLinearVelocity) {
        this.loggedLinearVelocity = loggedLinearVelocity;
    }

    public double getRevolutionTime() {
        return revolutionTime;
    }

    public void setRevolutionTime(double revolutionTime) {
        this.revolutionTime = revolutionTime;
    }

    public Planet getSuperPlanet() {
        return superPlanet;
    }

    public void setSuperPlanet(Planet superPlanet) {
        this.superPlanet = superPlanet;
    }

    public double getLengthConstant() {
        return lengthConstant;
    }

    public void setLengthConstant(double lengthConstant) {
        this.lengthConstant = lengthConstant;
        this.sphere.setRadius(sphere.getRadius() * lengthConstant);
    }
}
