import helpers.order.OrderChecks;
import helpers.order.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import pojo.order.Order;
import java.util.List;

import static org.junit.Assert.assertNotEquals;

public class TestOrderList {
    private final OrderClient client = new OrderClient();
    private final OrderChecks check = new OrderChecks();

    @Test
    @DisplayName("Получения списка")
    public void getListOfOrdersTest(){
        ValidatableResponse getOrdersResponse= client.getOrders();
        List<Order> orders = check.checkSuccessGetOrders(getOrdersResponse);
        assertNotEquals(0, orders.size());
    }
}
