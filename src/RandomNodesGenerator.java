import org.apache.commons.math3.random.MersenneTwister;
import java.security.SecureRandom;

public class RandomNodesGenerator {

    private int currentSize;
    private BSNode[] bsArray;
    private AVLNode[] avlArray;
    private RBNode[] rbArray;

    RandomNodesGenerator(){}

    void buildSize(int n){
        currentSize = n;
        bsArray = new BSNode[n];
        avlArray = new AVLNode[n];
        rbArray = new RBNode[n];
    }


    void generateRandomNodes() {
        MersenneTwister mt = new MersenneTwister(generateSeed());
        int value;
        for(int i = 0; i < currentSize; i++){
            value = mt.nextInt();
            bsArray[i] = new BSNode(value, "value");
            avlArray[i] = new AVLNode(value, "value",1);
            rbArray[i] = new RBNode(value, "value");
        }
    }

    BSNode[] getBsArray(){
        return bsArray;
    }

    AVLNode[] getAvlArray(){
        return avlArray;
    }

    RBNode[] getRbArray(){
        return rbArray;
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
