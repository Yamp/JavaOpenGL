package physics;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 20.07.13
 * Time: 21:36
 *
 * @author Ямпольский Дмитрий yampolskiydv@gmail.com
 */
public class Matrix3DUtils {
//    public static Matrix3D invert(Matrix3D A) {
//        return new Matrix3D(new LUDecomposition(new Array2DRowRealMatrix(A.matrix)).getSolver().getInverse().getData());
//    }

    //Для матриц поворота должно работать.
    public static Matrix3D invert(Matrix3D A) {
        double matr[][] = new double[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                matr[i][j] = A.matrix[j][i];
        return new Matrix3D(matr);
    }
}
