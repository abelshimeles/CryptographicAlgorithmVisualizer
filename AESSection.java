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
        private Label[][] sBoxCells = new Label[17][17];

	private TextField inputField;
	private TextField keyField;
	private Button encryptButton;
	private Button decryptButton;
	private Button resetButton;

	private HBox subBytesVisualize(int[][] state) {
		VBox stateGrid = Components.createMatrixGrid("STATE", plainCells, state, "plain-matrix-cell");
		stateGrid.setAlignment(Pos.CENTER);
		VBox sBoxGrid = Components.createSBoxMatrixGrid("SBOX", sBoxCells);

		HBox subBytesBox = new HBox(80);
		subBytesBox.getChildren().addAll(stateGrid,sBoxGrid);
		subBytesBox.setAlignment(Pos.CENTER);

		return subBytesBox;

		
	}

	private void visualize(HBox matrixBox) {
		String plaintext = inputField.getText();
		String key = keyField.getText();

		int[][] plainMatrix = AES.bytesToMatrix(plaintext);
		int[][] keyMatrix = AES.bytesToMatrix(key);
		
		HBox subBytesBox = subBytesVisualize(plainMatrix);

		matrixBox.getChildren().addAll(subBytesBox);
		matrixBox.setAlignment(Pos.CENTER);
       }

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

		HBox matrixBox = new HBox(80);
		encryptButton.setOnAction(e -> visualize(matrixBox));

		VBox section = new VBox(40);
		section.getChildren().addAll(controls, matrixBox);

		return section;
	}
}
