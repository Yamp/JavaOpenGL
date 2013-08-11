package physics;

/**
 * Created with IntelliJ IDEA.
 * User: dimitrius
 * Date: 07.08.13
 * Time: 22:20
 * To change this template use File | Settings | File Templates.
 */
public class Camera3D implements Options3D {
    public volatile static Vector3D direct = new Vector3D(0, 0, 1);
    public volatile static Vector3D up = new Vector3D(0, 1, 0);
    public volatile static Vector3D pos = new Vector3D(RIGHT_BOUND / 200, UPPER_BOUND / 200, -10);
}