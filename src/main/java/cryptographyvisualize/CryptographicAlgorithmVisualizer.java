package cryptographyvisualize;

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

/**
 * Main JavaFX application for visualizing cryptographic algorithms.
 * <p>
 * Provides a graphical interface that allows users to select and
 * interact with different cryptographic algorithm visualizations,
 * including Caesar Cipher, Diffie-Hellman, and AES.
 * </p>
 */

public class CryptographicAlgorithmVisualizer extends Application {
	private String[] algorithms = {"Caesar Cipher", "Diffie-Hellman", "AES"};
	private VBox sectionContainer = new VBox();
	private Section algorithmSection;

	/**
	* Updates the displayed algorithm visualization section based on the
	* selected algorithm.
	*
	* @param algorithm the name of the algorithm whose visualization
	*                  section should be displayed
	*/

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

	/**
	* {@inheritDoc}
	*
	* Initializes and displays the application's graphical user interface.
	* The interface includes the application title, algorithm selection
	* controls, and the currently selected algorithm visualization section.
	*
	* @param stage the primary stage for this application
	*/

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
		scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
		
		// Setting Stage values and then showing it
		stage.setTitle("Cryptographic Algorithm Visualizer");
		stage.setScene(scene);
		stage.show();
		javafx.application.Platform.runLater(() -> {
			stage.setMaximized(true);
		});
	}

	/**
	* Launches the Cryptographic Algorithm Visualizer application.
	*
	* @param args command-line arguments passed to the application
	*/

	public static void main(String[] args) {
		launch(args);
	}
}
