package physics.functions;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 17.05.13
 * Time: 18:13
 *
 * @author Ямпольский Дмитрий yampolskiydv@gmail.com
 */
public class SmartFunction implements DoubleFunction, PrecisionConstants {

    final DoubleFunction function;

    public SmartFunction(DoubleFunction function) {
        this.function = function;
    }

    @Override
    public double calculate(double x) {
        return function.calculate(x);
    }

    public DoubleFunction derive() {
        return new DoubleFunction() {
            @Override
            public double calculate(double x) {
                return (function.calculate(x + DERIVATIVE_PRECISION) - function.calculate(x)) / DERIVATIVE_PRECISION;
            }
        };
    }
}
