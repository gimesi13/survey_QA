package tests.integration;

import api.AvailableMembersApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AvailableMembersTest {

    private static AvailableMembersApi availableMembersApi;

    @BeforeAll
    static void setup() {
        availableMembersApi = new AvailableMembersApi();
    }

    @Test
    void testAvailableIds() {
        availableMembersApi.validateAvailableMembers("id");
    }

    @Test
    void testAvailableNames() {
        availableMembersApi.validateAvailableMembers("name");
    }

    @Test
    void testAvailableEmails() {
        availableMembersApi.validateAvailableMembers("email");
    }

    @Test
    void testAvailableStatuses() {
        availableMembersApi.validateAvailableMembers("status");
    }
}