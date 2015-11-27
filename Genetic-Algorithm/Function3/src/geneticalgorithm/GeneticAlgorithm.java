package geneticalgorithm;

/**
 *
 * @author alexpainter
 */
public class GeneticAlgorithm {

    private static final int POP_SIZE = 10;
    private static final double MUT_PROB = .5;

    public static void main(String[] args) {
        GeneticOperator operator = new GeneticOperator();

        //number of times to run the GA
        int numIterations = 1000;
        int average = 0;

            int i = 0;
            Population pop = new Population(POP_SIZE, true);
            while (i < 500) {
                pop = evolvePop(pop, operator, i);
                i++;

            }
            System.out.println("Finished on generation " + i);
            average += i;
        

        System.out.println("Average number of generations is " + average / numIterations);
    }

    public static Population evolvePop(Population pop, GeneticOperator operator, int t) {

        operator.forceSetFitness(pop);
        Population offspring = operator.tournamentSelection(pop);
        Population offspring2 = operator.singlePointCrossover(offspring);
        //Population offspring3 = operator.nonUniformMutation(offspring2, MUT_PROB, t, 500, 0.5);
        Population offspring3 = operator.uniformMutation(offspring2, MUT_PROB, 1.5f, -5.12f, 5.12f);
        
        Population finalOffspring = operator.bestPop(offspring3, pop);
        finalOffspring.setBestInv(operator.saveBest(offspring3, pop));

        System.out.println("Generation " + t);
        
        System.out.println("\nBest fitness after evolution: " + finalOffspring.getBestInv().getFitness());
        System.out.println("Average fitness after evolution: " + finalOffspring.getAverageFit());
        System.out.println("Total pop fitness after evolution: " + finalOffspring.getPopFitness());
        System.out.println("\n========================================");
        return finalOffspring;
    }

}
