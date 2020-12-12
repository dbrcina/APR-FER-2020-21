package hr.fer.zemris.apr.hw04;

import hr.fer.zemris.apr.hw04.ea.EvolutionaryAlgorithm;
import hr.fer.zemris.apr.hw04.ea.crossover.BLXACrossover;
import hr.fer.zemris.apr.hw04.ea.crossover.UniformBinaryCrossover;
import hr.fer.zemris.apr.hw04.ea.decoder.NaturalBinaryDecoder;
import hr.fer.zemris.apr.hw04.ea.decoder.PassThroughDecoder;
import hr.fer.zemris.apr.hw04.ea.fitness.F6;
import hr.fer.zemris.apr.hw04.ea.fitness.F7;
import hr.fer.zemris.apr.hw04.ea.fitness.FitnessFunction;
import hr.fer.zemris.apr.hw04.ea.ga.GeneticAlgorithm;
import hr.fer.zemris.apr.hw04.ea.initializer.PopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.initializer.RandomBinaryPopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.initializer.RandomDoublePopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.mutation.GaussMutation;
import hr.fer.zemris.apr.hw04.ea.mutation.RandomBinaryMutation;
import hr.fer.zemris.apr.hw04.ea.selection.KTournamentSelection;
import hr.fer.zemris.apr.hw04.ea.solution.Solution;
import hr.fer.zemris.apr.hw04.ea.util.Util;

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
        fDecimal(new F6(3), 200, 1e-6, (int) 1e5, 0.7, 3, 0.5, 3, "data/f6d3d.txt");
        fDecimal(new F6(6), 200, 1e-6, (int) 1e5, 0.7, 3, 0.5, 3, "data/f6d6d.txt");
        fDecimal(new F7(3), 200, 1e-6, (int) 1e5, 0.2, 3, 0.5, 1, "data/f7d3d.txt");
        fDecimal(new F7(6), 200, 1e-6, (int) 1e5, 0.2, 3, 0.5, 1, "data/f7d6d.txt");
        fBinary(new F6(3), 4, 200, 1e-6, (int) 1e5, 0.8, 3, 0.05, "data/f6d3b.txt");
        fBinary(new F6(6), 4, 200, 1e-6, (int) 1e5, 0.8, 3, 0.05, "data/f6d6b.txt");
        fBinary(new F7(3), 4, 200, 1e-6, (int) 1e5, 0.8, 3, 0.05, "data/f7d3b.txt");
        fBinary(new F7(6), 4, 200, 1e-6, (int) 1e5, 0.8, 3, 0.05, "data/f7d6b.txt");
    }

    private static void fDecimal(
            FitnessFunction f,
            int populationSize,
            double tol,
            int maxEvaluations,
            double mutationProb,
            int k,
            double alpha,
            double sigma,
            String file) {
        double[] lbs = new double[f.numberOfVariables()];
        double[] ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        EvolutionaryAlgorithm<? extends Solution<?>> alg = new GeneticAlgorithm<>(
                RANDOM,
                populationSize,
                tol,
                maxEvaluations,
                mutationProb,
                new RandomDoublePopulationInitializer(RANDOM, lbs, ubs),
                new KTournamentSelection<>(RANDOM, k),
                new BLXACrossover(RANDOM, alpha, lbs, ubs),
                new GaussMutation(RANDOM, sigma, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                f,
                false
        );
        saveToFile(alg, f, file);
    }

    private static void fBinary(
            FitnessFunction f,
            double precision,
            int populationSize,
            double tol,
            int maxEvaluations,
            double mutationProb,
            int k,
            double p,
            String file) {
        double[] lbs = new double[f.numberOfVariables()];
        double[] ubs = new double[f.numberOfVariables()];
        double[] precisions = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        Arrays.fill(precisions, precision);
        int[] bitsPerVariables = Util.calculateBitsPerVariables(lbs, ubs, precisions);
        PopulationInitializer<Solution<Boolean>> initializer =
                new RandomBinaryPopulationInitializer(RANDOM, bitsPerVariables);
        EvolutionaryAlgorithm<? extends Solution<?>> alg = new GeneticAlgorithm<>(
                RANDOM,
                populationSize,
                tol,
                maxEvaluations,
                mutationProb,
                initializer,
                new KTournamentSelection<>(RANDOM, k),
                new UniformBinaryCrossover(initializer),
                new RandomBinaryMutation(RANDOM, p),
                new NaturalBinaryDecoder(lbs, ubs, bitsPerVariables),
                f,
                false
        );
        saveToFile(alg, f, file);
    }

    private static void saveToFile(EvolutionaryAlgorithm<? extends Solution<?>> alg, FitnessFunction f, String file) {
        double[] fitnessVector = new double[30];
        for (int i = 0; i < fitnessVector.length; i++) {
            Solution<?> solution = alg.run();
            fitnessVector[i] = solution.getFitness();
            f.resetEvaluationsCounter();
        }
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
