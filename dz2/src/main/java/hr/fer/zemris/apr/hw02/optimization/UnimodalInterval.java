package hr.fer.zemris.apr.hw02.optimization;

import hr.fer.zemris.apr.hw02.function.IFunction;

/**
 * Models an <code>unimodal</code> interval through {@link #create(IFunction, double)}.
 *
 * @author dbrcina
 */
public class UnimodalInterval {

    private static final int H = 1;

    /**
     * Creates an unimodal interval based on provided <code>function</code> and <code>initialPoint</code> where
     * <code>h</code> and <code>step</code> are set to <code>1</code>.
     *
     * @param function     evaluation function.
     * @param initialPoint initial point.
     * @return unimodal interval [left, right].
     */
    public static double[] create(IFunction function, double initialPoint) {
        double m = initialPoint;
        double l = initialPoint - H;
        double r = initialPoint + H;
        int step = 1;

        double fm = function.value(new double[]{initialPoint});
        double fl = function.value((new double[]{l}));
        double fr = function.value((new double[]{r}));

        if (fm < fr && fm < fl) {
            // DO NOTHING...
        } else if (fm > fr) {
            do {
                l = m;
                m = r;
                fm = fr;
                r = initialPoint + H * (step *= 2);
                fr = function.value((new double[]{r}));
            } while (fm > fr);
        } else {
            do {
                r = m;
                m = l;
                fm = fl;
                l = initialPoint - H * (step *= 2);
                fl = function.value((new double[]{l}));
            } while (fm > fl);
        }

        return new double[]{l, r};
    }

}
