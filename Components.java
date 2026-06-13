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

	public static VBox createMatrixGrid(String title, Label[][] cells, int[][] matrix, String style) {
		Label gridLabel = getDefaultLabel(title, false, 18);
		HBox gridLabelBox = new HBox();
		gridLabelBox.getChildren().add(gridLabel);
		gridLabelBox.setAlignment(Pos.CENTER);

		GridPane grid = new GridPane();

		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				Label cell = new Label(toHex(matrix[row][col]));
				cell.setMinSize(50, 50);
				cell.getStyleClass().add(style);
				cells[row][col] = cell;
				grid.add(cell, col, row+1);
			}
		}

		VBox gridBox = new VBox(15);
		gridBox.getChildren().addAll(gridLabelBox, grid);

		return gridBox;
	}

	public static String toHex(int value) {
		return String.format("%02x", value & 0xFF).toUpperCase();
	}
}
