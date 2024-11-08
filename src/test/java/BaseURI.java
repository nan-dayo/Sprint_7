import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class BaseURI {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

}
