import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateCourierTest {

    private Integer courierId;
    Steps step = new Steps();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            step.deleteCourier(courierId);
        }
    }

    @Test
    @Step("Проверка успешного создания курьера")
    public void testCreateCourier() {
        courierId = step.createCourier("meowmeow", "1234", "kitty");
    }

    @Test
    @Step("Проверка успешного ответа и поля ok: true при создании курьера")
    public void testCreateCourierResponseOk() {
        String login = "auuuuf";
        String password = "1234";
        String firstName = "doggy";

        courierId = step.createCourier(login, password, firstName);
        given()
                .header("Content-type", "application/json")
                .body("{\n" +
                        "    \"login\": \"" + login + "\",\n" +
                        "    \"password\": \"" + password + "\",\n" +
                        "    \"firstName\": \"" + firstName + "\"\n" +
                        "}")
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(200)
                .body("id", is(courierId));
    }

    @Test
    @Step("Проверка ошибки при создании курьера с уже существующим логином")
    public void testCannotCreateCourierWithSameLogin() {
        String login = "jayjayyy";
        String password = "1234";
        String firstName = "jackie";

        courierId = step.createCourier(login, password, firstName);
        given()
                .header("Content-type", "application/json")
                .body("{\n" +
                        "    \"login\": \"" + login + "\",\n" +
                        "    \"password\": \"" + password + "\",\n" +
                        "    \"firstName\": \"" + firstName + "\"\n" +
                        "}")
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @Step("Проверка ошибки при создании курьера без обязательного поля логина")
    public void testCreateCourierWithoutLogin() {
        given()
                .header("Content-type", "application/json")
                .body("{\n" +
                        "    \"password\": \"1234\",\n" +
                        "    \"firstName\": \"doggy\"\n" +
                        "}")
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Step("Проверка ошибки при создании курьера без обязательного поля пароля")
    public void testCreateCourierWithoutPassword(){
        given()
                .header("Content-type", "application/json")
                .body("{\n" +
                        "    \"login\": \"affff\",\n" +
                        "    \"firstName\": \"Boris\"\n" +
                        "}")
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Step("Проверка ошибки при создании курьера без обязательного поля имени")
    public void testCreateCourierWithoutFirstName(){

        Response response = given()
                .header("Content-type", "application/json")
                .body("{\n" +
                        "    \"login\": \"aufauf\",\n" +
                        "    \"password\": \"1234\"\n" +
                        "}")
                .when()
                .post("/api/v1/courier");

        response.then()
                .statusCode(409)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        }

    }





