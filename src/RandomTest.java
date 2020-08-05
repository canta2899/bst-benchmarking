import org.apache.commons.math3.random.MersenneTwister;

import java.security.SecureRandom;

public class RandomTest {

    /**
     * Generates random input vector using MersenneTwiseter PRNG
     * @param targetSize the size of the randomly filled array that will be generated
     * @return the array generated
     */
    public static int[] randomInput(int targetSize) {
        MersenneTwister mt = new MersenneTwister(generateSeed());
        int[] array = new int[targetSize];
        int i = 0;
        while(i < targetSize) {
            array[i++] = mt.nextInt();
        }
        return array;
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

    /**
     * Random generator for k value (debug purposes only)
     * @param max the max size for the k value that will be generated
     * @return k value >=0  and  < max
     */
    static int getK(int max){
        int min = 1;
        int k = min + (int)(Math.random() * (max - min));
        return k;
    }
}
