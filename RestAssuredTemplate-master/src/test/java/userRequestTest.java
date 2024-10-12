import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Feature("User Module")
public class userRequestTest extends BaseTestUser {

    private String session; //Variable to store Session ID

    @Test(priority = 1) // create user with Array
    public void createWithArrayTest(){
        String requestBody = "[\n" +
                "  {\n" +
                "    \"id\": 1210,\n" +
                "    \"username\": \"Test01\",\n" +
                "    \"firstName\": \"Rheza\",\n" +
                "    \"lastName\": \"Aji\",\n" +
                "    \"email\": \"r.aji@mail.com\",\n" +
                "    \"password\": \"p455w0rd\",\n" +
                "    \"phone\": \"08123400001\",\n" +
                "    \"userStatus\": 1\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 1220,\n" +
                "    \"username\": \"Test02\",\n" +
                "    \"firstName\": \"Paramarta\",\n" +
                "    \"lastName\": \"Ajim\",\n" +
                "    \"email\": \"p.aji@mail.com\",\n" +
                "    \"password\": \"1nt3rn3t\",\n" +
                "    \"phone\": \"08123400002\",\n" +
                "    \"userStatus\": 1\n" +
                "  }\n" +
                "]";
        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .log().ifValidationFails()
                .when()
                .post("/createWithArray")
                .then()
                .statusCode(200);
    }

    @Test(priority = 2) // create user with List
    public void createWithListTest(){
        String requestBody = "[\n" +
                "  {\n" +
                "    \"id\": 1221,\n" +
                "    \"username\": \"Test03\",\n" +
                "    \"firstName\": \"Hazer\",\n" +
                "    \"lastName\": \"Maji\",\n" +
                "    \"email\": \"HMJZ@mail.com\",\n" +
                "    \"password\": \"5tr1ng\",\n" +
                "    \"phone\": \"081234000004\",\n" +
                "    \"userStatus\": 1\n" +
                "  }\n" +
                "]";
        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .log().ifValidationFails()
                .when()
                .post("/createWithList")
                .then()
                .statusCode(200);
    }

    @Test(priority = 3) // find user by username
    public void findUserByUsernameTest(){
        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/Test03")
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Test(priority = 4) // login user
    public void loginTest(){
        Response response = given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/login?username=Test01&password=p455w0rd")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        // Extract the session from the message
        String message = response.jsonPath().getString("message");
        session = message.split("session:")[1]; // Extract session ID from the message
        System.out.println("Session ID: " + session);
    }

    @Test(priority = 5) // update user
    public void updateUserTest() {
        String requestBody = "{\n" +
                "  \"id\": 1210,\n" +
                "  \"username\": \"Test01\",\n" +
                "  \"firstName\": \"Mheza\",\n" +
                "  \"lastName\": \"Aji\",\n" +
                "  \"email\": \"r.aji@mail.com\",\n" +
                "  \"password\": \"p455w0rd\",\n" +
                "  \"phone\": \"08123400001\",\n" +
                "  \"userStatus\": 1\n" +
                "}";
        given().header("session",session)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .log().ifValidationFails()
                .when()
                .put("/firstName")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();
    }

    @Test(priority = 6) // delete user
    public void deleteUserTest(){
        given()
                .header("session",session)
                .when()
                .delete("/Test03")
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Test(priority = 7) // create user after login
    public void createUserTest(){
        String requestBody = "{\n" +
                "  \"id\": 1400,\n" +
                "  \"username\": \"Test04\",\n" +
                "  \"firstName\": \"Emma\",\n" +
                "  \"lastName\": \"Karnakov\",\n" +
                "  \"email\": \"test04@UCS.go.ru\",\n" +
                "  \"password\": \"scrambler01\",\n" +
                "  \"phone\": \"000000000\",\n" +
                "  \"userStatus\": 9\n" +
                "}";
        given()
                .header("session",session)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .log().ifValidationFails()
                .when()
                .post()
                .then()
                .statusCode(200);
    }

    @Test(priority = 8) // logout
    public void logoutTest(){
        given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/logout")
                .then()
                .statusCode(200)
                .extract().response();
    }

}