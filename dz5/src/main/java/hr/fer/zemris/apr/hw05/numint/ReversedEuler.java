package hr.fer.zemris.apr.hw05.numint;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.Matrix;
import hr.fer.zemris.apr.hw01.math.MatrixUtils;

import java.util.List;
import java.util.Properties;
import java.util.function.Function;

/**
 * @author dbrcina
 */
public class ReversedEuler extends NumIntAlgorithm {

    public ReversedEuler() {
        super("reversed_euler.csv");
    }

    @Override
    protected void configureInternal(Properties configuration) {
    }

    @Override
    public IMatrix calculateExplicit(IMatrix xk, IMatrix A, IMatrix B, List<Function<Double, Double>> rFunctions) {
        double intPeriod = getIntegrationPeriod();
        double tk = getCurrentT();
        double tk1 = tk + intPeriod;
        IMatrix rk1 = new Matrix(xk.getRowsCount(), xk.getColumnsCount());
        for (int i = 0; i < rFunctions.size(); i++) {
            rk1.set(i, 0, rFunctions.get(i).apply(tk1));
        }
        IMatrix I = MatrixUtils.IDENTITY_MATRIX.apply(xk.getRowsCount());
        IMatrix P = MatrixUtils.lupInvert(I.sub(A.nScalarMul(intPeriod)));
        IMatrix Q = P.nScalarMul(intPeriod).mul(B);
        return P.mul(xk).add(Q.mul(rk1));
    }

    @Override
    protected IMatrix calculateImplicit(
            IMatrix xk, IMatrix xk1, IMatrix A, IMatrix B, List<Function<Double, Double>> rFunctions) {
        double intPeriod = getIntegrationPeriod();
        double tk = getCurrentT();
        double tk1 = tk + intPeriod;
        IMatrix rk1 = new Matrix(xk.getRowsCount(), xk.getColumnsCount());
        for (int i = 0; i < rFunctions.size(); i++) {
            rk1.set(i, 0, rFunctions.get(i).apply(tk1));
        }
        return xk.nAdd(A.mul(xk1).add(B.mul(rk1)).scalarMul(intPeriod));
    }

}
