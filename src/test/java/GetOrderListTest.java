import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class GetOrderListTest {

    Steps step = new Steps();

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Step("Проверка списка заказов")
    public void testGetOrders() {
        Response response = step.getOrders();

        response.then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders", hasSize(notNullValue()));
    }

}
