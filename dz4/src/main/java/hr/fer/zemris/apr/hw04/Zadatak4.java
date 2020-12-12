package hr.fer.zemris.apr.hw04;

import hr.fer.zemris.apr.hw04.ea.EvolutionaryAlgorithm;
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
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author dbrcina
 */
public class Zadatak4 {

    private static final Random RANDOM = new Random();
    private static final double X_MIN = -50;
    private static final double X_MAX = 150;
    private static final double TOL = 1e-6;
    private static final int MAX_EVALUATIONS = (int) 1e5;

    public static void main(String[] args) {
        System.out.println("\t#### Starting task 4! ####");
        switch (args[0]) {
            case "population" -> optimizePopulation();
            case "mutation" -> optimizeMutation();
            case "optimal" -> optimal();
        }
    }

    private static void optimal() {
        FitnessFunction f = new F6(3);
        double[] lbs = new double[f.numberOfVariables()];
        double[] ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        EvolutionaryAlgorithm<Solution<Double>> alg = new GeneticAlgorithm<>(
                RANDOM,
                100,
                TOL,
                MAX_EVALUATIONS,
                new RandomDoublePopulationInitializer(RANDOM, lbs, ubs),
                new KTournamentSelection<>(RANDOM, 3),
                new BLXACrossover(RANDOM, 0.5, lbs, ubs),
                new GaussMutation(RANDOM, 1.5, 0.9, lbs, ubs),
                new PassThroughDecoder(lbs, ubs),
                f,
                false
        );
        double[] fitnessVector = new double[30];
        for (int i = 0; i < fitnessVector.length; i++) {
            Solution<?> solution = alg.run();
            fitnessVector[i] = solution.getFitness();
            f.resetEvaluationsCounter();
        }
        String file = "data/optimal_population_mutation.txt";
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

    private static void optimizePopulation() { // optimal 100
        List<Integer> popSizes = List.of(30, 50, 100, 200);
        FitnessFunction f = new F6(3);
        double[] lbs = new double[f.numberOfVariables()];
        double[] ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        double[][] data = new double[30][popSizes.size()];
        for (int i = 0; i < popSizes.size(); i++) {
            EvolutionaryAlgorithm<? extends Solution<?>> alg = new GeneticAlgorithm<>(
                    RANDOM,
                    popSizes.get(i),
                    TOL,
                    MAX_EVALUATIONS,
                    new RandomDoublePopulationInitializer(RANDOM, lbs, ubs),
                    new KTournamentSelection<>(RANDOM, 3),
                    new BLXACrossover(RANDOM, 0.5, lbs, ubs),
                    new GaussMutation(RANDOM, 1.5, 0.9, lbs, ubs),
                    new PassThroughDecoder(lbs, ubs),
                    f,
                    false
            );
            for (int j = 0; j < data.length; j++) {
                data[j][i] = alg.run().getFitness();
                f.resetEvaluationsCounter();
            }
        }
        String[] header = popSizes.stream().map(String::valueOf).toArray(String[]::new);
        writeToCSV(header, data, "data/population_sizes.csv");
    }

    private static void optimizeMutation() { // optimal 0.9
        List<Double> mutations = List.of(0.1, 0.3, 0.6, 0.9);
        FitnessFunction f = new F6(3);
        double[] lbs = new double[f.numberOfVariables()];
        double[] ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        double[][] data = new double[30][mutations.size()];
        for (int i = 0; i < mutations.size(); i++) {
            EvolutionaryAlgorithm<? extends Solution<?>> alg = new GeneticAlgorithm<>(
                    RANDOM,
                    100,
                    TOL,
                    MAX_EVALUATIONS,
                    new RandomDoublePopulationInitializer(RANDOM, lbs, ubs),
                    new KTournamentSelection<>(RANDOM, 3),
                    new BLXACrossover(RANDOM, 0.5, lbs, ubs),
                    new GaussMutation(RANDOM, 1.5, mutations.get(i), lbs, ubs),
                    new PassThroughDecoder(lbs, ubs),
                    f,
                    false
            );
            for (int j = 0; j < data.length; j++) {
                data[j][i] = alg.run().getFitness();
                f.resetEvaluationsCounter();
            }
        }
        String[] header = mutations.stream().map(String::valueOf).toArray(String[]::new);
        writeToCSV(header, data, "data/mutations.csv");
    }

    private static void writeToCSV(String[] header, double[][] data, String file) {
        try (PrintWriter pwr = new PrintWriter(Files.newOutputStream(Paths.get(file)))) {
            pwr.println(String.join(",", header));
            Arrays.stream(data).forEach(row -> pwr.println(Arrays.stream(row)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(","))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
