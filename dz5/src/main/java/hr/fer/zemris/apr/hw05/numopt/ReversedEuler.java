package hr.fer.zemris.apr.hw05.numopt;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.MatrixUtils;

import java.util.Properties;

/**
 * @author dbrcina
 */
public class ReversedEuler extends NumOptAlgorithm {

    public ReversedEuler() {
        super("reversed_euler.csv");
    }

    @Override
    protected void configureInternal(Properties configuration) {
    }

    @Override
    protected IMatrix runInternal(IMatrix state, IMatrix A, IMatrix B, IMatrix r, double t) {
        IMatrix I = MatrixUtils.IDENTITY_MATRIX.apply(state.getRowsCount());
        IMatrix P = MatrixUtils.lupInvert(I.sub(A.nScalarMul(t)));
        IMatrix Q = P.nScalarMul(t).mul(B);
        return P.mul(state).add(Q.mul(r));
    }
}
