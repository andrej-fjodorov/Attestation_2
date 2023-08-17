import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static io.restassured.RestAssured.given;


public class TestApi {



    @Test
    @
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
               .extract().path("userToken");
        System.out.println(userToken);




    }
    @Test
    public void create_Company(){
        String uri = "https://x-clients-be.onrender.com/";
        Map<String, String> request = new HashMap<>();
        request.put("username", "leonardo");
        request.put("password", "leads");
        String userToken = given().contentType("application/json")
                .body(request)
                .when()
                .post(uri + "auth/login")
                .then().log().body()
                .extract().path("userToken");

        String companyName = "Кофемания";
        String companyDescription = "Кофейня";
       int id =  given()
                .header("x-client-token", userToken)
                .contentType("application/json; charset=utf-8")
                .body("{\"name\": \"" + companyName + "\",\"description\": \"" + companyDescription + "\"}")
                .when()
                .post(uri + "company")
                .then()
                .contentType("application/json; charset=utf-8")
                .extract().path("id");
        System.out.println(id);

    }

    @Test
    public void get_Companies(){
        String uri = "https://x-clients-be.onrender.com/";
        List name = given()
                    .when().get(uri+"company")
                    .then()
                    .extract().path("name");
        System.out.print(name);
        }

    }



