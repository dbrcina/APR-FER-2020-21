package hr.fer.zemris.apr.hw04;

import hr.fer.zemris.apr.hw04.ea.crossover.BLXACrossover;
import hr.fer.zemris.apr.hw04.ea.decoder.PassThroughDecoder;
import hr.fer.zemris.apr.hw04.ea.fitness.F6;
import hr.fer.zemris.apr.hw04.ea.fitness.FitnessFunction;
import hr.fer.zemris.apr.hw04.ea.ga.GeneticAlgorithm;
import hr.fer.zemris.apr.hw04.ea.initializer.RandomDoublePopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.mutation.GaussMutation;
import hr.fer.zemris.apr.hw04.ea.selection.KTournamentSelection;
import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author dbrcina
 */
public class Zadatak3 {

    private static final Random RANDOM = new Random();
    private static final double X_MIN = -50;
    private static final double X_MAX = 150;

    public static void main(String[] args) {
        System.out.println("\t#### Starting task 3! ####");
        f6Decimal();
        //f7Decimal();
        //f6Binary();
        //f7Binary();
    }

    private static void f6Decimal() {
        int populationSize = 100;
        double tol = 1e-6;
        int maxEvaluations = (int) 1e5;
        double mutationProb = 0.8;
        int k = 5;
        double alpha = 0.5;
        double sigma = 2;
        FitnessFunction f = new F6(3);
        double[] lbs = new double[f.numberOfVariables()];
        double[] ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        double[] fitnessVector = new double[30];
        for (int i = 0; i < fitnessVector.length; i++) {
            Solution<?> solution = new GeneticAlgorithm<>(
                    populationSize,
                    tol,
                    maxEvaluations,
                    mutationProb,
                    new RandomDoublePopulationInitializer(lbs, ubs),
                    new KTournamentSelection<>(k),
                    new BLXACrossover(alpha, lbs, ubs),
                    new GaussMutation(sigma, lbs, ubs),
                    new PassThroughDecoder(lbs, ubs),
                    f,
                    false
            ).run();
            fitnessVector[i] = solution.getFitness();
            f.resetEvaluationsCounter();
        }
        saveToFile(fitnessVector, "f6d3d.txt");
        f = new F6(6);
        lbs = new double[f.numberOfVariables()];
        ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        fitnessVector = new double[30];
        for (int i = 0; i < fitnessVector.length; i++) {
            Solution<?> solution = new GeneticAlgorithm<>(
                    populationSize,
                    tol,
                    maxEvaluations,
                    mutationProb,
                    new RandomDoublePopulationInitializer(lbs, ubs),
                    new KTournamentSelection<>(k),
                    new BLXACrossover(alpha, lbs, ubs),
                    new GaussMutation(sigma, lbs, ubs),
                    new PassThroughDecoder(lbs, ubs),
                    f,
                    false
            ).run();
            fitnessVector[i] = solution.getFitness();
            f.resetEvaluationsCounter();
        }
        saveToFile(fitnessVector, "f6d6d.txt");
    }

//    private static void f7Decimal() {
//        int populationSize = 200;
//        double tol = 1e-6;
//        int maxEvaluations = (int) 1e5;
//        double mutationProb = 0.2;
//        int k = 3;
//        double alpha = 0.5;
//        double sigma = 1;
//        FitnessFunction f = new F7(3);
//        double[] lbs = new double[f.numberOfVariables()];
//        double[] ubs = new double[f.numberOfVariables()];
//        Arrays.fill(lbs, X_MIN);
//        Arrays.fill(ubs, X_MAX);
//        EvolutionaryAlgorithm<Solution<Double>> alg = new GeneticAlgorithm<>(
//                populationSize,
//                tol,
//                maxEvaluations,
//                mutationProb,
//                new RandomDoublePopulationInitializer(lbs, ubs),
//                new KTournamentSelection<>(k),
//                new BLXACrossover(alpha, lbs, ubs),
//                new GaussMutation(sigma, lbs, ubs),
//                new PassThroughDecoder(lbs, ubs),
//                f,
//                false
//        );
//        saveToFile(alg, "f7d3d.txt");
//        f = new F7(6);
//        lbs = new double[f.numberOfVariables()];
//        ubs = new double[f.numberOfVariables()];
//        Arrays.fill(lbs, X_MIN);
//        Arrays.fill(ubs, X_MAX);
//        alg = new GeneticAlgorithm<>(
//                populationSize,
//                tol,
//                maxEvaluations,
//                mutationProb,
//                new RandomDoublePopulationInitializer(lbs, ubs),
//                new KTournamentSelection<>(k),
//                new BLXACrossover(alpha, lbs, ubs),
//                new GaussMutation(sigma, lbs, ubs),
//                new PassThroughDecoder(lbs, ubs),
//                f,
//                false
//        );
//        saveToFile(alg, "f7d6d.txt");
//    }
//
//    private static void f6Binary() {
//        int populationSize = 200;
//        double tol = 1e-6;
//        int maxEvaluations = (int) 1e5;
//        double mutationProb = 0.7;
//        int k = 3;
//        double p = 0.2;
//        FitnessFunction f = new F6(3);
//        double[] lbs = new double[f.numberOfVariables()];
//        double[] ubs = new double[f.numberOfVariables()];
//        double[] precisions = new double[f.numberOfVariables()];
//        Arrays.fill(lbs, X_MIN);
//        Arrays.fill(ubs, X_MAX);
//        Arrays.fill(precisions, 4);
//        int[] bitsPerVariables = Util.calculateBitsPerVariables(lbs, ubs, precisions);
//        PopulationInitializer<Solution<Boolean>> initializer =
//                new RandomBinaryPopulationInitializer(bitsPerVariables);
//        EvolutionaryAlgorithm<Solution<Boolean>> alg = new GeneticAlgorithm<>(
//                populationSize,
//                tol,
//                maxEvaluations,
//                mutationProb,
//                initializer,
//                new KTournamentSelection<>(k),
//                new UniformBinaryCrossover(initializer),
//                new RandomBinaryMutation(p),
//                new GrayDecoder(lbs, ubs, bitsPerVariables),
//                f,
//                false
//        );
//        saveToFile(alg, "f6d3b.txt");
//        f = new F6(6);
//        lbs = new double[f.numberOfVariables()];
//        ubs = new double[f.numberOfVariables()];
//        precisions = new double[f.numberOfVariables()];
//        Arrays.fill(lbs, X_MIN);
//        Arrays.fill(ubs, X_MAX);
//        Arrays.fill(precisions, 4);
//        bitsPerVariables = Util.calculateBitsPerVariables(lbs, ubs, precisions);
//        initializer = new RandomBinaryPopulationInitializer(bitsPerVariables);
//        alg = new GeneticAlgorithm<>(
//                populationSize,
//                tol,
//                maxEvaluations,
//                mutationProb,
//                initializer,
//                new KTournamentSelection<>(k),
//                new UniformBinaryCrossover(initializer),
//                new RandomBinaryMutation(p),
//                new GrayDecoder(lbs, ubs, bitsPerVariables),
//                f,
//                false
//        );
//        saveToFile(alg, "f6d6b.txt");
//    }
//
//    private static void f7Binary() {
//        int populationSize = 200;
//        double tol = 1e-6;
//        int maxEvaluations = (int) 1e5;
//        double mutationProb = 0.7;
//        int k = 3;
//        double p = 0.2;
//        FitnessFunction f = new F7(3);
//        double[] lbs = new double[f.numberOfVariables()];
//        double[] ubs = new double[f.numberOfVariables()];
//        double[] precisions = new double[f.numberOfVariables()];
//        Arrays.fill(lbs, X_MIN);
//        Arrays.fill(ubs, X_MAX);
//        Arrays.fill(precisions, 4);
//        int[] bitsPerVariables = Util.calculateBitsPerVariables(lbs, ubs, precisions);
//        PopulationInitializer<Solution<Boolean>> initializer =
//                new RandomBinaryPopulationInitializer(bitsPerVariables);
//        EvolutionaryAlgorithm<Solution<Boolean>> alg = new GeneticAlgorithm<>(
//                populationSize,
//                tol,
//                maxEvaluations,
//                mutationProb,
//                initializer,
//                new KTournamentSelection<>(k),
//                new UniformBinaryCrossover(initializer),
//                new RandomBinaryMutation(p),
//                new GrayDecoder(lbs, ubs, bitsPerVariables),
//                f,
//                false
//        );
//        saveToFile(alg, "f7d3b.txt");
//        f = new F7(6);
//        lbs = new double[f.numberOfVariables()];
//        ubs = new double[f.numberOfVariables()];
//        precisions = new double[f.numberOfVariables()];
//        Arrays.fill(lbs, X_MIN);
//        Arrays.fill(ubs, X_MAX);
//        Arrays.fill(precisions, 4);
//        bitsPerVariables = Util.calculateBitsPerVariables(lbs, ubs, precisions);
//        initializer = new RandomBinaryPopulationInitializer(bitsPerVariables);
//        alg = new GeneticAlgorithm<>(
//                populationSize,
//                tol,
//                maxEvaluations,
//                mutationProb,
//                initializer,
//                new KTournamentSelection<>(k),
//                new UniformBinaryCrossover(initializer),
//                new RandomBinaryMutation(p),
//                new GrayDecoder(lbs, ubs, bitsPerVariables),
//                f,
//                false
//        );
//        saveToFile(alg, "f7d6b.txt");
//    }

    private static void saveToFile(double[] fitnessVector, String file) {
        try (BufferedWriter wr = Files.newBufferedWriter(Paths.get(file))) {
            System.out.println("Saving to " + file + "...");
            wr.write(Arrays.stream(fitnessVector)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" "))
            );
            wr.flush();
            System.out.println("Saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Saving failed!");
            System.exit(-1);
        }
    }

}
