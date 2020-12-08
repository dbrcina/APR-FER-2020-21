package hr.fer.zemris.apr.hw04.ea.solution;

import java.util.Arrays;
import java.util.Objects;

/**
 * Models a solution of evolutionary algorithm. Each solution consists of:
 * <ul>
 *     <li><i>genes</i> - an array of genes,</li>
 *     <li><i>fitness</i> - double value,</li>
 *     <li><i>evaluated</i> - boolean flag.</li>
 * </ul>
 * Two solutions can be compared based on their fitness values:
 * <pre>S1 is "better than" S2 if it has higher fitness.</pre>
 *
 * @param <G> gene type.
 *
 * @author dbrcina
 */
public class Solution<G> implements Comparable<Solution<G>> {

    private final G[] genes;
    private double fitness;
    private boolean evaluated;

    /**
     * Constructor.
     *
     * @param genes an array of genes.
     */
    public Solution(G[] genes) {
        this.genes = genes;
    }

    /**
     * @return a number of solution's genes.
     */
    public int getNumberOfGenes() {
        return genes.length;
    }

    /**
     * @param index index.
     *
     * @return gene at the provided {@code index}.
     *
     * @throws IndexOutOfBoundsException if the provided {@code index} is invalid.
     */
    public G getGene(int index) {
        Objects.checkIndex(index, genes.length);
        return genes[index];
    }

    /**
     * Retrieves a new array of genes from index {@code from(inclusive)} to index {@code to(exclusive)}.
     *
     * @param fromIndex index from.
     * @param toIndex   index to.
     *
     * @return an array of genes.
     *
     * @throws IndexOutOfBoundsException if the provided indexes are invalid.
     */
    public G[] getSubGenes(int fromIndex, int toIndex) {
        Objects.checkFromToIndex(fromIndex, toIndex, genes.length);
        return Arrays.copyOfRange(genes, fromIndex, toIndex);
    }

    /**
     * Setts the provided {@code gene} at the provided {@code index}.
     *
     * @param gene  gene to be added.
     * @param index index.
     *
     * @throws IndexOutOfBoundsException if the provided {@code index} is invalid.
     */
    public void setGene(G gene, int index) {
        Objects.checkIndex(index, genes.length);
        genes[index] = gene;
        evaluated = false;
    }

    /**
     * Setts the provided genes from index {@code from(inclusive)} to index {@code to(exclusive)}.
     *
     * @param genes     genes.
     * @param fromIndex index from.
     * @param toIndex   index to.
     *
     * @throws IndexOutOfBoundsException if the provided indexes are invalid.
     */
    public void setSubGenes(G[] genes, int fromIndex, int toIndex) {
        Objects.checkFromToIndex(fromIndex, toIndex, this.genes.length);
        for (int i = 0, j = fromIndex; i < genes.length & j < toIndex; i++, j++) {
            setGene(genes[i], j);
        }
    }

    /**
     * @return solution's fitness.
     *
     * @throws IllegalStateException if solution hasn't been evaluated.
     */
    public double getFitness() {
        if (!isEvaluated()) {
            throw new IllegalStateException("Solution has yet not been evaluated!");
        }
        return fitness;
    }

    /**
     * Setts solution's fitness.
     *
     * @param fitness new fitness.
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
        evaluated = true;
    }

    /**
     * @return {@code true} if solution has been evaluated.
     */
    public boolean isEvaluated() {
        return evaluated;
    }

    /**
     * @return a copy of this solution.
     */
    public Solution<G> copy() {
        Solution<G> copied = new Solution<>(Arrays.copyOf(genes, genes.length));
        if (isEvaluated()) {
            copied.setFitness(getFitness());
        }
        return copied;
    }

    @Override
    public int compareTo(Solution<G> other) {
        if (!isEvaluated()) {
            throw new IllegalStateException("Solution has yet not been evaluated and it cannot be compared to others!");
        }
        return Double.compare(this.getFitness(), other.getFitness());
    }

}
