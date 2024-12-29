package api;

import io.restassured.response.Response;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ApiCallHelper;
import utils.AssertionHelper;
import utils.CsvHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SurveyStatApi extends BaseApi {

    private static final Logger logger = LoggerFactory.getLogger(SurveyStatApi.class);
    private static final String PARTICIPATION_CSV_PATH = "src/test/java/resources/csv/Participation.csv";

    // Validate survey statistics by comparing the actual API response with expected values
    public void testSurveyStatistic(String fieldName, String jsonPathKey) {
        logger.info("Starting validation for survey statistic: {}", fieldName);
        List<CSVRecord> participationRecords = CsvHelper.readCsv(PARTICIPATION_CSV_PATH);
        Response response = ApiCallHelper.get("/surveys/statistics");

        // Calculate expected statistics based on participation records
        List<SurveyStatistics> expectedStatistics = calculateExpectedStatistics(participationRecords);

        // Extract actual and expected values for comparison
        List<Integer> actualValues = response.jsonPath().getList(jsonPathKey, Integer.class);
        List<Integer> expectedValues = getSurveyStatistic(expectedStatistics, fieldName);

        // Validate values
        AssertionHelper.assertValues(fieldName, expectedValues, actualValues);
        logger.info("Validation completed for survey statistic: {}", fieldName);
    }

    // Calculates expected survey statistics from participation records
    private List<SurveyStatistics> calculateExpectedStatistics(List<CSVRecord> records) {
        Map<Integer, SurveyStatistics> statisticsMap = new HashMap<>();

        for (CSVRecord record : records) {
            try {
                int surveyId = Integer.parseInt(record.get("Survey Id"));
                String status = record.get("Status");
                int length = record.get("Length") != null && !record.get("Length").isEmpty()
                        ? Integer.parseInt(record.get("Length"))
                        : 0;

                // Update statistics for the survey
                statisticsMap.computeIfAbsent(surveyId, k -> new SurveyStatistics(surveyId))
                        .update(status, length);
            } catch (NumberFormatException e) {
                logger.error("Error processing record: {}", record, e);
                throw new RuntimeException("Error processing record: " + record, e);
            }
        }

        logger.debug("Calculated statistics for {} unique surveys.", statisticsMap.size());
        return new ArrayList<>(statisticsMap.values());
    }

    // Extract a specific field from the list of SurveyStatistics
    private List<Integer> getSurveyStatistic(List<SurveyStatistics> statistics, String fieldName) {
        return statistics.stream()
                .map(statistic -> {
                    switch (fieldName) {
                        case "Survey Id":
                            return statistic.getSurveyId();
                        case "Number of Completes":
                            return statistic.getNumberOfCompletes();
                        case "Number of Filtered":
                            return statistic.getNumberOfFiltered();
                        case "Number of Rejected":
                            return statistic.getNumberOfRejected();
                        case "Average Length":
                            return statistic.getAvgLengthOfSurvey();
                        default:
                            logger.error("Unknown field: {}", fieldName);
                            throw new IllegalArgumentException("Unknown field: " + fieldName);
                    }
                })
                .collect(Collectors.toList());
    }

    // Represents survey statistics for a single survey.
    private static class SurveyStatistics {
        private final int surveyId;
        private int numberOfCompletes;
        private int numberOfFiltered;
        private int numberOfRejected;
        private int totalLength;
        private int completedSurveys;

        public SurveyStatistics(int surveyId) {
            this.surveyId = surveyId;
        }

        // Update the statistics based on a record's status and survey length.
        public void update(String status, int length) {
            switch (status) {
                case "4":
                    numberOfCompletes++;
                    completedSurveys++;
                    totalLength += length;
                    break;
                case "3":
                    numberOfFiltered++;
                    break;
                case "2":
                    numberOfRejected++;
                    break;
                case "1":
                    break;
                default:
                    logger.error("Unknown status: {}", status);
                    throw new IllegalArgumentException("Unknown status: " + status);
            }
        }

        public int getSurveyId() {
            return surveyId;
        }

        public int getNumberOfCompletes() {
            return numberOfCompletes;
        }

        public int getNumberOfFiltered() {
            return numberOfFiltered;
        }

        public int getNumberOfRejected() {
            return numberOfRejected;
        }

        public int getAvgLengthOfSurvey() {
            return completedSurveys > 0 ? totalLength / completedSurveys : 0;
        }
    }
}