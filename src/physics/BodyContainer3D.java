package physics;

import java.util.ArrayList;
import java.util.List;

import static physics.Vector3D.*;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 08.07.13
 * Time: 21:58
 *
 * @author Ямпольский Дмитрий yampolskiydv@gmail.com
 */
public class BodyContainer3D implements Options3D {

    public final List<Body3D> bodies = new ArrayList<>(100);

    public boolean checkCollision(Body3D a, Body3D b) {
        boolean result = dist(a.position, b.position) <= a.size + b.size;
        if (result) {
            double diff = a.size + b.size - dist(a.position, b.position);

            Vector3D normAxis = getNormAxis(a, b);
            a.position = sub(a.position, normAxis.mult(diff/2));
            b.position = add(b.position, normAxis.mult(diff/2));
        }
        if (result && sub(a.speed, b.speed).getNorm() < MIN_SPEED_DIF) {
            Vector3D temp = sub(a.speed, b.speed).mult(1.0/2);
            a.speed = sub(a.speed, temp);
            b.speed = add(b.speed, temp);
            return false;
        }
        return result;
    }

    /**
     * Вектор из а в b
     */
    Vector3D getNormAxis(Body3D a, Body3D b) {
        return sub(b.position, a.position).normalize();
    }

    public void reactCollision(Body3D a, Body3D b) {
        Vector3D normAxis = getNormAxis(a, b);

        Vector3D aNorm = proj(a.speed, normAxis);
        Vector3D bNorm = proj(b.speed, normAxis);
        Vector3D aTan = sub(a.speed, aNorm);
        Vector3D bTan = sub(b.speed, bNorm);

//        aTan = aTan.mult(1 - 0.05 * b.mass / (a.mass + b.mass));
//        bTan = bTan.mult(1 - 0.05 * a.mass / (a.mass + b.mass));

        Vector3D centerSpeed = add(aNorm.mult(a.mass), bNorm.mult(b.mass)).mult(1/(a.mass + b.mass));

        aNorm = sub(aNorm, centerSpeed);
        bNorm = sub(bNorm, centerSpeed);

        double vA = aNorm.getNorm();
        double vB = -bNorm.getNorm();
        double nvA = b.mass * (vB - vA) / (a.mass + b.mass) * a.elasticity * b.elasticity;
        double nvB = a.mass * (vA - vB) / (a.mass + b.mass) * a.elasticity * b.elasticity;

        aNorm = normAxis.mult(nvA);
        bNorm = normAxis.mult(nvB);

        aNorm = add(aNorm, centerSpeed);
        bNorm = add(bNorm, centerSpeed);

        a.speed = add(aNorm, aTan);
        b.speed = add(bNorm, bTan);
    }

    //Ну вроде как эти коллизии ничего не должно прошибить...
    void reactEdgeCollision(Body3D a) {
        if (a.position.y + a.size >= UPPER_BOUND) { //скорость положительна
            a.speed.y = -a.speed.y * a.elasticity * WALL_ELASTICITY;
            a.position.y = UPPER_BOUND - a.size - 1;
            if (a.speed.y > 0)
                a.speed.y = 0;
        }
        if (a.position.y - a.size <= LOWER_BOUND) { //скорость отр
            a.speed.y = -a.speed.y * a.elasticity * WALL_ELASTICITY;
            a.position.y = LOWER_BOUND + a.size + 1;
            if (a.speed.y < 0)
                a.speed.y = 0;
        }
        if (a.position.x + a.size >= RIGHT_BOUND) {//Полож
            a.speed.x = -a.speed.x * a.elasticity * WALL_ELASTICITY;
            a.position.x = RIGHT_BOUND - a.size - 1;
            if (a.speed.x > 0)
                a.speed.x = 0;
        }
        if (a.position.x - a.size <= LEFT_BOUND) {//Отрицат
            a.speed.x = -a.speed.x * a.elasticity * WALL_ELASTICITY;
            a.position.x = LOWER_BOUND + a.size + 1;
            if (a.speed.x < 0)
                a.speed.x = 0;
        }
        if (a.position.z + a.size >= BACK_BOUND) {//Полож
            a.speed.z = -a.speed.z * a.elasticity * WALL_ELASTICITY;
            a.position.z = BACK_BOUND - a.size - 1;
            if (a.speed.z > 0)
                a.speed.z = 0;
        }
        if (a.position.z - a.size <= FRONT_BOUND) {//Отрицат
            a.speed.z = -a.speed.z * a.elasticity * WALL_ELASTICITY;
            a.position.z = FRONT_BOUND + a.size + 1;
            if (a.speed.z < 0)
                a.speed.z = 0;
        }
    }

    public void gravInteract(Body3D a, Body3D b) {
        double R2 = Math.pow(dist(a.position, b.position), 2);
        Vector3D normAxis = getNormAxis(a, b);

        a.speed = add(a.speed, normAxis.mult(b.mass * GRAV_CONST / R2));
        b.speed = sub(b.speed, normAxis.mult(a.mass * GRAV_CONST / R2));
    }

    public void recalculateSpeeds() {
        for (int i = 0; i < bodies.size(); ++i) {
//            bodies.get(i).speed.y -= 300;
            reactEdgeCollision(bodies.get(i));
            for (int j = i + 1; j < bodies.size(); ++j) {
                gravInteract(bodies.get(i), bodies.get(j));
                if (checkCollision(bodies.get(i), bodies.get(j)))
                    reactCollision(bodies.get(i), bodies.get(j));
            }
        }
    }

    public Step3D recalculatePositions() {
        for (Body3D body : bodies)
            body.position = add(body.position, body.speed.mult(SPEED_CONST));

        return new Step3D(bodies, 0, 0);
    }
}
