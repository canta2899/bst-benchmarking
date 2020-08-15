import org.apache.poi.hssf.record.formula.functions.Rand;
import java.text.DecimalFormat;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Main class, once executed produces a text file with execution times for analytic purposes
 */

public class Time {

    // Execution thread

    public static void main(String[] args) {
        // computing machine's resolution and deriving maxError to respect 1% error during execution
        long maxError = Resolution.getResolution()*101;

        // variables
        int n, count;
        int[] keys;
        long bsTime, avlTime, rbTime;
        double bsStd, avlStd, rbStd;

        // objects for statistical calculations (mean and std)
        Statistics statistics;
        RandomKeyGenerator rng;
        DecimalFormat df = new DecimalFormat("0.000");

        // tools for writing values on a txt file
        String fileName = "tempi_di_esecuzione.txt";
        StringBuilder infoLine = new StringBuilder();


        // Building text file
        File time = new File(fileName);
        try{
            if (time.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("An error happened during file creation");
            System.exit(1);
        }


        // Execution

        try (FileWriter writer = new FileWriter(fileName)){

            // JVM WARMUP
            count = 0;
            for(int length = 0; length < 60; length+=10) {
                int warmUpSize = (int)(Math.pow(1.25, length)*10);
                rng = new RandomKeyGenerator(warmUpSize);
                rng.generateRandomKey();
                keys = rng.getKeys();
                for (int iter_length = 0; iter_length < 50; iter_length++) {
                    System.out.print("\rWarming up JVM... " + ++count*100/(6*50*4) + "%");
                    getExTimeBSTree(warmUpSize, keys, maxError);
                    getExTimeAVLTree(warmUpSize, keys, maxError);
                    getExTimeRBTree(warmUpSize, keys, maxError);
                }
            }

            // Time calculation starts, for every "iter" a new value for n is computed according to the exp function
            for(int iter = 0; iter < 100; iter++){

                System.out.println("Starting calculation for a new line");

                // Computing n according to the following exponential function
                n = (int)(Math.pow(1.116, iter)*100);  // TODO exp function is between 100 and 5000000. Is that okay?


                // Obtaining a vector of random keys that will be used in trees' algorithms
                rng = new RandomKeyGenerator(n);
                rng.generateRandomKey();
                keys = rng.getKeys();


                // Building statistics object to compute mean and std
                statistics = new Statistics();


                // 50 times for every n and then computing the mean and std
                for(int i = 0; i < 49; i++) {
                    statistics.insertBsTime(getExTimeBSTree(n, keys, maxError));
                    System.out.println("BS done");
                    statistics.insertAvlTime(getExTimeAVLTree(n, keys, maxError));
                    System.out.println("AVL done");
                    statistics.insertRbTime(getExTimeRBTree(n, keys, maxError));
                    System.out.println("RB done");
                }

                System.out.println("Computing statistics...");

                // Statistics
                bsTime = statistics.computeBsMean();
                avlTime = statistics.computeAvlMean();
                rbTime = statistics.computeRbMean();

                bsStd = statistics.computeBsStd();
                avlStd = statistics.computeAvlStd();
                rbStd = statistics.computeRbStd();


                // Building summing string and writing that on the txt file
                System.out.println("Writing line on txt file");
                infoLine.append(n).append(" ")
                        .append(bsTime).append(" ").append(df.format(bsStd)).append(" ")
                        .append(avlTime).append(" ").append(df.format(avlStd)).append(" ")
                        .append(rbTime).append(" ").append(df.format(rbStd))
                        .append("\n");
                writer.write(infoLine.toString());

                // Resetting the string builder
                infoLine.setLength(0);
            }
        } catch (IOException e){
            System.out.println("An I/O error happened, text file hasn't been completed successfully");
            System.exit(1);
        }
    }


    /**
     * Gets the amortized execution time for a Binary Search Tree
     * @param n the number of iterations
     * @param keys the keys that will be searched (or inserted) in the tree
     * @param maxError value that time can't exceed in order to respect 1% relative error
     * @return the time value computed
     */
    public static long getExTimeBSTree(int n, int[] keys, long maxError) {
        long start, end;
        int count = 0;
        BSNode root;
        start = System.nanoTime();
        do {
            root = null;
            for(int i=0; i<n; i++){
                if(BSTree.find(root, keys[i]) == null){
                    root = BSTree.insert(root, new BSNode(keys[i], "value"));
                }
            }
            end = System.nanoTime();
            count++;
        } while (end - start <= maxError);

        return ((end - start) / count) / n;
    }


    /**
     * Gets the amortized execution time for a AVL Tree
     * @param n the number of iterations
     * @param keys the keys that will be searched (or inserted) in the tree
     * @param maxError value that time can't exceed in order to respect 1% relative error
     * @return the time value computed
     */
    public static long getExTimeAVLTree(int n, int[] keys, long maxError) {
        long start, end;
        int count = 0;
        AVLNode root;
        start = System.nanoTime();
        do {
            root = null;
            for(int i=0; i<n; i++){
                if(AVLTree.find(root, keys[i]) == null){
                    root = AVLTree.insert(root, new AVLNode(keys[i], "value", 1));
                }
            }
            end = System.nanoTime();
            count++;
        } while (end - start <= maxError);
        return ((end - start) / count) / n;
    }


    /**
     * Gets the amortized execution time for a Red Black Tree Tree
     * @param n the number of iterations
     * @param keys the keys that will be searched (or inserted) in the tree
     * @param maxError value that time can't exceed in order to respect 1% relative error
     * @return the time value computed
     */
    public static long getExTimeRBTree(int n, int[] keys, long maxError) {
        long start, end;
        int count = 0;
        RBNode root;
        start = System.nanoTime();
        do {
            root = null;
            for(int i=0; i<n; i++){
                if(RBTree.find(root, keys[i]) == null){
                    root = RBTree.insert(root, new RBNode(keys[i], "value"));
                }
            }
            end = System.nanoTime();
            count++;
        } while (end - start <= maxError);

        return ((end - start) / count) / n;
    }
} // class Time
