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
 * Графический объект планета земля.
 */
public class Earth implements GraphicObject {
    private Texture texture;

    public Earth() {
        TextureData data = null;
        try(InputStream stream = getClass().getResourceAsStream("earth_texture1024x512.png")) {
            data = TextureIO.newTextureData(stream, false, "png");
            texture = TextureIO.newTexture(data);
        } catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void draw(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();

        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, rgba, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 3f);

        texture.enable();
        texture.bind();

        GLUquadric earth = glu.gluNewQuadric(); //Создание новой квадратичной поверхности
        glu.gluQuadricTexture(earth, true); //Разрешение использовать текстуры
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL); //Fill - плоскости, line - линии, point - точки, SILHOUETTE - силует.
        glu.gluQuadricNormals(earth, GLU.GLU_SMOOTH); //Задаем нормали (SMOOTH - к вершинам, FLAT - к ребрам, NONE - не задаем)
        glu.gluQuadricOrientation(earth, GLU.GLU_OUTSIDE); //Заем ориентацию нормалей (внуть, внаружу).
        final float radius = 6.378f; //Радиус нашей сферы.
        final int slices = 400; //Меридианы
        final int stacks = 40; //Параллели
        glu.gluSphere(earth, radius, slices, stacks); //Рисуем сферу в начале координат.
        glu.gluDeleteQuadric(earth); //И после всего процесса удаляем квадрику.
    }
}
