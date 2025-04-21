import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Database{
    private static final String URL = "jdbc:postgresql://localhost:5432/atm_database";
    private static final String USER = "postgres";
    private static final String PASSWORD = "AmiPostgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}