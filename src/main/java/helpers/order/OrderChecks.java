package helpers.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.order.Order;
import java.net.HttpURLConnection;
import java.util.List;

public class OrderChecks {

    @Step("Check order create successfully")
    public int createSuccessOrder(ValidatableResponse createResponse) {
        return createResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("track");

    }

    @Step("Check orders get")
    public List<Order> checkSuccessGetOrders(ValidatableResponse getOrdersResponse){
         return  getOrdersResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("orders");
    }
}
