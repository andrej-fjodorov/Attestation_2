import org.example.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCtest {
    public static void main(String[] args) throws SQLException {


        String connectionString = "jdbc:postgresql://dpg-cj94hf0eba7s73bdki80-a.oregon-postgres.render.com/x_clients_db_r06g";
        String user = "x_clients_db_r06g_user";
        String password = "0R1RNWXMepS7mrvcKRThRi82GtJ2Ob58";

        Connection connection = DriverManager.getConnection(connectionString, user, password);

        String sql = "SELECT * FROM employee";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);

        List<Employee> names = new ArrayList<>();
        while (resultSet.next()){
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
            Employee emp = new Employee(id,firstName,lastName,middleName,companyId,email,url,phone,birthdate,isActive);
            names.add(emp);
            System.out.println(names);

        }
        connection.close();
    }
}

