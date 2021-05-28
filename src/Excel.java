import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Excel {
    public static void main(String[] args) {
        try {
            String templateName = "Time_Template.xlsx";
            String fileName = "TimeIterative.xlsx";

            Files.copy(Paths.get(templateName), Paths.get(fileName), REPLACE_EXISTING);
            //Initializing a new excel file and sheet in which data will be registered
            FileInputStream inputStream = new FileInputStream(new File(fileName));

            Workbook workbook = WorkbookFactory.create(inputStream);

            //Fills the excel sheet
            for (int i = 1; i < 100; i++) {
                Sheet sheet = workbook.cloneSheet(0);
                workbook.setSheetName(i, String.valueOf((int)(Math.pow(1.116, i)*100)));
            }
            //terminates after filling the sheet with 100 time execution's observations for each algorithm

            inputStream.close();

            //saves the excel file created before
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }
}
