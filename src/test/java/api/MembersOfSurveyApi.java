package api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.restassured.response.Response;
import org.apache.commons.csv.CSVRecord;
import utils.ApiCallHelper;
import utils.CsvHelper;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class MembersOfSurveyApi extends BaseApi {

    private static final Logger logger = LoggerFactory.getLogger(MembersOfSurveyApi.class);
    private static final String PARTICIPATION_CSV_PATH = "src/test/java/resources/csv/Participation.csv";
    private static final String MEMBERS_CSV_PATH = "src/test/java/resources/csv/Members.csv";

    // Validate members of surveys for a given status
    public void validateSurveyMembersForStatus(String status) {
        logger.info("Starting validation for status: {}", status);

        // Fetch the participation and member data from CSV
        List<CSVRecord> participationRecords = CsvHelper.readCsv(PARTICIPATION_CSV_PATH);
        List<CSVRecord> membersRecords = CsvHelper.readCsv(MEMBERS_CSV_PATH);

        // Filter participation records based on the status
        List<CSVRecord> validRecords = participationRecords.stream()
                .filter(record -> Integer.parseInt(record.get("Status")) == getStatusFromString(status))
                .collect(Collectors.toList());

        logger.debug("Found {} valid participation records for status: {}", validRecords.size(), status);

        // Create CompletableFutures for each Survey ID
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int surveyId = 1; surveyId <= 100; surveyId++) {
            final int finalSurveyId = surveyId;

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                List<Integer> memberIdsForSurvey = getMemberIdsForSurvey(finalSurveyId, validRecords);
                Response response = ApiCallHelper.get("/surveys/" + finalSurveyId + "/members?status=" + status);

                // Validate that the members in the API response match those in the Participation CSV for the given survey and status
                try {
                    validateMembersForSurvey(finalSurveyId, memberIdsForSurvey, response, membersRecords);
                } catch (AssertionError e) {
                    logger.error("Validation failed for Survey ID {}: {}", finalSurveyId, e.getMessage());
                    throw e;
                }
            });

            futures.add(future);
        }

        // Combine all CompletableFutures
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        try {
            // Wait for all tasks to complete
            allOf.join();
            logger.info("Completed validation for status: {}", status);
        } catch (Exception e) {
            logger.error("Error occurred during parallel validation execution: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Validate the members in the API response
    private void validateMembersForSurvey(int surveyId, List<Integer> expectedMemberIds, Response response, List<CSVRecord> membersRecords) {
        List<Integer> memberIdsFromResponse = response.jsonPath().getList("id", Integer.class);

        // Ensure that all expected member IDs are present in the API response
        for (Integer memberId : expectedMemberIds) {
            if (!memberIdsFromResponse.contains(memberId)) {
                logger.error("Member ID {} is missing from the response for Survey ID {}.", memberId, surveyId);
                throw new AssertionError(String.format("Member ID %d is missing from the response for Survey ID %d, but this member is expected to have the status.", memberId, surveyId));
            }
        }

        // Validate the member details from the response against Members.csv
        for (Integer memberId : expectedMemberIds) {
            // Get the expected member details from the Members.csv
            Map<String, String> memberDetails = getMemberDetails(memberId, membersRecords);

            String expectedFullName = memberDetails.get("Full name");
            String expectedEmailAddress = memberDetails.get("E-mail address");
            boolean expectedIsActive = "1".equals(memberDetails.get("Is Active"));

            // Fetch the member data from the API response
            int index = memberIdsFromResponse.indexOf(memberId);
            String actualFullName = response.jsonPath().getString(String.format("[%d].name", index));
            String actualEmailAddress = response.jsonPath().getString(String.format("[%d].emailAddress", index));
            Boolean actualIsActive = response.jsonPath().getBoolean(String.format("[%d].active", index));

            if (actualFullName == null || !actualFullName.equals(expectedFullName)) {
                logger.error("For Member ID {}, expected full name ('{}') does not match actual value ('{}').", memberId, expectedFullName, actualFullName);
                throw new AssertionError(String.format("For Member ID %d, expected full name ('%s') does not match the actual value ('%s').", memberId, expectedFullName, actualFullName));
            }

            if (actualEmailAddress == null || !actualEmailAddress.equals(expectedEmailAddress)) {
                logger.error("For Member ID {}, expected email address ('{}') does not match actual value ('{}').", memberId, expectedEmailAddress, actualEmailAddress);
                throw new AssertionError(String.format("For Member ID %d, expected email address ('%s') does not match the actual value ('%s').", memberId, expectedEmailAddress, actualEmailAddress));
            }

            if (actualIsActive != expectedIsActive) {
                logger.error("For Member ID {}, expected active status ('{}') does not match actual value ('{}').", memberId, expectedIsActive, actualIsActive);
                throw new AssertionError(String.format("For Member ID %d, expected active status (%b) does not match the actual value (%b).", memberId, expectedIsActive, actualIsActive));
            }
        }
    }

    // Helper method to map status string to its corresponding status code
    private int getStatusFromString(String status) {
        switch (status) {
            case "NOT_ASKED":
                return 1;
            case "REJECTED":
                return 2;
            case "FILTERED":
                return 3;
            case "COMPLETED":
                return 4;
            default:
                throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

    // Helper method to get the member IDs for a specific survey from the Participation CSV
    private List<Integer> getMemberIdsForSurvey(int surveyId, List<CSVRecord> participationRecords) {
        return participationRecords.stream()
                .filter(record -> Integer.parseInt(record.get("Survey Id")) == surveyId)
                .map(record -> Integer.parseInt(record.get("Member Id")))
                .collect(Collectors.toList());
    }


    // Helper method to get member details (Full name, E-mail address, Is Active) from Members.csv
    private Map<String, String> getMemberDetails(int memberId, List<CSVRecord> membersRecords) {
        return membersRecords.stream()
                .filter(record -> Integer.parseInt(record.get("Member Id")) == memberId)
                .findFirst()
                .map(record -> {
                    Map<String, String> details = new HashMap<>();
                    details.put("Full name", record.get("Full name"));
                    details.put("E-mail address", record.get("E-mail address"));
                    details.put("Is Active", record.get("Is Active"));
                    return details;
                })
                .orElseThrow(() -> new IllegalArgumentException("Member ID " + memberId + " not found in the Members CSV"));
    }
}