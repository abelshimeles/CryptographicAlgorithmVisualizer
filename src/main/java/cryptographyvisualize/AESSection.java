// Animation steps learned and implemented from https://formaestudio.com/rijndaelinspector/archivos/Rijndael_Animation_v4_eng-html5.html

package cryptographyvisualize;

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
	private int[][] addRoundKeyState = new int[4][4];
	private int[][] addCipherKeyState = new int[4][4];
	private int[][] cipherKeyState = new int[4][4];
	private int[][] roundKeyState = new int[4][4];
	private int[][] keySceduleState = new int[4][4];
	private int[][] roundOneState = new int[4][4];
	private int[][] roundTwoState = new int[4][4];
	private int[][] roundTenState = new int[4][4];

	private Label[][] plainCells = new Label[4][4];
	private Label[][] keyCells = new Label[4][4];
	private Label[][] keySceduleCells = new Label[4][4];
	private Label[][] roundOneCells = new Label[4][4];
	private Label[][] roundTwoCells = new Label[4][4];
	private Label[][] roundTenCells = new Label[4][4];
	private Label[][] sBoxCells = new Label[17][17];
	private Label[][] rconCells = new Label[4][10];

	private TextField inputField;
	private TextField keyField;
	private Button encryptButton;
	private Label validationMessage;

	private void clearValidation() {
		validationMessage.setVisible(false);

		inputField.getStyleClass().remove("input-field-error");
		keyField.getStyleClass().remove("input-field-error");

		inputField.getStyleClass().remove("input-field");
		keyField.getStyleClass().remove("input-field");
	}

	private void showValidationError(TextField field, String message) {
		validationMessage.setText(message);
		validationMessage.setVisible(true);

		if (!field.getStyleClass().contains("input-field-error")) {
			field.getStyleClass().remove("input-field");
			field.getStyleClass().add("input-field-error");
		}
	}

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

	private VBox subBytesVisualize() {
		Label title = Components.getDefaultLabel("S-BOX BYTE SUBSTUTION", true, 18);
		HBox titleBox = new HBox();
		titleBox.getChildren().add(title);
		titleBox.setAlignment(Pos.CENTER);

	    subBytesState = copyState(AES.addRoundKey(plainTextState, cipherKeyState));
	    VBox stateGrid = Components.createMatrixGrid("STATE", plainCells, subBytesState, "plain-matrix-cell");
	    stateGrid.setAlignment(Pos.CENTER);
	    VBox sBoxGrid = Components.createSBoxMatrixGrid("SBOX", sBoxCells);

	    HBox subBytesBox = new HBox(80);
	    subBytesBox.getChildren().addAll(stateGrid,sBoxGrid);
	    subBytesBox.setAlignment(Pos.CENTER);

	    VBox resultBox = new VBox(30, titleBox, subBytesBox);

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

	    return resultBox;
	}

	private VBox shiftRowsVisualize() {
		Label title = Components.getDefaultLabel("SHIFT ROWS", true, 18);
		HBox titleBox = new HBox();
		titleBox.getChildren().add(title);
		titleBox.setAlignment(Pos.CENTER);

	    Label arrow = Components.getDefaultLabel("\u2190", true, 30);
	    arrow.setAlignment(Pos.CENTER);

	    shiftRowsState = copyState(subBytesState);
	    VBox stateGrid = Components.createMatrixGrid("STATE", plainCells, shiftRowsState, "plain-matrix-cell");

	    HBox shiftRowsBox = new HBox(10);
	    shiftRowsBox.getChildren().add(stateGrid);
	    shiftRowsBox.setAlignment(Pos.CENTER);

	    VBox resultBox = new VBox(30, titleBox, shiftRowsBox);

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

	    return resultBox;
	}


	private VBox updateStateColumn(int[][] cells, Label[][] labelCells, int[] newColumn, VBox stateGrid, int column, String newStyle, String oldStyle) {
	    for (int row = 0; row < 4; row++) {
		    cells[row][column] = newColumn[row];
	    }

	    stateGrid = Components.updatedColumnMatrixGrid(
			    "STATE",
			    labelCells,
			    cells,
			    newStyle,
			    oldStyle,
			    column
	    );

	    return stateGrid;
	}

	private VBox mixColumnsVisualize() {
		Label title = Components.getDefaultLabel("MIX COLUMNS", true, 18);
		HBox titleBox = new HBox();
		titleBox.getChildren().add(title);
		titleBox.setAlignment(Pos.CENTER);

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

	    VBox resultBox = new VBox(30, titleBox, mixColumnsBox);

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

			    index[0]++;

			    PauseTransition pause = new PauseTransition(Duration.seconds(3));
			    pause.setOnFinished(event -> {
				    calculationSection.getChildren().add(mixColumnArray);

				    PauseTransition removeElements = new PauseTransition(Duration.seconds(2));

				    removeElements.setOnFinished(ev -> {
					    VBox currentStateGrid = updateStateColumn(
							    mixColumnsState,
							    plainCells,
							    mixColumn,
							    stateGrid,
							    index[0]-1,
							    "mix-column-matrix-cell",
							    "plain-matrix-cell"
					    );

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

	    animator[0].run();

	    return resultBox;

	}

	private VBox addCipherKeyVisualize() {
		Label title = Components.getDefaultLabel("ADD ROUND KEY", true, 18);
		HBox titleBox = new HBox();
		titleBox.getChildren().add(title);
		titleBox.setAlignment(Pos.CENTER);

	    addCipherKeyState = copyState(plainTextState);
	    VBox stateGrid = Components.createMatrixGrid("PLAINTEXT", plainCells, addCipherKeyState, "plain-matrix-cell");
	    VBox keyGrid = Components.createMatrixGrid("CIPHER KEY", keyCells, cipherKeyState, "key-matrix-cell");

	    Label galoisAddSymbol = Components.getDefaultLabel("+", true, 50);
	    VBox galoisAddSymbolContainer = new VBox();
	    galoisAddSymbolContainer.getChildren().add(galoisAddSymbol);
	    galoisAddSymbolContainer.setAlignment(Pos.CENTER);

	    Label equalsSymbol = Components.getDefaultLabel("\u003d", true, 50);
	    VBox equalsSymbolContainer = new VBox();
	    equalsSymbolContainer.getChildren().add(equalsSymbol);
	    equalsSymbolContainer.setAlignment(Pos.CENTER);

	    HBox addCipherKeyBox = new HBox(100);
	    addCipherKeyBox.getChildren().addAll(stateGrid);
	    addCipherKeyBox.setAlignment(Pos.CENTER);

	    VBox resultBox = new VBox(30, titleBox, addCipherKeyBox);

	    final int[] index = {0};

	    Runnable[] animator = new Runnable[1];

	    animator[0] = () -> {
		    if(index[0] >= 4) return;

		    PauseTransition addCipherKeyDelay = new PauseTransition(Duration.seconds(2));

		    addCipherKeyDelay.setOnFinished(e -> {
			    int[] stateColumn = AES.copyColumn(addCipherKeyState, index[0]);
			    int[] roundKeyColumn = AES.copyColumn(cipherKeyState, index[0]);
			    int[] resultColumn = AES.galoisAddColumn(stateColumn, roundKeyColumn);

			    VBox stateColumnArray = Components.createSingleColumnArray(stateColumn, "plain-matrix-cell");
			    VBox roundKeyColumnArray = Components.createSingleColumnArray(roundKeyColumn, "round-key-matrix-cell");
			    VBox resultColumnArray = Components.createSingleColumnArray(resultColumn, "plain-matrix-cell-substituted");

			    HBox calculationSection = new HBox(20);
			    calculationSection.getChildren().addAll(stateColumnArray, galoisAddSymbolContainer, roundKeyColumnArray, equalsSymbolContainer);

			    addCipherKeyBox.getChildren().addAll(calculationSection, keyGrid);

			    index[0]++;

			    PauseTransition pause = new PauseTransition(Duration.seconds(2));
			    pause.setOnFinished(event -> {
				    calculationSection.getChildren().add(resultColumnArray);

				    PauseTransition removeElements = new PauseTransition(Duration.seconds(2));

				    removeElements.setOnFinished(ev -> {
					    VBox currentStateGrid = updateStateColumn(
							    addCipherKeyState,
							    keyCells,
							    resultColumn,
							    stateGrid,
							    index[0]-1,
							    "plain-matrix-cell-substituted",
							    "plain-matrix-cell"
					    );

					    addCipherKeyBox.getChildren().clear();
					    addCipherKeyBox.getChildren().add(currentStateGrid);
					    animator[0].run();
				    });

				    removeElements.play();
			    });
			    pause.play();
		    });

		    addCipherKeyDelay.play();
	    };

	    PauseTransition initialDelay = new PauseTransition(Duration.seconds(2));
	    initialDelay.setOnFinished(e -> animator[0].run());
	    initialDelay.play();

	    return resultBox;

	}
	private VBox addRoundKeyVisualize() {
		Label title = Components.getDefaultLabel("ADD ROUND KEY", true, 18);
		HBox titleBox = new HBox();
		titleBox.getChildren().add(title);
		titleBox.setAlignment(Pos.CENTER);

	    addRoundKeyState = copyState(mixColumnsState);
	    VBox stateGrid = Components.createMatrixGrid("STATE", plainCells, addRoundKeyState, "plain-matrix-cell");
	    VBox keyGrid = Components.createMatrixGrid("ROUND KEY 1", keyCells, roundKeyState, "round-key-matrix-cell");

	    Label galoisAddSymbol = Components.getDefaultLabel("+", true, 50);
	    VBox galoisAddSymbolContainer = new VBox();
	    galoisAddSymbolContainer.getChildren().add(galoisAddSymbol);
	    galoisAddSymbolContainer.setAlignment(Pos.CENTER);

	    Label equalsSymbol = Components.getDefaultLabel("\u003d", true, 50);
	    VBox equalsSymbolContainer = new VBox();
	    equalsSymbolContainer.getChildren().add(equalsSymbol);
	    equalsSymbolContainer.setAlignment(Pos.CENTER);

	    HBox addRoundKeyBox = new HBox(100);
	    addRoundKeyBox.getChildren().addAll(stateGrid);
	    addRoundKeyBox.setAlignment(Pos.CENTER);

	    VBox resultBox = new VBox(30, titleBox, addRoundKeyBox);

	    final int[] index = {0};

	    Runnable[] animator = new Runnable[1];

	    animator[0] = () -> {
		    if(index[0] >= 4) return;

		    PauseTransition addRoundKeyDelay = new PauseTransition(Duration.seconds(2));

		    addRoundKeyDelay.setOnFinished(e -> {
			    int[] stateColumn = AES.copyColumn(addRoundKeyState, index[0]);
			    int[] roundKeyColumn = AES.copyColumn(roundKeyState, index[0]);
			    int[] resultColumn = AES.galoisAddColumn(stateColumn, roundKeyColumn);

			    VBox stateColumnArray = Components.createSingleColumnArray(stateColumn, "plain-matrix-cell");
			    VBox roundKeyColumnArray = Components.createSingleColumnArray(roundKeyColumn, "round-key-matrix-cell");
			    VBox resultColumnArray = Components.createSingleColumnArray(resultColumn, "plain-matrix-cell-substituted");

			    HBox calculationSection = new HBox(20);
			    calculationSection.getChildren().addAll(stateColumnArray, galoisAddSymbolContainer, roundKeyColumnArray, equalsSymbolContainer);

			    addRoundKeyBox.getChildren().addAll(calculationSection, keyGrid);

			    index[0]++;

			    PauseTransition pause = new PauseTransition(Duration.seconds(2));
			    pause.setOnFinished(event -> {
				    calculationSection.getChildren().add(resultColumnArray);

				    PauseTransition removeElements = new PauseTransition(Duration.seconds(2));

				    removeElements.setOnFinished(ev -> {
					    VBox currentStateGrid = updateStateColumn(
							    addRoundKeyState,
							    keyCells,
							    resultColumn,
							    stateGrid,
							    index[0]-1,
							    "plain-matrix-cell-substituted",
							    "plain-matrix-cell"
					    );

					    addRoundKeyBox.getChildren().clear();
					    addRoundKeyBox.getChildren().add(currentStateGrid);
					    animator[0].run();
				    });

				    removeElements.play();
			    });
			    pause.play();
		    });

		    addRoundKeyDelay.play();
	    };

	    PauseTransition initialDelay = new PauseTransition(Duration.seconds(2));
	    initialDelay.setOnFinished(e -> animator[0].run());
	    initialDelay.play();

	    return resultBox;

	}

	private VBox allRoundsVisualize() {
	    int[][] plainText = copyState(plainTextState);
	    int[][] cipherKey = copyState(cipherKeyState);
	    int[][] roundKey = copyState(cipherKey);
	    int[][] state = copyState(plainText);

	    HBox inputRoundSection = Components.createInputRound(plainText, cipherKey);
	    VBox allRoundsBox = new VBox(20);
	    allRoundsBox.getChildren().add(inputRoundSection);

	    for (int round = 1; round < 10; round++) {

		    state = AES.addRoundKey(state, roundKey);
		    int[][] subByte = AES.getSubstitutedMatrix(state);
		    int[][] shiftRow = AES.shiftRows(subByte);
		    int[][] mixColumn = AES.mixColumns(shiftRow);
		    roundKey = AES.getRoundKey(roundKey, round);

		    HBox roundSection = Components.createRounds(String.format("ROUND %d", round), state, subByte, shiftRow, mixColumn, roundKey);
		    state = copyState(mixColumn);

		    allRoundsBox.getChildren().add(roundSection);
	    }

	    state = AES.addRoundKey(state, roundKey);
	    int[][] subByte = AES.getSubstitutedMatrix(state);
	    int[][] shiftRow = AES.shiftRows(subByte);
	    roundKey = AES.getRoundKey(roundKey, 10);

	    HBox finalRoundSection = Components.createFinalRound(state, subByte, shiftRow, roundKey);
	    allRoundsBox.getChildren().add(finalRoundSection);

	    state = copyState(shiftRow);
	    state = AES.addRoundKey(state, roundKey);

	    HBox cipherTextSection = Components.getAESCipherText(state);
	    allRoundsBox.getChildren().add(cipherTextSection);

	    return allRoundsBox;
	}

	private HBox rotWordKeyScedule(int[] rotWordColumn, String originalStyle) {
	    VBox originalColumnArray = Components.createSingleColumnArray(rotWordColumn, originalStyle);
	    rotWordColumn = AES.rotWord(rotWordColumn);
	    VBox rotWordColumnArray = Components.createSingleColumnArray(rotWordColumn, originalStyle);

	    Label rotWordLabel = Components.getDefaultLabel("RotWord", true, 18);

	    HBox rotWordBox = new HBox(10);
	    rotWordBox.getChildren().add(originalColumnArray);
	    rotWordBox.setAlignment(Pos.CENTER);

	    PauseTransition initialDelay = new PauseTransition(Duration.seconds(2));

	    initialDelay.setOnFinished(event -> {
		    rotWordBox.getChildren().add(rotWordLabel);

		    PauseTransition rotWordDelay = new PauseTransition(Duration.seconds(2));

		    rotWordDelay.setOnFinished(e -> {
			    rotWordBox.getChildren().clear();
			    rotWordBox.getChildren().addAll(rotWordColumnArray, rotWordLabel);

			    PauseTransition delay = new PauseTransition(Duration.seconds(2));

			    delay.setOnFinished(ev -> {
				    rotWordBox.getChildren().remove(rotWordLabel);
			    });

			    delay.play();
		    });

		    rotWordDelay.play();
	    });

	    initialDelay.play();

	    return rotWordBox;
	}

	private HBox sBoxColumnSubstitution(int[] sBoxColumn, String originalStyle) {
	    sBoxColumn = AES.rotWord(sBoxColumn);
	    VBox originalColumnArray = Components.createSingleColumnArray(sBoxColumn, originalStyle);
	    VBox sBoxMatrix = Components.createSBoxMatrixGrid("SBOX", sBoxCells);
	    sBoxColumn = AES.substitutedRotWordColumn(sBoxColumn);
	    VBox sBoxColumnArray = Components.createSingleColumnArray(sBoxColumn, "plain-matrix-cell-substituted");

	    Label sBoxLabel = Components.getDefaultLabel("SubBytes", true, 18);

	    HBox sBoxBox = new HBox(10);
	    sBoxBox.getChildren().add(originalColumnArray);
	    sBoxBox.setAlignment(Pos.CENTER);

	    PauseTransition initialDelay = new PauseTransition(Duration.seconds(2));

	    initialDelay.setOnFinished(event -> {
		    sBoxBox.getChildren().addAll(sBoxLabel, sBoxMatrix);

		    PauseTransition subBytesDelay = new PauseTransition(Duration.seconds(2));

		    subBytesDelay.setOnFinished(e -> {
			    sBoxBox.getChildren().clear();
			    sBoxBox.getChildren().addAll(sBoxColumnArray, sBoxLabel, sBoxMatrix);

			    PauseTransition delay = new PauseTransition(Duration.seconds(2));

			    delay.setOnFinished(ev -> {
				    sBoxBox.getChildren().removeAll(sBoxLabel, sBoxMatrix);
			    });

			    delay.play();
		    });

		    subBytesDelay.play();
	    });

	    initialDelay.play();

	    return sBoxBox;
	}

	private HBox addRconRound(int[] column, int[] resultColumn, int[] rconColumn, String originalStyle) {
	    resultColumn = AES.substitutedRotWordColumn(AES.rotWord(resultColumn));
	    VBox originalColumnArray = Components.createSingleColumnArray(column, originalStyle);
	    VBox originalResultColumnArray = Components.createSingleColumnArray(resultColumn, "plain-matrix-cell-substituted");
	    VBox rconColumnArray = Components.createSingleColumnArray(rconColumn, "plain-matrix-cell");

	    resultColumn = AES.galoisAddColumn(resultColumn, column);
	    resultColumn = AES.galoisAddColumn(resultColumn, rconColumn);
	    VBox resultColumnArray = Components.createSingleColumnArray(resultColumn, "round-key-matrix-cell");

	    Label galoisAddSymbolOne = Components.getDefaultLabel("+", true, 50);
	    Label galoisAddSymbolTwo = Components.getDefaultLabel("+", true, 50);

	    VBox galoisAddSymbolContainerOne = new VBox();
	    galoisAddSymbolContainerOne.getChildren().add(galoisAddSymbolOne);
	    galoisAddSymbolContainerOne.setAlignment(Pos.CENTER);

	    VBox galoisAddSymbolContainerTwo = new VBox();
	    galoisAddSymbolContainerTwo.getChildren().add(galoisAddSymbolTwo);
	    galoisAddSymbolContainerTwo.setAlignment(Pos.CENTER);

	    Label equalsSymbol = Components.getDefaultLabel("\u003d", true, 50);
	    VBox equalsSymbolContainer = new VBox();
	    equalsSymbolContainer.getChildren().add(equalsSymbol);
	    equalsSymbolContainer.setAlignment(Pos.CENTER);

	    HBox addRconRoundBox = new HBox(10);
	    addRconRoundBox.getChildren().addAll(originalColumnArray, galoisAddSymbolContainerOne, originalResultColumnArray);

	    PauseTransition initialDelay = new PauseTransition(Duration.seconds(3));

	    initialDelay.setOnFinished(event -> {
		    addRconRoundBox.getChildren().addAll(galoisAddSymbolContainerTwo, rconColumnArray);

		    PauseTransition addBytesDelay = new PauseTransition(Duration.seconds(3));

		    addBytesDelay.setOnFinished(e -> {
			    addRconRoundBox.getChildren().addAll(equalsSymbolContainer, resultColumnArray);

			    PauseTransition delay = new PauseTransition(Duration.seconds(3));

			    delay.setOnFinished(ev -> {
				    addRconRoundBox.getChildren().clear();
				    addRconRoundBox.getChildren().add(resultColumnArray);

			    });

			    delay.play();
		    });

		    addBytesDelay.play();
	    });

	    initialDelay.play();

	    return addRconRoundBox;

	}

	private VBox oneRoundKeyScedule(int[][] prevRoundKeyState, int[][] roundKeyState, Label[][] cells, String originalStyle, int round) {
	    int[] prevRoundFirstColumn = AES.copyColumn(prevRoundKeyState, 0);
	    int[] prevRoundColumn = AES.copyColumn(prevRoundKeyState, 3);
	    int[] roundKeyColumn = AES.copyColumn(roundKeyState, 0);
	    int[] rconColumn = AES.copyColumn(AES.RCON, round - 1);

	    VBox oneRoundKeySceduleBox = new VBox();
	    oneRoundKeySceduleBox.getChildren().add(rotWordKeyScedule(prevRoundColumn, originalStyle));

	    PauseTransition pause = new PauseTransition(Duration.seconds(6));

	    pause.setOnFinished(e -> {
		    oneRoundKeySceduleBox.getChildren().clear();
		    oneRoundKeySceduleBox.getChildren().add(sBoxColumnSubstitution(prevRoundColumn, originalStyle));

		    PauseTransition delay = new PauseTransition(Duration.seconds(6));
		    delay.setOnFinished(event -> {
			    oneRoundKeySceduleBox.getChildren().clear();
			    oneRoundKeySceduleBox.getChildren().add(addRconRound(prevRoundFirstColumn, prevRoundColumn, rconColumn, originalStyle));

		    });

		    delay.play();
	    });

	    pause.play();

	    return oneRoundKeySceduleBox;

	}

	private VBox keySceduleVisualize() {
	    keySceduleState = copyState(cipherKeyState);
	    boolean[] roundOneEmpty = new boolean[] {true, true, true, true};
	    boolean[] roundTwoEmpty = new boolean[] {true, true, true, true};
	    boolean[] roundTenEmpty = new boolean[] {true, true, true, true};

	    roundOneState = AES.getRoundKey(keySceduleState, 1);
	    roundTwoState = AES.getRoundKey(roundOneState, 2);
	    roundTenState = AES.getRoundKey(roundTwoState, 3);

	    for (int round = 4; round < 11; round++) {
		    roundTenState = AES.getRoundKey(roundTenState, round);
	    }

	    VBox cipherKeyGrid = Components.createMatrixGrid("CIPHER KEY", keySceduleCells, keySceduleState, "key-matrix-cell");
	    VBox roundOneKeyGrid = Components.createRoundKeyMatrixGrid("ROUND KEY 1", roundOneCells, roundOneState, "round-key-matrix-cell", roundOneEmpty);
	    VBox roundTwoKeyGrid = Components.createRoundKeyMatrixGrid("ROUND KEY 2", roundTwoCells, roundTwoState, "round-key-matrix-cell", roundTwoEmpty);
	    VBox roundTenKeyGrid = Components.createRoundKeyMatrixGrid("ROUND KEY 10", roundTenCells, roundTenState, "round-key-matrix-cell", roundTenEmpty);

	    Label ellipsisSymbol = Components.getDefaultLabel("\u22c5 \u22c5 \u22c5", true, 50);
	    VBox ellipsisSymbolContainer = new VBox();
	    ellipsisSymbolContainer.getChildren().add(ellipsisSymbol);
	    ellipsisSymbolContainer.setAlignment(Pos.CENTER);
	    ellipsisSymbolContainer.setPadding(new Insets(50));

	    HBox roundKeysGridBox = new HBox(10);
	    roundKeysGridBox.getChildren().addAll(cipherKeyGrid, roundOneKeyGrid, roundTwoKeyGrid, ellipsisSymbolContainer, roundTenKeyGrid);
	    roundKeysGridBox.setAlignment(Pos.CENTER);

	    VBox rconGrid = Components.createRconMatrixGrid("RCON", rconCells, "plain-matrix-cell");
	    HBox rconGridBox = new HBox();
	    rconGridBox.getChildren().add(rconGrid);
	    rconGridBox.setAlignment(Pos.CENTER);

	    VBox keySceduleBox = new VBox(100);
	    keySceduleBox.getChildren().addAll(roundKeysGridBox, rconGridBox);

	    for (int col = 0; col < 4; col++) {

		    VBox oneRoundKeySceduleBox = oneRoundKeyScedule(keySceduleState, roundOneState, roundOneCells, "key-matrix-cell", 1);

		    PauseTransition delay = new PauseTransition(Duration.seconds(2));

		    delay.setOnFinished(e -> {
			    keySceduleBox.getChildren().clear();
			    keySceduleBox.getChildren().addAll(roundKeysGridBox, oneRoundKeySceduleBox, rconGridBox);
		    });

		    delay.play();

		    roundOneEmpty[col] = false;
		    roundOneKeyGrid = Components.createRoundKeyMatrixGrid("ROUND KEY 1", roundOneCells, roundOneState, "round-key-matrix-cell", roundOneEmpty);
		    keySceduleBox.getChildren().clear();
		    keySceduleBox.getChildren().addAll(roundKeysGridBox, rconGridBox);
	    }

	    return keySceduleBox;

	}

	private void visualize(HBox matrixBox) {
		clearValidation();

		String plaintext = inputField.getText();
		String key = keyField.getText();

		try {
			plainTextState = AES.bytesToMatrix(plaintext);
		} catch (IllegalArgumentException ex) {
			showValidationError(inputField, "Invalid plaintext length. AES requires exactly 16 characters.");
			return;
		}

		try {
			cipherKeyState = AES.bytesToMatrix(key);
		} catch (IllegalArgumentException ex) {
			showValidationError(keyField, "Invalid key length. AES requires exactly 16 characters.");
			return;
		}

		matrixBox.getChildren().clear();
		encryptButton.setDisable(true);

		roundKeyState = AES.getRoundKey(cipherKeyState, 1);

		List<Supplier<Node>> steps = List.of(
			this::addCipherKeyVisualize,
			this::subBytesVisualize,
			this::shiftRowsVisualize,
			this::mixColumnsVisualize,
			this::addRoundKeyVisualize,
			this::allRoundsVisualize
		);

		final int[] index = {0};

		Runnable[] runner = new Runnable[1];

		runner[0] = () -> {

			if (index[0] >= steps.size()) {
				encryptButton.setDisable(false);
				return;
			}

			matrixBox.getChildren().clear();
			matrixBox.getChildren().add(steps.get(index[0]).get());

			index[0]++;

			PauseTransition waitForCompletion = new PauseTransition(Duration.seconds(30));

			waitForCompletion.setOnFinished(e -> {
				runner[0].run();
			});

			waitForCompletion.play();
		};

		runner[0].run();
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

		validationMessage = new Label();
		validationMessage.getStyleClass().add("validation-message");
		validationMessage.setVisible(false);

		VBox textBox = new VBox(20);
		textBox.getChildren().addAll(inputBox, keyBox, validationMessage);

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
		matrixBox.setAlignment(Pos.CENTER);
		encryptButton.setOnAction(e -> visualize(matrixBox));

		VBox section = new VBox(40);
		section.getChildren().addAll(controls, matrixBox);

		return section;
	}
}
