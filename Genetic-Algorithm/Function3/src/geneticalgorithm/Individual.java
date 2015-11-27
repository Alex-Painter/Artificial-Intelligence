package geneticalgorithm;

import java.util.Random;

/**
 * @author Alex Painter
 */
public final class Individual {

    private float[] chromosome;
    private float fitness;
    private final Random rand;

    //size is the chromosome size
    public Individual(int size) {
        this.rand = new Random();
        this.chromosome = initChromo(size);
        this.fitnessFunction();
    }

    public float[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(float[] chromosome) {
        this.chromosome = chromosome;
        this.fitnessFunction();
    }

    public float getFitness() {
        return fitness;
    }

    //function 3 fitness function
    public void fitnessFunction() {
        float f = 0;
        for (int i = 0; i < this.chromosome.length; i++){
            f += func(this.chromosome[i]);
        }
        
        this.fitness = (this.chromosome.length * 10) + f;               
    }
    
    //This is the second part of the function 3 equation; form the summation
    //  sign onwards
    private float func(float x){
        float n;
        float p = (float) (2 * Math.PI);
        float z  = p * x;
        float c = (float) Math.cos(z);
        
        n = x - 10 * c;
        
        return n;
    }

    //Initialises the chromosome with float point numbers
    private float[] initChromo(int size) {
        float min = -5.12f;
        float max = 5.12f;
        
        float[] chromo = new float[size];
        for (int i = 0; i < size; i++) {
            chromo[i] = rand.nextFloat() * (max - min) + min;
        }
        return chromo;
    }
}
