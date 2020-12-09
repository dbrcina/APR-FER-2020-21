package hr.fer.zemris.apr.hw04.ea.decoder;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;
import hr.fer.zemris.apr.hw04.ea.util.Util;

/**
 * An implementation of <i>Gray binary decoder</i>.
 *
 * @author dbrcina
 */
public class GrayDecoder extends BinaryDecoder {

    public GrayDecoder(double[] lbs, double[] ubs, int[] bitsPerVariables) {
        super(lbs, ubs, bitsPerVariables);
    }

    public GrayDecoder(int lb, int ub, int dimensions, int[] bitsPerVariables) {
        super(lb, ub, dimensions, bitsPerVariables);
    }

    @Override
    public double[] decode(Solution<Boolean> solution) {
        double[] decoded = new double[bitsPerVariables.length];
        int fromIndex = 0;
        for (int i = 0; i < decoded.length; i++) {
            int toIndex = fromIndex + bitsPerVariables[i];
            Boolean[] currentVariableBin = solution.getSubGenes(fromIndex, toIndex);
            Boolean[] currentVariableGray = fromBinaryToGray(currentVariableBin);
            decoded[i] = Util.fromBinaryToDouble(currentVariableGray, lbs[i], ubs[i]);
            fromIndex += currentVariableGray.length;
        }
        return decoded;
    }

    private Boolean[] fromBinaryToGray(Boolean[] currentVariableBin) {
        Boolean[] result = new Boolean[currentVariableBin.length];
        result[0] = currentVariableBin[0];
        for (int i = 1; i < result.length; i++) {
            result[i] = result[i - 1] ^ currentVariableBin[i];
        }
        return result;
    }

}
