package earthcute;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

/**
 * Created with IntelliJ IDEA.
 * User: dimitrius
 * Date: 08.08.13
 * Time: 13:23
 * To change this template use File | Settings | File Templates.
 */
public interface GraphicObject {
    final static GLU glu = new GLU();
    public void draw(GLAutoDrawable drawable);
}
