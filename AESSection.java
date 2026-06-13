import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;


public class AESSection implements Section { 
        private Label[][] plainCells = new Label[4][4];
        private Label[][] keyCells = new Label[4][4];

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

                String plaintext = "plaintextmessage";
                String key = "abcdefghijklmnop";

                int[][] plainMatrix = AES.bytesToMatrix(plaintext);
                int[][] keyMatrix = AES.bytesToMatrix(key);

                VBox stateGrid = Components.createMatrixGrid("STATE", plainCells, plainMatrix, "plain-matrix-cell");
                VBox keyGrid = Components.createMatrixGrid("CIPHER KEY", keyCells, keyMatrix, "key-matrix-cell");

                HBox matrixBox = new HBox(50);
                matrixBox.getChildren().addAll(stateGrid, keyGrid);
		matrixBox.setAlignment(Pos.CENTER);


		VBox section = new VBox(40);
		section.getChildren().addAll(controls, matrixBox);
		
		return section;
	}
}
