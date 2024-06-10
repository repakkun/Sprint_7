package helpers.courier;

import helpers.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.courier.CourierCreate;
import pojo.courier.CourierLogin;
import java.util.Map;

public class CourierClient extends Client {
    private static final String COURIER_PATH = "/courier";

    @Step("Login courier request")
    public ValidatableResponse loginCourier(CourierLogin creds) {
        return spec()
                .body(creds)
                .when()
                .post(COURIER_PATH + "/login")
                .then().log().all();
    }

    @Step("Create courier request")
    public ValidatableResponse createCourier(CourierCreate courierCreate) {
        return spec()
                .body(courierCreate)
                .when()
                .post(COURIER_PATH)
                .then().log().all();
    }

    @Step("Delete courier request")
    public ValidatableResponse deleteCourier(int id) {
        return spec()
                .body(Map.of("id", id))
                .when()
                .delete(COURIER_PATH + "/" + id)
                .then().log().all();
    }
}
