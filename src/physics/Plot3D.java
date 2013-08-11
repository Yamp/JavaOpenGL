package physics;

import java.awt.*;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 19.07.13
 * Time: 17:24
 *
 * @author Ямпольский Дмитрий yampolskiydv@gmail.com
 */
public class Plot3D implements Options3D {
    static final Random rnd = new Random();

    static void add(BodyContainer3D container) {

//        container.bodies.add(new Body3D(80, 0.95, 0.5, 0, 200,
//                Color.BLUE, new Vector3D(1000, 2000, 5000), new Vector3D(600, 300, 400)));

//        container.bodies.add(new Body3D(80, 0.95, 0.5, 0, 100,
//                Color.BLUE, new Vector3D(0, 0, 0), new Vector3D(600, 300, 400)));
//        container.bodies.add(new Body3D(10, 0.95, 0.5, 0, 10,
//                Color.GREEN, new Vector3D(00, 00, 100), new Vector3D(0, 0, 100)));

//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//                container.bodies.add(new Body3D(1, 0.95, 0.5, 0, 50,
//                        new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256), 255),
//                        new Vector3D(000, 0, 5000), new Vector3D(10 + 110 * i, 100 + 110 * j, 500 + rnd.nextInt(20))));
//            }
//        }

        container.bodies.add(new Body3D(100, 0.95, 0.5, 0, 100,
                Color.GREEN, new Vector3D(2000, 0, 0), new Vector3D(400, 300, 300)));
        container.bodies.add(new Body3D(100, 0.95, 0.5, 0, 100,
                Color.GREEN, new Vector3D(-2000, 0, 0), new Vector3D(1000, 300, 300)));

    }
}
