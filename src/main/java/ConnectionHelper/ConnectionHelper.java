package ConnectionHelper;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    public static Connection createConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection("database path","database username" ,"database password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
