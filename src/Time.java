import org.apache.poi.ss.usermodel.*;

import java.io.*;

/**
 * Main class, once executed produces a text file with execution times for analytic purposes
 */

public class Time {

    // Execution thread

    public static void main(String[] args) {
        // computing machine's resolution and deriving maxError to respect 1% error during execution
        long maxError = Resolution.getResolution() * 101;

        // variables
        int n;
        int[] keys;

        RandomKeyGenerator rng;

        try {
            // Init Excel Workbook
            String fileName = "Time.xlsx";
            FileInputStream inputStream = new FileInputStream(new File(fileName));
            Workbook workbook = WorkbookFactory.create(inputStream);

            // Execution

            // JVM WARMUP
            for (int length = 0; length < 50; length++) {
                n = (int) (Math.pow(1.116, length) * 100);
                rng = new RandomKeyGenerator(n);
                rng.generateRandomKey();
                keys = rng.getKeys();
                for (int iter_length = 0; iter_length < 50; iter_length++) {
                    System.out.print("\rWarming up JVM... ");
                    getExTimeBSTree(n, keys, maxError);
                    getExTimeAVLTree(n, keys, maxError);
                    getExTimeRBTree(n, keys, maxError);
                }
            }

            // Time calculation starts, for every "iter" a new value for n is computed according to the exp function
            for (int iter = 0; iter < 100; iter++) {

                System.out.println("Starting calculation for a new line " + iter);

                // Computing n according to the following exponential function
                n = (int) (Math.pow(1.116, iter) * 100);

                // Obtaining a vector of random keys that will be used in trees' algorithms
                rng = new RandomKeyGenerator(n);
                rng.generateRandomKey();
                keys = rng.getKeys();

                // Opening sheet corresponding to iter
                Sheet sheet = workbook.getSheetAt(iter);


                // 50 times for every n and then computing the mean and std
                for (int i = 0; i < 50; i++) {
                    Row row = sheet.getRow(i + 4);
                    Cell cell = row.createCell(0);
                    cell.setCellValue(getExTimeBSTree(n, keys, maxError));
                    //System.out.println("BS done");
                    cell = row.createCell(1);
                    cell.setCellValue(getExTimeAVLTree(n, keys, maxError));
                    //System.out.println("AVL done");
                    cell = row.createCell(2);
                    cell.setCellValue(getExTimeRBTree(n, keys, maxError));
                    //System.out.println("RB done");
                }

            } // iterate for new n value

            // Writing and closing Excel file
            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            System.out.println("Time calculation finished correctly. Data can be found on file " + fileName);

        } catch (IOException e) {
            System.out.println("An I/O error happened");
            System.exit(1);
        }
    }


    /**
     * Gets the amortized execution time for a Binary Search Tree
     *
     * @param n        the number of iterations
     * @param keys     the keys that will be searched (or inserted) in the tree
     * @param maxError value that time can't exceed in order to respect 1% relative error
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
     * @param maxError value that time can't exceed in order to respect 1% relative error
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
     * @param maxError value that time can't exceed in order to respect 1% relative error
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

        return ((end - start) / count) / n;  // division by n gives the amortized time
    }
} // class Time
