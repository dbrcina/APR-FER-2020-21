package hr.fer.zemris.apr.hw04.ea.ga;

import hr.fer.zemris.apr.hw04.ea.EvolutionaryAlgorithm;
import hr.fer.zemris.apr.hw04.ea.crossover.Crossover;
import hr.fer.zemris.apr.hw04.ea.decoder.Decoder;
import hr.fer.zemris.apr.hw04.ea.fitness.FitnessFunction;
import hr.fer.zemris.apr.hw04.ea.initializer.PopulationInitializer;
import hr.fer.zemris.apr.hw04.ea.mutation.Mutation;
import hr.fer.zemris.apr.hw04.ea.selection.Selection;
import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * An implementation of <i>Elimination Genetic algorithm</i>.
 *
 * @author dbrcina
 */
public class GeneticAlgorithm<S extends Solution<?>> implements EvolutionaryAlgorithm<S> {

    private final Random random;
    private final int populationSize;
    private final double tol;
    private final int maxEvaluations;
    private final double mutationProb;
    private final PopulationInitializer<S> initializer;
    private final Selection<S> selection;
    private final Crossover<S> crossover;
    private final Mutation<S> mutation;
    private final Decoder<S> decoder;
    private final FitnessFunction fitnessFunction;

    public GeneticAlgorithm(
            Random random,
            int populationSize,
            double tol,
            int maxEvaluations,
            double mutationProb,
            PopulationInitializer<S> initializer,
            Selection<S> selection,
            Crossover<S> crossover,
            Mutation<S> mutation,
            Decoder<S> decoder,
            FitnessFunction fitnessFunction) {
        this.random = random;
        this.populationSize = populationSize;
        this.tol = tol;
        this.maxEvaluations = maxEvaluations;
        this.mutationProb = mutationProb;
        this.initializer = initializer;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.decoder = decoder;
        this.fitnessFunction = fitnessFunction;
    }

    @Override
    public S run() {
        S bestSolution = null;

        System.out.println("Initializing the population...");
        List<S> population = initializer.generatePopulation(populationSize);
        population.forEach(s -> s.setFitness(fitnessFunction.calculateFitness(decoder.decode(s))));
        System.out.println("Population initialized!");

        System.out.println("Starting iterations...");
        for (int iteration = 0; ; iteration++) {

            // Find the best in the current iteration.
            S currentBest = Collections.max(population);
            if (bestSolution == null || currentBest.getFitness() > bestSolution.getFitness()) {
                bestSolution = currentBest;
                System.out.printf("Iteration %d:%n" +
                                "\tSolution = %s%n" +
                                "\tFitness = %e%n" +
                                "\tEvaluations = %d%n",
                        iteration + 1,
                        Arrays.toString(decoder.decode(bestSolution)),
                        bestSolution.getFitness(),
                        fitnessFunction.numberOfEvaluations()
                );
                System.out.println();
            }

            // Exit condition.
            if (Math.abs(bestSolution.getFitness()) <= tol
                    || fitnessFunction.numberOfEvaluations() >= maxEvaluations) {
                break;
            }

            // Generate a new child.
            S[] selected = selection.select(population);
            S p1 = selected[0];
            S p2 = selected[1];
            S child = crossover.crossover(p1, p2);
            if (random.nextDouble() < mutationProb) {
                child = mutation.mutate(child);
            }
            child.setFitness(fitnessFunction.calculateFitness(decoder.decode(child)));
            int index = population.indexOf(selected[selected.length - 1]);
            population.set(index, child);
        }

        System.out.println("Algorithm finished!");
        return bestSolution;
    }

}
