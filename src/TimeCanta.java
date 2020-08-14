import org.apache.poi.hssf.record.formula.functions.Rand;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TimeCanta {
    private static BSNode bsTree;
    private static AVLNode avlTree;
    private static RBNode rbTree;

    public static void main(String[] args) {
        int n;
        long bsTime, avlTime, rbTime;
        double bsStd, avlStd, rbStd;
        long maxError = Resolution.getResolution()*101;
        Statistics statistics;

        RandomNodesGenerator rng = new RandomNodesGenerator();
        String fileName = "tempi_di_esecuzione.txt";
        StringBuilder infoLine = new StringBuilder();

        File time = new File(fileName);
        try {
            if(time.createNewFile()){
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }

            FileWriter writer = new FileWriter(fileName);

            // TODO JVM WARM UP

            // Executing time calculation for "iter" times, every time with a higher n according to the exp function
            for(int iter = 0; iter < 100; iter++){
                statistics = new Statistics();
                System.out.println("Starting calculation for a new line");
                // n = (int)(Math.pow(1.116, iter)*100);  // TODO is exp function alright?
                n = (iter + 1) * 3;
                // Building three arrays with same random k values, each one with nodes for a specific tree
                // use getBsArray, get AvlArray or getRbArray to obtain the array of nodes for each execution
                rng.buildSize(n);
                rng.generateRandomNodes();

                // TODO random nodes are ready, execute algorithm and compute amortized time

                // ! PATTERN !

                // generate array of nodes with casual key
                //for 50 times
                    // time calculation starts
                        // initialize tree
                        // for n times...
                            // search for node k in  tree or insert node k in tree --> T
                    // time calculation stops
                    // if time respects maxError keep, otherwise execute again
                    // RETURN TIME / executions / n
                // calculate mean and standard deviation

                for(int i = 0; i < 20; i++) {
                    statistics.insertBsTime(getExTimeBSTree(n, rng.getBsArray(), maxError));
                    statistics.insertAvlTime(getExTimeAVLTree(n, rng.getAvlArray(), maxError));
                    statistics.insertRbTime(getExTimeRBTree(n, rng.getRbArray(), maxError));
                }

                System.out.println("First line done, computing statistics...");

                bsTime = statistics.computeBsMean();
                avlTime = statistics.computeAvlMean();
                rbTime = statistics.computeRbMean();

                bsStd = statistics.computeBsStd();
                avlStd = statistics.computeAvlStd();
                rbStd = statistics.computeRbStd();


                // Writing on text file
                // N T1 (computate D1) T2 (computate D2) T3 (computate D3)

                System.out.println("Writing line on txt file");
                infoLine.append(n).append(" ").append(bsTime).append(" ").append(bsStd).append(" ")
                        .append(avlTime).append(" ").append(avlStd).append(" ").append(rbTime)
                        .append(" ").append(rbStd).append("\n");
                writer.write(infoLine.toString());

                infoLine.setLength(0);
            }
            writer.close();
        } catch (IOException e){
            System.out.println("An error accoured");
        }

    }

    public static long getExTimeBSTree(int n, BSNode[] nodes, long maxError) {
        long start, end;
        int count = 0;
        BSNode root;
        start = System.nanoTime();
        do {
            root = null;
            for(int i=0; i<n; i++){
                if(BSTree.find(root, nodes[i].key) == null){
                    root = BSTree.insert(root, nodes[i]);
                }
            }
            end = System.nanoTime();
            count++;
        } while (end - start <= maxError);

        return ((end - start) / count) / n;
    }



    public static long getExTimeAVLTree(int n, AVLNode[] nodes, long maxError) {
        long start, end;
        int count = 0;
        AVLNode root;
        start = System.nanoTime();
        do {
            root = null;
            for(int i=0; i<n; i++){
                if(AVLTree.find(root, nodes[i].key) == null){
                    root = AVLTree.insert(root, nodes[i]);
                }
            }
            end = System.nanoTime();
            count++;
        } while (end - start <= maxError);

        return ((end - start) / count) / n;
    }


    public static long getExTimeRBTree(int n, RBNode[] nodes, long maxError) {
        long start, end;
        int count = 0;
        RBNode root;
        start = System.nanoTime();
        do {
            root = null;
            for(int i=0; i<n; i++){
                if(RBTree.find(root, nodes[i].key) == null){
                    root = RBTree.insert(root, nodes[i]);
                }
            }
            end = System.nanoTime();
            count++;
        } while (end - start <= maxError);

        return ((end - start) / count) / n;
    }
}
