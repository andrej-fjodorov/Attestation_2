package Database;

import Model.Employee;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCtest {

    @Test
    public void GetAllEmployee() throws SQLException {

        File propFile = new File("src/test/java/db.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String dburl = properties.getProperty("DB");
        String user = properties.getProperty("USER");
        String password = properties.getProperty("PASS");

        Connection connection = DriverManager.getConnection(dburl, user, password);

        String sql = "SELECT * FROM employee where company_id = 715";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);

        List<Employee> names = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String lastName = resultSet.getString("last_name");
            String firstName = resultSet.getString("first_name");
            String middleName = resultSet.getString("middle_name");
            int companyId = resultSet.getInt("company_id");
            String email = resultSet.getString("email");
            String url = resultSet.getString("avatar_url");
            String phone = resultSet.getString("phone");
            String birthdate = resultSet.getString("birthdate");
            boolean isActive = resultSet.getBoolean("is_active");
            Employee emp = new Employee(id, firstName, lastName, middleName, companyId, email, url, phone, birthdate, isActive);
            names.add(emp);
            System.out.println(names);

        }
        connection.close();
    }
}



