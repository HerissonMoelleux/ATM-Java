import java.io.*;
import java.util.*;

public class ATM {
    private final List<Transaction> transactions = new ArrayList<>();
    private final String dataFile;
    private final String logFile;

    ATM(String dataFile, String logFile) {
        this.dataFile = dataFile;
        this.logFile = logFile;
    }

    void loadTransactions() {
        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                transactions.add(Transaction.fromString(line));
            }
        } catch (IOException ignored) {}
    }

    void saveTransactions() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile))) {
            for (Transaction t : transactions) {
                bw.write(t.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            log("Ошибка сохранения данных.");
        }
    }

    void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        log(transaction.getType() + ": " + Math.abs(transaction.getAmount()));
        saveTransactions();
    }

    public void updateTransaction(int index, double amount) {
        if (!isValidIndex(index)) return;

        Transaction t = transactions.get(index);
        transactions.set(index, new Transaction(t.getId(), t.getType(), t.getType().equals("Снятие") ? -Math.abs(amount) : amount));
        log("Обновлена транзакция #" + index);
        saveTransactions();
    }

    public void deleteTransaction(int index) {
        if (!isValidIndex(index)) return;

        transactions.remove(index);
        log("Удалена транзакция #" + index);
        saveTransactions();
    }

    public String generateReport() {
        int deposits = 0, withdrawals = 0;
        double totalDeposits = 0, totalWithdrawals = 0;

        for (Transaction t : transactions) {
            if (t.getType().equals("Депозит")) {
                deposits++;
                totalDeposits += t.getAmount();
            } else {
                withdrawals++;
                totalWithdrawals += -t.getAmount();
            }
        }

        StringBuilder report = new StringBuilder();
        report.append("\n===== Отчет =====\n");
        report.append("Всего депозитов: ").append(deposits).append(", сумма: ").append(totalDeposits).append("\n");
        report.append("Всего снятий: ").append(withdrawals).append(", сумма: ").append(totalWithdrawals).append("\n");
        report.append("Итоговый баланс: ").append(getBalance());

        log("Сформирован отчет");
        return report.toString();
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < transactions.size();
    }

    public double getBalance() {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    private void log(String msg) {
        try (FileWriter fw = new FileWriter(logFile, true)) {
            fw.write(new Date() + ": " + msg + "\n");
        } catch (IOException ignored) {}
    }
}