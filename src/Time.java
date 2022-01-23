import java.text.DecimalFormat;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Main class, once executed produces a text file with execution times for
 * analytic purposes
 */

public class Time {

    // Execution thread

    public static void main(String[] args) {
        // computing machine's resolution and deriving maxError to respect 1% error
        // during execution
        long maxError = Resolution.getResolution() * 101;

        // variables
        int n, count;
        int[] keys;
        long bsTime, avlTime, rbTime;
        double bsStd, avlStd, rbStd;

        // objects for statistical calculations (mean and std)
        Statistics statistics;
        RandomKeyGenerator rng;

        // tools for writing values on a txt file
        String fileName = "results.csv";
        StringBuilder infoLine;

        // Building text file
        File timefile = new File("./results/" + fileName);

        try {
            timefile.createNewFile();
            System.out.println("File " + fileName + " created.");
        } catch (IOException e) {
            System.out.println("An error happened during file creation");
            System.exit(1);
        }

        try (FileWriter writer = new FileWriter("./results/" + fileName)) {
            writer.write("size,bst_time,bst_std,avl_time,avl_std,rbt_time,rbt_std\n");
        } catch (IOException e) {
            System.out.println("Error while building results.csv file");
            System.exit(1);
        }

        // JVM WARMUP
        for (int length = 0; length < 70; length++) {
            n = (int) (Math.pow(1.116, length) * 10);
            rng = new RandomKeyGenerator(n);
            rng.generateRandomKey();
            keys = rng.getKeys();
            for (int iter_length = 0; iter_length < 50; iter_length++) {
                System.out.print("\rWarming up JVM...");
                getExTimeBSTree(n, keys, maxError);
                getExTimeAVLTree(n, keys, maxError);
                getExTimeRBTree(n, keys, maxError);
            }
        }

        // Time calculation starts, for every "iter" a new value for n is computed
        // according to the exp function
        for (int iter = 0; iter < 100; iter++) {
            infoLine = new StringBuilder();
            System.out.print("Iteration " + String.valueOf(iter) + "/100 - ");

            // Computing n according to the following exponential function
            n = (int) (Math.pow(1.116, iter) * 100);

            // Obtaining a vector of random keys that will be used in trees' algorithms
            rng = new RandomKeyGenerator(n);
            rng.generateRandomKey();
            keys = rng.getKeys();

            // Building statistics object to compute mean and std
            statistics = new Statistics();

            // 50 times for every n and then computing the mean and std
            for (int i = 0; i < 49; i++) {
                statistics.insertBsTime(getExTimeBSTree(n, keys, maxError));
                statistics.insertAvlTime(getExTimeAVLTree(n, keys, maxError));
                statistics.insertRbTime(getExTimeRBTree(n, keys, maxError));
            }

            // Statistics
            bsTime = statistics.computeBsMedian();
            avlTime = statistics.computeAvlMedian();
            rbTime = statistics.computeRbMedian();

            bsStd = statistics.computeBsStd();
            avlStd = statistics.computeAvlStd();
            rbStd = statistics.computeRbStd();

            // Building summing string and writing that on the txt file
            infoLine.append(n).append(",")
                    .append(bsTime).append(",").append(bsStd).append(",")
                    .append(avlTime).append(",").append(avlStd).append(",")
                    .append(rbTime).append(",").append(rbStd).append("\n");

            // Writing results line
            try (FileWriter writer = new FileWriter("./results" + fileName, true)) {
                writer.write(infoLine.toString());
            } catch (IOException e) {
                System.out.println("An I/O error for line " + String.valueOf(iter));
                System.exit(1);
            }
            System.out.print("Done\n");

        } // iterate for new n value

    }

    /**
     * Gets the amortized execution time for a Binary Search Tree
     * 
     * @param n        the number of iterations
     * @param keys     the keys that will be searched (or inserted) in the tree
     * @param maxError value that time can't exceed in order to respect 1% relative
     *                 error
     * @return the time value computed
     */
    public static long getExTimeBSTree(int n, int[] keys, long maxError) {
        long start, end;
        int count = 0;
        BSNode root;
        start = System.nanoTime();
        do {
            root = null; // Garbage Collector clears the previous tree
            for (int i = 0; i < n; i++) {
                if (BSTree.find(root, keys[i]) == null) {
                    root = BSTree.insert(root, new BSNode(keys[i], "value"));
                }
            }
            end = System.nanoTime();
            count++;
        } while (end - start <= maxError);

        return ((end - start) / count) / n; // division by n gives the amortized time
    }

    /**
     * Gets the amortized execution time for a AVL Tree
     * 
     * @param n        the number of iterations
     * @param keys     the keys that will be searched (or inserted) in the tree
     * @param maxError value that time can't exceed in order to respect 1% relative
     *                 error
     * @return the time value computed
     */
    public static long getExTimeAVLTree(int n, int[] keys, long maxError) {
        long start, end;
        int count = 0;
        AVLNode root;
        start = System.nanoTime();
        do {
            root = null; // Garbage Collector clears the previous tree
            for (int i = 0; i < n; i++) {
                if (AVLTree.find(root, keys[i]) == null) {
                    root = AVLTree.insert(root, new AVLNode(keys[i], "value", 1));
                }
            }
            end = System.nanoTime();
            count++;
        } while (end - start <= maxError);
        return ((end - start) / count) / n; // division by n gives the amortized time
    }

    /**
     * Gets the amortized execution time for a Red Black Tree Tree
     * 
     * @param n        the number of iterations
     * @param keys     the keys that will be searched (or inserted) in the tree
     * @param maxError value that time can't exceed in order to respect 1% relative
     *                 error
     * @return the time value computed
     */
    public static long getExTimeRBTree(int n, int[] keys, long maxError) {
        long start, end;
        int count = 0;
        RBNode root;
        start = System.nanoTime();
        do {
            root = null; // Garbage Collector clears the previous tree
            for (int i = 0; i < n; i++) {
                if (RBTree.find(root, keys[i]) == null) {
                    root = RBTree.insert(root, new RBNode(keys[i], "value"));
                }
            }
            end = System.nanoTime();
            count++;
        } while (end - start <= maxError);

        return ((end - start) / count) / n; // division by n gives the amortized time
    }
} // class Time
