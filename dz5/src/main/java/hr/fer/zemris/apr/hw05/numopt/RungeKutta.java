package hr.fer.zemris.apr.hw05.numopt;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;

import java.util.List;
import java.util.Properties;
import java.util.function.Function;

/**
 * @author dbrcina
 */
public class RungeKutta extends NumOptAlgorithm {

    public RungeKutta() {
        super("runge_kutta.csv");
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
        IMatrix m1 = A.mul(xk).add(B.mul(rk));
        for (int i = 0; i < rFunctions.size(); i++) {
            rk.set(i, 0, rFunctions.get(i).apply(tk + intPeriod / 2));
        }
        IMatrix m2 = A.mul(xk.nAdd(m1.nScalarMul(intPeriod / 2))).add(B.mul(rk));
        IMatrix m3 = A.mul(xk.nAdd(m2.nScalarMul(intPeriod / 2))).add(B.mul(rk));
        for (int i = 0; i < rFunctions.size(); i++) {
            rk.set(i, 0, rFunctions.get(i).apply(tk + intPeriod));
        }
        IMatrix m4 = A.mul(xk.nAdd(m3.nScalarMul(intPeriod))).add(B.mul(rk));
        return xk.nAdd(m1.add(m2.scalarMul(2).add(m3.scalarMul(2)).add(m4)).scalarMul(intPeriod / 6));
    }

    @Override
    protected IMatrix calculateImplicit(
            IMatrix xk, IMatrix xk1, IMatrix A, IMatrix B, List<Function<Double, Double>> rFunctions) {
        return null;
    }

}
