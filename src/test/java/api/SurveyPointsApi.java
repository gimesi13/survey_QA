package api;

import io.restassured.response.Response;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ApiCallHelper;
import utils.CsvHelper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SurveyPointsApi extends BaseApi {

    private static final Logger logger = LoggerFactory.getLogger(SurveyPointsApi.class);
    private static final String SURVEYS_CSV_PATH = "src/test/java/resources/csv/Surveys.csv";
    private static final String PARTICIPATION_CSV_PATH = "src/test/java/resources/csv/Participation.csv";

    public void testSurveyPoints(String csvFieldName) {
        logger.info("Starting validation for status: {}", csvFieldName);
        String jsonPathKey = "";
        switch (csvFieldName) {
            case "Survey Id":
                jsonPathKey = "id";
                break;
            case "Name":
                jsonPathKey = "name";
                break;
            case "Expected completes":
                jsonPathKey = "expectedCompletes";
                break;
            case "Completion points":
                jsonPathKey = "completionPoints";
                break;
            case "Filtered points":
                jsonPathKey = "filteredPoints";
                break;
            default:
                throw new IllegalArgumentException("Unknown CSV field name: " + csvFieldName);
        }

        List<CSVRecord> surveyRecords = CsvHelper.readCsv(SURVEYS_CSV_PATH);

        // Extract the list of values for the given field from the CSV
        List<String> csvValues = surveyRecords.stream()
                .map(record -> record.get(csvFieldName))
                .collect(Collectors.toList());

        // Iterate through all members (1 to 300)
        for (int memberId = 1; memberId <= 300; memberId++) {
            Response response = ApiCallHelper.get("/members/" + memberId + "/points");
            List<String> actualValues = response.jsonPath().getList("survey." + jsonPathKey, String.class);

            // Identify missing values
            List<String> missingValues = actualValues.stream()
                    .filter(value -> !csvValues.contains(value))
                    .collect(Collectors.toList());

            if (!missingValues.isEmpty()) {
                logger.error("Mismatch found for Member ID: {}", memberId);
                logger.error("Missing Values for Field '{}': {}", csvFieldName, missingValues);
                throw new AssertionError(String.format(
                        "Field mismatch for Member ID: %d%nField: %s%nMissing Values: %s",
                        memberId, csvFieldName, missingValues
                ));
            } else {
                logger.info("All values for field '{}' are valid for Member ID: {}", csvFieldName, memberId);
            }
        }
    }

    public void validatePoints() {
        // Read Surveys.csv and Participation.csv
        List<CSVRecord> surveyRecords = CsvHelper.readCsv(SURVEYS_CSV_PATH);
        List<CSVRecord> participationRecords = CsvHelper.readCsv(PARTICIPATION_CSV_PATH);

        // Maps for easy lookup
        Map<Integer, Integer> completionPointsMap = surveyRecords.stream()
                .collect(Collectors.toMap(
                        record -> Integer.parseInt(record.get("Survey Id")),
                        record -> Integer.parseInt(record.get("Completion points"))
                ));

        Map<Integer, Integer> filteredPointsMap = surveyRecords.stream()
                .collect(Collectors.toMap(
                        record -> Integer.parseInt(record.get("Survey Id")),
                        record -> Integer.parseInt(record.get("Filtered points"))
                ));

        // Iterate through all members (1 to 300)
        for (int memberId = 1; memberId <= 300; memberId++) {
            // Filter participation records for the current member
            int finalMemberId = memberId;
            List<CSVRecord> memberParticipations = participationRecords.stream()
                    .filter(record -> Integer.parseInt(record.get("Member Id")) == finalMemberId)
                    .collect(Collectors.toList());

            // Fetch the survey points for the current member
            Response response = ApiCallHelper.get("/members/" + memberId + "/points");

            // Extract the survey and point data from the API response
            List<Map<String, Object>> surveyData = response.jsonPath().getList("");

            for (Map<String, Object> surveyEntry : surveyData) {
                int surveyId = (int) ((Map<String, Object>) surveyEntry.get("survey")).get("id");
                int actualPoint = (int) surveyEntry.get("point");

                // Find the status for this survey ID in the participation records
                List<CSVRecord> relevantParticipations = memberParticipations.stream()
                        .filter(record -> Integer.parseInt(record.get("Survey Id")) == surveyId)
                        .collect(Collectors.toList());

                if (relevantParticipations.isEmpty()) {
                    throw new AssertionError(String.format(
                            "No participation record found for Member ID: %d and Survey ID: %d", memberId, surveyId));
                }

                // Calculate the expected point based on the Status
                int expectedPoint = relevantParticipations.stream()
                        .mapToInt(record -> {
                            int status = Integer.parseInt(record.get("Status"));
                            if (status == 3) {
                                return filteredPointsMap.getOrDefault(surveyId, 0);
                            } else if (status == 4) {
                                return completionPointsMap.getOrDefault(surveyId, 0);
                            } else {
                                return 0;
                            }
                        })
                        .sum();

                // Validate the actual point matches the expected point
                if (actualPoint != expectedPoint) {
                    throw new AssertionError(String.format(
                            "Point mismatch for Member ID: %d and Survey ID: %d%nExpected: %d, Actual: %d",
                            memberId, surveyId, expectedPoint, actualPoint
                    ));
                } else {
                    System.out.printf(
                            "Point validation passed for Member ID: %d and Survey ID: %d%nPoint: %d%n",
                            memberId, surveyId, actualPoint);
                }
            }
        }
    }
}