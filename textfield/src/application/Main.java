package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("textField Experiment 1");

		TextField textField = new TextField();

		Button button = new Button("Click");

		button.setOnAction(action -> {
		System.out.println(textField.getText());
		});

		HBox hbox = new HBox(textField, button);

		Scene scene = new Scene(hbox, 400, 100);
		primaryStage.setScene(scene);
		primaryStage.show();

		}

		public static void main(String[] args) {
		Application.launch(args);
		}
		}