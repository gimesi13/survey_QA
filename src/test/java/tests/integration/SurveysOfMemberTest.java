package tests.integration;

import api.SurveysOfMemberApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SurveysOfMemberTest {
    private static SurveysOfMemberApi surveysOfMemberApi;

    @BeforeAll
    static void setup() {
        surveysOfMemberApi = new SurveysOfMemberApi();
    }

    @Test
    void validateMemberSurveyData() {
        surveysOfMemberApi.validateMemberSurveyData();
    }
}