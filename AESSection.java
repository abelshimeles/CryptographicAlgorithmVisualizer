// Animation steps learned and implemented from https://formaestudio.com/rijndaelinspector/archivos/Rijndael_Animation_v4_eng-html5.html

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class AESSection implements Section { 
	private int[][] state = new int[4][4];
	private int[][] keyMatrix = new int[4][4];

        private Label[][] plainCells = new Label[4][4];
        private Label[][] keyCells = new Label[4][4];
        private Label[][] sBoxCells = new Label[17][17];

	private TextField inputField;
	private TextField keyField;
	private Button encryptButton;
	private Button decryptButton;
	private Button resetButton;

	private int[][] copyState(int[][] state) {
		int[][] prevState = new int[4][4];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				prevState[i][j] = state[i][j];
			}
		}

		return prevState;
	}

	private void resetSBoxCells() {
		for (int r = 1; r < 17; r++) {
			for(int c = 1; c < 17; c++) {
				sBoxCells[r][c].getStyleClass().removeAll(
					"sbox-matrix-cells-highlight",
					"sbox-matrix-cell-highlight"		
				);

				if (!sBoxCells[r][c].getStyleClass().contains("sbox-matrix-cell")) {
				    sBoxCells[r][c].getStyleClass().add("sbox-matrix-cell");
				}
			}	
		}
	}

	private HBox subBytesVisualize(int[][] state) {
		VBox stateGrid = Components.createMatrixGrid("STATE", plainCells, state, "plain-matrix-cell");
		stateGrid.setAlignment(Pos.CENTER);
		VBox sBoxGrid = Components.createSBoxMatrixGrid("SBOX", sBoxCells);

		HBox subBytesBox = new HBox(80);
		subBytesBox.getChildren().addAll(stateGrid,sBoxGrid);
		subBytesBox.setAlignment(Pos.CENTER);
	
		final int[] index = {0};

		Runnable[] animator = new Runnable[1];

		animator[0] = () -> {
                        if(index[0] >= 16) return;

                        resetSBoxCells();

                        int stateRow = index[0] % 4;
                        int stateColumn = index[0] / 4;

                        int value = state[stateRow][stateColumn];

                        int row = value >> 0x4;
                        int column = value & 0xF;

                        for(int c = 1; c < 17; c++) {
                                sBoxCells[row + 1][c].getStyleClass().remove("sbox-matrix-cell");

                                if(!sBoxCells[row + 1][c].getStyleClass().contains("sbox-matrix-cells-highlight")) {
                                        sBoxCells[row + 1][c].getStyleClass().add("sbox-matrix-cells-highlight");
                                }
                        }

                        for(int r = 1; r < 17; r++) {
                                sBoxCells[r][column + 1].getStyleClass().remove("sbox-matrix-cell");

                                if(!sBoxCells[r][column + 1].getStyleClass().contains("sbox-matrix-cells-highlight")) {
                                        sBoxCells[r][column + 1].getStyleClass().add("sbox-matrix-cells-highlight");
                                }
                        }

                        sBoxCells[row + 1][column + 1].getStyleClass().remove("sbox-matrix-cells-highlight");

                        if(!sBoxCells[row + 1][column + 1].getStyleClass().contains("sbox-matrix-cell-highlight")) {
                                sBoxCells[row + 1][column + 1].getStyleClass().add("sbox-matrix-cell-highlight");
                        }

                        PauseTransition substitutionDelay = new PauseTransition(Duration.seconds(1));

                        substitutionDelay.setOnFinished(e -> {
                                plainCells[stateRow][stateColumn].getStyleClass().remove("plain-matrix-cell");

                                if(!plainCells[stateRow][stateColumn].getStyleClass().contains("plain-matrix-cell-substituted")) {
                                        plainCells[stateRow][stateColumn].getStyleClass().add("plain-matrix-cell-substituted");
                                }

				state[stateRow][stateColumn] = AES.SBOX[row][column];
                                plainCells[stateRow][stateColumn]
                                                .setText(Components.toHex(AES.SBOX[row][column]));

                                index[0]++;

                                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                                pause.setOnFinished(event -> animator[0].run());
                                pause.play();
                        });

                        substitutionDelay.play();
                };

		PauseTransition initialDelay = new PauseTransition(Duration.seconds(2));
		initialDelay.setOnFinished(e -> animator[0].run());
		initialDelay.play();

		return subBytesBox;
	}

	private HBox shiftRowsVisualize(int[][] state) {
		VBox stateGird = Components.createMatrixGrid("STATE", plainCells, state, "plain-matrix-cell");

		HBox shiftRowsBox = new HBox(20);
		shiftRowsBox.getChildren().addAll(stateGird);
		shiftRowsBox.setAlignment(Pos.CENTER);

		return shiftRowsBox;
	}

	private void visualize(HBox matrixBox) {
		String plaintext = inputField.getText();
		String key = keyField.getText();

		state = AES.bytesToMatrix(plaintext);
		keyMatrix = AES.bytesToMatrix(key);
		int[][] prevState = copyState(state);

		Button previousButton = new Button("previous");
		Button nextButton = new Button("next");

		matrixBox.getChildren().addAll(previousButton, subBytesVisualize(prevState), nextButton);
		matrixBox.setAlignment(Pos.CENTER);

		nextButton.setOnAction(e -> matrixBox.getChildren().set(1, shiftRowsVisualize(state)));
		previousButton.setOnAction(e -> matrixBox.getChildren().set(1, subBytesVisualize(prevState)));
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
