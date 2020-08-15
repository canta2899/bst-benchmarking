import org.apache.poi.hssf.record.formula.functions.Rand;
import java.text.DecimalFormat;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Time {
    private static BSNode bsTree;
    private static AVLNode avlTree;
    private static RBNode rbTree;

    public static void main(String[] args) {
        int n;
        long bsTime, avlTime, rbTime;
        double bsStd, avlStd, rbStd;
        long maxError = Resolution.getResolution()*101;
        int[] keys;
        Statistics statistics;
        DecimalFormat df = new DecimalFormat("0.000");
        RandomKeyGenerator rng;
        String fileName = "tempi_di_esecuzione.txt";
        StringBuilder infoLine = new StringBuilder();

        File time = new File(fileName);
        try {
            if (time.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("An error accoured during file creation");
        }

        try (FileWriter writer = new FileWriter(fileName)){
            // TODO JVM WARM UP

            // Executing time calculation for "iter" times, every time with a higher n according to the exp function
            for(int iter = 0; iter < 100; iter++){

                System.out.println("Starting calculation for a new line");


                // n = (int)(Math.pow(1.116, iter)*100);  // TODO is exp function alright?
                n = (iter + 1) * 5; // just for debugging

                // Obtaining a vector of random keys that will be used in trees' algorithms
                rng = new RandomKeyGenerator(n);
                rng.generateRandomKey();
                keys = rng.getKeys();


                // building statistics object to compute mean and std
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


                // Writing summing string on a text file using a string builder
                System.out.println("Writing line on txt file");
                infoLine.append(n).append(" ")
                        .append(bsTime).append(" ").append(df.format(bsStd)).append(" ")
                        .append(avlTime).append(" ").append(df.format(avlStd)).append(" ")
                        .append(rbTime).append(" ").append(df.format(rbStd))
                        .append("\n");
                writer.write(infoLine.toString());

                // resetting the string builder
                infoLine.setLength(0);
            }
        } catch (IOException e){
            System.out.println("An I/O error happened, text file wasn't compiled successfully");
        }
    }


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
}
