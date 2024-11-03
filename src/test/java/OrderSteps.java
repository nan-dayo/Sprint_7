import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    String CREATE_ORDER_URL = "/api/v1/orders";

    @Step("Создать заказ с параметрами: firstName={firstName}, lastName={lastName}, address={address}, metroStation={metroStation}, " +
            "phone={phone}, rentTime={rentTime}, deliveryDate={deliveryDate}, comment={comment}, color={color}")
    public Response createOrder(String firstName, String lastName, String address, int metroStation,
                                String phone, int rentTime, String deliveryDate, String comment, List<String> colors) {

        Map<String, Object> orderDetails = new HashMap<>();
        orderDetails.put("firstName", firstName);
        orderDetails.put("lastName", lastName);
        orderDetails.put("address", address);
        orderDetails.put("metroStation", metroStation);
        orderDetails.put("phone", phone);
        orderDetails.put("rentTime", rentTime);
        orderDetails.put("deliveryDate", deliveryDate);
        orderDetails.put("comment", comment);
        orderDetails.put("color", colors);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(orderDetails) // Передаем тело запроса
                .when()
                .post(CREATE_ORDER_URL);

        response.then().statusCode(201);
        return response;
    }


    @Step("Получение списка заказов")
    public Response getOrders(){
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(CREATE_ORDER_URL);
    }
}
