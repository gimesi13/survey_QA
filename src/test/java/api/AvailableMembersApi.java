package api;

import io.restassured.response.Response;
import org.apache.commons.csv.CSVRecord;
import utils.ApiCallHelper;
import utils.CsvHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class AvailableMembersApi extends BaseApi {

    private static final Logger logger = LoggerFactory.getLogger(AvailableMembersApi.class);
    private static final String MEMBERS_CSV_PATH = "src/test/java/resources/csv/Members.csv";
    private static final String PARTICIPATION_CSV_PATH = "src/test/java/resources/csv/Participation.csv";

    // Validate available members for the given attribute (id, name, email, or status)
    public void validateAvailableMembers(String attribute, int finalSurveyId, int step) {
        logger.info("Starting validation for attribute: {}", attribute);

        for (int surveyId = 1; surveyId <= finalSurveyId; surveyId+= step) {
            String surveyIdStr = String.valueOf(surveyId);
            List<String> availableData = fetchAvailableData(attribute, surveyIdStr);

            logger.info("Validating members for Survey ID: {}", surveyIdStr);

            switch (attribute) {
                case "id":
                    validateAvailableIds(availableData, surveyIdStr);
                    break;
                case "name":
                    validateAvailableNames(availableData);
                    break;
                case "email":
                    validateAvailableEmails(availableData);
                    break;
                case "status":
                    validateAvailableStatuses(surveyIdStr);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown attribute: " + attribute);
            }
        }
    }

    // Validate that all IDs are correct (not in Participation CSV and are in Members CSV)
    private void validateAvailableIds(List<String> availableIds, String surveyId) {
        Set<String> participationIds = getParticipationIdsForSurvey(surveyId);
        Set<String> activeMemberIds = getActiveMemberIds();

        // Check that the available members do not include any who have already participated
        for (String id : availableIds) {
            String stringId = String.valueOf(id);

            if (participationIds.contains(stringId)) {
                logger.error("ID {} should not be in the participation list for survey {}", stringId, surveyId);
                throw new AssertionError("ID " + stringId + " should not be in the participation list for survey " + surveyId);
            }
            if (!activeMemberIds.contains(stringId)) {
                logger.error("ID {} is not an active member in the Members CSV", stringId);
                throw new AssertionError("ID " + stringId + " is not an active member in the Members CSV");
            }
        }

        // Check if any active member who has NOT participated in the survey is missing from the response
        Set<String> nonParticipatingActiveMembers = activeMemberIds.stream()
                .filter(id -> !participationIds.contains(id))
                .collect(Collectors.toSet());

        // If a non-participating active member is missing from the available API response, that's an error
        for (String nonParticipatingId : nonParticipatingActiveMembers) {
            if (!availableIds.contains(nonParticipatingId)) {
                logger.error("Active member ID {} has not participated in survey {} but is missing from the available API response", nonParticipatingId, surveyId);
                throw new AssertionError("Active member ID " + nonParticipatingId + " has not participated in survey " + surveyId + " but is missing from the available API response");
            }
        }
    }

    // Validate that all names are present in Members CSV
    private void validateAvailableNames(List<String> availableNames) {
        Set<String> memberNames = getMemberNames();

        for (String name : availableNames) {
            if (!memberNames.contains(name)) {
                logger.error("Name {} is not present in the Members CSV file", name);
                throw new AssertionError("Name " + name + " is not present in the Members CSV file");
            }
        }
    }

    // Validate that all emails are present in Members CSV
    private void validateAvailableEmails(List<String> availableEmails) {
        Set<String> memberEmails = getMemberEmails();

        for (String email : availableEmails) {
            if (!memberEmails.contains(email)) {
                logger.error("Email {} is not present in the Members CSV file", email);
                throw new AssertionError("Email " + email + " is not present in the Members CSV file");
            }
        }
    }

    // Validate that all statuses are active
    private void validateAvailableStatuses(String surveyId) {
        List<String> ids = getAvailableMemberIds(surveyId);

        // Fetch the participation and member data from CSV
        Set<String> participationIds = getParticipationIdsForSurvey(surveyId);
        List<CSVRecord> membersData = CsvHelper.readCsv(MEMBERS_CSV_PATH);

        // Iterate through each available ID in the API response
        for (String id : ids) {
            String stringId = String.valueOf(id);

            // Check if the member is active in the Members CSV
            boolean isActive = false;
            boolean hasParticipated = participationIds.contains(stringId);

            for (CSVRecord member : membersData) {
                if (member.get("Member Id").equals(stringId)) {
                    isActive = member.get("Is Active").equals("1");
                    break;
                }
            }

            if (!isActive) {
                logger.error("Member ID {} is not active (Is Active = 1) in the Members CSV", stringId);
                throw new AssertionError("Member ID " + stringId + " is not active (Is Active = 1) in the Members CSV");
            }

            // Check if the active member has already participated in the given survey
            if (hasParticipated) {
                logger.error("Member ID {} is active but has already participated in the survey {}", stringId, surveyId);
                throw new AssertionError("Member ID " + stringId + " is active but has already participated in the survey " + surveyId);
            }
        }
    }

    // Fetch available data for the given attribute
    private List<String> fetchAvailableData(String attribute, String surveyId) {
        switch (attribute) {
            case "id":
                return getAvailableMemberIds(surveyId);
            case "name":
                return getAvailableMemberNames(surveyId);
            case "email":
                return getAvailableMemberEmails(surveyId);
            case "status":
                return getAvailableMemberStatuses(surveyId);
            default:
                throw new IllegalArgumentException("Unknown attribute: " + attribute);
        }
    }

    // Fetch available members for the given survey ID
    private Response getAvailableMembers(String surveyId) {
        String endpoint = "/surveys/" + surveyId + "/members/not-invited";
        Response response = ApiCallHelper.get(endpoint);
        logger.info("Received response for survey ID {}: Status Code = {}", surveyId, response.getStatusCode());
        return response;
    }

    // Fetch available member IDs from the API response
    private List<String> getAvailableMemberIds(String surveyId) {
        Response response = getAvailableMembers(surveyId);
        List<?> availableIds = response.jsonPath().getList("id");
        List<String> availableIdsAsStrings = availableIds.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        logger.info("Converted Available IDs for survey ID {}: {}", surveyId, availableIdsAsStrings);
        return availableIdsAsStrings;
    }


    // Fetch available member names from the API response
    private List<String> getAvailableMemberNames(String surveyId) {
        Response response = getAvailableMembers(surveyId);
        List<String> availableNames = response.jsonPath().getList("name");
        logger.info("Available names for survey ID {}: {}", surveyId, availableNames);
        return availableNames;
    }

    // Fetch available member emails from the API response
    private List<String> getAvailableMemberEmails(String surveyId) {
        Response response = getAvailableMembers(surveyId);
        List<String> availableEmails = response.jsonPath().getList("emailAddress");
        logger.info("Available emails for survey ID {}: {}", surveyId, availableEmails);
        return availableEmails;
    }

    // Fetch available member statuses from the API response
    private List<String> getAvailableMemberStatuses(String surveyId) {
        Response response = getAvailableMembers(surveyId);
        List<String> availableStatuses = response.jsonPath().getList("active");
        logger.info("Available statuses for survey ID {}: {}", surveyId, availableStatuses);
        return availableStatuses;
    }

    // Helper methods to read CSV data
    private Set<String> getParticipationIdsForSurvey(String surveyId) {
        return CsvHelper.readCsv(PARTICIPATION_CSV_PATH).stream()
                .filter(record -> record.get("Survey Id").equals(surveyId))
                .map(record -> record.get("Member Id"))
                .collect(Collectors.toSet());
    }

    private Set<String> getMemberNames() {
        return CsvHelper.readCsv(MEMBERS_CSV_PATH).stream()
                .map(record -> record.get("Full name"))
                .collect(Collectors.toSet());
    }

    private Set<String> getMemberEmails() {
        return CsvHelper.readCsv(MEMBERS_CSV_PATH).stream()
                .map(record -> record.get("E-mail address"))
                .collect(Collectors.toSet());
    }

    private Set<String> getActiveMemberIds() {
        return CsvHelper.readCsv(MEMBERS_CSV_PATH).stream()
                .filter(record -> "1".equals(record.get("Is Active")))
                .map(record -> record.get("Member Id"))
                .collect(Collectors.toSet());
    }
}