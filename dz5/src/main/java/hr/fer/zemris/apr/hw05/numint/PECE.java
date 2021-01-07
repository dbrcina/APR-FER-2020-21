package hr.fer.zemris.apr.hw05.numint;

import hr.fer.zemris.apr.hw01.math.IMatrix;

import java.util.List;
import java.util.Properties;
import java.util.function.Function;

/**
 * @author dbrcina
 */
public class PECE extends NumIntAlgorithm {

    private final int s;

    private NumIntAlgorithm predictor;
    private NumIntAlgorithm corrector;

    public PECE(int s) {
        super("pece" + (s >= 2 ? s : "") + ".csv");
        this.s = s;
    }

    @Override
    protected void configureInternal(Properties configuration) {
        Object predictorObject = configuration.get("predictor");
        if (predictorObject == null) {
            throw new RuntimeException("Predictor is missing!");
        }
        Object correctorObject = configuration.get("corrector");
        if (correctorObject == null) {
            throw new RuntimeException("Corrector is missing!");
        }
        try {
            predictor = (NumIntAlgorithm) Class.forName((String) predictorObject)
                    .getConstructor()
                    .newInstance();
            corrector = (NumIntAlgorithm) Class.forName((String) correctorObject)
                    .getConstructor()
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        predictor.configure(configuration);
        corrector.configure(configuration);
    }

    @Override
    public IMatrix calculateExplicit(IMatrix xk, IMatrix A, IMatrix B, List<Function<Double, Double>> rFunctions) {
        predictor.setCurrentT(getCurrentT());
        corrector.setCurrentT(getCurrentT());
        IMatrix xk1 = predictor.calculateExplicit(xk, A, B, rFunctions);
        for (int i = 0; i < s; i++) {
            xk1 = corrector.calculateImplicit(xk, xk1, A, B, rFunctions);
        }
        return xk1;
    }

    @Override
    protected IMatrix calculateImplicit(
            IMatrix xk, IMatrix xk1, IMatrix A, IMatrix B, List<Function<Double, Double>> rFunctions) {
        return null;
    }

}
