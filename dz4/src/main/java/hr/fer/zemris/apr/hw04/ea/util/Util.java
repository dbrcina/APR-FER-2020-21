package hr.fer.zemris.apr.hw04.ea.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author dbrcina
 */
public class Util {

    /**
     * @param bits bits.
     *
     * @return a string representation of the provided {@code} bits array.
     */
    public static String fromBooleanArray(Boolean[] bits) {
        return Arrays.stream(bits)
                .map(bit -> bit ? "1" : "0")
                .collect(Collectors.joining(""));
    }

    /**
     * Calculates bits per variables based on provided constraints.
     *
     * @param lbs        lower bounds.
     * @param ubs        upper bounds.
     * @param precisions precisions.
     *
     * @return bits per variables.
     */
    public static int[] calculateBitsPerVariables(double[] lbs, double[] ubs, double[] precisions) {
        int[] bitsPerVariables = new int[precisions.length];
        for (int i = 0; i < bitsPerVariables.length; i++) {
            double n = Math.log10(Math.floor(1 + (ubs[i] - lbs[i]) * Math.pow(10, precisions[i]))) / Math.log10(2);
            bitsPerVariables[i] = (int) (n + 1);
        }
        return bitsPerVariables;
    }

}
