package integration;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kainos.ea.model.Product;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(MockitoExtension.class)
public class
ProductIntegrationIT {

    private final Product badProductData = new Product(
            "very long description very long description very long description very long description very long description very long description",
            "bad product",
            159);


    private Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    @Test
    void When_ProductPriceLessThan10_Expect_MessageReturned() {
        String apiUrl = dotenv.get("API_URL", "http://localhost:8080");
        given()
                .contentType("application/json")
                .get(apiUrl + "api/products/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("description", equalTo("One Description"));
    }

    @Test
    void When_ProductNameIsTooLength_Expect_ErrorMessageReturned() {
        String apiUrl = dotenv.get("API_URL", "http://localhost:8080");
        given()
                .with()
                .body(badProductData)
                .contentType("application/json")
                .post(apiUrl + "api/products/")
                .then()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Name greater than 75 characters"));
    }
}
