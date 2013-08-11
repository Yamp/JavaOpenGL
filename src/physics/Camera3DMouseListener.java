package physics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 20.07.13
 * Time: 19:14
 *
 * @author Ямпольский Дмитрий yampolskiydv@gmail.com
 */
public class Camera3DMouseListener extends MouseAdapter {
    static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException ignored) {}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int dx = e.getXOnScreen() - 655;
        int dy = e.getYOnScreen() - 355;
        robot.mouseMove(655, 355);

//        curRot = curRot.mult(Matrix3DConstants.getMatrix(dy, dx));
        Matrix3DConstants.curRot = Matrix3DConstants.getMatrix(dy, dx).mult(Matrix3DConstants.curRot);
        Camera3D.direct = Matrix3DConstants.getMatrix(dy, dx).mult(Camera3D.direct);
        Camera3D.up = Matrix3DConstants.getMatrix(dy, dx).mult(Camera3D.up);
    }
}
