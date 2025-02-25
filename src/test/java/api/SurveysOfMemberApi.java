package api;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ApiCallHelper;
import utils.CsvHelper;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SurveysOfMemberApi extends BaseApi {

    private static final Logger logger = LoggerFactory.getLogger(SurveysOfMemberApi.class);
    private static final String PARTICIPATION_CSV_PATH = "src/test/java/resources/csv/Participation.csv";
    private static final String SURVEYS_CSV_PATH = "src/test/java/resources/csv/Surveys.csv";

    // Validate that the member data matches participation and survey information
    public void validateMemberSurveyData() {
        Allure.step("Validating that the member data matches participation and survey information");

        List<CSVRecord> participationRecords = CsvHelper.readCsv(PARTICIPATION_CSV_PATH);
        List<CSVRecord> surveyRecords = CsvHelper.readCsv(SURVEYS_CSV_PATH);

        // Filter participation records to only include those with Status 2, 3, or 4
        List<CSVRecord> validParticipationRecords = participationRecords.stream()
                .filter(record -> {
                    int status = Integer.parseInt(record.get("Status"));
                    return status == 2 || status == 3 || status == 4;
                })
                .collect(Collectors.toList());

        // Get distinct member IDs from the valid participation records
        Set<Integer> memberIds = validParticipationRecords.stream()
                .map(record -> Integer.parseInt(record.get("Member Id")))
                .collect(Collectors.toSet());

        // Use CompletableFuture to parallelize the validation of each member
        CompletableFuture.allOf(memberIds.stream()
                .map(memberId -> CompletableFuture.runAsync(() -> {
                    try {
                        // List of surveys this member participated in
                        List<Integer> participatingSurveyIds = getParticipatingSurveyIds(memberId, validParticipationRecords);
                        Response response = ApiCallHelper.get("/members/" + memberId);

                        // Validate that the surveys in the response match the surveys the member participated in
                        validateSurveysForMember(memberId, participatingSurveyIds, response);

                        // Validate that the survey values in the response match those in Surveys.csv
                        validateSurveyValues(memberId, response, surveyRecords);

                    } catch (Exception e) {
                        throw new RuntimeException("Validation failed for Member ID " + memberId, e);
                    }
                })).toArray(CompletableFuture[]::new)).join();
    }

    // Validate that the survey values match those in the Surveys.csv for each survey
    private void validateSurveyValues(Integer memberId, Response response, List<CSVRecord> surveyRecords) {
        List<Integer> surveyIdsFromResponse = response.jsonPath().getList("id", Integer.class);

        // Validate the survey values for each survey in the response
        for (Integer surveyId : surveyIdsFromResponse) {
            // Fetch the survey details from Surveys.csv
            Map<String, Object> surveyDetails = getSurveyDetails(surveyId, surveyRecords);
            int expectedCompletes = (int) surveyDetails.get("expectedCompletes");
            int completionPoints = (int) surveyDetails.get("completionPoints");
            int filteredPoints = (int) surveyDetails.get("filteredPoints");
            String expectedSurveyName = (String) surveyDetails.get("name");

            // Fetch the survey data from the API response
            int index = surveyIdsFromResponse.indexOf(surveyId);
            int actualExpectedCompletes = response.jsonPath().getInt(String.format("[%d].expectedCompletes", index));
            int actualCompletionPoints = response.jsonPath().getInt(String.format("[%d].completionPoints", index));
            int actualFilteredPoints = response.jsonPath().getInt(String.format("[%d].filteredPoints", index));
            String actualSurveyName = response.jsonPath().getString(String.format("[%d].name", index));

            // Validate the expected vs actual values
            if (actualExpectedCompletes != expectedCompletes) {
                throw new AssertionError(String.format("For Survey ID %d, Member ID %d, expected completes (%d) do not match the actual value (%d).",
                        surveyId, memberId, expectedCompletes, actualExpectedCompletes));
            }
            if (actualCompletionPoints != completionPoints) {
                throw new AssertionError(String.format("For Survey ID %d, Member ID %d, completion points (%d) do not match the actual value (%d).",
                        surveyId, memberId, completionPoints, actualCompletionPoints));
            }
            if (actualFilteredPoints != filteredPoints) {
                throw new AssertionError(String.format("For Survey ID %d, Member ID %d, filtered points (%d) do not match the actual value (%d).",
                        surveyId, memberId, filteredPoints, actualFilteredPoints));
            }
            if (!actualSurveyName.equals(expectedSurveyName)) {
                throw new AssertionError(String.format("For Survey ID %d, Member ID %d, expected survey name ('%s') does not match the actual name ('%s').",
                        surveyId, memberId, expectedSurveyName, actualSurveyName));
            }
        }
    }

    // Validate the surveys that a member is involved in based on the API response
    private void validateSurveysForMember(Integer memberId, List<Integer> participatingSurveyIds, Response response) {
        List<Integer> surveyIdsFromResponse = response.jsonPath().getList("id", Integer.class);

        // Ensure the API response contains the surveys this member participated in
        for (Integer surveyId : surveyIdsFromResponse) {
            if (!participatingSurveyIds.contains(surveyId)) {
                throw new AssertionError(String.format(
                        "Survey ID %d is present in the response for Member ID %d, but the member did not participate in this survey.",
                        surveyId, memberId));
            }
        }

        // Ensure all surveys the member participated in are in the API response
        for (Integer surveyId : participatingSurveyIds) {
            if (!surveyIdsFromResponse.contains(surveyId)) {
                throw new AssertionError(String.format(
                        "Survey ID %d is missing from the response for Member ID %d, but the member participated in this survey.",
                        surveyId, memberId));
            }
        }
    }

    // Helper method to get the survey IDs that a specific member participated in
    private List<Integer> getParticipatingSurveyIds(Integer memberId, List<CSVRecord> validParticipationRecords) {
        return validParticipationRecords.stream()
                .filter(record -> Integer.parseInt(record.get("Member Id")) == memberId)
                .map(record -> Integer.parseInt(record.get("Survey Id")))
                .collect(Collectors.toList());
    }

    // Helper method to get survey details (Expected completes, Completion points, Filtered points, Name) from Surveys.csv
    private Map<String, Object> getSurveyDetails(int surveyId, List<CSVRecord> surveyRecords) {
        return surveyRecords.stream()
                .filter(record -> Integer.parseInt(record.get("Survey Id")) == surveyId)
                .findFirst()
                .map(record -> {
                    Map<String, Object> details = new HashMap<>();
                    details.put("expectedCompletes", Integer.parseInt(record.get("Expected completes")));
                    details.put("completionPoints", Integer.parseInt(record.get("Completion points")));
                    details.put("filteredPoints", Integer.parseInt(record.get("Filtered points")));
                    details.put("name", record.get("Name"));
                    return details;
                })
                .orElseThrow(() -> new IllegalArgumentException("Survey ID " + surveyId + " not found in the Surveys CSV"));
    }
}