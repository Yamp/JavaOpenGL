package earthcute;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: dimitrius
 * Date: 08.08.13
 * Time: 14:07
 * To change this template use File | Settings | File Templates.
 */
public class Satellite implements GraphicObject {

    private Texture texture;

    public Satellite() {
        try(InputStream stream = getClass().getResourceAsStream("solar_panel_256x32.png")) {
            TextureData data = TextureIO.newTextureData(stream, false, "png");
            texture = TextureIO.newTexture(data);
        }
        catch (IOException exc) {
            exc.printStackTrace();
            System.exit(2);
        }
    }

    @Override
    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        float[] ambiColor = {0.3f, 0.3f, 0.3f, 1f};
        float[] specColor = {0.8f, 0.8f, 0.8f, 1f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, ambiColor, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specColor, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 90f);

        // Draw satellite body.
        final float cylinderRadius = 1f;
        final float cylinderHeight = 2f;
        final int cylinderSlices = 360;
        final int cylinderStacks = 1;
        GLUquadric body = glu.gluNewQuadric();
        glu.gluQuadricTexture(body, false);
        glu.gluQuadricDrawStyle(body, GLU.GLU_FILL);
        glu.gluQuadricNormals(body, GLU.GLU_FLAT);
        glu.gluQuadricOrientation(body, GLU.GLU_OUTSIDE);
        gl.glTranslatef(0, 0, -cylinderHeight / 2);
        glu.gluDisk(body, 0, cylinderRadius, cylinderSlices, 2);
        glu.gluCylinder(body, cylinderRadius, cylinderRadius, cylinderHeight, cylinderSlices, cylinderStacks); //Цилиндр (на самом деле конус)
        gl.glTranslatef(0, 0, cylinderHeight);
        glu.gluDisk(body, 0, cylinderRadius, cylinderSlices, 2);
        glu.gluDeleteQuadric(body);
        gl.glTranslatef(0, 0, -cylinderHeight / 2);

        // Set white color, and enable texturing.
        float[] rgba = {1f, 1f, 1f};
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0f);

        // Draw solar panels.
        gl.glScalef(6f, 0.7f, 0.1f);
        texture.bind();
        gl.glBegin(GL.GL_QUADS);
        final float[] frontUL = {-1.0f, -1.0f, 1.0f};
        final float[] frontUR = {1.0f, -1.0f, 1.0f};
        final float[] frontLR = {1.0f, 1.0f, 1.0f};
        final float[] frontLL = {-1.0f, 1.0f, 1.0f};
        final float[] backUL = {-1.0f, -1.0f, -1.0f};
        final float[] backLL = {-1.0f, 1.0f, -1.0f};
        final float[] backLR = {1.0f, 1.0f, -1.0f};
        final float[] backUR = {1.0f, -1.0f, -1.0f};
        // Front Face.
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3fv(frontUR, 0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3fv(frontUL, 0);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3fv(frontLL, 0);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3fv(frontLR, 0);
        // Back Face.
        gl.glNormal3f(0.0f, 0.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3fv(backUL, 0);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3fv(backUR, 0);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3fv(backLR, 0);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3fv(backLL, 0);
        gl.glEnd();
    }
}
