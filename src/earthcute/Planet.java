package earthcute;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;
import javax.swing.JFrame;

import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

public class Planet extends GLCanvas implements GLEventListener {

    private static float cX;
    private static float cY;
    private static float cZ;

    private GLU glu;

    /** The frames per second setting. */
    private int fps = 60;

    /** The OpenGL animator. */
    private FPSAnimator animator;

    private Earth earth;
    private Satellite satellite;

    /** The angle of the satellite orbit (0..359). */
    private float satelliteAngle = 0;

    /** The texture for a solar panel. */
    private Texture solarPanelTexture;

    /**
     * A new mini starter.
     *
     * @param capabilities The GL capabilities.
     * @param width The window width.
     * @param height The window height.
     */
    public Planet(GLCapabilities capabilities, int width, int height) {
        addGLEventListener(this);
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

        gl.glEnable(GL.GL_DEPTH_TEST); //Разрешаем z-буффер.
        gl.glDepthFunc(GL.GL_LEQUAL); //Задаем функцию глубины для z-буффера.

        gl.glShadeModel(GL.GL_SMOOTH); //Плавный переход цветов. Нужно чтобы тень красиво плавно переходила.
        gl.glClearColor(0f, 0f, 0.2f, 0f); //Задаем цвет затирания. Ну то-есть у нас это цвет фона.

        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); //Все настройки на максимум.
        gl.glHint(GL.GL_POINT_SMOOTH_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_TEXTURE_COMPRESSION_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_FRAGMENT_SHADER_DERIVATIVE_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_FOG_HINT, GL.GL_NICEST);
        gl.glHint(GL.GL_GENERATE_MIPMAP_HINT, GL.GL_NICEST);

        glu = new GLU(); //Глу он и есть глу.

        earth = new Earth();
        satellite = new Satellite();


        animator = new FPSAnimator(this, fps);
        animator.start(); //Создаем и запускаем аниматора.
    }

    /**
     * Собственно всякое изображалово.
     */
    public void display(GLAutoDrawable drawable) {
        if (!animator.isAnimating()) {
            return;
        }
        final GL gl = drawable.getGL();


        // Clear screen.
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Set camera.
        setCamera(gl, glu, 30);

        //Всякие данные освещения.
        float SHINE_ALL_DIRECTIONS = 1; //Источник светит во все стороны, находится в опр. точке.
        float SHINE_ONE_SIDE = 0; //Источник света находится на бесконечности в заданом направлении.
        float[] lightPos = {-30, 0, 0, SHINE_ONE_SIDE}; //Та самая позиция света. (4d координаты)
        float[] lightColorAmbient = {0.3f, 0.3f, 0.3f, 1f};
        float[] lightColorSpecular = {1f, 1f, 1f, 1f};

        // Set light parameters.
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, lightColorSpecular, 0);

        gl.glEnable(GL.GL_LIGHT1); //Разрешить источники типа LIGHT1. (Пока хз что это)
        gl.glEnable(GL.GL_LIGHTING); //Разрешить использовать освещение.

        gl.glPushMatrix();
        gl.glRotatef((float) (satelliteAngle / Math.E / 2), 0, 0, 1);
        earth.draw(drawable);
        gl.glPopMatrix(); //Вернули невращаюшуюся матрицу.


        gl.glPushMatrix(); //И сохранили ее в стек.

        // Compute satellite position.
        satelliteAngle += 1;
        final float distance = 12.000f;
        final float x = (float) Math.sin(Math.toRadians(satelliteAngle)) * distance;
        final float z = (float) Math.cos(Math.toRadians(satelliteAngle)) * distance;
        final float y = 0;
        gl.glTranslatef(x, y, z);
        gl.glRotatef((float) (satelliteAngle * Math.PI), 0, 0, -1); //Это вращение спутника вокруг своей оси.
        gl.glRotatef(45f, 0, 1, 0);

        // Set silver color, and disable texturing.
        gl.glDisable(GL.GL_TEXTURE_2D);
        satellite.draw(drawable);

        // Restore old state.
        gl.glPopMatrix();
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
     * @param distance The distance from the screen.
     */
    private void setCamera(GL gl, GLU glu, float distance) {
        // Change to projection matrix.
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();

        //Ну тут задается самая фишка с перспективой, плоскости - обрезатели и положение камеры.
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 0.001, 1000);
        glu.gluLookAt(cX, cY, distance + cZ, cX, cY, cZ + distance - 10, 0, 1, 0);

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
        Planet canvas = new Planet(capabilities, 800, 500);
        JFrame frame = new JFrame("Mini JOGL Demo (breed)");
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.setSize(800, 500);
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
                        cX += 0.1;
                        break;
                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        cX -= 0.1;
                        break;
                    case KeyEvent.VK_UP:
                        cY += 0.1;
                        break;
                    case KeyEvent.VK_DOWN:
                        cY -= 0.1;
                        break;
                    case KeyEvent.VK_W:
                        cZ -= 0.1;
                        break;
                    case KeyEvent.VK_S:
                        cZ += 0.1;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        frame.setVisible(true);
        canvas.requestFocus();
    }

}

