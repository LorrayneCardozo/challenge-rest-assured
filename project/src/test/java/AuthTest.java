import Entities.Booking;
import Entities.BookingDates;
import Entities.User;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;

public class AuthTest {
    @BeforeAll
    public static void Setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    // Auth - CreateToken
    // https://restful-booker.herokuapp.com/auth
    @Test
    void createToken_returnOk() {
        given()
                .config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON)
                .when()
                    .body("{ \"username\" : \"admin\", \"password\" : \"password123\"}")
                    .post("/auth")
                .then()
                    .assertThat().statusCode(200);
    }

}
