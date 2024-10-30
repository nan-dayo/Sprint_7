import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;


@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final List<String> color;
    Steps step = new Steps();

    public CreateOrderTest(List<String> color){
        this.color = color;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters(name = "Цвет(а) самоката: {0}")
    public static Object[][] getColorOptions(){
        return new Object[][] {
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()}
        };
    }

    @Test
    @Step("Проверка создания заказа с цветами: {color}")
    public void testCreateOrder() {
        Response response = step.createOrder(color);

        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }

}
