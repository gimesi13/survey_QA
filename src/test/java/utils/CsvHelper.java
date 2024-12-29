package utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvHelper {

    private static final Logger LOGGER = Logger.getLogger(CsvHelper.class.getName());

    public static List<CSVRecord> readCsv(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            CSVFormat csvFormat = CSVFormat.Builder.create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build();

            return csvFormat.parse(reader).getRecords();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading CSV file: " + filePath, e);
            return Collections.emptyList();
        }
    }
}