package hr.fer.zemris.apr.hw04;

import hr.fer.zemris.apr.hw04.ea.EvolutionaryAlgorithm;
import hr.fer.zemris.apr.hw04.ea.crossover.Crossover;
import hr.fer.zemris.apr.hw04.ea.crossover.TPointCrossover;
import hr.fer.zemris.apr.hw04.ea.crossover.UniformBinaryCrossover;
import hr.fer.zemris.apr.hw04.ea.decoder.Decoder;
import hr.fer.zemris.apr.hw04.ea.decoder.GrayDecoder;
import hr.fer.zemris.apr.hw04.ea.decoder.NaturalBinaryDecoder;
import hr.fer.zemris.apr.hw04.ea.fitness.*;
import hr.fer.zemris.apr.hw04.ea.ga.GeneticAlgorithm;
import hr.fer.zemris.apr.hw04.ea.initializer.PopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.initializer.RandomBinaryPopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.mutation.Mutation;
import hr.fer.zemris.apr.hw04.ea.mutation.RandomBinaryMutation;
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
    private static final int PRECISION = 4;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        FitnessFunction f1 = new F3(5);
        double[] lbs = new double[f1.numberOfVariables()];
        double[] ubs = new double[f1.numberOfVariables()];
        double[] precisions = new double[f1.numberOfVariables()];
        Arrays.fill(lbs, MIN_VALUE);
        Arrays.fill(ubs, MAX_VALUE);
        Arrays.fill(precisions, PRECISION);
        int[] bitsPerVariable = Util.calculateBitsPerVariables(lbs, ubs, precisions);
        PopulationInitializer<Solution<Boolean>> initializer =
                new RandomBinaryPopulationInitializer(RANDOM, bitsPerVariable);
        Selection<Solution<Boolean>> selection = new KTournamentSelection<>(RANDOM, 3);
        Crossover<Solution<Boolean>> crossover = new UniformBinaryCrossover(initializer);
        Mutation<Solution<Boolean>> mutation = new RandomBinaryMutation(RANDOM, 0.15);
        Decoder<Solution<Boolean>> decoder = new GrayDecoder(lbs, ubs, bitsPerVariable);
        EvolutionaryAlgorithm<Solution<Boolean>> alg = new GeneticAlgorithm<>(
                RANDOM,
                100,
                1e-6,
                50_000,
                0.7,
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
