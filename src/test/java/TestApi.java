import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.given;


public class TestApi {



    @Test
    public void get_UserToken() {
      String uri = "https://x-clients-be.onrender.com/";
        Map<String, String> request = new HashMap<>();
        request.put("username", "leonardo");
        request.put("password", "leads");
        String userToken = given().contentType("application/json")
               .body(request)
                .when()
                .post(uri + "auth/login")
                .then().log().body()
               .extract()
               .response().jsonPath().get("userToken");


    }
    @Test
    public void create_Company(){
        String uri = "https://x-clients-be.onrender.com/";
        String companyName = "Кофемания";
        String companyDescription = "Кофейня";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiYWRtaW4iLCJpYXQiOjE2OTIxODYyMTAsImV4cCI6MTY5MjE4NzExMH0.cbJ_kNHfLMQJBOzBwT5PKyun7mOAWCQfD6vA6T0M2Lg";

        given()
                .header("x-client-token", token)
                .contentType("application/json; charset=utf-8")
                .body("{\"name\": \"" + companyName + "\",\"description\": \"" + companyDescription + "\"}")
                .when()
                .post(uri + "company")
                .then()
                .statusCode(201)
                .contentType("application/json; charset=utf-8")
                .extract().path("id");

    }

    @Test
    public void delete_Company(){

    }




}


