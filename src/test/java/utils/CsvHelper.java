package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

public class CsvHelper {

    public static List<CSVRecord> readCsv(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            return CSVFormat.Builder.create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(reader)
                    .getRecords();
        } catch (Exception e) {
            logCsvReadError(filePath, e);
            return Collections.emptyList();
        }
    }

    @Step("❌ CSV Read Error: {filePath}")
    private static void logCsvReadError(String filePath, Exception e) {
        Allure.addAttachment("CSV Read Error - " + filePath, e.getMessage());
    }
}
