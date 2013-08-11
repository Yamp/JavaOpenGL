package physics.functions;

/**
 * Created with IntelliJ IDEA.
 * User: dima
 * Date: 17.05.13
 * Time: 18:28
 *
 * @author Ямпольский Дмитрий yampolskiydv@gmail.com
 */
public class FunctionUtils implements PrecisionConstants {
    public static double dichotomyFindRoot(final SmartFunction function, double begin, double end) {
        double middle;

//        if (begin > end) {
//            double temp = begin;
//            begin = end;
//            end = temp;
//        }

        final double beginValue = function.calculate(begin);
        final double endValue = function.calculate(end);

        final int inTheLeft = (int)(beginValue / Math.abs(beginValue));

        if (beginValue * endValue == 0)
            return (beginValue == 0) ? begin : end;

        if (beginValue * endValue > 0)
            return Double.NaN;

        while (end - begin > DICHOTOMY_PRECISION) {
            middle = (begin + end) / 2;
            if (function.calculate(middle) * inTheLeft >= 0)
                begin = middle;
            else
                end = middle;
        }

        return (begin + end) / 2;
    }
}
