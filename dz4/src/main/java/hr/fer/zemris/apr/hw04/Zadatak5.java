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

    public static void main(String[] args) {
        List<Integer> ks = List.of(3, 5, 10, 15, 20, 25, 30, 40, 50);
        FitnessFunction f = new F6(3);
        double[] lbs = new double[f.numberOfVariables()];
        double[] ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, -50);
        Arrays.fill(ubs, 150);
        double[][] data = new double[30][ks.size()];
        for (int i = 0; i < ks.size(); i++) {
            EvolutionaryAlgorithm<? extends Solution<?>> alg = new GeneticAlgorithm<>(
                    RANDOM,
                    50,
                    1e-6,
                    (int) 1e5,
                    new RandomDoublePopulationInitializer(RANDOM, lbs, ubs),
                    new KTournamentSelection<>(RANDOM, ks.get(i)),
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
        String[] header = ks.stream().map(String::valueOf).toArray(String[]::new);
        writeToCSV(header, data, "data/tournaments.csv");
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
