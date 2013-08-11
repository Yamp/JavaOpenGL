package physics;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 20.07.13
 * Time: 19:40
 *
 * @author Ямпольский Дмитрий yampolskiydv@gmail.com
 */
public class Matrix3D {
    double matrix[][] = new double[3][3];

    public Matrix3D(double[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix3D() {
    }

    public Vector3D mult(Vector3D a) {
        Vector3D result = new Vector3D(0, 0, 0);

        result.x = a.x * matrix[0][0] + a.y * matrix[0][1] + a.z * matrix[0][2];
        result.y = a.x * matrix[1][0] + a.y * matrix[1][1] + a.z * matrix[1][2];
        result.z = a.x * matrix[2][0] + a.y * matrix[2][1] + a.z * matrix[2][2];

        return result;
    }

    public synchronized Matrix3D mult(Matrix3D b) {
        synchronized (b) {
            Matrix3D result = new Matrix3D();

            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    for (int k = 0; k < 3; k++)
                        result.matrix[i][j] += matrix[i][k] * b.matrix[k][j];

            return result;
        }
    }
}
