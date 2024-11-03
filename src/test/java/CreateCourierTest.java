import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class CreateCourierTest extends BaseURI{

    Integer courierId;

    private final CourierSteps steps = new CourierSteps();

    @After
    public void tearDown() {
        if (courierId != null) {
            steps.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Успешное создание курьера и получение ok: true в ответе")
    public void testCreateCourierSuccess() {
        String login = "auuuuf"; // Уникальный логин
        String password = "1234";
        String firstName = "doggy";


        Response response = steps.createCourier(login, password, firstName);
        response.then().statusCode(201).body("ok", is(true));

        Response loginResponse = steps.loginCourier(login, password);
        courierId = loginResponse.jsonPath().getInt("id");
    }


    @Test
    @DisplayName("Ошибка при создании курьера с существующим логином")
    public void testCreateCourierDuplicateLogin() {
        String login = "jayjayyy";
        String password = "1234";
        String firstName = "jackie";

        steps.createCourier(login, password, firstName);

        Response loginResponse = steps.loginCourier(login, password);
        courierId = loginResponse.jsonPath().getInt("id");

        Response secondResponse = steps.createCourier(login, password, firstName);
        secondResponse.then()
                .statusCode(409) // Ожидаем статус 409
                .body("message", equalTo("Этот логин уже используется"));
    }


    @Test
    @DisplayName("Проверка ошибки при создании курьера без обязательного поля логина")
    public void testCreateCourierWithoutLogin() {
        String login = null;
        String password = "1234";
        String firstName = "doggy";

        Response response = steps.createCourier(login, password, firstName);

        response
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка ошибки при создании курьера без обязательного поля пароля")
    public void testCreateCourierWithoutPassword(){
        String login = "affff";
        String password = null;
        String firstName = "Boris";

        Response response = steps.createCourier(login, password, firstName);

        response
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка ошибки при создании курьера без обязательного поля имени")
    public void testCreateCourierWithoutFirstName(){

        String login = "aufauf";
        String password = "1234";
        String firstName = "null";

        Response response = steps.createCourier(login, password, firstName);

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    }





