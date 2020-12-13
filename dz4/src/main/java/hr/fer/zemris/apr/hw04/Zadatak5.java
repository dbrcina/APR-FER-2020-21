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
public class Zadatak5 {

    private static final Random RANDOM = new Random();
    private static final double X_MIN = -50;
    private static final double X_MAX = 150;
    private static final double TOL = 1e-6;
    private static final int MAX_EVALUATIONS = (int) 1e5;

    public static void main(String[] args) {
        switch (args[0]) {
            case "tournament" -> optimizeTournament();
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
                new GaussMutation(RANDOM, 1.3, 0.6, lbs, ubs),
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
        String file = "data/task5/optimal_population_mutation_tournament.txt";
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

    private static void optimizeTournament() { // optimal 3
        List<Integer> ks = List.of(3, 5, 10, 15, 20, 25, 30, 40, 50);
        FitnessFunction f = new F6(3);
        double[] lbs = new double[f.numberOfVariables()];
        double[] ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        double[][] data = new double[30][ks.size()];
        for (int i = 0; i < ks.size(); i++) {
            EvolutionaryAlgorithm<? extends Solution<?>> alg = new GeneticAlgorithm<>(
                    RANDOM,
                    100,
                    TOL,
                    MAX_EVALUATIONS,
                    new RandomDoublePopulationInitializer(RANDOM, lbs, ubs),
                    new KTournamentSelection<>(RANDOM, ks.get(i)),
                    new BLXACrossover(RANDOM, 0.5, lbs, ubs),
                    new GaussMutation(RANDOM, 1.3, 0.6, lbs, ubs),
                    new PassThroughDecoder(lbs, ubs),
                    f,
                    false
            );
            for (int j = 0; j < data.length; j++) {
                data[j][i] = alg.run().getFitness();
                f.resetEvaluationsCounter();
            }
        }
        String[] header = ks.stream().map(String::valueOf).toArray(String[]::new);
        writeToCSV(header, data, "data/task5/tournaments.csv");
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
