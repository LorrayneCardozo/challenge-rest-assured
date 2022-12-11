import Entities.Booking;
import Entities.BookingDates;
import Entities.User;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

public class BookingTest {
    public static Faker faker;
    private static RequestSpecification request;
    private static Booking booking;
    private static BookingDates bookingDates;
    private static User user;
    @BeforeAll
    public static void Setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        faker = new Faker();
        user = new User(faker.name().username(),
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.internet().safeEmailAddress(),
                        faker.internet().password(),
                        faker.phoneNumber().toString());

        bookingDates = new BookingDates(LocalDate.of(2018,02,01), LocalDate.of(2018,03,01));

        booking = new Booking((user.getFirstname()), user.getLastname(), (float)faker.number().randomDouble(2,50,1000), true, bookingDates, "");

        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
    }

    @BeforeEach
    void setRequest() {
        request = given()
                .config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON)
                .auth().basic("admin", "password123");
    }

    @Test
    void getAllBookingsById_returnOk(){
        Response response = request
                                    .when()
                                        .get("/booking")
                                    .then()
                                        .extract().response();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void  getAllBookingsByUserFirstName_BookingExists_returnOk(){
        request
                .when()
                        .queryParam("firstName", "Carol")
                        .get("/booking")
                .then()
                        .assertThat()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                .and()
                        .body("results", hasSize(greaterThan(0)));
    }
}
