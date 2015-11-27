package geneticalgorithm;

/**
 *
 * @author alexpainter
 */
public class GeneticAlgorithm {

    private static final int POP_SIZE = 10;
    private static final double MUT_PROB = 0.02;

    public static void main(String[] args) {
        GeneticOperator operator = new GeneticOperator();

        //number of times to run the GA
        int numIterations = 1000;
        int average = 0;
        for (int j = 0; j < numIterations; j++) {
            int i = 0;
            Population pop = new Population(POP_SIZE, true);
            while (pop.getBestInv().getFitness() != 65025 && i != 10000) {
                pop = evolvePop(pop, operator);
                i++;

            }
            System.out.println("Finished on generation " + i);
            average += i;
        }

        System.out.println("Average number of generations is " + average/numIterations);
    }

    public static Population evolvePop(Population pop, GeneticOperator operator) {

        operator.forceSetFitness(pop);
        Population offspring = operator.tSelection(pop);
        Population offspring2 = operator.crossover(offspring);
        Population offspring3 = operator.mutate(offspring2, MUT_PROB);

        Population finalOffspring = operator.bestPop(offspring3, pop);
        finalOffspring.setBestInv(operator.saveBest(offspring3, pop));

        //System.out.println("Generation " + t);
        
        //System.out.println("\nBest fitness after evolution: " + finalOffspring.getBestInv().getFitness());
        //System.out.println("Average fitness after evolution: " + finalOffspring.getAverageFit());
        //System.out.println("Total pop fitness after evolution: " + finalOffspring.getPopFitness());
        //System.out.println("\n========================================");
        return finalOffspring;
    }

}
