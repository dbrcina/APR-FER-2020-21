package hr.fer.zemris.apr.hw04.ea.decoder;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Arrays;

/**
 * Models a decoder that is used to decode solution's gene values into decimal values from range [lb, ub] for the each
 * dimension.
 * <ul>
 *     <li>lb - lower bound,</li>
 *     <li>up - upper bound.</li>
 * </ul>
 *
 * @param <S> solution type.
 *
 * @author dbrcina
 */
public abstract class Decoder<S extends Solution<?>> {

    protected final double[] lbs;
    protected final double[] ubs;

    protected Decoder(double[] lbs, double[] ubs) {
        if (lbs.length != ubs.length) {
            throw new RuntimeException("Dimensions of explicit constraints are not equal!");
        }
        this.lbs = lbs;
        this.ubs = ubs;
    }

    protected Decoder(int lb, int ub, int dimensions) {
        this(new double[dimensions], new double[dimensions]);
        Arrays.fill(lbs, lb);
        Arrays.fill(ubs, ub);
    }

    /**
     * Decodes provided solution.
     *
     * @param solution solution to be decoded.
     *
     * @return decoded solution.
     */
    public abstract double[] decode(S solution);

}
