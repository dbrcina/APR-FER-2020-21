package hr.fer.zemris.apr.hw05.numopt;

import hr.fer.zemris.apr.hw01.math.IMatrix;

import java.util.Properties;

/**
 * @author dbrcina
 */
public class Euler extends NumOptAlgorithm {

    public Euler() {
        super("euler.csv");
    }

    @Override
    protected void configureInternal(Properties configuration) {
    }

    @Override
    protected IMatrix runInternal(IMatrix state, IMatrix A, IMatrix B, IMatrix r, double t) {
        return state.nAdd(A.mul(state).add(B.mul(r)).scalarMul(t));
    }


}
