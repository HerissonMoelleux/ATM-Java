import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        Connection conn = DataBase.connect();
        if (conn != null) {
            System.out.println("Успешное подключение к PostgreSQL!");
        }

    }
}
