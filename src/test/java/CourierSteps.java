import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    String CREATE_COURIER_URL = "/api/v1/courier";
    String LOGIN_COURIER_URL = "/api/v1/courier/login";



    @Step("Создать курьера с параметрами: login={login}, password={password}, firstName={firstName}")
    public Response createCourier(String login, String password, String firstName) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("login", login);
        requestBody.put("password", password);
        requestBody.put("firstName", firstName);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(CREATE_COURIER_URL);

        return response;
    }

    @Step("Удалить курьера по ID")
    public void deleteCourier(int courierId) {
        given()
                .header("Content-Type", "application/json")
                .when()
                .delete(CREATE_COURIER_URL + "/" + courierId)
                .then()
                .statusCode(200);
    }

    @Step("Авторизовать курьера с логином: {login} и паролем: {password}")
    public Response loginCourier(String login, String password) {
        Map<String, String> loginData = new HashMap<>();
        loginData.put("login", login);
        loginData.put("password", password);

        return given()
                .header("Content-Type", "application/json")
                .body(loginData)
                .when()
                .post(LOGIN_COURIER_URL);
    }



}
