import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        ATMJavaFX gui = new ATMJavaFX("transactions.txt", "activity.log");
        gui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}