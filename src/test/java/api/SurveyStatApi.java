package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.csv.CSVRecord;
import utils.ApiCallHelper;
import utils.AssertionHelper;
import utils.CsvHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SurveyStatApi extends BaseApi {

    private static final String PARTICIPATION_CSV_PATH = "src/test/java/resources/csv/Participation.csv";

    @Step("Validate survey statistic for field: {fieldName}")
    public void testSurveyStatistic(String fieldName) {
        String jsonPathKey = getJsonPathKey(fieldName);
        List<CSVRecord> participationRecords = CsvHelper.readCsv(PARTICIPATION_CSV_PATH);
        Response response = ApiCallHelper.get("/surveys/statistics");

        List<SurveyStatistics> expectedStatistics = calculateExpectedStatistics(participationRecords);
        List<Integer> actualValues = response.jsonPath().getList(jsonPathKey, Integer.class);
        List<Integer> expectedValues = getSurveyStatistic(expectedStatistics, fieldName);

        AssertionHelper.assertValues(fieldName, expectedValues, actualValues);
    }

    private String getJsonPathKey(String fieldName) {
        switch (fieldName) {
            case "Survey Id":
                return "surveyId";
            case "Number of Completes":
                return "numberOfCompletes";
            case "Number of Filtered":
                return "numberOfFiltered";
            case "Number of Rejected":
                return "numberOfRejected";
            case "Average Length":
                return "avgLengthOfSurvey";
            default:
                throw new IllegalArgumentException("Unknown field name: " + fieldName);
        }
    }

    @Step("Calculate expected statistics from participation records")
    private List<SurveyStatistics> calculateExpectedStatistics(List<CSVRecord> records) {
        Map<Integer, SurveyStatistics> statisticsMap = new HashMap<>();

        for (CSVRecord record : records) {
            try {
                int surveyId = Integer.parseInt(record.get("Survey Id"));
                String status = record.get("Status");
                int length = record.get("Length") != null && !record.get("Length").isEmpty()
                        ? Integer.parseInt(record.get("Length"))
                        : 0;

                statisticsMap.computeIfAbsent(surveyId, k -> new SurveyStatistics(surveyId))
                        .update(status, length);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Error processing record: " + record, e);
            }
        }

        return new ArrayList<>(statisticsMap.values());
    }

    @Step("Extract {fieldName} from survey statistics")
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
                            throw new IllegalArgumentException("Unknown field: " + fieldName);
                    }
                })
                .collect(Collectors.toList());
    }

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