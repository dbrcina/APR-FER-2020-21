package hr.fer.zemris.apr.hw04;

import hr.fer.zemris.apr.hw04.ea.EvolutionaryAlgorithm;
import hr.fer.zemris.apr.hw04.ea.crossover.BLXACrossover;
import hr.fer.zemris.apr.hw04.ea.crossover.UniformBinaryCrossover;
import hr.fer.zemris.apr.hw04.ea.decoder.GrayDecoder;
import hr.fer.zemris.apr.hw04.ea.decoder.PassThroughDecoder;
import hr.fer.zemris.apr.hw04.ea.fitness.F6;
import hr.fer.zemris.apr.hw04.ea.fitness.F7;
import hr.fer.zemris.apr.hw04.ea.fitness.FitnessFunction;
import hr.fer.zemris.apr.hw04.ea.ga.GeneticAlgorithm;
import hr.fer.zemris.apr.hw04.ea.initializer.PopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.initializer.RandomBinaryPopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.initializer.RandomDoublePopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.mutation.GaussMutation;
import hr.fer.zemris.apr.hw04.ea.mutation.SimpleBinaryMutation;
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
    private static final int POPULATION_SIZE = 200;
    private static final double TOL = 1e-6;
    private static final int MAX_EVALUATIONS = (int) 1e5;

    public static void main(String[] args) {
        System.out.println("\t#### Starting task 3! ####");
        fDecimal(new F6(3), 1, 0.6, "data/f6d3d.txt");
        fDecimal(new F6(6), 1, 0.6, "data/f6d6d.txt");
        fDecimal(new F7(3), 1, 0.5, "data/f7d3d.txt");
        fDecimal(new F7(6), 1, 0.5, "data/f7d6d.txt");
        fBinary(new F6(3), 0.04, "data/f6d3b.txt");
        fBinary(new F6(6), 0.04, "data/f6d6b.txt");
        fBinary(new F7(3), 0.04, "data/f7d3b.txt");
        fBinary(new F7(6), 0.04, "data/f7d6b.txt");
    }

    private static void fDecimal(FitnessFunction f, double sigma, double p, String file) {
        double[] lbs = new double[f.numberOfVariables()];
        double[] ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        EvolutionaryAlgorithm<? extends Solution<?>> alg = new GeneticAlgorithm<>(
                RANDOM,
                POPULATION_SIZE,
                TOL,
                MAX_EVALUATIONS,
                new RandomDoublePopulationInitializer(RANDOM, lbs, ubs),
                new KTournamentSelection<>(RANDOM, 3),
                new BLXACrossover(RANDOM, 0.5, lbs, ubs),
                new GaussMutation(RANDOM, sigma, p, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                f,
                false
        );
        saveToFile(alg, f, file);
    }

    private static void fBinary(FitnessFunction f, double p, String file) {
        double[] lbs = new double[f.numberOfVariables()];
        double[] ubs = new double[f.numberOfVariables()];
        double[] precisions = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        Arrays.fill(precisions, 4);
        int[] bitsPerVariables = Util.calculateBitsPerVariables(lbs, ubs, precisions);
        PopulationInitializer<Solution<Boolean>> initializer =
                new RandomBinaryPopulationInitializer(RANDOM, bitsPerVariables);
        EvolutionaryAlgorithm<? extends Solution<?>> alg = new GeneticAlgorithm<>(
                RANDOM,
                POPULATION_SIZE,
                TOL,
                MAX_EVALUATIONS,
                initializer,
                new KTournamentSelection<>(RANDOM, 3),
                new UniformBinaryCrossover(RANDOM),
                new SimpleBinaryMutation(RANDOM, p),
                new GrayDecoder(lbs, ubs, bitsPerVariables),
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
