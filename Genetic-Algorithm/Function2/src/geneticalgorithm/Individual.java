package geneticalgorithm;

import java.util.Random;

/**
 * @author Alex Painter
 */
public class Individual {

    private int[] chromosome;
    private double fitness;
    private final Random RAND;

    //size is the chromosome size
    public Individual(int size) {
        this.RAND = new Random();
        this.chromosome = initChromo(size);
        this.fitnessFunction();
    }

    public int[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public double getFitness() {
        this.fitnessFunction();
        return fitness;
    }

    //Counts the binary of both 5 gene sections
    //Inserts x and y into fitness function
    public void fitnessFunction() {
        int x = this.countBinary(chromosome, 0);
        int y = this.countBinary(chromosome, 5);

        double matyas = (double) 0.26 * (x ^ 2 + y ^ 2) - 0.48 * x * y;

        this.fitness = matyas;
    }

    //initialises the chromosome with random 1s and 0s
    private int[] initChromo(int size) {
        int[] chromo = new int[size];
        for (int i = 0; i < size; i++) {
            chromo[i] = RAND.nextInt(2);
        }
        return chromo;
    }

    //Count the chromosome in accordance to structure in report
    private int countBinary(int[] chromo, int start) {
        int f = 0, x = 1;

        //start at 1 to skip +/- flag at 0
        for (int i = start + 1; i < start + 5; i++) {
            if (chromo[i] == 1) {
                f += x;
            }
            x *= 2;
        }

        //if flag is negative; convert value to negative
        if (chromo[start] == 1) {
            f -= f * 2;
        }

        return f;
    }
}
