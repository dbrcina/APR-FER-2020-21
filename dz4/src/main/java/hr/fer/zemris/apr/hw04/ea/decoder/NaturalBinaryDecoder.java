package hr.fer.zemris.apr.hw04.ea.decoder;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;
import hr.fer.zemris.apr.hw04.ea.util.Util;

/**
 * An implementation of <i>Natural binary decoder</i>.
 *
 * @author dbrcina
 */
public class NaturalBinaryDecoder extends BinaryDecoder {

    public NaturalBinaryDecoder(double[] lbs, double[] ubs, int[] bitsPerVariables) {
        super(lbs, ubs, bitsPerVariables);
    }

    public NaturalBinaryDecoder(int lb, int ub, int dimensions, int[] bitsPerVariables) {
        super(lb, ub, dimensions, bitsPerVariables);
    }

    @Override
    public double[] decode(Solution<Boolean> solution) {
        double[] decoded = new double[bitsPerVariables.length];
        int fromIndex = 0;
        for (int i = 0; i < decoded.length; i++) {
            int toIndex = fromIndex + bitsPerVariables[i];
            Boolean[] currentVariable = solution.getSubGenes(fromIndex, toIndex);
            decoded[i] = Util.fromBinaryToDouble(currentVariable, lbs[i], ubs[i]);
            fromIndex += currentVariable.length;
        }
        return decoded;
    }

}
