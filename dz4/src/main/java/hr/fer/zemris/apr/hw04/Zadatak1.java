package hr.fer.zemris.apr.hw04;

import hr.fer.zemris.apr.hw04.ea.crossover.BLXACrossover;
import hr.fer.zemris.apr.hw04.ea.crossover.UniformBinaryCrossover;
import hr.fer.zemris.apr.hw04.ea.decoder.GrayDecoder;
import hr.fer.zemris.apr.hw04.ea.decoder.PassThroughDecoder;
import hr.fer.zemris.apr.hw04.ea.fitness.*;
import hr.fer.zemris.apr.hw04.ea.ga.GeneticAlgorithm;
import hr.fer.zemris.apr.hw04.ea.initializer.PopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.initializer.RandomBinaryPopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.initializer.RandomDoublePopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.mutation.GaussMutation;
import hr.fer.zemris.apr.hw04.ea.mutation.RandomBinaryMutation;
import hr.fer.zemris.apr.hw04.ea.selection.KTournamentSelection;
import hr.fer.zemris.apr.hw04.ea.solution.Solution;
import hr.fer.zemris.apr.hw04.ea.util.Util;

import java.util.Arrays;
import java.util.Random;

/**
 * @author dbrcina
 */
public class Zadatak1 {

    private static final Random RANDOM = new Random();
    private static final FitnessFunction F1 = new F1();
    private static final FitnessFunction F3 = new F3(5);
    private static final FitnessFunction F6 = new F6(2);
    private static final FitnessFunction F7 = new F7(2);
    private static final double X_MIN = -50;
    private static final double X_MAX = 150;

    public static void main(String[] args) {
        System.out.println("\t#### Starting task 1! ####");
        switch (args[0]) {
            case "f1-decimal" -> f1Decimal();
            case "f3-decimal" -> f3Decimal();
            case "f6-decimal" -> f6Decimal();
            case "f7-decimal" -> f7Decimal();
            case "f1-binary" -> f1Binary();
            case "f3-binary" -> f3Binary();
            case "f6-binary" -> f6Binary();
            case "f7-binary" -> f7Binary();
        }
    }

    private static void f1Decimal() {
        System.out.println("Decimal solution:");
        System.out.println("Function f1:");
        double[] lbs = new double[F1.numberOfVariables()];
        double[] ubs = new double[F1.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        new GeneticAlgorithm<>(
                RANDOM,
                100,
                1e-6,
                50_000,
                0.2,
                new RandomDoublePopulationInitializer(RANDOM, lbs, ubs),
                new KTournamentSelection<>(RANDOM, 3),
                new BLXACrossover(RANDOM, 0.5, lbs, ubs),
                new GaussMutation(RANDOM, 0.1, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                F1,
                true).run();
        F1.resetEvaluationsCounter();
    }

    private static void f3Decimal() {
        System.out.println("Decimal solution:");
        System.out.println("Function f3:");
        double[] lbs = new double[F3.numberOfVariables()];
        double[] ubs = new double[F3.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        new GeneticAlgorithm<>(
                RANDOM,
                100,
                1e-6,
                50_000,
                0.2,
                new RandomDoublePopulationInitializer(RANDOM, lbs, ubs),
                new KTournamentSelection<>(RANDOM, 3),
                new BLXACrossover(RANDOM, 0.5, lbs, ubs),
                new GaussMutation(RANDOM, 0.01, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                F3,
                true).run();
        F3.resetEvaluationsCounter();
    }

    private static void f6Decimal() {
        System.out.println("Decimal solution:");
        System.out.println("Function f6:");
        double[] lbs = new double[F6.numberOfVariables()];
        double[] ubs = new double[F6.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        new GeneticAlgorithm<>(
                RANDOM,
                100,
                1e-6,
                50_000,
                0.5,
                new RandomDoublePopulationInitializer(RANDOM, lbs, ubs),
                new KTournamentSelection<>(RANDOM, 3),
                new BLXACrossover(RANDOM, 0.5, lbs, ubs),
                new GaussMutation(RANDOM, 1.5, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                F6,
                true).run();
        F6.resetEvaluationsCounter();
    }

    private static void f7Decimal() {
        System.out.println("Decimal solution:");
        System.out.println("Function f7:");
        double[] lbs = new double[F7.numberOfVariables()];
        double[] ubs = new double[F7.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        new GeneticAlgorithm<>(
                RANDOM,
                100,
                1e-6,
                50_000,
                0.2,
                new RandomDoublePopulationInitializer(RANDOM, lbs, ubs),
                new KTournamentSelection<>(RANDOM, 3),
                new BLXACrossover(RANDOM, 0.5, lbs, ubs),
                new GaussMutation(RANDOM, 0.1, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                F7,
                true).run();
        F7.resetEvaluationsCounter();
    }

    private static void f1Binary() {
        System.out.println("Binary solution:");
        System.out.println("Function f1:");
        double[] lbs = new double[F1.numberOfVariables()];
        double[] ubs = new double[F1.numberOfVariables()];
        double[] precisions = new double[F1.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        Arrays.fill(precisions, 5);
        int[] bitsPerVariables = Util.calculateBitsPerVariables(lbs, ubs, precisions);
        PopulationInitializer<Solution<Boolean>> initializer =
                new RandomBinaryPopulationInitializer(RANDOM, bitsPerVariables);
        new GeneticAlgorithm<>(
                RANDOM,
                100,
                1e-6,
                100_000,
                0.7,
                initializer,
                new KTournamentSelection<>(RANDOM, 3),
                new UniformBinaryCrossover(initializer),
                new RandomBinaryMutation(RANDOM, 0.15),
                new GrayDecoder(lbs, ubs, bitsPerVariables),
                F1,
                true).run();
        F1.resetEvaluationsCounter();
    }

    private static void f3Binary() {
        System.out.println("Binary solution:");
        System.out.println("Function f3:");
        double[] lbs = new double[F3.numberOfVariables()];
        double[] ubs = new double[F3.numberOfVariables()];
        double[] precisions = new double[F3.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        Arrays.fill(precisions, 5);
        int[] bitsPerVariables = Util.calculateBitsPerVariables(lbs, ubs, precisions);
        PopulationInitializer<Solution<Boolean>> initializer =
                new RandomBinaryPopulationInitializer(RANDOM, bitsPerVariables);
        new GeneticAlgorithm<>(
                RANDOM,
                100,
                1e-6,
                100_000,
                0.5,
                initializer,
                new KTournamentSelection<>(RANDOM, 3),
                new UniformBinaryCrossover(initializer),
                new RandomBinaryMutation(RANDOM, 0.01),
                new GrayDecoder(lbs, ubs, bitsPerVariables),
                F3,
                true).run();
        F3.resetEvaluationsCounter();
    }

    private static void f6Binary() {
        System.out.println("Binary solution:");
        System.out.println("Function f6:");
        double[] lbs = new double[F6.numberOfVariables()];
        double[] ubs = new double[F6.numberOfVariables()];
        double[] precisions = new double[F6.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        Arrays.fill(precisions, 5);
        int[] bitsPerVariables = Util.calculateBitsPerVariables(lbs, ubs, precisions);
        PopulationInitializer<Solution<Boolean>> initializer =
                new RandomBinaryPopulationInitializer(RANDOM, bitsPerVariables);
        new GeneticAlgorithm<>(
                RANDOM,
                100,
                1e-6,
                100_000,
                0.5,
                initializer,
                new KTournamentSelection<>(RANDOM, 3),
                new UniformBinaryCrossover(initializer),
                new RandomBinaryMutation(RANDOM, 0.15),
                new GrayDecoder(lbs, ubs, bitsPerVariables),
                F6,
                true).run();
        F6.resetEvaluationsCounter();
    }

    private static void f7Binary() {
        System.out.println("Binary solution:");
        System.out.println("Function f7:");
        double[] lbs = new double[F7.numberOfVariables()];
        double[] ubs = new double[F7.numberOfVariables()];
        double[] precisions = new double[F7.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        Arrays.fill(precisions, 12);
        int[] bitsPerVariables = Util.calculateBitsPerVariables(lbs, ubs, precisions);
        PopulationInitializer<Solution<Boolean>> initializer =
                new RandomBinaryPopulationInitializer(RANDOM, bitsPerVariables);
        new GeneticAlgorithm<>(
                RANDOM,
                100,
                1e-6,
                100_000,
                0.7,
                initializer,
                new KTournamentSelection<>(RANDOM, 3),
                new UniformBinaryCrossover(initializer),
                new RandomBinaryMutation(RANDOM, 0.03),
                new GrayDecoder(lbs, ubs, bitsPerVariables),
                F7,
                true).run();
        F7.resetEvaluationsCounter();
    }

}
