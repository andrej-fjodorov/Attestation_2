package Company;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class CompanyContractTest {



        @Test
        public void getUserToken() {
            File propFile = new File("src/test/java/employee.properties");
            Properties properties = new Properties();
            try {
                properties.load(new FileReader(propFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String uri = properties.getProperty("URI");
            String username = properties.getProperty("USER");
            String password = properties.getProperty("PASS");
            Map<String, String> request = new HashMap<>();
            request.put("username", username);
            request.put("password", password);
            String userToken = given().contentType("application/json")
                    .body(request)
                    .when()
                    .post(uri + "auth/login")
                    .then().log().body()
                    .extract().path("userToken");
            System.out.println(userToken);




        }
        @Test
        public void createCompany(){
            File propFile = new File("src/test/java/company.properties");
            Properties properties = new Properties();
            try {
                properties.load(new FileReader(propFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String uri = properties.getProperty("URI");
            String userToken = properties.getProperty("TOKEN");

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
        public void getCompanies(){
            File propFile = new File("src/test/java/employee.properties");
            Properties properties = new Properties();
            try {
                properties.load(new FileReader(propFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String uri = properties.getProperty("URI");
            String name = given()
                    .when().get(uri+"company")
                    .then()
                    .extract().response().asPrettyString();
            System.out.print(name);
        }

    }


