package parallelepiped;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static javax.media.opengl.GL.*;

public class JavaRenderer
        implements GLEventListener, KeyListener {
    private float rotateT = 0.0f;
    private static final GLU glu = new GLU();
    private float z = 0;
    private GL gl;

    float cX, cY, cZ;

    public void display(GLAutoDrawable gLDrawable) {
        gl = gLDrawable.getGL();

        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT1);
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//        gl.glEnable(GL_ALPHA_TEST);
//        gl.glEnable(GL_BLEND);
//        gl.glBlendFunc(GL_SRC0_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        //Всякие данные освещения.
        float SHINE_ALL_DIRECTIONS = 1; //Источник светит во все стороны, находится в опр. точке.
        float SHINE_ONE_SIDE = 0; //Источник света находится на бесконечности в заданом направлении.
        float[] lightPos = {-30, 0, 0, SHINE_ONE_SIDE}; //Та самая позиция света. (4d координаты)
        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

        // Set light parameters.
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, lightColorSpecular, 0);

        gl.glEnable(GL.GL_LIGHT1); //Разрешить источники типа LIGHT1. (Пока хз что это)
        gl.glEnable(GL.GL_LIGHTING); //Разрешить использовать освещение.

        // Set material properties.
        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.5f);

        gl.glPushMatrix();

        gl.glTranslatef(cX, 0.0f, -5.0f);
        gl.glTranslatef(0.0f, cY, -5.0f);
        gl.glTranslatef(0.0f, 0.0f, cZ - 5.0f);


        gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);

//        glu.auxSolidCilinder(1);

        gl.glBegin(GL_POLYGON);
        gl.glColor4f(0, 0, 1, 0.2f);
        //1
        gl.glNormal3f(-1, 0, 0);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(0, 1, 0);
        gl.glVertex3f(0, 1, 1);
        gl.glVertex3f(0, 0, 1);
        gl.glEnd();

        gl.glBegin(GL_POLYGON);
        gl.glColor4f(0, 1, 0, 0.2f);
        //2
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(1, 0, 0);
        gl.glVertex3f(1, 1, 0);
        gl.glVertex3f(0, 1, 0);
        gl.glVertex3f(0, 0, 0);
        gl.glEnd();

        gl.glBegin(GL_POLYGON);
        gl.glColor4f(1, 0, 0, 0.2f);
        //3
        gl.glNormal3f(0, -1, 0);
        gl.glVertex3f(1, 0, 1);
        gl.glVertex3f(0, 0, 1);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(1, 0, 0);
        gl.glEnd();

        gl.glBegin(GL_POLYGON);
        gl.glColor4f(1, 1, 0, 0.2f);
        //4
        gl.glNormal3f(1, 0, 0);
        gl.glVertex3f(1, 1, 1);
        gl.glVertex3f(1, 1, 0);
        gl.glVertex3f(1, 0, 0);
        gl.glVertex3f(1, 0, 1);
        gl.glEnd();

        gl.glBegin(GL_POLYGON);
        gl.glColor4f(1, 0, 1, 0.2f);
        //5
        gl.glNormal3f(0, 1, 0);
        gl.glVertex3f(0, 1, 0);
        gl.glVertex3f(1, 1, 0);
        gl.glVertex3f(1, 1, 1);
        gl.glVertex3f(0, 1, 1);
        gl.glEnd();

        gl.glBegin(GL_POLYGON);
        gl.glColor4f(0, 1, 1, 0.2f);
        //6
        gl.glNormal3f(0, 0, 1);
        gl.glVertex3f(0, 0, 1);
        gl.glVertex3f(0, 1, 1);
        gl.glVertex3f(1, 1, 1);
        gl.glVertex3f(1, 0, 1);
        gl.glEnd();

        gl.glPopMatrix();

        gl.glDisable(GL_BLEND);
        gl.glDisable(GL_ALPHA_TEST);

        rotateT += 0.2f;
//        z -= 0.01;
    }

    public void displayChanged(GLAutoDrawable gLDrawable,
                               boolean modeChanged, boolean deviceChanged) {
    }

    public void init(GLAutoDrawable gLDrawable) {
        gl = gLDrawable.getGL();
        gl.glShadeModel(GL_FLAT); //Режим интерполяции цвета

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);

        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LEQUAL);
        gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT,
                GL_NICEST);
        gLDrawable.addKeyListener(this);
    }

    public void reshape(GLAutoDrawable gLDrawable, int x,
                        int y, int width, int height) {
        final GL gl = gLDrawable.getGL();
        if(height <= 0) {
            height = 1;
        }
        final float h = (float)width / (float)height;
        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_RIGHT:
                cX -= 0.1;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_LEFT:
                cX += 0.1;
                break;
            case KeyEvent.VK_UP:
                cY -= 0.1;
                break;
            case KeyEvent.VK_DOWN:
                cY += 0.1;
                break;
            case KeyEvent.VK_W:
                cZ += 0.1;
                break;
            case KeyEvent.VK_S:
                cZ -= 0.1;
                break;
            case KeyEvent.VK_ESCAPE:
                JavaDia.bQuit = true;
                JavaDia.displayT = null;
                System.exit(0);
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}