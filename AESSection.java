import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;


public class AESSection implements Section { 
	private TextField inputField;
	private TextField keyField;
	private Button encryptButton;
	private Button decryptButton;
	private Button resetButton;

	@Override
	public VBox getSection() {
		Label inputMessage = Components.getDefaultLabel("PLAINTEXT / CIPHERTEXT", false, 18);

		inputField = new TextField();
		inputField.setPrefWidth(630);
		inputField.setPrefHeight(50);
		inputField.getStyleClass().add("input-field");

		VBox inputBox = new VBox(10);
		inputBox.getChildren().addAll(inputMessage, inputField);

		Label keyMessage = Components.getDefaultLabel("KEY", false, 18);

		keyField = new TextField();
		keyField.setPrefWidth(630);
		keyField.setPrefHeight(50);
		keyField.getStyleClass().add("input-field");

		VBox keyBox = new VBox(10);
		keyBox.getChildren().addAll(keyMessage, keyField);

		VBox textBox = new VBox(20);
		textBox.getChildren().addAll(inputBox, keyBox);

		HBox userInputBox = new HBox();
		userInputBox.getChildren().add(textBox);
		userInputBox.setAlignment(Pos.CENTER);

		encryptButton = Components.getDefaultButton("ENCRYPT");
		decryptButton = Components.getDefaultButton("DECRYPT");
		resetButton = Components.getDefaultButton("RESET");

		HBox buttonsBox = new HBox(45);
		buttonsBox.getChildren().addAll(encryptButton, decryptButton, resetButton);
		buttonsBox.setAlignment(Pos.CENTER);
		
		VBox controls = new VBox(35);
		controls.getChildren().addAll(userInputBox, buttonsBox);

		VBox section = new VBox(40);
		section.getChildren().addAll(controls);
		
		return section;
	}
}
