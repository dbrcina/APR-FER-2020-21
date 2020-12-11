package hr.fer.zemris.apr.hw04;

import hr.fer.zemris.apr.hw04.ea.crossover.BLXACrossover;
import hr.fer.zemris.apr.hw04.ea.decoder.PassThroughDecoder;
import hr.fer.zemris.apr.hw04.ea.fitness.F6;
import hr.fer.zemris.apr.hw04.ea.fitness.F7;
import hr.fer.zemris.apr.hw04.ea.fitness.FitnessFunction;
import hr.fer.zemris.apr.hw04.ea.ga.GeneticAlgorithm;
import hr.fer.zemris.apr.hw04.ea.initializer.RandomDoublePopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.mutation.GaussMutation;
import hr.fer.zemris.apr.hw04.ea.selection.KTournamentSelection;
import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Arrays;

/**
 * @author dbrcina
 */
public class Zadatak2 {

    private static final double X_MIN = -50;
    private static final double X_MAX = 150;

    public static void main(String[] args) {
        System.out.println("\t#### Starting task 2! ####");
        switch (args[0]) {
            case "f6-decimal" -> f6Decimal();
            case "f7-decimal" -> f7Decimal();
        }
    }

    private static void f6Decimal() {
        System.out.println("Function f6:");
        System.out.println("Dimensions: 1");
        FitnessFunction f = new F6(1);
        double[] lbs = new double[f.numberOfVariables()];
        double[] ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        Solution<Double> solution = new GeneticAlgorithm<>(
                100,
                1e-6,
                50_000,
                0.1,
                new RandomDoublePopulationInitializer(lbs, ubs),
                new KTournamentSelection<>(3),
                new BLXACrossover(0.5, lbs, ubs),
                new GaussMutation(0.01, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                f,
                false).run();
        printResults(solution, f);
        System.out.println();
        System.out.println("Dimensions: 3");
        f = new F6(3);
        lbs = new double[f.numberOfVariables()];
        ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        solution = new GeneticAlgorithm<>(
                200,
                1e-6,
                100_000,
                0.7,
                new RandomDoublePopulationInitializer(lbs, ubs),
                new KTournamentSelection<>(3),
                new BLXACrossover(0.5, lbs, ubs),
                new GaussMutation(3, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                f,
                false).run();
        printResults(solution, f);
        System.out.println();
        System.out.println("Dimensions: 6");
        f = new F6(6);
        lbs = new double[f.numberOfVariables()];
        ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        solution = new GeneticAlgorithm<>(
                200,
                1e-6,
                100_000,
                0.7,
                new RandomDoublePopulationInitializer(lbs, ubs),
                new KTournamentSelection<>(3),
                new BLXACrossover(0.5, lbs, ubs),
                new GaussMutation(5, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                f,
                false).run();
        printResults(solution, f);
        System.out.println();
        System.out.println("Dimensions: 10");
        f = new F6(10);
        lbs = new double[f.numberOfVariables()];
        ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        solution = new GeneticAlgorithm<>(
                200,
                1e-6,
                200_000,
                0.8,
                new RandomDoublePopulationInitializer(lbs, ubs),
                new KTournamentSelection<>(3),
                new BLXACrossover(0.5, lbs, ubs),
                new GaussMutation(5, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                f,
                false).run();
        printResults(solution, f);
    }

    private static void f7Decimal() {
        System.out.println("Function f7:");
        System.out.println("Dimensions: 1");
        FitnessFunction f = new F7(1);
        double[] lbs = new double[f.numberOfVariables()];
        double[] ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        Solution<Double> solution = new GeneticAlgorithm<>(
                100,
                1e-6,
                50_000,
                0.1,
                new RandomDoublePopulationInitializer(lbs, ubs),
                new KTournamentSelection<>(3),
                new BLXACrossover(0.5, lbs, ubs),
                new GaussMutation(0.01, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                f,
                false).run();
        printResults(solution, f);
        System.out.println();
        System.out.println("Dimensions: 3");
        f = new F7(3);
        lbs = new double[f.numberOfVariables()];
        ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        solution = new GeneticAlgorithm<>(
                100,
                1e-6,
                50_000,
                0.1,
                new RandomDoublePopulationInitializer(lbs, ubs),
                new KTournamentSelection<>(3),
                new BLXACrossover(0.5, lbs, ubs),
                new GaussMutation(0.01, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                f,
                false).run();
        printResults(solution, f);
        System.out.println();
        System.out.println("Dimensions: 6");
        f = new F7(6);
        lbs = new double[f.numberOfVariables()];
        ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        solution = new GeneticAlgorithm<>(
                200,
                1e-6,
                200_000,
                0.2,
                new RandomDoublePopulationInitializer(lbs, ubs),
                new KTournamentSelection<>(3),
                new BLXACrossover(0.5, lbs, ubs),
                new GaussMutation(1, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                f,
                false).run();
        printResults(solution, f);
        System.out.println();
        System.out.println("Dimensions: 10");
        f = new F7(10);
        lbs = new double[f.numberOfVariables()];
        ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        solution = new GeneticAlgorithm<>(
                200,
                1e-6,
                200_000,
                0.5,
                new RandomDoublePopulationInitializer(lbs, ubs),
                new KTournamentSelection<>(3),
                new BLXACrossover(0.5, lbs, ubs),
                new GaussMutation(1, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                f,
                false).run();
        printResults(solution, f);
    }

    private static void printResults(Solution<Double> solution, FitnessFunction f) {
        System.out.printf("\tSolution = %s%n\tFitness = %e%n\tEvaluations = %d%n",
                Arrays.toString(solution.getSubGenes(0, solution.getNumberOfGenes())),
                solution.getFitness(),
                f.numberOfEvaluations()
        );
    }

}
