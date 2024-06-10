package helpers.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourierChecks {

    @Step("Check courier login successfully")
    public int loggedInSuccessfully(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("id");
        return id;
    }

    @Step("Check courier created successfully")
    public void createdSuccessfully(ValidatableResponse createResponse) {
        boolean created = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("ok");
        assertTrue(created);
    }

    @Step("Check courier deleted successfully")
    public void deletedSuccesfully(ValidatableResponse deleteResponse) {
        boolean deleted = deleteResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("ok");
        assertTrue(deleted);
    }

    @Step("Check courier created with same login")
    public void createdSameLogin(ValidatableResponse createResponse,String expectedMessage) {
        String actualMessage = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .extract()
                .path("message");
        assertEquals(expectedMessage,actualMessage);
    }

    @Step("Check courier created without required fields")
    public void createdWithoutRequiredFields(ValidatableResponse createResponse,String expectedMessage) {
        String actualMessage = createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .extract()
                .path("message");
        assertEquals(expectedMessage,actualMessage);
    }

    @Step("Check courier login without required fields")
    public void loggedInWithoutRequiredFields(ValidatableResponse badLoginResponse,String expectedMessage) {
        String actualMessage = badLoginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .extract()
                .path("message");
        assertEquals(expectedMessage,actualMessage);
    }

    @Step("Check courier login non existent")
    public void loggedNonExistent(ValidatableResponse loginResponse, String expectedMessage) {
        String actualMessage = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .extract()
                .path("message");
        assertEquals(expectedMessage,actualMessage);
    }
}
