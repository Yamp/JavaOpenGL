package physics;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 19.07.13
 * Time: 16:49
 *
 * @author Ямпольский Дмитрий yampolskiydv@gmail.com
 */
public class Vector3D {
    public double x, y, z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getNorm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3D normalize() {
        double temp = getNorm();
        return new Vector3D(x/temp, y/temp, z/temp);
    }

    public Vector3D mult(double c) {
        return new Vector3D(x * c, y * c, z * c);
    }

    public static double mult(Vector3D a, Vector3D b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static double dist(Vector3D a, Vector3D b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y) + (a.z - b.z) * (a.z - b.z));
    }

    public static Vector3D add(Vector3D a, Vector3D b) {
        return new Vector3D(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static Vector3D sub(Vector3D a, Vector3D b) {
        return new Vector3D(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static Vector3D proj(Vector3D a, Vector3D axis) {
        axis = axis.normalize();
        return axis.mult(mult(axis, a));
    }
    public static Vector3D vectMult(Vector3D a, Vector3D b) {
        return new Vector3D(a.z * b.y - a.y * b.z, a.x * b.z - a.z * b.x, a.y * b.x - a.x * b.y);
    }

    public static double getAngle(Vector3D a, Vector3D b) {
        return Math.acos(mult(a, b) / a.getNorm() / b.getNorm());
    }


    @Override
    public String toString() {
        return String.format("[%5f, %5f, %5f]", x, y, z);
//        return "[" + x + ", " + y + ", " + z + ']';
    }
}
