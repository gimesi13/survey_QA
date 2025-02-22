package tests.integration;

import api.AvailableMembersApi;
import org.junit.jupiter.api.Test;

public class AvailableMembersTest extends AvailableMembersApi {

    @Test
    void testAvailableIds() {
        validateAvailableMembers("id");
    }

    @Test
    void testAvailableNames() {
        validateAvailableMembers("name");
    }

    @Test
    void testAvailableEmails() {
        validateAvailableMembers("email");
    }

    @Test
    void testAvailableStatuses() {
        validateAvailableMembers("status");
    }
}
