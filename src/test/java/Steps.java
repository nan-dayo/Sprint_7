import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class Steps {

    @Step("Создать курьера с параметрами: login={login}, password={password}, firstName={firstName}")
    public Integer createCourier(String login, String password, String firstName) {
        String requestBody = "{\n" +
                "    \"login\": \"" + login + "\",\n" +
                "    \"password\": \"" + password + "\",\n" +
                "    \"firstName\": \"" + firstName + "\"\n" +
                "}";

        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier");

        response.then().statusCode(201).body("ok", is(true));

        Response loginResponse = loginCourier(login, password);
        return loginResponse.jsonPath().getInt("id");
    }

    @Step("Удалить курьера с ID={courierId}")
    public void deleteCourier(int courierId) {
        given()
                .when()
                .delete("/api/v1/courier/" + courierId)
                .then()
                .statusCode(200);
    }

    @Step("Войти как курьер с логином={login} и паролем={password}")
    public Response loginCourier(String login, String password) {
        String loginRequest = "{\n" +
                "    \"login\": \"" + login + "\",\n" +
                "    \"password\": \"" + password + "\"\n" +
                "}";

        return given()
                .header("Content-type", "application/json")
                .body(loginRequest)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Создать заказ")
    public Response createOrder(List<String> color) {

        String colorJson = new Gson().toJson(color);

        String requestBody = "{\n" +
                "    \"firstName\": \"Dean\",\n" +
                "    \"lastName\": \"Winchester\",\n" +
                "    \"address\": \"San Jose, 525\",\n" +
                "    \"metroStation\": 4,\n" +
                "    \"phone\": \"+7 911 0123 45 67\",\n" +
                "    \"rentTime\": 5,\n" +
                "    \"deliveryDate\": \"2024-11-06\",\n" +
                "    \"comment\": \"Marshmallow nachos\",\n" +
                "    \"color\": " + colorJson + "\n" +
                "}";

        return given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Получение списка заказов")
    public Response getOrders(){
        return given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders");
    }

}
