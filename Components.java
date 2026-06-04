import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
}
