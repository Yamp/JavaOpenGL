package physics;

import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static javax.media.opengl.GL.GL_POLYGON;

public class LightTest extends GLCanvas implements GLEventListener, Options3D {

//    private static float Camera3D.pos.x;
//    private static float Camera3D.pos.y;
//    private static float Camera3D.pos.z;

    float f;

    private GLU glu;

    /** The frames per second setting. */
    private int fps = 40;

    /** The OpenGL animator. */
    private FPSAnimator animator;
    private static final BodyContainer3D container = new BodyContainer3D();

    /**
     * A new mini starter.
     *
     * @param capabilities The GL capabilities.
     * @param width The window width.
     * @param height The window height.
     */
    public LightTest(GLCapabilities capabilities, int width, int height) {
        addGLEventListener(this);
        Plot3D.add(container);
    }

    /**
     * Задаем количества бит для цветов и возвращаем странную штуку.
     */
    private static GLCapabilities createGLCapabilities() {
        GLCapabilities capabilities = new GLCapabilities();
        capabilities.setRedBits(8);
        capabilities.setBlueBits(8);
        capabilities.setGreenBits(8);
        capabilities.setAlphaBits(8);
        return capabilities;
    }

    /**
     * Sets up the screen.
     *
     * @see javax.media.opengl.GLEventListener#init(javax.media.opengl.GLAutoDrawable)
     */
    public void init(GLAutoDrawable drawable) {
        drawable.setGL(new DebugGL(drawable.getGL())); //Ну может быть зачем-то он тут и нужен...
        final GL gl = drawable.getGL(); //Объект со всеми функциями.


        float fogColor[] = {0.5f, 0.5f, 0.5f, 1.0f}; //set the for
        gl.glEnable(GL.GL_FOG); //enable the fog
        gl.glFogi(GL.GL_FOG_MODE, GL.GL_EXP); //set the fog mode to GL_EXP2
        gl.glFogfv(GL.GL_FOG_COLOR, fogColor, 0); //set the fog color to
        gl.glFogf(GL.GL_FOG_DENSITY, 0.005f); //set the density to the


        gl.glEnable(GL.GL_DEPTH_TEST); //Разрешаем z-буффер.
        gl.glDepthFunc(GL.GL_LEQUAL); //Задаем функцию глубины для z-буффера.

        gl.glShadeModel(GL.GL_SMOOTH); //Плавный переход цветов. Нужно чтобы тень красиво плавно переходила.
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0f); //Задаем цвет затирания. Ну то-есть у нас это цвет фона.
//        gl.glEnable(GL.GL_NORMALIZE);//???

        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); //Все настройки на максимум.
        gl.glHint(GL.GL_POINT_SMOOTH_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_TEXTURE_COMPRESSION_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_FRAGMENT_SHADER_DERIVATIVE_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_FOG_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_GENERATE_MIPMAP_HINT, GL.GL_NICEST);
//
        gl.glEnable(GL.GL_LIGHTING); //Разрешить использовать освещение.

        float model_ambient[] = {0.0f, 0.0f, 0.0f, 1.0f};
        gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, model_ambient, 0);
        gl.glLightModeli(GL.GL_LIGHT_MODEL_TWO_SIDE, GL.GL_TRUE);
        gl.glLightModeli(GL.GL_LIGHT_MODEL_LOCAL_VIEWER, GL.GL_TRUE); //Почему-то пропадают блики


        float[] rgba0 = {1, 0, 0, 1};
        float[] rgbam0 = {0.1f, 0.0f, 0.0f, 1};
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, new float[]{1, 1, BACK_BOUND / 100 - 1, 1}, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, rgbam0, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, rgba0, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, rgba0, 0);
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_QUADRATIC_ATTENUATION, new float[]{0.01f}, 0);


        float[] rgba1 = {0, 0, 1, 1};
        float[] rgbam1 = {0.0f, 0.0f, 0.1f, 1};
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, new float[]{RIGHT_BOUND / 100 - 1, UPPER_BOUND / 100 - 1, BACK_BOUND / 100 - 1, 1}, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, rgbam1, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, rgba1, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, rgba1, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_QUADRATIC_ATTENUATION, new float[]{0.01f}, 0);


        float[] rgba2 = {0, 1, 0, 1};
        float[] rgbam2 = {0.0f, 0.1f, 0.0f, 1};
        gl.glLightfv(GL.GL_LIGHT2, GL.GL_POSITION, new float[]{RIGHT_BOUND / 100 - 1, 1, 1, 1}, 0);
        gl.glLightfv(GL.GL_LIGHT2, GL.GL_AMBIENT, rgbam2, 0);
        gl.glLightfv(GL.GL_LIGHT2, GL.GL_SPECULAR, rgba2, 0);
        gl.glLightfv(GL.GL_LIGHT2, GL.GL_DIFFUSE, rgba2, 0);
        gl.glLightfv(GL.GL_LIGHT2, GL.GL_QUADRATIC_ATTENUATION, new float[]{0.01f}, 0);


        float[] rgba3 = {1, 1, 1, 1};
        float[] rgbam3 = {0.1f, 0.1f, 0.1f, 1};
        gl.glLightfv(GL.GL_LIGHT3, GL.GL_POSITION, new float[]{1, UPPER_BOUND / 100 - 1, 1, 1}, 0);
        gl.glLightfv(GL.GL_LIGHT3, GL.GL_AMBIENT, rgbam3, 0);
        gl.glLightfv(GL.GL_LIGHT3, GL.GL_SPECULAR, rgba3, 0);
        gl.glLightfv(GL.GL_LIGHT3, GL.GL_DIFFUSE, rgba3, 0);
        gl.glLightfv(GL.GL_LIGHT3, GL.GL_QUADRATIC_ATTENUATION, new float[]{0.001f}, 0);

        gl.glEnable(GL.GL_LIGHT0);
//        gl.glEnable(GL.GL_LIGHT1);
//        gl.glEnable(GL.GL_LIGHT2);
        gl.glEnable(GL.GL_LIGHT3);


        glu = new GLU(); //Глу он и есть глу.

        animator = new FPSAnimator(this, fps);
        animator.start(); //Создаем и запускаем аниматора.
    }

    public void drawBody(GLAutoDrawable drawable, Body3D body) {

        final GL gl = drawable.getGL();

        float[] color = {body.color.getRed() / 255.0f, body.color.getGreen() / 255.0f, body.color.getBlue() / 255.0f, 1};

        float[] rgba = {1.0f, 1.0f, 1.0f, 1};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, color, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, color, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 90f);

        gl.glPushMatrix();

        gl.glTranslatef((float) body.position.x / 100.0f, (float) body.position.y / 100.0f, (float) body.position.z / 100.0f);

        GLUquadric earth = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluQuadricNormals(earth, GLU.GLU_SMOOTH);
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE);
        final double radius = body.size / 100.0f;
        final int slices = 10; //Меридианы
        final int stacks = 10; //Параллели
        glu.gluSphere(earth, radius, slices, stacks);
        glu.gluDeleteQuadric(earth);

        gl.glPopMatrix();
    }

    public void drawWalls(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        float[] rgba = {0.5f, 0.5f, 0.5f};
        float[] white = {1.0f, 1.0f, 1.0f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, white, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 100f);

        gl.glBegin(GL_POLYGON);
        //Правая стена
        gl.glNormal3f(1, 0, 0);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(0, UPPER_BOUND / 100, 0);
        gl.glVertex3f(0, UPPER_BOUND / 100, BACK_BOUND / 100);
        gl.glVertex3f(0, 0, BACK_BOUND / 100);
        gl.glEnd();

      /*  gl.glBegin(GL_POLYGON);
        gl.glColor4f(0, 1, 0, 0.2f);
        //2
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(RIGHT_BOUND / 100, 0, 0);
        gl.glVertex3f(RIGHT_BOUND / 100, UPPER_BOUND / 100, 0);
        gl.glVertex3f(0, UPPER_BOUND / 100, 0);
        gl.glVertex3f(0, 0, 0);
        gl.glEnd(); */

        gl.glBegin(GL_POLYGON);
        //Пол
        gl.glNormal3f(0, 1, 0);
        gl.glVertex3f(RIGHT_BOUND / 100, 0, 0);
        gl.glVertex3f(0, 0, 0);
        gl.glVertex3f(0, 0, BACK_BOUND / 100);
        gl.glVertex3f(RIGHT_BOUND / 100, 0, BACK_BOUND / 100);
        gl.glEnd();

        gl.glBegin(GL_POLYGON);
        //Левая стена
        gl.glNormal3f(-1, 0, 0);
        gl.glVertex3f(RIGHT_BOUND / 100, UPPER_BOUND / 100, BACK_BOUND / 100);
        gl.glVertex3f(RIGHT_BOUND / 100, UPPER_BOUND / 100, 0);
        gl.glVertex3f(RIGHT_BOUND / 100, 0, 0);
        gl.glVertex3f(RIGHT_BOUND / 100, 0, BACK_BOUND / 100);
        gl.glEnd();

        gl.glBegin(GL_POLYGON);
        //Потолок
        gl.glNormal3f(0, -1, 0);
        gl.glVertex3f(0, UPPER_BOUND / 100, 0);
        gl.glVertex3f(RIGHT_BOUND / 100, UPPER_BOUND / 100, 0);
        gl.glVertex3f(RIGHT_BOUND / 100, UPPER_BOUND / 100, BACK_BOUND / 100);
        gl.glVertex3f(0, UPPER_BOUND / 100, BACK_BOUND / 100);
        gl.glEnd();

        gl.glBegin(GL_POLYGON);
        //Задняя стенка
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(0, 0, BACK_BOUND / 100);
        gl.glVertex3f(0, UPPER_BOUND / 100, BACK_BOUND / 100);
        gl.glVertex3f(RIGHT_BOUND / 100, UPPER_BOUND / 100, BACK_BOUND / 100);
        gl.glVertex3f(RIGHT_BOUND / 100, 0, BACK_BOUND / 100);
        gl.glEnd();
    }

    /**
     * Собственно всякое изображалово.
     */
    public void display(GLAutoDrawable drawable) {
        if (!animator.isAnimating()) {
            return;
        }

        final GL gl = drawable.getGL();

        // Set camera.
        setCamera(gl, glu);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        container.recalculateSpeeds();
        Step3D step = container.recalculatePositions();
        ArrayList<Body3D> bodies = new ArrayList<>(step.bodies);

        drawWalls(drawable);

        for (Body3D body : bodies) {
            drawBody(drawable, body);
        }
    }

    /**
     * Шняга при изменении размеров окна.
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL gl = drawable.getGL();
        gl.glViewport(0, 0, width, height);
    }

    /**
     * Ну вроде бы как нужно реализовать этот метод:)
     */
    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        throw new UnsupportedOperationException("Changing display is not supported.");
    }

    /**
     * @param gl The GL context.
     * @param glu The GL unit.
     */
    private void setCamera(GL gl, GLU glu) {
        // Change to projection matrix.
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        //Ну тут задается самая фишка с перспективой, плоскости - обрезатели и положение камеры.
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(36, widthHeightRatio, 0.001, 5000);
        glu.gluLookAt(Camera3D.pos.x, Camera3D.pos.y, Camera3D.pos.z,
                Camera3D.pos.x + Camera3D.direct.x, Camera3D.pos.y + Camera3D.direct.y, Camera3D.pos.z + Camera3D.direct.z,
                Camera3D.up.x, Camera3D.up.y, Camera3D.up.z);

        // Change back to model view matrix.
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /**
     * Starts the JOGL mini demo.
     *
     * @param args Command line args.
     */
    public final static void main(String[] args) {
        GLCapabilities capabilities = createGLCapabilities();
        LightTest canvas = new LightTest(capabilities, 800, 500);
        JFrame frame = new JFrame("Mini JOGL Demo (breed)");
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        int size = frame.getExtendedState();
        size |= Frame.MAXIMIZED_BOTH;
        frame.setExtendedState(size);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        Camera3D.pos = Vector3D.add(Camera3D.pos, Matrix3DConstants.getVect("RIGHT").mult(CAMERA_SPEED));
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        Camera3D.pos = Vector3D.sub(Camera3D.pos, Matrix3DConstants.getVect("RIGHT").mult(CAMERA_SPEED));
                        break;
                    case KeyEvent.VK_UP:
                        Camera3D.pos = Vector3D.add(Camera3D.pos, Matrix3DConstants.getVect("UP").mult(CAMERA_SPEED));
                        break;
                    case KeyEvent.VK_DOWN:
                        Camera3D.pos = Vector3D.sub(Camera3D.pos, Matrix3DConstants.getVect("UP").mult(CAMERA_SPEED));
                        break;
                    case KeyEvent.VK_W:
                        Camera3D.pos = Vector3D.add(Camera3D.pos, Matrix3DConstants.getVect("FRONT").mult(CAMERA_SPEED));
                        break;
                    case KeyEvent.VK_S:
                        Camera3D.pos = Vector3D.sub(Camera3D.pos, Matrix3DConstants.getVect("FRONT").mult(CAMERA_SPEED));
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        canvas.addMouseMotionListener(new Camera3DMouseListener());

        frame.setVisible(true);
        canvas.requestFocus();
    }

}

