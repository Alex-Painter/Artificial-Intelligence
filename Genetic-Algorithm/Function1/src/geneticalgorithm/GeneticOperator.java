package geneticalgorithm;

import java.util.Random;

/**
 * @author Alex Painter
 */
public class GeneticOperator {

    private final Random RAND;

    public GeneticOperator() {
        RAND = new Random();
    }

    /*
     Tournament Selection:
     - choose two random individuals from parent population
     - compare fitness
     - copy chromosome of better into child and place into offspring population
     - repeat until offspring population is filled
     - return offspring population
     */
    public Population tSelection(Population pop) {
        Population offspring = new Population(pop.getSize(), false);

        for (int i = 0; i < offspring.getSize(); i++) {
            Individual parent1 = pop.getRandom();
            Individual parent2 = pop.getRandom();
            Individual child = new Individual(parent1.getChromosome().length);

            if (Math.abs(parent1.getFitness()) < Math.abs(parent2.getFitness())) {
                copyChromo(parent1, child);
                child.fitnessFunction();
                offspring.getIndividuals()[i] = child;
            } else {
                copyChromo(parent2, child);
                child.fitnessFunction();
                offspring.getIndividuals()[i] = child;
            }
        }
        offspring.setPopFitness();
        offspring.setBestInv();
        return offspring;
    }

    /*
     Single Point Crossover:
     - Choose two random individuals from parent population
     - Choose random crossover point r
     - Move genes from r to end from inv 1 to temp
     - Move genes from r to end from inv 2 to inv 1
     - Move genes from r to end from temp to inv 2
     - Repeat until offspring population is filled
     - Return offspring population
     */
    public Population crossover(Population pop) {
        Population offspring = new Population(pop.getSize(), false);

        for (int i = 0; i < offspring.getSize(); i++) {
            Individual parent1 = pop.getRandom();
            Individual parent2 = pop.getRandom();

            Individual child1 = new Individual(parent1.getChromosome().length);
            copyChromo(parent1, child1);
            Individual child2 = new Individual(parent1.getChromosome().length);
            copyChromo(parent2, child2);

            int seperator = RAND.nextInt(parent1.getChromosome().length);

            int[] temp = new int[pop.getBestInv().getChromosome().length];
            System.arraycopy(child1.getChromosome(), seperator, temp, seperator,
                    child1.getChromosome().length - seperator);//parent 1 to temp
            System.arraycopy(child2.getChromosome(), seperator, child1.getChromosome(), seperator,
                    child1.getChromosome().length - seperator);//parent2 to parent1
            System.arraycopy(temp, seperator, child2.getChromosome(), seperator,
                    child1.getChromosome().length - seperator);

            if (child1.getFitness() > child2.getFitness()) {
                child1.fitnessFunction();
                offspring.getIndividuals()[i] = child1;
            } else {
                child2.fitnessFunction();
                offspring.getIndividuals()[i] = child2;
            }
        }
        offspring.setPopFitness();
        offspring.setBestInv();
        return offspring;
    }

    /*
    Bit-wise Mutation
    - For every gene in every individual's chromosome
    - If random double is less than mutation probability m then flip the bit
    -   If it's a 0, change it to a 1 and vice versa
    */
    public Population mutate(Population pop, double mProb) {

        Population offspring = new Population(pop.getSize(), false);

        for (int i = 0; i < pop.getSize(); i++) {
            Individual parent = pop.getIndividuals()[i];
            Individual child = new Individual(parent.getChromosome().length);
            copyChromo(parent, child);
            child.setChromosome(parent.getChromosome());
            for (int j = 0; j < child.getChromosome().length; j++) {
                double k = RAND.nextDouble();
                if (k < mProb) {
                    if (child.getChromosome()[j] == 1) {
                        child.getChromosome()[j] = 0;
                    } else {
                        child.getChromosome()[j] = 1;
                    }
                }
            }
            child.fitnessFunction();
            offspring.getIndividuals()[i] = child;
        }

        offspring.setPopFitness();
        offspring.setBestInv();
        return offspring;
    }

    //Method to set all individual's fitnesses, pop fitness and find best individual
    public void forceSetFitness(Population pop) {
        for (int i = 0; i > pop.getSize(); i++) {
            pop.getIndividuals()[i].fitnessFunction();
        }
    }

    //Returns the better population out of two; used to compare original
    //  population and one generated after all operations
    public Population bestPop(Population pop, Population pop2) {
        if (pop.getAverageFit() > pop2.getAverageFit()) {
            return pop;
        } else {
            return pop2;
        }
    }

    //Returns the best individual between two populations
    public Individual saveBest(Population pop, Population pop2) {
        Individual newBest = new Individual(8);
        if (pop.getBestInv().getFitness() > pop2.getBestInv().getFitness()) {
            copyChromo(pop.getBestInv(), newBest);
            newBest.fitnessFunction();
            return newBest;
        } else {
            copyChromo(pop2.getBestInv(), newBest);
            newBest.fitnessFunction();
            return newBest;
        }
    }

    //This copies the chromosome from the parent to the child
    //This circumnavigates the problem of copying objects such as individuals
    //  and arrays. I found adding these to an new population would just copy a
    //  pointer to the object rather than copy it. This meant, for example, if 
    //  ndividual 4 was present in a population more than once becuase of
    //  tournamemt selection an operation on that individual would affect all 3.
    //This caused the populations to become full the same individual; all pointers
    //  to the same object.
    public void copyChromo(Individual parent, Individual child) {
        for (int i = 0; i < parent.getChromosome().length; i++) {
            child.getChromosome()[i] = parent.getChromosome()[i];
        }
    }

}
