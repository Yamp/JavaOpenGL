package physics;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 08.07.13
 * Time: 21:52
 *
 * @author Ямпольский Дмитрий yampolskiydv@gmail.com
 */
public class Vector2D {
    public double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getNorm() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D normalize() {
        double temp = getNorm();
        return new Vector2D(x/temp, y/temp);
    }

    public Vector2D mult(double c) {
        return new Vector2D(x * c, y * c);
    }

    public static double mult(Vector2D a, Vector2D b) {
        return a.x * b.x + a.y * b.y;
    }

    public static double dist(Vector2D a, Vector2D b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    public static Vector2D add(Vector2D a, Vector2D b) {
        return new Vector2D(a.x + b.x, a.y + b.y);
    }

    public static Vector2D sub(Vector2D a, Vector2D b) {
        return new Vector2D(a.x - b.x, a.y - b.y);
    }

    public static Vector2D proj(Vector2D a, Vector2D axis) {
        axis = axis.normalize();
        return axis.mult(mult(axis, a));
    }
}
