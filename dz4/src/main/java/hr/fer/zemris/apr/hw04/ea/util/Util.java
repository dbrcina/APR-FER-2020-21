package hr.fer.zemris.apr.hw04.ea.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author dbrcina
 */
public class Util {

    /**
     * @param bits binary number.
     * @param xMin lower bound.
     * @param xMax upper bound.
     *
     * @return a double representation of binary number within [{@code xMin}, {@code xMax}].
     */
    public static double fromBinaryToDouble(Boolean[] bits, double xMin, double xMax) {
        long b = Long.parseUnsignedLong(fromBooleanArrayToString(bits), 2);
        return xMin + b / (Math.pow(2, bits.length) - 1) * (xMax - xMin);
    }

    /**
     * @param bits bits.
     *
     * @return a string representation of the provided {@code} bits array.
     */
    public static String fromBooleanArrayToString(Boolean[] bits) {
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
            bitsPerVariables[i] = (int) Math.ceil(n);
        }
        return bitsPerVariables;
    }

}
