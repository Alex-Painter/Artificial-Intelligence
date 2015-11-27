package geneticalgorithm;

import java.util.Random;

/**
 * @author Alex Painter
 */
public class Population {

    private Individual[] individuals;
    private Individual bestInv;
    private int fitness;
    private final int SIZE;
    private final Random RAND;

    public Population(int size, Boolean firstPop) {
        this.SIZE = size;
        individuals = new Individual[size];
        RAND = new Random();
        this.bestInv = new Individual(8);

        //setting 'blank' best inv to worst possbile value
        for (int j = 0; j < this.bestInv.getChromosome().length; j++) {
            this.bestInv.getChromosome()[j] = 0;
        }
        this.bestInv.fitnessFunction();

        if (firstPop) {
            initPop(individuals);
        } else {
            initBlankPop(individuals);
        }
        this.setPopFitness();
        this.setBestInv();
    }

    public Individual[] getIndividuals() {
        return individuals;
    }

    public Individual getRandom() {
        int r = RAND.nextInt(SIZE);

        Individual i = individuals[r];
        return i;
    }

    public void setIndividuals(Individual[] individuals) {
        this.individuals = individuals;
    }

    public void setPopFitness() {
        int i = 0;

        for (int j = 0; j < this.individuals.length; j++) {
            i += individuals[j].getFitness();
        }
        this.fitness = i;
    }

    public int getPopFitness() {
        return this.fitness;
    }

    public int getSize() {
        return SIZE;
    }

    private void initPop(Individual[] pop) {
        for (int i = 0; i < pop.length; i++) {
            pop[i] = new Individual(8);
        }
    }

    private void initBlankPop(Individual[] pop) {
        int k = pop.length;
        int[] x = new int[8];

        for (int i = 0; i < pop.length; i++) {
            Individual t = new Individual(8);
            t.setChromosome(x);
            t.fitnessFunction();
            pop[i] = t;
        }
    }

    public Individual getBestInv() {
        return bestInv;
    }

    public void setBestInv() {
        for (int i = 0; i < this.individuals.length; i++) {
            if (this.individuals[i].getFitness() > this.bestInv.getFitness()) {
                this.bestInv = this.individuals[i];
            }
        }
    }

    public void setBestInv(Individual inv) {
        this.bestInv = inv;
    }

    public double getAverageFit() {
        double mean = (double) this.fitness / this.SIZE;
        return mean;
    }

}
