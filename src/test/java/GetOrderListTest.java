import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.Matchers.*;


public class GetOrderListTest extends BaseURI{

    OrderSteps step = new OrderSteps();

    @Test
    @DisplayName("Проверка списка заказов")
    public void testGetOrders() {
        Response response = step.getOrders();

        response.then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders", hasSize(notNullValue()));
    }

}
