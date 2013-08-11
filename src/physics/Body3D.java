package physics;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 08.07.13
 * Time: 21:49
 *
 * @author Ямпольский Дмитрий yampolskiydv@gmail.com
 */
public class Body3D {
    final public double mass;
    final public double elasticity; //from 0 to 1 (% absorbed energy)
    final public double size; //радиус;
    final public double roughness;
    final public Color color;
    public double angle;
    public double angle_speed;
    public Vector3D speed;
    public Vector3D position;

    public Body3D(double mass, double elasticity, double roughness, double angle_speed,
                  double size, Color color, Vector3D speed, Vector3D position) {
        this.mass = mass;
        this.elasticity = elasticity;
        this.roughness = roughness;
        this.angle_speed = angle_speed;
        this.size = size;
        this.color = color;
        this.speed = speed;
        this.position = position;
        this.angle = 0;
    }

//    public Body3D(double mass, int size, double elasticity, double speedX, double speedY, double x, double y) {
//        this.mass = mass;
//        this.elasticity = elasticity;
//        this.roughness = 0.5;
//        this.angle_speed = 0;
//        this.size = size;
//        this.color = Color.BLUE;
//        this.speed = new Vector2D(speedX, speedY);
//        this.position = new Vector2D(x, y);
//        this.angle = 0;
//    }
//
    @Override
    public Body3D clone() {
        return new Body3D(mass, elasticity, roughness, angle_speed, size, color,
                new Vector3D(speed.x, speed.y, speed.z), new Vector3D(position.x, position.y, position.z));
    }
}
