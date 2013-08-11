package physics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 18.07.13
 * Time: 21:45
 *
 * @author Ямпольский Дмитрий yampolskiydv@gmail.com
 */
public class Step3D {
    public static int steps = 0;

    public List<Body3D> bodies;
    long cadreNumber;
    int cadresInQueue;

    public Step3D(List<Body3D> bodies, long cadreNumber, int cadresInQueue) {
        steps++;
        this.cadreNumber = cadreNumber;
        this.cadresInQueue = cadresInQueue;
        this.bodies = new ArrayList<>(bodies.size());
        for (Body3D body : bodies)
            this.bodies.add(body.clone());
    }
}
