package hr.fer.zemris.apr.hw05.numopt;

import hr.fer.zemris.apr.hw01.math.IMatrix;
import hr.fer.zemris.apr.hw01.math.MatrixUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * @author dbrcina
 */
public abstract class NumOptAlgorithm {

    private final String file;

    private int currentIteration;
    private double currentT;
    private double intPeriod;
    private double tMax;
    private int nPrint;
    private Path dirPrint;
    private PrintWriter pwr;

    protected NumOptAlgorithm(String file) {
        this.file = file;
    }

    public void configure(Properties configuration) {
        Object intPeriodObject = configuration.get("T");
        if (intPeriodObject == null) {
            throw new RuntimeException("Integration period is missing!");
        }
        intPeriod = Double.parseDouble((String) intPeriodObject);
        Object tMaxObject = configuration.get("t.max");
        if (tMaxObject == null) {
            throw new RuntimeException("T max is missing!");
        }
        tMax = Double.parseDouble((String) tMaxObject);
        Object nPrintObject = configuration.get("n.print");
        if (nPrintObject == null) {
            throw new RuntimeException("N print is missing!");
        }
        nPrint = Integer.parseInt((String) nPrintObject);
        Object dirPrintObject = configuration.get("dir.print");
        if (dirPrintObject == null) {
            throw new RuntimeException("Dir print is missing!");
        }
        dirPrint = Paths.get((String) dirPrintObject);
        configureInternal(configuration);
    }

    protected abstract void configureInternal(Properties configuration);

    public IMatrix run(
            IMatrix state,
            IMatrix A, IMatrix B, List<Function<Double, Double>> rFunctions,
            List<Function<Double, Double>> analyticalFunctions) {
        double error = 0.0;
        while (currentT <= tMax) {
            if (analyticalFunctions != null) {
                for (int i = 0; i < state.getRowsCount(); i++) {
                    error += Math.abs(state.get(i, 0) - analyticalFunctions.get(i).apply(currentT));
                }
            }
            if (currentIteration % nPrint == 0) {
                print(state, file, false);
            }
            state = calculateExplicit(state, A, B, rFunctions);
            currentT += intPeriod;
            currentIteration += 1;
        }
        print(state, file, true);
        if (analyticalFunctions != null) {
            System.out.println("Error = " + error);
        }
        return state;
    }

    public abstract IMatrix calculateExplicit(
            IMatrix xk, IMatrix A, IMatrix B, List<Function<Double, Double>> rFunctions);

    protected abstract IMatrix calculateImplicit(
            IMatrix xk, IMatrix xk1, IMatrix A, IMatrix B, List<Function<Double, Double>> rFunctions);

    private void print(IMatrix x, String file, boolean flush) {
        if (pwr == null) {
            try {
                pwr = new PrintWriter(dirPrint + "/" + file);
                StringJoiner sj = new StringJoiner(",");
                // Create header for csv file.
                sj.add("t");
                for (int i = 0; i < x.getRowsCount(); i++) {
                    sj.add("x" + (i + 1));
                }
                pwr.println(sj.toString());
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        if (flush) {
            pwr.flush();
            return;
        }
        System.out.println("Iteration " + currentIteration);
        MatrixUtils.printMatrix(x, System.out);
        System.out.println();
        StringJoiner sj = new StringJoiner(",");
        sj.add(String.valueOf(currentT));
        for (int i = 0; i < x.getRowsCount(); i++) {
            sj.add(String.valueOf(x.get(i, 0)));
        }
        pwr.println(sj.toString());
    }

    protected double getCurrentT() {
        return currentT;
    }

    protected double getIntegrationPeriod() {
        return intPeriod;
    }

}
