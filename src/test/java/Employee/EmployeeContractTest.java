package Employee;


import jdk.jfr.Description;
import Model.Employee;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class EmployeeContractTest {
    @Test
    @Description("Получение информации о токене авторизации")
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
    @Description("Создание авторизованного сотрудника")
    public void createEmployee(){
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
    @Description("Вывод информации о сотрудниках, которые работают в кофейне")
    public void getEmployee() {
        File propFile = new File("src/test/java/employee.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String uri = properties.getProperty("URI");
        String name = given()
                .when().get(uri + "employee?company=715")
                .then()
                .extract().response().asPrettyString();
        System.out.print(name);


    }

    @Test
    @Description("Создание не авторизованного сотрудника")
    public void createNotAutEmployee(){
        File propFile = new File("src/test/java/employee.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String uri = properties.getProperty("URI");


        Employee employee = new Employee(5,"Dmitriy","Varzugin","",715,"hello@cofemaniya.ru",null,"8172858585","2023-08-17T10:07:33.189Z",true);
         String message =  given()
                .contentType("application/json; charset=utf-8")
                .body(employee)
                .when()
                .post(uri + "employee")
                .then()
                .statusCode(401)
                 .extract().body().path("message");
        assertEquals("Unauthorized", message);

    }

    @Test
    @Description("Компания без сотрудников")
    public void getCompanyWithoutEmploee() {
        File propFile = new File("src/test/java/employee.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String uri = properties.getProperty("URI");
        String name = given()
                .when().get(uri + "employee?company=997")
                .then()
                .extract().response().asPrettyString();
    }

    @Test
    @Description("Вывод информации о сотруднике по Id")
    public void getEmployeeById() {
        File propFile = new File("src/test/java/employee.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String uri = properties.getProperty("URI");
        String name = given()
                .when().get(uri + "employee/"+1630)
                .then()
                .extract().response().asPrettyString();
        System.out.print(name);
    }

    @Test
    @Description("Изменение авторизованного сотрудника")
    public void changeEmployeeByID(){
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

        Employee employee = new Employee(1630,"Dmitriy","Kolesov","",715,"hello@cofemaniya.ru",null,"8172858585","2023-08-17T10:07:33.189Z",true);
        String emp =  given()
                .header("x-client-token", userToken)
                .contentType("application/json; charset=utf-8")
                .body(employee)
                .when()
                .patch(uri + "employee/"+1630)
                .then()
                .contentType("application/json; charset=utf-8")
                .extract().response().asPrettyString();
        System.out.print(emp);


    }

    @Test
    @Description("Создание не авторизованного сотрудника")
    public void changeNotAutEmployee(){
        File propFile = new File("src/test/java/employee.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String uri = properties.getProperty("URI");


        Employee employee = new Employee(5,"Dmitriy","Kolesov","",715,"hello@cofemaniya.ru",null,"8172858585","2023-08-17T10:07:33.189Z",true);
        String message =  given()
                .contentType("application/json; charset=utf-8")
                .body(employee)
                .when()
                .patch(uri + "employee/"+1630)
                .then()
                .statusCode(401)
                .extract().body().path("message");
        assertEquals("Unauthorized", message);

    }

}
