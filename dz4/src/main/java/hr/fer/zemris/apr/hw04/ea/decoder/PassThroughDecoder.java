package hr.fer.zemris.apr.hw04.ea.decoder;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Arrays;

/**
 * Decoder used to decode solutions that use double values as gene type. Essentially, solutions just "pass through"
 * because no decoding is applied except explicit constraints from {@link Decoder}.
 *
 * @author dbrcina
 */
public class PassThroughDecoder extends Decoder<Solution<Double>> {

    public PassThroughDecoder(double[] lbs, double[] ubs) {
        super(lbs, ubs);
    }

    public PassThroughDecoder(int lb, int ub, int dimensions) {
        super(lb, ub, dimensions);
    }

    @Override
    public double[] decode(Solution<Double> solution) {
        Double[] genes = solution.getSubGenes(0, solution.getNumberOfGenes());
        boolean changed = false;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] < lbs[i]) {
                genes[i] = lbs[i];
                changed = true;
            } else if (genes[i] > ubs[i]) {
                genes[i] = ubs[i];
                changed = true;
            }
        }
        if (changed) {
            solution.setSubGenes(genes, 0, solution.getNumberOfGenes());
        }
        return Arrays.stream(genes)
                .mapToDouble(Double::doubleValue)
                .toArray();
    }

}
