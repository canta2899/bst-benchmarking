import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SearchTest {
    public static void main(String[] args) throws IOException {
        String fileName = "Search.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(fileName));
        Workbook workbook = WorkbookFactory.create(inputStream);
        long maxError = Resolution.getResolution() * 101;
        int n;
        int[] keys;
        RandomKeyGenerator rng;
        for (int length = 0; length < 100; length++) {
            Sheet sheet = workbook.getSheetAt(length);
            n = (int) (Math.pow(1.116, length) * 10);
            rng = new RandomKeyGenerator(n);
            rng.generateRandomKey();
            keys = rng.getKeys();
            BSNode bs = null;
            AVLNode avl = null;
            for (int key : keys) {
                bs = BSTree.insert(bs, new BSNode(key, "value"));
                avl = AVLTree.insert(avl, new AVLNode(key, "value", 1));
            }
            rng = new RandomKeyGenerator(1);
            rng.generateRandomKey();
            int key = rng.getKeys()[0];
            long start, end;
            for (int i = 0; i < 50; i++) {
                Row row = sheet.getRow(i + 4);
                Cell cell = row.createCell(0);
                int count = 0;
                start = System.nanoTime();
                do {
                    BSTree.find(bs, key);
                    end = System.nanoTime();
                    count++;
                } while (end - start <= maxError);
                cell.setCellValue((end - start) / count);
                cell = row.createCell(1);
                count = 0;
                start = System.nanoTime();
                do {
                    AVLTree.find(avl, key);
                    end = System.nanoTime();
                    count++;
                } while (end - start <= maxError);
                cell.setCellValue((end - start) / count);
            }
        }
        inputStream.close();
        FileOutputStream outputStream = new FileOutputStream(fileName);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
