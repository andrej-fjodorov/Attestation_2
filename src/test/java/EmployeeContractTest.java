import org.junit.jupiter.api.Test;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class EmployeeContractTest {
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
                .extract().path("userToken");
        System.out.println(userToken);
    }

    @Test
    public void get_Employee(){
            String uri = "https://x-clients-be.onrender.com/";
            List name = given()
                    .when().get(uri+"employee")
                    .then()
                    .extract().path("name");
            System.out.print(name);

    }
}
