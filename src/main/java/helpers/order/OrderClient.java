package helpers.order;

import helpers.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.order.OrderCreate;

public class OrderClient extends Client {

    @Step("Create order request")
    public ValidatableResponse createOrder(OrderCreate creds) {
        return spec()
                .body(creds)
                .when()
                .post("orders")
                .then().log().all();
    }

    @Step("Get orders request")
    public ValidatableResponse getOrders() {
        return spec()
                .when()
                .get("orders")
                .then().log().all();
    }
}