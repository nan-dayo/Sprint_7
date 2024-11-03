import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;


@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseURI{

    private final List<String> colors;
    OrderSteps steps = new OrderSteps();

    public CreateOrderTest(List<String> colors){
        this.colors = colors;
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
    @DisplayName("Проверка создания заказа с цветами: {color}")
    public void testCreateOrder() {
        String firstName = "Dean";
        String lastName = "Winchester";
        String address = "San Jose, 525";
        int metroStation = 4;
        String phone = "+7 911 0123 45 67";
        int rentTime = 5;
        String deliveryDate = "2024-11-06";
        String comment = "Marshmallow nachos";

        Response response = steps.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colors);

        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }

}
