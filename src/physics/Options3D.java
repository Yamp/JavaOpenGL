package physics;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 09.07.13
 * Time: 13:53
 *
 * @author Ямпольский Дмитрий yampolskiydv@gmail.com
 */
public interface Options3D {
    static final double GRAV_CONST = 100000;
    static final double WALL_ELASTICITY = 1;

    static final double SPEED_CONST = 0.003;
    static final int STEP_PAUSE_MS = 10;

    static final double MIN_SPEED_DIF = 0.2;

    static final int LOWER_BOUND = 0;
    static final int UPPER_BOUND = 1000;
    static final int LEFT_BOUND = 0;
    static final int RIGHT_BOUND = 2000;
    static final int BACK_BOUND = 3000;
    static final int FRONT_BOUND = 0;

    static final int SCREEN_CENTER_X = 680;
    static final int SCREEN_CENTER_Y = 360;

    static final float CAMERA_SPEED = 30 / 100.0f;
    public static final double ROT_ANGLE = 0.03;

    static final int CADRES_QUEUE_SIZE = 50;
    static final double VIEWING_ANGLE = 36;
    static final double PROJ_PLANE_DISTANCE = Math.tan(VIEWING_ANGLE * Math.PI / 180 / 2);
}
