package geneticalgorithm;

import java.util.Random;

/**
 * @author Alex Painter
 */
public class Population {

    private Individual[] individuals;
    private Individual bestInv;
    private float fitness;
    private final int SIZE;
    private final Random RAND;

    public Population(int size, Boolean firstPop) {
        this.SIZE = size;
        individuals = new Individual[size];
        RAND = new Random();
        this.bestInv = new Individual(10);

        //setting 'blank' best inv to worst possbile value
        for (int j = 0; j < size; j++) {
            this.bestInv.getChromosome()[j] = 1;
        }
        this.bestInv.fitnessFunction();

        if (firstPop) {
            initPop(individuals);
        } else {
            initBlankPop(individuals);
        }

        this.setPopFitness();

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
        float i = 0;

        for (int j = 0; j < this.individuals.length; j++) {
            i += individuals[j].getFitness();
        }
        this.fitness = i;
    }

    public double getPopFitness() {
        return this.fitness;
    }

    public int getSize() {
        return SIZE;
    }

    private void initPop(Individual[] pop) {
        for (int i = 0; i < pop.length; i++) {
            pop[i] = new Individual(10);
        }
    }

    private void initBlankPop(Individual[] pop) {
        int k = pop.length;
        float[] x = new float[10];

        for (int i = 0; i < pop.length; i++) {
            Individual t = new Individual(k);
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
            this.individuals[i].fitnessFunction();
            if (Math.abs(this.individuals[i].getFitness()) < Math.abs(this.bestInv.getFitness())) {
                this.bestInv.setChromosome(this.individuals[i].getChromosome());
                this.bestInv.fitnessFunction();
                this.individuals[i].fitnessFunction();
            }
        }
    }

    public void setBestInv(Individual inv) {
        this.bestInv = inv;
    }

    public double getAverageFit() {
        this.setPopFitness();
        return this.fitness / this.SIZE;
    }

}
