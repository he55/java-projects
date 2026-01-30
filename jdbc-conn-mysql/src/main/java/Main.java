import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

void main() throws SQLException {
    String url = "jdbc:mysql://localhost:3306/test_db";
    String username = "root";
    String password = "password";

    Connection connection = DriverManager.getConnection(url, username, password);

    PreparedStatement preparedStatement = connection.prepareStatement("select * from person where first_name = ?");

    preparedStatement.setString(1, "yang");
    ResultSet resultSet = preparedStatement.executeQuery();

    IO.println("id\tfirstName\tlastName\taddress\tcity");

    while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String address = resultSet.getString("address");
        String city = resultSet.getString("city");
        IO.println(id + "\t" + firstName + "\t" + lastName + "\t" + address + "\t" + city);
    }

    resultSet.close();
    preparedStatement.close();
    connection.close();
}
