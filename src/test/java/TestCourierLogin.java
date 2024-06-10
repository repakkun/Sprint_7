import helpers.courier.CourierChecks;
import helpers.courier.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import pojo.courier.CourierCreate;
import pojo.courier.CourierLogin;

import static constants.Messаges.*;
import static org.junit.Assert.assertNotEquals;

public class TestCourierLogin {
    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    int courierId;

    @Test
    @DisplayName("Проверка авторизации")
    public void courierАuthorizationTest(){
        CourierCreate courier = CourierCreate.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        CourierLogin creds = CourierLogin.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Проверка только с логином")
    public void courierLoginWithOnlyLoginTest() {

        CourierCreate courier = CourierCreate.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        CourierLogin badCreds = CourierLogin.from(courier);
        badCreds.setPassword("");
        ValidatableResponse badLoginResponse = client.loginCourier(badCreds);
        check.loggedInWithoutRequiredFields(badLoginResponse, NOT_LOGIN_OR_PASSWORD);

        CourierLogin creds = CourierLogin.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Проверка только с паролем")
    public void courierLoginWithOnlyPasswordTest() {

        CourierCreate courier = CourierCreate.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        CourierLogin badCreds = CourierLogin.from(courier);
        badCreds.setLogin("");
        ValidatableResponse badLoginResponse = client.loginCourier(badCreds);
        check.loggedInWithoutRequiredFields(badLoginResponse, NOT_LOGIN_OR_PASSWORD);

        CourierLogin creds = CourierLogin.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Несуществующий логин")
    public void courierLoginNonExistent(){
        CourierCreate courier = CourierCreate.random();
        CourierLogin creds = CourierLogin.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        check.loggedNonExistent(loginResponse, NON_EXISTENT_LOGIN_OR_PASSWORD);
    }

    @Test
    @DisplayName("Несуществующий пароль")
    public void courierPasswordNonExistent(){
        CourierCreate courier = CourierCreate.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        CourierLogin badCreds = CourierLogin.from(courier);
        badCreds.setPassword(RandomStringUtils.randomAlphabetic(5, 15));
        ValidatableResponse badLoginResponse = client.loginCourier(badCreds);
        check.loggedNonExistent(badLoginResponse, NON_EXISTENT_LOGIN_OR_PASSWORD);

        CourierLogin creds = CourierLogin.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assertNotEquals(0, courierId);
    }

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            ValidatableResponse response = client.deleteCourier(courierId);
            check.deletedSuccesfully(response);
        }
    }
}
