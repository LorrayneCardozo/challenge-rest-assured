import Entities.Booking;
import Entities.BookingDates;
import Entities.User;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
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
    @Order(2)
    void getAllBookingsByUserFirstName_BookingExists_returnOk(){
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

    @Test
    @Order(3)
    void createBooking_WithValidData_returnOk(){

        given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON)
                .when()
                        .body(booking)
                        .post("/booking")
                .then()
                        .body(matchesJsonSchemaInClasspath("createBookingRequestSchema.json"))
                .and()
                        .assertThat()
                        .statusCode(200)
                        .contentType(ContentType.JSON).and().time(lessThan(5000L));
    }

    @Test
    @Order(4)
    void getBooking_InvalidId_returnFail(){
        Response response = request
                                    .when()
                                        .get("/booking/-1")
                                    .then()
                                        .extract().response();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    @Order(5)
    void createToken_returnOk() {
        given()
                    .config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                    .contentType(ContentType.JSON)
                .when()
                    .body("{ \"username\" : \"admin\", \"password\" : \"password123\"}")
                    .post("/auth")
                .then()
                    .assertThat()
                    .statusCode(200);
    }

    @Test
    @Order(6)
    void updateBooking_returnOk(){
        int idBooking = request
                .when()
                    .body(booking)
                    .post("/booking")
                .then()
                    .extract().response().jsonPath().get("bookingid");

        user.setFirstname("Lorrayne");
        booking.setFirstname(user.getFirstname());

        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("admin");
        authScheme.setPassword("password123");
        RestAssured.authentication = authScheme;

        given()
                .config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON)
                .when()
                    .body(booking)
                    .put("/booking/"+idBooking)
                .then()
                    .assertThat().statusCode(200);
    }

    @Test
    @Order(7)
    void partialUpdateBooking_returnOk(){
        int idBooking = request
                .when()
                    .body(booking)
                    .post("/booking")
                .then()
                    .extract().response().jsonPath().get("bookingid");

        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("admin");
        authScheme.setPassword("password123");
        RestAssured.authentication = authScheme;

        given()
                .config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON)
                .when()
                    .body("{\n" +
                            "    \"firstname\" : \"Lorrayne\",\n" +
                            "    \"lastname\" : \"Cardozo\"\n" +
                            "}")
                    .patch("/booking/"+idBooking)
                .then()
                    .assertThat().statusCode(200);
    }
}
