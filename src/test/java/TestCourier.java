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

public class TestCourier {

    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    int courierId;

    @Test
    @DisplayName("Создание курьера")
    public void courierCreateTest() {
        CourierCreate courier = CourierCreate.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        CourierLogin creds = CourierLogin.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Создание одинаковых")
    public void courierTwoIdenticalTest(){
        CourierCreate courier = CourierCreate.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);
        createResponse = client.createCourier(courier);
        check.createdSameLogin(createResponse, DUBLICATE_LOGIN);

        CourierLogin creds = CourierLogin.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Создание только с логином")
    public void courierCreateWithOnlyLoginTest() {
        CourierCreate courier = new CourierCreate();
        courier.setLogin(RandomStringUtils.randomAlphabetic(5, 15));
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdWithoutRequiredFields(createResponse, NOT_CREATE_LOGIN_OR_PASSWORD);
    }

    @Test
    @DisplayName("Создание только с паролем")
    public void courierCreateWithOnlyPasswordTest() {
        CourierCreate courier = new CourierCreate();
        courier.setPassword(RandomStringUtils.randomAlphabetic(5, 15));
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdWithoutRequiredFields(createResponse, NOT_CREATE_LOGIN_OR_PASSWORD);
    }

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            ValidatableResponse response = client.deleteCourier(courierId);
            check.deletedSuccesfully(response);
        }
    }
}