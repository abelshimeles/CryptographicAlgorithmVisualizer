import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.stage.Stage;

public class CryptographicAlgorithmVisualizer extends Application {
	private TextField inputField;
	private Spinner<Integer> spinner;
	private Label outputLabel;
	private Label[] alphabetLabels;
	private final String DEFAULT_FONT = "Inter";
	private final String DEFAULT_FONT_COLOR = "#152238";

	private String defaultChoiceButtonStyle() {
		return	"-fx-background-color: transparent;" +
			"-fx-text-fill: #30384A;" +
			"-fx-background-radius: 18;" +
			"-fx-border-radius: 18;" +
			"-fx-padding: 7 22 7 22;" +
			"-fx-font-size: 18px;";
	}

	private String activeChoiceButtonStyle() {
		return  "-fx-background-color: #172544;" +
			"-fx-text-fill: white;" +
			"-fx-background-radius: 18;" +
			"-fx-border-radius: 18;" +
			"-fx-padding: 7 22 7 22;" +
			"-fx-font-size: 18px;";
	}

	private String defaultButtonStyle() {
		return	"-fx-background-color: #f8f8f8;" +
			"-fx-border-color: #d3d3d3;" +
			"-fx-border-width: 1;" +
			"-fx-background-radius: 4;" +
			"-fx-border-radius: 4;" +
			"-fx-text-fill: #30384A;" +
			"-fx-font-size: 14px;";
	}


	private String activeButtonStyle() {
		return	"-fx-background-color: #ff8c1a;" +
			"-fx-border-color: #ff8c1a;" +
			"-fx-border-width: 1;" +
			"-fx-background-radius: 4;" +
			"-fx-border-radius: 4;" +
			"-fx-text-fill: white;" +
			"-fx-font-size: 14px;";
	}

	private Label getDefaultLabel(String value, boolean bold, double fontSize) {
		Label label = new Label(value);

		if (bold) {
			label.setFont(Font.font(DEFAULT_FONT, FontWeight.BOLD, fontSize));
		} else {
			label.setFont(Font.font(DEFAULT_FONT, fontSize));
		}

		label.setStyle(String.format("-fx-text-fill: %s;", DEFAULT_FONT_COLOR));

		return label;
	}

	private Button getChoiceButton(String value, boolean active) {
		Button button = new Button(value);
		if (active) {
			button.setStyle(activeChoiceButtonStyle());
		} else {
			button.setStyle(defaultChoiceButtonStyle());
		}
		button.setPrefWidth(150);
		button.setPrefHeight(50);

		return button;
	}

	private Button getDefaultButton(String value) {
		Button button = new Button(value);
		button.setStyle(defaultButtonStyle());
		button.setPrefWidth(125);
		button.setPrefHeight(50);

		return button;
	}

	@Override

	public void start(Stage stage) {
		// Title
		Label title = getDefaultLabel("Cryptographic Algorithm Visualizer", true, 36);

		HBox titleBox = new HBox();
		titleBox.getChildren().add(title);
		titleBox.setAlignment(Pos.CENTER);

		Button caesarCipherButton = getChoiceButton("Caesar", true);
		Button diffieHellmanButton = getChoiceButton("Diffie-Hellman", false);
		Button eccButton = getChoiceButton("ECC", false);

		HBox choicesBox = new HBox(45);
		choicesBox.getChildren().addAll(caesarCipherButton, diffieHellmanButton, eccButton);
		choicesBox.setAlignment(Pos.CENTER);

		Label message = getDefaultLabel("MESSAGE", false, 18);
	
		inputField = new TextField();
		inputField.setPrefWidth(630);
		inputField.setPrefHeight(50);

		VBox textBox = new VBox(10);
		textBox.getChildren().addAll(message, inputField);

		spinner = new Spinner<>();
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25, 13);

		Label spinnerMessage = getDefaultLabel("SHIFT VALUE", false, 18);

		spinner.setValueFactory(valueFactory);
		spinner.setEditable(true);
		spinner.setPrefWidth(125);
		spinner.setPrefHeight(45);

		Button encryptButton = getDefaultButton("ENCRYPT");
		Button decryptButton = getDefaultButton("DECRYPT");
		Button resetButton = getDefaultButton("RESET");

		HBox functionalityBox = new HBox(45);
		functionalityBox.getChildren().addAll(spinner, encryptButton, decryptButton, resetButton);
		functionalityBox.setAlignment(Pos.CENTER);

		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(spinnerMessage, functionalityBox);

		VBox controls = new VBox(35);

		controls.getChildren().addAll(textBox, buttonsBox);
		controls.setAlignment(Pos.CENTER);

		HBox userFunctionality = new HBox();
		userFunctionality.setAlignment(Pos.CENTER);
		userFunctionality.getChildren().addAll(controls);

		// Root
		VBox root = new VBox(40);

		root.getChildren().addAll(titleBox, choicesBox, userFunctionality);
		root.setPadding(new Insets(25));

		root.setStyle(
		    "-fx-background-color: linear-gradient(to right, " +
		    "#f6f1d4 0%, " +
		    "#fefefc 50%, " +
		    "#f3edc7 100%);"
		);

		// Scene
		Scene scene = new Scene(root);
		
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
