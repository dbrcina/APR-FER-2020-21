package hr.fer.zemris.apr.hw04.ea.decoder;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

/**
 * Decoder used to decode solutions that use bit (boolean) values as gene type. It keeps track of the number of bits per
 * variables.
 *
 * @author dbrcina
 */
public abstract class BinaryDecoder extends Decoder<Solution<Boolean>> {

    protected final int[] bitsPerVariables;

    protected BinaryDecoder(double[] lbs, double[] ubs, int[] bitsPerVariables) {
        super(lbs, ubs);
        this.bitsPerVariables = bitsPerVariables;
    }

    protected BinaryDecoder(int lb, int ub, int dimensions, int[] bitsPerVariables) {
        super(lb, ub, dimensions);
        this.bitsPerVariables = bitsPerVariables;
    }

}
