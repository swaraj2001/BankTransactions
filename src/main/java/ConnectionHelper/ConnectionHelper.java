package ConnectionHelper;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    public static Connection createConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/j2ee","root" ,"sql123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
