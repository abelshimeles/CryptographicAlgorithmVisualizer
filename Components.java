import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Components { 
	public static final String DEFAULT_FONT = "Inter";

	public static Label getDefaultLabel(String value, boolean bold, double fontSize) {
		Label label = new Label(value);

		if (bold) {
			label.setFont(Font.font(DEFAULT_FONT, FontWeight.BOLD, fontSize));
		} else {
			label.setFont(Font.font(DEFAULT_FONT, fontSize));
		}

		return label;
	}

	public static HBox getColoredMessageLabel(String value, boolean bold, double fontSize) {
                Label labelMessage = getDefaultLabel(value, bold, fontSize);
                labelMessage.getStyleClass().add("colored-text");
                HBox labelMessageContainer = new HBox();
                labelMessageContainer.getChildren().add(labelMessage);
                labelMessageContainer.setAlignment(Pos.CENTER);

                return labelMessageContainer;

        }

	public static HBox createAlgorithmSelector(Consumer<String> onSelection, String... options) {
		HBox container = new HBox(10);
		container.setAlignment(Pos.CENTER);

		ToggleGroup group = new ToggleGroup();
		List<ToggleButton> buttons = new ArrayList<>();

		for (String option : options) {
			ToggleButton button = new ToggleButton(option);
			button.setToggleGroup(group);
			button.getStyleClass().add("default-tab");
			button.setPrefHeight(42);

			buttons.add(button);
			container.getChildren().add(button);

		}

		group.selectedToggleProperty().addListener(
			(obs, oldToggle, newToggle) -> {
				for (ToggleButton button : buttons) {
					button.getStyleClass().remove("active-tab");
				}

				if (newToggle != null) {
					ToggleButton selectedButton = (ToggleButton) newToggle;
					selectedButton.getStyleClass().add("active-tab");

					onSelection.accept(
						selectedButton.getText()
					);
				}
			}
		);

		if (!buttons.isEmpty()) {
			buttons.get(0).setSelected(true);
		}

		return container;
	}


	public static Button getDefaultButton(String value) {
		Button button = new Button(value);
		button.getStyleClass().add("default-button");
		button.setPrefWidth(125);
		button.setPrefHeight(50);

		button.setOnMouseEntered(
			e -> {
				button.getStyleClass().remove("default-button");
				button.getStyleClass().add("active-button");
			}
		);
		button.setOnMouseExited(
			e -> {
				button.getStyleClass().remove("active-button");
				button.getStyleClass().add("default-button");
			}
		);

		return button;
	}


	public static GridPane getAlphabetPane(Label[] alphabetLabels) {
		GridPane alphabetPane = new GridPane();
		alphabetPane.setAlignment(Pos.CENTER);
		alphabetPane.setHgap(15);
		alphabetPane.setVgap(15);

		for (int i = 0; i < 26; i++) {
			char ch = (char) (0x41 + i);
			Label letter = new Label(String.valueOf(ch));
			letter.setPrefSize(35, 35);
			letter.setAlignment(Pos.CENTER);
			letter.getStyleClass().add("default-letter");

			int row = i % 13;
			int column = i / 13;

			alphabetLabels[i] = letter;
			alphabetPane.add(letter, row, column);
		}

		return alphabetPane;
	}

	public static void resetHighlights(Label[] alphabetLabels) {
		for (Label label : alphabetLabels) {
			label.getStyleClass().remove("input-letter");
			label.getStyleClass().remove("output-letter");
			label.getStyleClass().add("default-letter");
		}
	}

	public static VBox getDiffieHellmanParameter(TextField labelField, String title, String symbol) {
                Label labelMessage = Components.getDefaultLabel(title, false, 18);
                Label labelSymbol = Components.getDefaultLabel(symbol, false, 18);
                labelSymbol.getStyleClass().add("colored-text");

                HBox labelMessageContainer = new HBox(5);
                labelMessageContainer.getChildren().addAll(labelMessage, labelSymbol);
                labelMessageContainer.setAlignment(Pos.CENTER);

                labelField.setPrefWidth(110);
                labelField.setPrefHeight(40);
                labelField.getStyleClass().add("input-field");
		labelField.setAlignment(Pos.CENTER);

                VBox labelContainer = new VBox(10);
                labelContainer.getChildren().addAll(labelMessageContainer, labelField);

		return labelContainer;
	} 

	public static VBox getKeyCalculation(String[] variables, long[] values, long answer) {
                Label userLabel = Components.getDefaultLabel(variables[0] + " Computes", false, 18);
		HBox userLabelContainer = new HBox();
		userLabelContainer.getChildren().add(userLabel);
		userLabelContainer.setAlignment(Pos.CENTER);

		Label calculationTheory = getDefaultLabel(String.format("%s = %s^%s mod p", variables[1], variables[2], variables[3]), false, 18);
		calculationTheory.getStyleClass().add("calculation-theory");
		HBox calculationTheoryContainer = new HBox();
		calculationTheoryContainer.getChildren().add(calculationTheory);
		calculationTheoryContainer.setAlignment(Pos.CENTER);

		Label calculation = getDefaultLabel(String.format("A = %d^%d mod %d", values[0], values[1], values[2]), false, 18);
		HBox calculationLabelContainer = new HBox();
		calculationLabelContainer.getChildren().add(calculation);
		calculationLabelContainer.getStyleClass().add("output-label");
		calculationLabelContainer.setAlignment(Pos.CENTER);
		calculationLabelContainer.setPadding(new Insets(10));

		Label result = getDefaultLabel(String.valueOf(answer), true, 24);
		HBox resultContainer = new HBox();
		resultContainer.getChildren().add(result);
		resultContainer.setAlignment(Pos.CENTER);

		VBox calculationContainer = new VBox(20);
		calculationContainer.getChildren().addAll(userLabelContainer, calculationTheoryContainer, calculationLabelContainer, resultContainer);
		calculationContainer.setPadding(new Insets(10));

		return calculationContainer;

	}

	public static HBox getKeyCalculationSection(VBox calculationOne, VBox calculationTwo) {
                VBox line = new VBox();
                line.setPrefWidth(4);
                line.setPrefHeight(100);
                line.getStyleClass().add("line-default");
                line.setAlignment(Pos.CENTER);

                HBox calculationsSectionContainer = new HBox(30);
               	calculationsSectionContainer.getChildren().addAll(calculationOne, line, calculationTwo);
                	calculationsSectionContainer.setAlignment(Pos.CENTER);

		return calculationsSectionContainer;
	}

	public static HBox getKeyExchangeSection(String sender, String reciever, String variable, long value) {
		Label senderLabel = getDefaultLabel(sender + " sends", false, 14);
		senderLabel.getStyleClass().add("calculation-theory");
		HBox senderLabelContainer = new HBox();
		senderLabelContainer.getChildren().add(senderLabel);
		senderLabelContainer.setAlignment(Pos.CENTER);
		
		Label senderVariableLabel = getDefaultLabel(String.format("%s = %d", variable, value), false, 18);
		HBox senderVariableContainer = new HBox();
		senderVariableContainer.getChildren().add(senderVariableLabel);
		senderVariableContainer.setAlignment(Pos.CENTER);

		VBox senderContainer = new VBox(5);
		senderContainer.getChildren().addAll(senderLabelContainer, senderVariableContainer);
		senderContainer.setAlignment(Pos.CENTER);

		Label arrow = getDefaultLabel("\u2192", true, 70);
		arrow.setAlignment(Pos.CENTER);
		VBox arrowContainer = new VBox();
		arrowContainer.getChildren().add(arrow);
		arrowContainer.setAlignment(Pos.CENTER);

		Label recieverLabel = getDefaultLabel(reciever, false, 18);
		VBox recieverContainer = new VBox();
		recieverContainer.getChildren().add(recieverLabel);
		recieverContainer.setAlignment(Pos.CENTER);

		HBox exchangeContainer = new HBox(60);
		exchangeContainer.getChildren().addAll(senderContainer, arrowContainer, recieverContainer);
		exchangeContainer.setAlignment(Pos.CENTER);

		return exchangeContainer;

	}

	public static Label getCell(int value, int w, int h, String style) {
		Label cell = new Label(toHex(value));
		cell.setMinSize(w, h);
		cell.getStyleClass().add(style);

		return cell;
	
	}

	public static Label getEmptyCell(int w, int h, String style) {
		Label cell = new Label("");
		cell.setMinSize(w, h);
		cell.getStyleClass().add(style);

		return cell;
	
	}
	
	public static VBox createMatrixGrid(String title, Label[][] cells, int[][] matrix, String style) {
		Label gridLabel = getDefaultLabel(title, false, 18);
		HBox gridLabelBox = new HBox();
		gridLabelBox.getChildren().add(gridLabel);
		gridLabelBox.setAlignment(Pos.CENTER);

		GridPane grid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getCell(matrix[row][col], 60, 60, style);
				cells[row][col] = cell;
				grid.add(cell, col, row+1);
			}
		}

		VBox gridBox = new VBox(15);
		gridBox.getChildren().addAll(gridLabelBox, grid);

		return gridBox;
	}

	public static VBox createSBoxMatrixGrid(String title, Label[][] cells) {
		Label gridLabel = getDefaultLabel("SBOX", false, 18);
		HBox gridLabelBox = new HBox();
		gridLabelBox.getChildren().add(gridLabel);
		gridLabelBox.setAlignment(Pos.CENTER);

		GridPane grid = new GridPane();

		int[][] SBOX = AES.SBOX;

		Label lookupMessageCell = new Label("HEX");
		lookupMessageCell.setMinSize(40, 40);
		lookupMessageCell.getStyleClass().add("sbox-matrix-cell");
		cells[0][0] = lookupMessageCell;
		grid.add(lookupMessageCell, 0, 0);

		for (int col = 1; col < 17; col++) {
			Label cell = new Label(String.format("%X", col - 1));
			cell.setMinSize(30, 30);
			cell.getStyleClass().add("sbox-matrix-column-lookup-cell");
			cells[0][col] = cell;
			grid.add(cell, col, 0);
		}

		for (int row = 1; row < 17; row++) {
			Label cell = new Label(String.format("%X", row-1));
			cell.setMinSize(30, 30);
			cell.getStyleClass().add("sbox-matrix-row-lookup-cell");
			cells[row][0] = cell;
			grid.add(cell, 0, row);
		}

		for (int row = 1; row < 17; row++) {
			for (int col = 1; col < 17; col++) {
				Label cell = new Label(toHex(SBOX[row - 1][col - 1]));
				cell.setMinSize(30, 30);
				cell.getStyleClass().add("sbox-matrix-cell");
				cells[row][col] = cell;
				grid.add(cell, col, row);
			}
		}

		VBox gridBox = new VBox(15);
		gridBox.getChildren().addAll(gridLabelBox, grid);

		return gridBox;
	}

	public static HBox createMixColumnMatrix() {
		int[][] mixColumnMatrix = AES.MIX_COLUMN;

		GridPane grid = new GridPane();
		grid.setHgap(25);
		grid.setVgap(20);
		grid.setAlignment(Pos.CENTER);

		for (int row = 0; row < mixColumnMatrix.length; row++) {
			for (int col = 0; col < mixColumnMatrix[row].length; col++) {
				Label label = getDefaultLabel(toHex(mixColumnMatrix[row][col]), false, 18);
				grid.add(label, col, row);
			}
		}

		VBox leftBracket = new VBox();
		leftBracket.setMinWidth(20);
		leftBracket.setPrefHeight(30);
		leftBracket.getStyleClass().add("left-bracket");

		VBox rightBracket = new VBox();
		rightBracket.setMinWidth(20);
		rightBracket.setPrefHeight(30);
		rightBracket.getStyleClass().add("right-bracket");

		HBox matrix = new HBox(10);
		matrix.getChildren().addAll(leftBracket, grid, rightBracket);
		matrix.setAlignment(Pos.CENTER);

		return matrix;
	}

	public static VBox createSingleColumnArray(int[] column, String style) {
		GridPane grid = new GridPane();

		for (int row = 0; row < 4; row++) {
			Label cell = getCell(column[row], 60, 60, style);
			grid.add(cell, 0, row+1);
		}

		VBox gridBox = new VBox(15);
		gridBox.getChildren().addAll(grid);

		return gridBox;
		
	}

	public static VBox updatedColumnMatrixGrid(String title, Label[][] cells, int[][] matrix, String newStyle, String oldStyle, int columns) {
		Label gridLabel = getDefaultLabel(title, false, 18);
		HBox gridLabelBox = new HBox();
		gridLabelBox.getChildren().add(gridLabel);
		gridLabelBox.setAlignment(Pos.CENTER);

		GridPane grid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				String style = col <= columns ? newStyle : oldStyle;
				Label cell = getCell(matrix[row][col], 60, 60, style);
				cells[row][col] = cell;
				grid.add(cell, col, row+1);
			}
		}

		VBox gridBox = new VBox(15);
		gridBox.getChildren().addAll(gridLabelBox, grid);

		return gridBox;
	}

	public static HBox createInputRound(int[][] state, int[][] cipherKey) {
		Label inputLabel = getDefaultLabel("INITIAL", true, 18);
		VBox inputLabelBox = new VBox();
		inputLabelBox.getChildren().add(inputLabel);
		inputLabelBox.setAlignment(Pos.CENTER);

		Label startLabel = Components.getDefaultLabel("START ROUND", false, 18);
		HBox startLabelBox = new HBox();
		startLabelBox.getChildren().add(startLabel);
		startLabelBox.setAlignment(Pos.CENTER);
		
                Label subBytesLabel = Components.getDefaultLabel("SUBBYTES", false, 18);
		HBox subBytesLabelBox = new HBox();
		subBytesLabelBox.getChildren().add(subBytesLabel);
		subBytesLabelBox.setAlignment(Pos.CENTER);

                Label shiftRowLabel = Components.getDefaultLabel("SHIFTROWS", false, 18);
		HBox shiftRowLabelBox = new HBox();
		shiftRowLabelBox.getChildren().add(shiftRowLabel);
		shiftRowLabelBox.setAlignment(Pos.CENTER);

                Label mixColumnLabel = Components.getDefaultLabel("MIXCOLUMNS", false, 18);
		HBox mixColumnLabelBox = new HBox();
		mixColumnLabelBox.getChildren().add(mixColumnLabel);
		mixColumnLabelBox.setAlignment(Pos.CENTER);

                Label roundKeyLabel = Components.getDefaultLabel("ROUND KEY", false, 18);
		HBox roundKeyLabelBox = new HBox();
		roundKeyLabelBox.getChildren().add(roundKeyLabel);
		roundKeyLabelBox.setAlignment(Pos.CENTER);

		Label galoisAddSymbol = Components.getDefaultLabel("+", true, 50);
                VBox galoisAddSymbolContainer = new VBox();
                galoisAddSymbolContainer.getChildren().add(galoisAddSymbol);
                galoisAddSymbolContainer.setAlignment(Pos.CENTER);

                Label equalsSymbol = Components.getDefaultLabel("\u003d", true, 50);
                VBox equalsSymbolContainer = new VBox();
                equalsSymbolContainer.getChildren().add(equalsSymbol);
                equalsSymbolContainer.setAlignment(Pos.CENTER);

		GridPane stateGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getCell(state[row][col], 40, 40, "plain-matrix-cell");
				stateGrid.add(cell, col, row+1);
			}
		}

		VBox stateBox = new VBox(10);
		stateBox.getChildren().addAll(startLabelBox, stateGrid);

		GridPane subByteGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getEmptyCell(40, 40, "plain-matrix-cell-empty");
				subByteGrid.add(cell, col, row+1);
			}
		}

		VBox subByteBox = new VBox(10);
		subByteBox.getChildren().addAll(subBytesLabelBox, subByteGrid);

		GridPane shiftRowGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getEmptyCell(40, 40, "plain-matrix-cell-empty");
				shiftRowGrid.add(cell, col, row+1);
			}
		}

		
		VBox shiftRowBox = new VBox(10);
		shiftRowBox.getChildren().addAll(shiftRowLabelBox, shiftRowGrid);

		GridPane mixColumnGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getEmptyCell(40, 40, "plain-matrix-cell-empty");
				mixColumnGrid.add(cell, col, row+1);
			}
		}

		VBox mixColumnBox = new VBox(10);
		mixColumnBox.getChildren().addAll(mixColumnLabelBox, mixColumnGrid);

		GridPane roundKeyGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getCell(cipherKey[row][col], 40, 40, "key-matrix-cell");
				roundKeyGrid.add(cell, col, row+1);
			}
		}

		VBox roundKeyBox = new VBox(10);
		roundKeyBox.getChildren().addAll(roundKeyLabelBox, roundKeyGrid);

		HBox gridBox = new HBox(20);
		gridBox.getChildren().addAll(
				inputLabelBox, 
				stateBox, 
				subByteBox, 
				shiftRowBox, 
				mixColumnBox, 
				galoisAddSymbolContainer, 
				roundKeyBox, 
				equalsSymbolContainer
		);
		gridBox.setAlignment(Pos.CENTER);

		return gridBox;
	}

	public static HBox createRounds(String round, int[][] state, int[][] subByte, int[][] shiftRow, int[][] mixColumn, int[][] roundKey) {
		Label roundLabel = getDefaultLabel(round, true, 18);
		VBox roundLabelBox = new VBox();
		roundLabelBox.getChildren().add(roundLabel);
		roundLabelBox.setAlignment(Pos.CENTER);

		Label galoisAddSymbol = Components.getDefaultLabel("+", true, 50);
                VBox galoisAddSymbolContainer = new VBox();
                galoisAddSymbolContainer.getChildren().add(galoisAddSymbol);
                galoisAddSymbolContainer.setAlignment(Pos.CENTER);

                Label equalsSymbol = Components.getDefaultLabel("\u003d", true, 50);
                VBox equalsSymbolContainer = new VBox();
                equalsSymbolContainer.getChildren().add(equalsSymbol);
                equalsSymbolContainer.setAlignment(Pos.CENTER);

		GridPane stateGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getCell(state[row][col], 40, 40, "plain-matrix-cell");
				stateGrid.add(cell, col, row+1);
			}
		}

		GridPane subByteGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getCell(subByte[row][col], 40, 40, "plain-matrix-cell");
				subByteGrid.add(cell, col, row+1);
			}
		}

		GridPane shiftRowGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getCell(shiftRow[row][col], 40, 40, "plain-matrix-cell");
				shiftRowGrid.add(cell, col, row+1);
			}
		}
		
		GridPane mixColumnGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getCell(mixColumn[row][col], 40, 40, "plain-matrix-cell");
				mixColumnGrid.add(cell, col, row+1);
			}
		}

		GridPane roundKeyGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getCell(roundKey[row][col], 40, 40, "round-key-matrix-cell");
				roundKeyGrid.add(cell, col, row+1);
			}
		}

		HBox gridBox = new HBox(20);
		gridBox.getChildren().addAll(
				roundLabelBox, 
				stateGrid, 
				subByteGrid, 
				shiftRowGrid, 
				mixColumnGrid, 
				galoisAddSymbolContainer, 
				roundKeyGrid, 
				equalsSymbolContainer
		);
		gridBox.setAlignment(Pos.CENTER);

		return gridBox;
	}

	public static HBox createFinalRound(int[][] state, int[][] subByte, int[][] shiftRow, int[][] roundKey) {
		Label roundLabel = getDefaultLabel("ROUND 10", true, 18);
		VBox roundLabelBox = new VBox();
		roundLabelBox.getChildren().add(roundLabel);
		roundLabelBox.setAlignment(Pos.CENTER);

		Label galoisAddSymbol = Components.getDefaultLabel("+", true, 50);
                VBox galoisAddSymbolContainer = new VBox();
                galoisAddSymbolContainer.getChildren().add(galoisAddSymbol);
                galoisAddSymbolContainer.setAlignment(Pos.CENTER);

                Label equalsSymbol = Components.getDefaultLabel("\u003d", true, 50);
                VBox equalsSymbolContainer = new VBox();
                equalsSymbolContainer.getChildren().add(equalsSymbol);
                equalsSymbolContainer.setAlignment(Pos.CENTER);

		GridPane stateGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getCell(state[row][col], 40, 40, "plain-matrix-cell");
				stateGrid.add(cell, col, row+1);
			}
		}

		GridPane subByteGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getCell(subByte[row][col], 40, 40, "plain-matrix-cell");
				subByteGrid.add(cell, col, row+1);
			}
		}

		GridPane shiftRowGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getCell(shiftRow[row][col], 40, 40, "plain-matrix-cell");
				shiftRowGrid.add(cell, col, row+1);
			}
		}
		
		GridPane mixColumnGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getEmptyCell(40, 40, "plain-matrix-cell-empty");
				mixColumnGrid.add(cell, col, row+1);
			}
		}

		GridPane roundKeyGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getCell(roundKey[row][col], 40, 40, "round-key-matrix-cell");
				roundKeyGrid.add(cell, col, row+1);
			}
		}

		HBox gridBox = new HBox(20);
		gridBox.getChildren().addAll(
				roundLabelBox, 
				stateGrid, 
				subByteGrid, 
				shiftRowGrid, 
				mixColumnGrid, 
				galoisAddSymbolContainer, 
				roundKeyGrid, 
				equalsSymbolContainer
		);
		gridBox.setAlignment(Pos.CENTER);

		return gridBox;
	}

	public static HBox getAESCipherText(int[][] cipherTextState) {
		Label cipherTextLabel = getDefaultLabel("OUTPUT", true, 18);
		VBox cipherTextLabelBox = new VBox();
		cipherTextLabelBox.getChildren().add(cipherTextLabel);
		cipherTextLabelBox.setAlignment(Pos.CENTER);

		GridPane cipherTextGrid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = getCell(cipherTextState[row][col], 40, 40, "plain-matrix-cell-substituted");
				cipherTextGrid.add(cell, col, row+1);
			}
		}

		HBox gridBox = new HBox(20);
		gridBox.getChildren().addAll(cipherTextLabelBox, cipherTextGrid);
		gridBox.setAlignment(Pos.CENTER);

		return gridBox;
	}

	public static String toHex(int value) {
		return String.format("%02x", value & 0xFF).toUpperCase();
	}
}
