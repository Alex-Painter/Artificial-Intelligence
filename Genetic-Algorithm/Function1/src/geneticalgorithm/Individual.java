package geneticalgorithm;

import java.util.Random;

/**
 * @author Alex Painter
 */
public class Individual {

    private int[] chromosome;
    private int fitness;
    private final Random RAND;

    public Individual(int cSize) {
        this.RAND = new Random();
        this.chromosome = initChromo(cSize);
        this.fitnessFunction();
    }

    public int[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(int[] chromosome) {
        this.chromosome = chromosome;
        this.fitnessFunction();
    }

    public int getFitness() {
        return fitness;
    }

    //adds up the binary
    public void fitnessFunction() {
        int x = 1;
        int f = 0;
        for (int i = 0; i < chromosome.length; i++) {
            if (chromosome[i] == 1) {
                f += x;
            }
            x *= 2;
        }
        this.fitness = f*f;
    }

    //initialises the chromosome with random 1s and 0s
    private int[] initChromo(int size) {
        int[] chromo = new int[size];
        for (int i = 0; i < size; i++) {
            chromo[i] = RAND.nextInt(2);
        }
        return chromo;
    }
}
