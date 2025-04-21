import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.UUID;

public class ATMJavaFX {
    private final ATM atm;
    private final TextArea output = new TextArea();
    private final TextField amountField = new TextField();

    public ATMJavaFX(String logFile) {
        atm = new ATM(logFile);
    }

    public void start(Stage stage) {
        stage.setTitle("ATM JavaFX GUI");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        amountField.setPromptText("Введите сумму");

        HBox buttonRow = new HBox(10);
        Button depositBtn = new Button("Внести");
        Button withdrawBtn = new Button("Снять");
        Button balanceBtn = new Button("Баланс");
        Button updateBtn = new Button("Обновить");
        Button deleteBtn = new Button("Удалить");
        Button reportBtn = new Button("Отчет");
        Button exitBtn = new Button("Выход");
        buttonRow.getChildren().addAll(depositBtn, withdrawBtn, balanceBtn, updateBtn, deleteBtn, reportBtn, exitBtn);

        output.setEditable(false);
        output.setPrefHeight(300);

        root.getChildren().addAll(new Label("Сумма:"), amountField, buttonRow, output);

        depositBtn.setOnAction(e -> {
            double amount = parseAmount();
            if (amount > 0) {
                atm.addTransaction(new Transaction(UUID.randomUUID().toString(), "Депозит", amount));
                show("Внесено: " + amount);
            }
        });

        withdrawBtn.setOnAction(e -> {
            double amount = parseAmount();
            if (amount > 0) {
                if (atm.getBalance() >= amount) {
                    atm.addTransaction(new Transaction(UUID.randomUUID().toString(), "Снятие", -amount));
                    show("Снято: " + amount);
                } else {
                    show("Недостаточно средств.");
                }
            }
        });

        balanceBtn.setOnAction(e -> show("Баланс: " + atm.getBalance()));

        updateBtn.setOnAction(e -> {
            TextInputDialog idPrompt = new TextInputDialog();
            idPrompt.setTitle("Обновление");
            idPrompt.setHeaderText("Введите номер транзакции");
            idPrompt.setContentText("Индекс:");

            idPrompt.showAndWait().ifPresent(indexStr -> {
                TextInputDialog amountPrompt = new TextInputDialog();
                amountPrompt.setHeaderText("Введите новую сумму");
                amountPrompt.setContentText("Сумма:");
                amountPrompt.showAndWait().ifPresent(amountStr -> {
                    try {
                        int index = Integer.parseInt(indexStr);
                        double newAmount = Double.parseDouble(amountStr);
                        atm.updateTransaction(index, newAmount);
                        show("Обновлено.");
                    } catch (Exception ex) {
                        show("Ошибка ввода");
                    }
                });
            });
        });

        deleteBtn.setOnAction(e -> {
            TextInputDialog prompt = new TextInputDialog();
            prompt.setTitle("Удаление");
            prompt.setHeaderText("Введите номер транзакции");
            prompt.setContentText("Индекс:");
            prompt.showAndWait().ifPresent(indexStr -> {
                try {
                    int index = Integer.parseInt(indexStr);
                    atm.deleteTransaction(index);
                    show("Удалено.");
                } catch (Exception ex) {
                    show("Ошибка ввода");
                }
            });
        });

        reportBtn.setOnAction(e -> show(atm.generateReport()));
        exitBtn.setOnAction(e -> Platform.exit());

        stage.setScene(new Scene(root, 700, 450));
        stage.show();
    }

    private double parseAmount() {
        try {
            return Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            show("Некорректная сумма");
            return 0;
        }
    }

    private void show(String msg) {
        output.appendText(msg + "\n");
    }
}
