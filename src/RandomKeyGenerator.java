import org.apache.commons.math3.random.MersenneTwister;
import java.security.SecureRandom;

/**
 * Allows to generate a certain amount of random keys
 */
class RandomKeyGenerator {

    private int currentSize;
    private int[] keys;

    /**
     * Constructor for a Random Key Generator
     * @param n the number of keys that will be generated
     */
    RandomKeyGenerator(int n){
        this.currentSize = n;
        this.keys = new int[n];
    }

    /**
     * Populates the vector of random keys according to the currentSize value.
     * Keys are generated randomly with MersenneTwister algorithm
     */
    void generateRandomKey() {
        MersenneTwister mt = new MersenneTwister(generateSeed());
        int value;
        for(int i = 0; i < currentSize; i++){
            value = mt.nextInt();
            keys[i] = value;
        }
    }

    /**
     * Allows to obtain the keys generated
     * @return the vector of keys
     */
    int[] getKeys(){
        return keys;
    }


    /**
     * Generates a seed for MersenneTwister algorithm
     * @return the seed
     */
    private static int[] generateSeed() {
        byte[] byteSeed = SecureRandom.getSeed(64);
        int[] intSeed = new int[byteSeed.length];
        for(int i = 0; i < 64; i++)
            intSeed[i] = byteSeed[i];
        return intSeed;
    }

}
