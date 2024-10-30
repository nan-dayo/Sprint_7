import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class LoginCourierTest {

    private Integer courierId;
    Steps step = new Steps();

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        courierId = step.createCourier("starrr", "1234", "name");
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            step.deleteCourier(courierId);
        }
    }


    @Test
    @Step("Проверка успешной авторизации в системе и получения id курьера в ответе")
    public void testLoginCourierReturnsId() {
        Response response = step.loginCourier("starrr", "1234");

        response.then()
                .statusCode(200)
                .body("id", is(courierId));

    }

    @Test
    @Step("Проверка ошибки при авторизации без логина")
    public void testLoginWithoutLogin(){
        given()
                .header("Content-type", "application/json")
                .body("{\"password\": \"1234\"}")
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Step("Проверка ошибки при авторизации без пароля")
    public void testLoginWithoutPassword(){
        given()
                .header("Content-type", "application/json")
                .body("{\"login\": \"starrrr\"}")
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Step("Проверка ошибки при неправильном логине")
    public void testLoginWithInvalidLogin() {
        given()
                .header("Content-type", "application/json")
                .body("{\"login\": \"sssstarrrr\", \"password\": \"1234\"}")
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @Step("Проверка ошибки при неправильном пароле")
    public void testLoginWithInvalidPassword() {
        given()
                .header("Content-type", "application/json")
                .body("{\"login\": \"starrr\", \"password\": \"4321\"}")
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
