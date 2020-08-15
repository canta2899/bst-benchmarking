import org.apache.commons.math3.random.MersenneTwister;
import java.security.SecureRandom;

public class RandomKeyGenerator {



    private int currentSize;
    private int[] keys;

    RandomKeyGenerator(int n){
        this.currentSize = n;
        this.keys = new int[n];
    }


    void generateRandomKey() {
        MersenneTwister mt = new MersenneTwister(generateSeed());
        int value;
        for(int i = 0; i < currentSize; i++){
            value = mt.nextInt();
            keys[i] = value;
        }
    }

    int[] getKeys(){
        return keys;
    }


    /**
     * Generates a seed for MersenneTwister algorithm
     * @return the seed
     */
    static int[] generateSeed() {
        byte[] byteSeed = SecureRandom.getSeed(64);
        int[] intSeed = new int[byteSeed.length];
        for(int i = 0; i < 64; i++)
            intSeed[i] = byteSeed[i];
        return intSeed;
    }

}
