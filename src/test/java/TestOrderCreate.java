import com.github.javafaker.Faker;
import helpers.order.*;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.order.OrderCreate;

import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class TestOrderCreate {

    private final OrderClient client = new OrderClient();
    private final OrderChecks check = new OrderChecks();

    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;
    static Faker faker = new Faker();

    public TestOrderCreate(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }


    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {"имя_" + faker.name().firstName(), "фамилия_" + faker.name().lastName(), "адрес_" + faker.address().cityName(), 0, faker.phoneNumber().phoneNumber(), 1, "2024-06-10", "комментарий_" + faker.harryPotter().quote(), new String[]{"BLACK"}},
                {"имя_" + faker.name().firstName(), "фамилия_" + faker.name().lastName(), "адрес_" + faker.address().cityName(), 1, faker.phoneNumber().phoneNumber(), 2, "2024-06-11", "комментарий_" + faker.witcher().quote(), new String[]{"GRAY"}},
                {"имя_" + faker.name().firstName(), "фамилия_" + faker.name().lastName(), "адрес_" + faker.address().cityName(), 2, faker.phoneNumber().phoneNumber(), 3, "2024-06-12", "комментарий_" + faker.zelda().character(), new String[]{"BLACK", "GRAY"}},
                {"имя_" + faker.name().firstName(), "фамилия_" + faker.name().lastName(), "адрес_" + faker.address().cityName(), 3, faker.phoneNumber().phoneNumber(), 4, "2024-06-13", "комментарий_" + faker.yoda().quote(), new String[]{}}
        };
    }

    @Test
    public void createOrderTest(){
        OrderCreate orderCreate = new OrderCreate(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse createResponse=client.createOrder(orderCreate);
        int orderTrack=check.createSuccessOrder(createResponse);
        assertNotEquals(0, orderTrack);
    }
}
