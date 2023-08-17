import jdk.jfr.Description;
import org.example.Employee;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;



public class EmployeeContractTest {
    @Test
    @Description("Получение информации о токене авторизации")
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
    @Description("Создание авторизованного сотрудника")
    public void create_Employee(){
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

        Employee employee = new Employee(5,"Dmitriy","Varzugin","",715,"hello@cofemaniya.ru",null,"8172858585","2023-08-17T10:07:33.189Z",true);
        int id =  given()
                .header("x-client-token", userToken)
                .contentType("application/json; charset=utf-8")
                .body(employee)
                .when()
                .post(uri + "employee")
                .then()
                .contentType("application/json; charset=utf-8")
                .extract().path("id");
        System.out.println(id);

    }

    @Test
    @Description("Вывод имен сотрудников, которые работают в кофейне")
    public void get_Employee() {
        String uri = "https://x-clients-be.onrender.com/";
        List name = given()
                .when().get(uri + "employee?company=715")
                .then()
                .extract().path("firstName");
        System.out.print(name);


    }


}
