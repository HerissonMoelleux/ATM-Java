import java.sql.*;

public class DataBase {
    public static Connection connect() {
        try {
            String url = "jdbc:postgresql://localhost:5432/atm_database";
            String user = "postgres";
            String password = "AmiPostgres";

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
            return null;
        }
    }
}
