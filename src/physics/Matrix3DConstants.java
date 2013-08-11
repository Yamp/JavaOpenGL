package physics;

import static java.lang.Math.*;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 20.07.13
 * Time: 20:31
 *
 * @author Ямпольский Дмитрий yampolskiydv@gmail.com
 */
public class Matrix3DConstants implements Options3D {

    public static final double MULTIPLIER = 500;

    public static Vector3D RIGHT = new Vector3D(1, 0, 0);
    public static Vector3D UP  =   new Vector3D(0,-1, 0);
    public static Vector3D FRONT = new Vector3D(0, 0, 1);

    public static Matrix3D getRotMatrix(final Vector3D axis, final double angle) {
        double result[][] = new double[3][3];
        result[0][0] = (1 - cos(angle)) * axis.x * axis.x + cos(angle);
        result[0][1] = (1 - cos(angle)) * axis.x * axis.y - sin(angle) * axis.z;
        result[0][2] = (1 - cos(angle)) * axis.x * axis.z + sin(angle) * axis.y;

        result[1][0] = (1 - cos(angle)) * axis.y * axis.x + sin(angle) * axis.z;
        result[1][1] = (1 - cos(angle)) * axis.y * axis.y + cos(angle);
        result[1][2] = (1 - cos(angle)) * axis.y * axis.z - sin(angle) * axis.x;

        result[2][0] = (1 - cos(angle)) * axis.z * axis.x - sin(angle) * axis.y;
        result[2][1] = (1 - cos(angle)) * axis.z * axis.y + sin(angle) * axis.x;
        result[2][2] = (1 - cos(angle)) * axis.z * axis.z + cos(angle);

        return new Matrix3D(result);
    }

    public static Matrix3D getMatrix(int x, int y) {
        Matrix3D MX = new Matrix3D(new double[][]{
                {1,   0,                      0                  },
                {0,   cos(x / MULTIPLIER),   -sin(x / MULTIPLIER)},
                {0,   sin(x / MULTIPLIER),    cos(x / MULTIPLIER)}
        });

        Matrix3D MY = new Matrix3D(new double[][]{
                {cos(y / MULTIPLIER),    0,  -sin(y / MULTIPLIER)},
                {0,                      1,   0                  },
                {sin(y / MULTIPLIER),    0,   cos(y / MULTIPLIER)}
        });

        return MX.mult(MY);
    }

    public static volatile Matrix3D curRot = new Matrix3D(new double[][]{
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
    });

    public static final Matrix3D RotX = new Matrix3D(new double[][]{
            {1,   0,                      0                  },
            {0,   cos(ROT_ANGLE),   -sin(ROT_ANGLE)},
            {0,   sin(ROT_ANGLE),    cos(ROT_ANGLE)}
    });

    public static final Matrix3D RotY = new Matrix3D(new double[][]{
            {cos(ROT_ANGLE),    0,  -sin(ROT_ANGLE)},
            {0,                      1,   0                  },
            {sin(ROT_ANGLE),    0,   cos(ROT_ANGLE)}
    });

    public static final Matrix3D RotZ = new Matrix3D(new double[][]{
            {cos(ROT_ANGLE),   -sin(ROT_ANGLE),   0},
            {sin(ROT_ANGLE),    cos(ROT_ANGLE),   0},
            {0,                      0,                     1}
    });
}