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
import java.util.Random;

/**
 * @author dbrcina
 */
public class Zadatak2 {

    private static final Random RANDOM = new Random();
    private static final double X_MIN = -50;
    private static final double X_MAX = 150;
    private static final int POPULATION_SIZE = 100;
    private static final double TOL = 1e-6;
    private static final int MAX_EVALUATIONS = (int) 1e5;

    public static void main(String[] args) {
        System.out.println("\t#### Starting task 2! ####");
        switch (args[0]) {
            case "f6" -> f6();
            case "f7" -> f7();
        }
    }

    private static void f6() {
        System.out.println("Function f6:");
        execute(new F6(1), 1, 0.5, 1);
        execute(new F6(3), 2, 0.6, 2);
        execute(new F6(6), 1, 0.8, 2);
        execute(new F6(10), 1, 0.9, 2);
    }

    private static void f7() {
        System.out.println("Function f7:");
        execute(new F7(1), 1, 0.5, 1);
        execute(new F7(3), 1, 0.5, 1);
        execute(new F7(6), 1, 0.5, 2);
        execute(new F7(10), 1, 0.5, 2);
    }

    private static void execute(FitnessFunction f, double sigma, double p, int populationMultiplier) {
        System.out.println("Dimensions: " + f.numberOfVariables());
        double[] lbs = new double[f.numberOfVariables()];
        double[] ubs = new double[f.numberOfVariables()];
        Arrays.fill(lbs, X_MIN);
        Arrays.fill(ubs, X_MAX);
        Solution<Double> solution = new GeneticAlgorithm<>(
                RANDOM,
                POPULATION_SIZE * populationMultiplier,
                TOL,
                MAX_EVALUATIONS,
                new RandomDoublePopulationInitializer(RANDOM, lbs, ubs),
                new KTournamentSelection<>(RANDOM, 3),
                new BLXACrossover(RANDOM, 0.5, lbs, ubs),
                new GaussMutation(RANDOM, sigma, p, lbs, ubs),
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
        System.out.println();
    }

}
