package hr.fer.zemris.apr.hw04;

import hr.fer.zemris.apr.hw04.ea.EvolutionaryAlgorithm;
import hr.fer.zemris.apr.hw04.ea.crossover.BLXACrossover;
import hr.fer.zemris.apr.hw04.ea.crossover.Crossover;
import hr.fer.zemris.apr.hw04.ea.decoder.Decoder;
import hr.fer.zemris.apr.hw04.ea.decoder.PassThroughDecoder;
import hr.fer.zemris.apr.hw04.ea.fitness.F3;
import hr.fer.zemris.apr.hw04.ea.fitness.FitnessFunction;
import hr.fer.zemris.apr.hw04.ea.ga.GeneticAlgorithm;
import hr.fer.zemris.apr.hw04.ea.initializer.PopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.initializer.RandomDoublePopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.mutation.GaussMutation;
import hr.fer.zemris.apr.hw04.ea.mutation.Mutation;
import hr.fer.zemris.apr.hw04.ea.selection.KTournamentSelection;
import hr.fer.zemris.apr.hw04.ea.selection.Selection;
import hr.fer.zemris.apr.hw04.ea.solution.Solution;
import hr.fer.zemris.apr.hw04.ea.util.Util;

import java.util.Arrays;
import java.util.Random;

/**
 * Demo program that runs through all task problems.
 *
 * @author dbrcina
 */
public class Demo {

    private static final double MIN_VALUE = -50;
    private static final double MAX_VALUE = 150;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        FitnessFunction f1 = new F3(2);
        double[] lbs = new double[f1.numberOfVariables()];
        double[] ubs = new double[f1.numberOfVariables()];
        double[] precisions = new double[f1.numberOfVariables()];
        Arrays.fill(lbs, MIN_VALUE);
        Arrays.fill(ubs, MAX_VALUE);
        Arrays.fill(precisions, 0.1);
        int[] bitsPerVariable = Util.calculateBitsPerVariables(lbs, ubs, precisions);
        PopulationInitializer<Solution<Double>> initializer =
                new RandomDoublePopulationInitializer(RANDOM, lbs, ubs);
        Selection<Solution<Double>> selection = new KTournamentSelection<>(RANDOM, 3);
        Crossover<Solution<Double>> crossover = new BLXACrossover(RANDOM, 0.5, lbs, ubs);
        Mutation<Solution<Double>> mutation = new GaussMutation(RANDOM, 0.1, lbs, ubs);
        Decoder<Solution<Double>> decoder = new PassThroughDecoder(lbs, ubs);
        EvolutionaryAlgorithm<Solution<Double>> alg = new GeneticAlgorithm<>(
                100,
                1e-6,
                15_000,
                initializer,
                selection,
                crossover,
                mutation,
                decoder,
                f1
        );
        alg.run();
    }

}
