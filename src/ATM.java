import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

class ATM {
    private final String logFile;

    ATM(String logFile) {
        this.logFile = logFile;
    }

    void loadTransactions() {

    }

    List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM transactions")) {

            while (rs.next()) {
                String id = rs.getString("id");
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                transactions.add(new Transaction(id, type, amount));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при загрузке транзакций: " + e.getMessage());
        }
        return transactions;
    }

    void addTransaction(Transaction transaction) {
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO transactions (id, type, amount) VALUES (?, ?, ?)")) {

            pstmt.setString(1, transaction.getId());
            pstmt.setString(2, transaction.getType());
            pstmt.setDouble(3, transaction.getAmount());
            pstmt.executeUpdate();

            log(transaction.getType() + ": " + Math.abs(transaction.getAmount()));
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении транзакции: " + e.getMessage());
        }
    }

    public void updateTransaction(int index, double amount) {
        List<Transaction> transactions = getTransactions();
        if (index < 0 || index >= transactions.size()) {
            System.out.println("Неверный номер.");
            return;
        }

        Transaction t = transactions.get(index);
        double newAmount = t.getType().equals("Снятие") ? -Math.abs(amount) : amount;

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "UPDATE transactions SET amount = ? WHERE id = ?")) {

            pstmt.setDouble(1, newAmount);
            pstmt.setString(2, t.getId());
            pstmt.executeUpdate();

            log("Обновлена транзакция: " + t.getId());
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении транзакции: " + e.getMessage());
        }
    }

    public void deleteTransaction(int index) {
        List<Transaction> transactions = getTransactions();
        if (index < 0 || index >= transactions.size()) {
            System.out.println("Неверный номер.");
            return;
        }

        Transaction t = transactions.get(index);

        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "DELETE FROM transactions WHERE id = ?")) {

            pstmt.setString(1, t.getId());
            pstmt.executeUpdate();

            log("Удалена транзакция: " + t.getId());
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении транзакции: " + e.getMessage());
        }
    }

    public double getBalance() {
        double balance = 0;
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SUM(amount) AS balance FROM transactions")) {

            if (rs.next()) {
                balance = rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при расчете баланса: " + e.getMessage());
        }
        return balance;
    }

    public String generateReport() {
        int deposits = 0, withdrawals = 0;
        double totalDeposits = 0, totalWithdrawals = 0;

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT type, SUM(CASE WHEN amount > 0 THEN 1 ELSE 0 END) AS deposit_count, " +
                             "SUM(CASE WHEN amount < 0 THEN 1 ELSE 0 END) AS withdrawal_count, " +
                             "SUM(CASE WHEN amount > 0 THEN amount ELSE 0 END) AS total_deposits, " +
                             "SUM(CASE WHEN amount < 0 THEN -amount ELSE 0 END) AS total_withdrawals " +
                             "FROM transactions")) {

            if (rs.next()) {
                deposits = rs.getInt("deposit_count");
                withdrawals = rs.getInt("withdrawal_count");
                totalDeposits = rs.getDouble("total_deposits");
                totalWithdrawals = rs.getDouble("total_withdrawals");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при формировании отчета: " + e.getMessage());
        }

        StringBuilder report = new StringBuilder();
        report.append("\n===== Отчет =====\n");
        report.append("Всего депозитов: ").append(deposits).append(", сумма: ").append(totalDeposits).append("\n");
        report.append("Всего снятий: ").append(withdrawals).append(", сумма: ").append(totalWithdrawals).append("\n");
        report.append("Итоговый баланс: ").append(getBalance());

        log("Сформирован отчет");
        return report.toString();
    }

    private void log(String msg) {
        // Логирование осталось прежним
    }
}