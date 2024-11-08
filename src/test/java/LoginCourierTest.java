import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class LoginCourierTest extends BaseURI{

    private final CourierSteps steps = new CourierSteps();
    private Integer courierId;
    private String login = "wowowow";
    private String password = "1234";
    private String firstName = "anne";

    @After
    public void tearDown() {

        if (courierId != null) {
            steps.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Успешная авторизация курьера с корректными данными и получение id в ответе")
    public void testSuccessfulAuthorization() {
        steps.createCourier(login, password, firstName);

        Response response = steps.loginCourier(login, password);

        response.then()
                .statusCode(200)
                .body("id", notNullValue());
        courierId = response.jsonPath().getInt("id");
    }

    @Test
    @DisplayName("Ошибка при авторизации с неверным логином")
    public void testAuthorizationWithInvalidLogin() {
        steps.createCourier(login, password, firstName);

        Response response = steps.loginCourier("invalidLogin", password);

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Ошибка при авторизации с неверным паролем")
    public void testAuthorizationWithInvalidPassword() {
        steps.createCourier(login, password, firstName);

        Response response = steps.loginCourier(login, "wrongPassword");

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Ошибка при авторизации без логина")
    public void testAuthorizationWithoutLogin() {
        steps.createCourier(login, password, firstName);

        Response response = steps.loginCourier(null, password);

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Ошибка при авторизации без пароля")
    public void testAuthorizationWithoutPassword() {
        steps.createCourier(login, password, firstName);

        Response response = steps.loginCourier(login, null);

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
