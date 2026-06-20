// Animation steps learned and implemented from https://formaestudio.com/rijndaelinspector/archivos/Rijndael_Animation_v4_eng-html5.html

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.util.Duration;
import java.util.List;
import java.util.function.Supplier;

public class AESSection implements Section { 
	private int[][] plainTextState = new int[4][4];
	private int[][] subBytesState = new int[4][4];
	private int[][] shiftRowsState = new int[4][4];
	private int[][] mixColumnsState = new int[4][4];
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

	private HBox subBytesVisualize() {
		subBytesState = copyState(plainTextState);
		VBox stateGrid = Components.createMatrixGrid("STATE", plainCells, subBytesState, "plain-matrix-cell");
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

                        int value = subBytesState[stateRow][stateColumn];

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

			double delay = (stateColumn == 0) ? 1.0 : 0.15;
			double nextDelay = (stateColumn == 0) ? 2.0 : 0.15;

                        PauseTransition substitutionDelay = new PauseTransition(Duration.seconds(delay));

                        substitutionDelay.setOnFinished(e -> {
                                plainCells[stateRow][stateColumn].getStyleClass().remove("plain-matrix-cell");

                                if(!plainCells[stateRow][stateColumn].getStyleClass().contains("plain-matrix-cell-substituted")) {
                                        plainCells[stateRow][stateColumn].getStyleClass().add("plain-matrix-cell-substituted");
                                }

				subBytesState[stateRow][stateColumn] = AES.SBOX[row][column];
                                plainCells[stateRow][stateColumn]
                                                .setText(Components.toHex(AES.SBOX[row][column]));

                                index[0]++;

                                PauseTransition pause = new PauseTransition(Duration.seconds(nextDelay));
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

	private HBox shiftRowsVisualize() {
		Label arrow = Components.getDefaultLabel("\u2190", true, 30);
		arrow.setAlignment(Pos.CENTER);

		shiftRowsState = copyState(subBytesState);
		VBox stateGrid = Components.createMatrixGrid("STATE", plainCells, shiftRowsState, "plain-matrix-cell");

		HBox shiftRowsBox = new HBox(10);
		shiftRowsBox.getChildren().add(stateGrid);
		shiftRowsBox.setAlignment(Pos.CENTER);

		final int[] index = {0};

		Runnable[] animator = new Runnable[1];

		animator[0] = () -> {
                        if(index[0] >= 3) return;

			Label descriptionText = Components.getDefaultLabel(String.format("rotate over %d byte(s)", index[0]+1), false, 25);
			HBox descriptionContainer = new HBox(30);
			descriptionContainer.getChildren().addAll(arrow, descriptionText);

			VBox descriptionBox = new VBox();
			descriptionBox.getChildren().add(descriptionContainer);

			VBox.setMargin(descriptionContainer, new Insets(110 + (60 * index[0]), 0, 0, 0));

			shiftRowsBox.getChildren().add(descriptionBox);

                        PauseTransition shiftDelay = new PauseTransition(Duration.seconds(3));

                        shiftDelay.setOnFinished(e -> {
                                index[0]++;
				shiftRowsState = AES.shiftRow(shiftRowsState, index[0]);

				for (int i = 0; i < 4; i++) {
					plainCells[index[0]][i].setText(Components.toHex(shiftRowsState[index[0]][i]));
				}

                                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                                pause.setOnFinished(event -> {
					shiftRowsBox.getChildren().remove(descriptionBox);
					animator[0].run();
				});
                                pause.play();
                        });

                        shiftDelay.play();
                };

		PauseTransition initialDelay = new PauseTransition(Duration.seconds(2));
		initialDelay.setOnFinished(e -> animator[0].run());
		initialDelay.play();

		return shiftRowsBox;
	}


	private VBox updateStateColumn(int[][] cells, int[] newColumn, VBox stateGrid, int column) {
		for (int row = 0; row < 4; row++) {
			cells[row][column] = newColumn[row];
		}

		stateGrid = Components.updatedColumnMatrixGrid(
				"STATE", 
				plainCells, 
				cells, 
				"mix-column-matrix-cell", 
				"plain-matrix-cell", 
				column
		);

		return stateGrid;
	}
	private HBox mixColumnsVisualize() {
		mixColumnsState = copyState(shiftRowsState);
		VBox stateGrid = Components.createMatrixGrid("STATE", plainCells, mixColumnsState, "plain-matrix-cell");
		HBox mixColumnMatrix = Components.createMixColumnMatrix();

		Label galoisMultiplySymbol = Components.getDefaultLabel("\u22c5", true, 50);
		VBox galoisMultiplySymbolContainer = new VBox();
		galoisMultiplySymbolContainer.getChildren().add(galoisMultiplySymbol);
		galoisMultiplySymbolContainer.setAlignment(Pos.CENTER);

		Label equalsSymbol = Components.getDefaultLabel("\u003d", true, 50);
		VBox equalsSymbolContainer = new VBox();
		equalsSymbolContainer.getChildren().add(equalsSymbol);
		equalsSymbolContainer.setAlignment(Pos.CENTER);

		HBox mixColumnsBox = new HBox(100);
		mixColumnsBox.getChildren().add(stateGrid);
		mixColumnsBox.setAlignment(Pos.CENTER);

		final int[] index = {0};

		Runnable[] animator = new Runnable[1];

		animator[0] = () -> {
                        if(index[0] >= 4) return;

                        PauseTransition mixColumnDelay = new PauseTransition(Duration.seconds(2));

                        mixColumnDelay.setOnFinished(e -> {
				int[] column = AES.copyColumn(mixColumnsState, index[0]);
				int[] mixColumn = AES.mixColumn(column);

				VBox columnArray = Components.createSingleColumnArray(column, "plain-matrix-cell");
				VBox mixColumnArray = Components.createSingleColumnArray(mixColumn, "mix-column-matrix-cell");

				HBox calculationSection = new HBox(20);
				calculationSection.getChildren().addAll(mixColumnMatrix, galoisMultiplySymbolContainer, columnArray, equalsSymbolContainer);

				mixColumnsBox.getChildren().add(calculationSection);

				for (int i = 0; i < 4; i++) {
					plainCells[index[0]][i].setText(Components.toHex(shiftRowsState[index[0]][i]));
				}

				index[0]++;

                                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                                pause.setOnFinished(event -> {
					calculationSection.getChildren().add(mixColumnArray);

					PauseTransition removeElements = new PauseTransition(Duration.seconds(2));

					removeElements.setOnFinished(ev -> {
						VBox currentStateGrid = updateStateColumn(mixColumnsState, mixColumn, stateGrid, index[0]-1);
						mixColumnsBox.getChildren().clear();
						mixColumnsBox.getChildren().add(currentStateGrid);
						animator[0].run();
					});

					removeElements.play();
				});
                                pause.play();
                        });

                        mixColumnDelay.play();
                };

		PauseTransition initialDelay = new PauseTransition(Duration.seconds(1));
		initialDelay.setOnFinished(e -> animator[0].run());
		initialDelay.play();

		return mixColumnsBox;
		
	} 

	private void visualize(HBox matrixBox) {
                String plaintext = inputField.getText();
                String key = keyField.getText();

                plainTextState = AES.bytesToMatrix(plaintext);
                keyMatrix = AES.bytesToMatrix(key);

                Button previousButton = new Button("previous");
                Button nextButton = new Button("next");

                List<Supplier<Node>> steps = List.of(
                        this::subBytesVisualize,
                        this::shiftRowsVisualize,
                        this::mixColumnsVisualize
                );

                final int[] currentStep = {0};

                matrixBox.getChildren().addAll(
                        previousButton,
                        steps.get(currentStep[0]).get(),
                        nextButton
                );
                matrixBox.setAlignment(Pos.CENTER);

                Runnable updateView = () -> {
                        matrixBox.getChildren().set(1, steps.get(currentStep[0]).get());

                        previousButton.setDisable(currentStep[0] == 0);
                        nextButton.setDisable(currentStep[0] == steps.size() - 1);
                };

                nextButton.setOnAction(e -> {
                        if (currentStep[0] < steps.size() - 1) {
                                currentStep[0]++;
                                updateView.run();
                        }
                });

                previousButton.setOnAction(e -> {
                        if (currentStep[0] > 0) {
                                currentStep[0]--;
                                updateView.run();
                        }
                });

                updateView.run();
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

		HBox buttonsBox = new HBox();
		buttonsBox.getChildren().add(encryptButton);
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
