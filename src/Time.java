import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.lang.Math;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Time {

    /**
     * Main algorithm that computes all the execution times for any of the three algorithms with random input and
     * given k and fills an excel spreadsheet with all the obtained data
     */
    public static void main(String[] args) {
        try {
            int targetSize;
            long maxError = Resolution.getResolution()*101;
            String fileName = "Time.xlsx";
            //Initializing a new excel file and sheet in which data will be registered
            FileInputStream inputStream = new FileInputStream(new File(fileName));
            Workbook workbook = WorkbookFactory.create(inputStream);

            int count = 0;
            for(int length = 0; length < 60; length+=10) {
                int warmUpSize = (int)(Math.pow(1.25, length)*10);
                for (int iter_length = 0; iter_length < 50; iter_length++) {
                    int[] warmUpArray = RandomTest.randomInput(warmUpSize);
                    for (int iter_k = 0; iter_k < 4; iter_k++) {
                        System.out.print("\rWarmingup JVM... " + ++count*100/(6*50*4) + "%");
                        getExTimeBSTree(warmUpArray, maxError);
                        getExTimeAVLTree(warmUpArray, maxError);
                        getExTimeRBTree(warmUpArray, maxError);
                    }
                }
            }

            //For each iteration generates a targetsize for the array based on the exponential function
            for(int iter = 0; iter < 100; iter++){
                targetSize = (int)(Math.pow(1.116, iter)*100);

                //builds the random filled array based on the target size and sets 4 different values for k
                int[] kArray = {5, targetSize/2, (int)(Math.log(targetSize)/Math.log(2)), (targetSize - 5)};

                //Compute the execution time 50 times for every algorithm choosing a different k every time.
                for (int i = 0; i < 4; i++) {
                    int k = kArray[i];
                    Sheet sheet = workbook.getSheetAt(iter);
                    System.out.println("Sheet " + iter + "    Array length: " + sheet.getSheetName() + "    k: " + kArray[i]);
                    for (int row_index = 4; row_index < 54; row_index++) {
                        int[] input = RandomTest.randomInput(targetSize);
                        //New line
                        Row row = sheet.getRow(row_index);
                        Cell cell = row.createCell(5*i);

                        //Executes Binary Search Tree
                        long ExTimeBSTree = getExTimeBSTree(input, maxError);
                        cell.setCellValue(ExTimeBSTree);
                        cell = row.createCell(5*i + 1);

                        //Executes AVL Tree
                        long ExTimeAVLTree= getExTimeAVLTree(input, maxError);
                        cell.setCellValue(ExTimeAVLTree);
                        cell = row.createCell(5*i + 2);

                        //Executes Red Black Tree
                        long ExTimeRBTree = getExTimeRBTree(input, maxError);
                        cell.setCellValue(ExTimeRBTree);
                    }
                }
            }

            inputStream.close();

            //saves the excel file created before
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            // workbook.close(); TODO why workbook.close() is error?
            outputStream.close();
        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }


    public static long getExTimeBSTree(int[] array, long maxError) {
        long start, end;
        int count = 0;
        start = System.nanoTime();
        do {
            // execution
            end = System.nanoTime();
            count++;
        } while (end - start <= maxError);

        return (end - start) / count;
    }


    /**
     * Computes execution time for median of medians selection algorithm
     *
     * @return the execution time as a long value
     */
    public static long getExTimeAVLTree(int[] array, long maxError) {
        long start, end;
        int count = 0;
        start = System.nanoTime();
        do {
            // execution
            end = System.nanoTime();
            count++;
        } while (end - start <= maxError);

        return (end - start) / count;
    }


    /**
     * Computes execution time for quick select algorithm
     *
     * @return the execution time as a long value
     */
    public static long getExTimeRBTree(int[] array, long maxError) {
        long start, end;
        int count = 0;
        start = System.nanoTime();
        do {
            // execution
            end = System.nanoTime();
            count++;
        } while (end - start <= maxError);

        return (end - start) / count;
    }
}