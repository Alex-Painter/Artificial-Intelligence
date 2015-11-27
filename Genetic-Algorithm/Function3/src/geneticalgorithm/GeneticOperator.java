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
    public Population tournamentSelection(Population pop) {
        Population offspring = new Population(pop.getSize(), false);

        for (int i = 0; i < offspring.getSize(); i++) {
            Individual parent1 = pop.getRandom();
            Individual parent2 = pop.getRandom();
            Individual child = new Individual(parent1.getChromosome().length);

            //using Math.abs as some values can be negative; making all positive
            //to be able to compare
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
    public Population singlePointCrossover(Population pop) {
        Population offspring = new Population(pop.getSize(), false);

        for (int i = 0; i < offspring.getSize(); i++) {
            Individual parent1 = pop.getRandom();
            Individual parent2 = pop.getRandom();

            Individual child1 = new Individual(parent1.getChromosome().length);
            copyChromo(parent1, child1);
            Individual child2 = new Individual(parent1.getChromosome().length);
            copyChromo(parent2, child2);

            int seperator = RAND.nextInt(parent1.getChromosome().length);

            float[] temp = new float[parent1.getChromosome().length];
            System.arraycopy(child1.getChromosome(), seperator, temp, seperator,
                    child1.getChromosome().length - seperator);//parent 1 to temp
            System.arraycopy(child2.getChromosome(), seperator, child1.getChromosome(), seperator,
                    child1.getChromosome().length - seperator);//parent2 to parent1
            System.arraycopy(temp, seperator, child2.getChromosome(), seperator,
                    child1.getChromosome().length - seperator);

            if (Math.abs(child1.getFitness()) < Math.abs(child2.getFitness())) {
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
    Non-Uniform Mutation
    - For every gene in every individual in population
    - Random double w
    - If w is less than mutation probability m then perform operation on gene j
    - If random numenr 1 or 0 is 0 perform first function; else perform second
      - Calling deltaFunc
    - Repeat until offspring population is filled
    */
    public Population nonUniformMutation(Population pop, double m, int t, int T, double b) {
        Population offspring = new Population(pop.getSize(), false);

        for (int i = 0; i < pop.getSize(); i++) {
            Individual parent = pop.getIndividuals()[i];
            Individual child = new Individual(parent.getChromosome().length);
            copyChromo(parent, child);
            for (int j = 0; j < child.getChromosome().length; j++) {
                double w = RAND.nextDouble();
                float x = child.getChromosome()[j];

                if (w < m) {
                    int c = RAND.nextInt(2);

                    //deltaFunk(generation number, delta(), Max gen number, dependancy on iteration number
                    if (c == 0) {
                        x = x + deltaFunc(t, (5.12f - x), 1000, 0.5);
                        child.getChromosome()[j] = x;
                    } else {
                        x = x - deltaFunc(t, (x - (-5.12f)), 1000, 0.5);
                        child.getChromosome()[j] = x;
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

    /*
    Uniform Mutation
    - For every gene in every individual in the population
    - If random double is lower than mutation probability m
    - Temp fitness = random (float - 0.5) * perturbation
    - If temp += individual i gene j < lower boundry, set gene to lower boundry
    - Else if temp += individual i gene j > higher boundry, set gene to higher boundry
    */
    public Population uniformMutation(Population pop, double m, float perturbation, float lB, float hB) {
        Population offspring = new Population(pop.getSize(), false);

        for (int i = 0; i < pop.getSize(); i++) {
            Individual child = new Individual(pop.getIndividuals()[i].getChromosome().length);
            copyChromo(pop.getIndividuals()[i], child);
            for (int j = 0; j < pop.getIndividuals()[i].getChromosome().length; j++) {
                if (RAND.nextDouble() < m) {
                    float random = RAND.nextFloat();
                    float temp = (random - 0.5f) * perturbation;

                    temp += pop.getIndividuals()[i].getChromosome()[j];

                    if (temp < lB) {
                        temp = lB;
                    } else if (temp > hB) {
                        temp = hB;
                    }
                    child.getChromosome()[j] = temp;
                }
            }

            child.fitnessFunction();
            offspring.getIndividuals()[i] = child;
        }

        offspring.setPopFitness();
        offspring.setBestInv();
        return offspring;
    }

    private float deltaFunc(int t, float y, int T, double b) {
        float d;
        float r = RAND.nextFloat();

        d = (float) (y * (1 - Math.pow(r, (Math.pow(1 - (t / T), b)))));

        return d;
    }

    //Method to set all individual's fitnesses, pop fitness and find best individual
    public void forceSetFitness(Population pop) {
        for (int i = 0; i < pop.getSize(); i++) {
            pop.getIndividuals()[i].fitnessFunction();
        }
        pop.getBestInv().fitnessFunction();
        pop.setBestInv();
        pop.setPopFitness();
    }

    //Returns the better population out of two; used to compare original
    //  population and one generated after all operations
    public Population bestPop(Population pop, Population pop2) {
        if (Math.abs(pop.getAverageFit()) < Math.abs(pop2.getAverageFit())) {
            return pop;
        } else {
            return pop2;
        }
    }

    //Returns the best individual between two populations
    public Individual saveBest(Population pop, Population pop2) {
        Individual newBest = new Individual(10);
        if (Math.abs(pop.getBestInv().getFitness()) < Math.abs(pop2.getBestInv().getFitness())) {
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
