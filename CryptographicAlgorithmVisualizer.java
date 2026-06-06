import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.stage.Stage;

public class CryptographicAlgorithmVisualizer extends Application {
	private String[] algorithms = {"Caesar Cipher", "Diffie-Hellman", "AES"};
	private VBox sectionContainer = new VBox();
	private Section algorithmSection;

	private void setSection(String algorithm) {
		switch (algorithm) {
			case "Caesar Cipher":
				algorithmSection = new CaesarCipherSection();
				break;
			case "Diffie-Hellman":
				algorithmSection = new DiffieHellmanSection();
				break;
			case "AES":
				algorithmSection = new AESSection();
				break;
		}

		sectionContainer.getChildren().setAll(algorithmSection.getSection());

	}

	@Override
	public void start(Stage stage) {
		// Title
		Label title = Components.getDefaultLabel("Cryptographic Algorithm Visualizer", true, 36);
		
		// Algorithm selection section
		HBox algorithmsSelectorContainer = Components.createAlgorithmSelector(
				algorithm -> setSection(algorithm),
				algorithms
		);

		HBox titleBox = new HBox();
		titleBox.getChildren().add(title);
		titleBox.setAlignment(Pos.CENTER);
		
		VBox body = new VBox(40);
		body.getChildren().addAll(titleBox, algorithmsSelectorContainer, sectionContainer);
		body.setPadding(new Insets(25));

		ScrollPane scrollPane = new ScrollPane(body);
		scrollPane.setFitToWidth(true);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		// Root
		VBox root = new VBox(scrollPane);
		
		// Scene
		Scene scene = new Scene(root);
		scene.getStylesheets().add("style.css");
		
		// Setting Stage values and then showing it
		stage.setTitle("Cryptographic Algorithm Visualizer");
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
