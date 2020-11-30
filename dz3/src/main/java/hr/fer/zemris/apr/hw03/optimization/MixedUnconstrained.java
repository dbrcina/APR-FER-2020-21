package hr.fer.zemris.apr.hw03.optimization;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw03.function.AbstractFunction;
import hr.fer.zemris.apr.hw03.function.IFunction;
import hr.fer.zemris.apr.hw03.optimization.constraint.ArgsConstraints;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author dbrcina
 */
public class MixedUnconstrained extends AbstractOptAlgorithm {

    private static final long DEFAULT_T = 1;
    private static final int DEFAULT_STEP = 10;

    private long t = DEFAULT_T;
    private int step = DEFAULT_STEP;

    /**
     * @see AbstractOptAlgorithm#AbstractOptAlgorithm(int)
     */
    protected MixedUnconstrained() {
        super(0);
    }

    @Override
    public void configure(Properties properties) throws Exception {
        try {
            super.configure(properties);
            Object tObj = properties.get("t");
            if (tObj != null) {
                t = Long.parseLong((String) tObj);
            }
            Object stepObj = properties.get("step");
            if (stepObj != null) {
                step = Integer.parseInt((String) stepObj);
            }
        } catch (NumberFormatException | ConfigInvalidException e) {
            handleConfigureExceptions(e);
        }
    }

    @Override
    public IMatrix run(IFunction function) {
        super.run(function);

        IMatrix current = getInitialPoint();
        IMatrix epsilons = getEpsilons();
        ArgsConstraints constraints = getConstraints();

        IOptAlgorithm alg = null;
        try {
            alg = OptAlgorithmProvider.getInstance("Simplex");
            alg.setEpsilons(epsilons);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!constraints.testImplicitConstraints(current)) {
            current = findInnerPoint(current, constraints, alg);
        }

        while (true) {
            IFunction f = new AbstractFunction() {
                @Override
                public double value(IMatrix point) {
                    double fX = function.value(point);
                    double[] inequalityValues = constraints.applyInequalityConstraints(point);
                    double sumGX = 0;
                    for (double v : inequalityValues) {
                        if (v <= 0) return Double.MAX_VALUE;
                        sumGX += Math.log(v);
                    }
                    double sumHX = Arrays.stream(constraints.applyEqualityConstraints(point))
                            .map(h -> h * h)
                            .sum();
                    return fX - 1.0 / t * sumGX + t * sumHX;
                }

                @Override
                public IMatrix gradient(IMatrix point) {
                    return null;
                }

                @Override
                public IMatrix hesse(IMatrix point) {
                    return null;
                }
            };
            alg.setInitialPoint(current);
            IMatrix solution = alg.run(f);
            if (solution.equals(current)) {
                current = solution;
                break;
            }
            current = solution;
            t *= step;
        }

        return current;
    }

    private IMatrix findInnerPoint(IMatrix point, ArgsConstraints constraints, IOptAlgorithm alg) {
        IFunction f = new AbstractFunction() {
            @Override
            public double value(IMatrix point) {
                return Arrays.stream(constraints.applyInequalityConstraints(point))
                        .filter(v -> v < 0)
                        .sum() * (-1);
            }

            @Override
            public IMatrix gradient(IMatrix point) {
                return null;
            }

            @Override
            public IMatrix hesse(IMatrix point) {
                return null;
            }
        };
        alg.setInitialPoint(point);
        return alg.run(f);
    }

}
