package hr.fer.zemris.apr.hw05.numint;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;

import java.util.List;
import java.util.Properties;
import java.util.function.Function;

/**
 * @author dbrcina
 */
public class Euler extends NumIntAlgorithm {

    public Euler() {
        super("euler.csv");
    }

    @Override
    protected void configureInternal(Properties configuration) {
    }

    @Override
    public IMatrix calculateExplicit(IMatrix xk, IMatrix A, IMatrix B, List<Function<Double, Double>> rFunctions) {
        double intPeriod = getIntegrationPeriod();
        double tk = getCurrentT();
        IMatrix rk = new Matrix(xk.getRowsCount(), xk.getColumnsCount());
        for (int i = 0; i < rFunctions.size(); i++) {
            rk.set(i, 0, rFunctions.get(i).apply(tk));
        }
        return xk.nAdd(A.mul(xk).add(B.mul(rk)).scalarMul(intPeriod));
    }

    @Override
    protected IMatrix calculateImplicit(
            IMatrix xk, IMatrix xk1, IMatrix A, IMatrix B, List<Function<Double, Double>> rFunctions) {
        return null;
    }

}
