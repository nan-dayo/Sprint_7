import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class CourierSteps {

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



}
