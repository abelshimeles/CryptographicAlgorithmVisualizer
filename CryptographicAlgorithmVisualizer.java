import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class CryptographicAlgorithmVisualizer extends Application {
	private TextField inputField;
	private Spinner<Integer> spinner;
	private Label outputLabel;
	private Label[] alphabetLabels = new Label[26];
	private final String DEFAULT_FONT = "Inter";
	private final String DEFAULT_FONT_COLOR = "#152238";

	private Label getDefaultLabel(String value, boolean bold, double fontSize) {
		Label label = new Label(value);

		if (bold) {
			label.setFont(Font.font(DEFAULT_FONT, FontWeight.BOLD, fontSize));
		} else {
			label.setFont(Font.font(DEFAULT_FONT, fontSize));
		}

		return label;
	}

	private HBox createAlgorithmSelector(String... options) {
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

		// TODO Make the choice buttons change they style when hovered over without affecting the clicked effect

		group.selectedToggleProperty().addListener(
			(obs, oldToggle, newToggle) -> {
				for (ToggleButton button : buttons) {
					button.getStyleClass().remove("active-tab");
				}

				if (newToggle != null) {
					((ToggleButton) newToggle).getStyleClass().add("active-tab");
				}
			}
		);

		if (!buttons.isEmpty()) {
			buttons.get(0).setSelected(true);
		}

		return container;
	}


	private Button getDefaultButton(String value) {
		Button button = new Button(value);
		button.getStyleClass().add("default-button");
		button.setPrefWidth(125);
		button.setPrefHeight(50);

		return button;
	}


	private GridPane getAlphabetPane() {
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

	private void resetHighlights() {
		for (Label label : alphabetLabels) {
			label.getStyleClass().remove("input-letter");
			label.getStyleClass().remove("output-letter");
			label.getStyleClass().add("default-letter");
		}
	}

	private void run(boolean encrypt) {
		resetHighlights();
		outputLabel.setText("");
		String text = inputField.getText().toUpperCase();
		int shift = (int) spinner.getValue();

		Timeline timeline = new Timeline();
		StringBuilder result = new StringBuilder();;

		int animationStep = 0;
		int currentStep;

		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			int original;

			if (Character.isLetter(ch)) {
				original = ch - 0x41;
			} else {
				currentStep = animationStep;

				KeyFrame nonLetterFrame = new KeyFrame(
					Duration.seconds(currentStep),
					e -> {
						resetHighlights();
						result.append(ch);
						outputLabel.setText(result.toString());
					}
				);

				timeline.getKeyFrames().add(nonLetterFrame);

				animationStep++;
				continue;
			}

			char changed;

			if (encrypt) {
				changed = CaesarCipher.encrypt(ch, shift);
			} else {
				changed = CaesarCipher.decrypt(ch, shift);
			}

			int shifted = changed - 0x41;
			currentStep = animationStep;

			KeyFrame inputFrame = new KeyFrame(
				Duration.seconds(1.5 * currentStep),
				e -> {
					resetHighlights();
					alphabetLabels[original].getStyleClass().remove("default-letter");
					alphabetLabels[original].getStyleClass().remove("output-letter");
					alphabetLabels[original].getStyleClass().add("input-letter");
				}
			);

			KeyFrame outputFrame = new KeyFrame(
				Duration.seconds(1.5 * currentStep + 0.5),
				e -> {
					resetHighlights();
					alphabetLabels[shifted].getStyleClass().remove("default-letter");
					alphabetLabels[shifted].getStyleClass().remove("input-letter");
					alphabetLabels[shifted].getStyleClass().add("output-letter");
					result.append(changed);
					outputLabel.setText(result.toString());
				}
			);

			timeline.getKeyFrames().addAll(inputFrame, outputFrame);
			animationStep++;
		}
		Duration endTime = timeline.getTotalDuration();

		timeline.getKeyFrames().add(
		    new KeyFrame(
			endTime.add(Duration.seconds(1)),
			e -> resetHighlights()
		    )
		);

		timeline.play();
	}

	@Override

	public void start(Stage stage) {
		// Title
		Label title = getDefaultLabel("Cryptographic Algorithm Visualizer", true, 36);

		HBox titleBox = new HBox();
		titleBox.getChildren().add(title);
		titleBox.setAlignment(Pos.CENTER);

		HBox algorithmsSelectorContainer = createAlgorithmSelector("Caesar Cipher", "Diffie-Hellman", "ECC");

		Label message = getDefaultLabel("MESSAGE", false, 18);
	
		inputField = new TextField();
		inputField.setPrefWidth(630);
		inputField.setPrefHeight(50);
		inputField.getStyleClass().add("input-field");

		VBox textBox = new VBox(10);
		textBox.getChildren().addAll(message, inputField);

		spinner = new Spinner<>();
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25, 13);

		Label spinnerMessage = getDefaultLabel("SHIFT VALUE", false, 18);

		spinner.setValueFactory(valueFactory);
		spinner.setEditable(false);
		spinner.setPrefWidth(125);
		spinner.setPrefHeight(45);
		spinner.getStyleClass().add("default-button");

		Button encryptButton = getDefaultButton("ENCRYPT");
		encryptButton.setOnAction(e -> run(true));
		encryptButton.setOnMouseEntered(
			e -> {
				encryptButton.getStyleClass().remove("default-button");
				encryptButton.getStyleClass().add("active-button");
			}
		);
		encryptButton.setOnMouseExited(
			e -> {
				encryptButton.getStyleClass().remove("active-button");
				encryptButton.getStyleClass().add("default-button");
			}
		);

		Button decryptButton = getDefaultButton("DECRYPT");
		decryptButton.setOnAction(e -> run(false));
		decryptButton.setOnMouseEntered(
			e -> {
				decryptButton.getStyleClass().remove("default-button");
				decryptButton.getStyleClass().add("active-button");
			}
		);
		decryptButton.setOnMouseExited(
			e -> {
				decryptButton.getStyleClass().remove("active-button");
				decryptButton.getStyleClass().add("default-button");
			}
		);

		Button resetButton = getDefaultButton("RESET");
		resetButton.setOnAction(
			e -> {
				inputField.setText("");
				outputLabel.setText("");
			}		
		);
		resetButton.setOnMouseEntered(
			e -> {
				resetButton.getStyleClass().remove("default-button");
				resetButton.getStyleClass().add("active-button");
			}
		);
		resetButton.setOnMouseExited(
			e -> {
				resetButton.getStyleClass().remove("active-button");
				resetButton.getStyleClass().add("default-button");
			}
		);

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

		// Alphabet Section
		Label alphabetLabel = getDefaultLabel("ALPHABET REFERENCE", false, 18);

		GridPane alphabetPane = getAlphabetPane();

		VBox alphabetBox = new VBox(15);
		alphabetBox.getChildren().addAll(alphabetLabel, alphabetPane);
		alphabetBox.setAlignment(Pos.CENTER);

		Label resultText = getDefaultLabel("RESULT", false, 18);
		outputLabel = getDefaultLabel("", false, 18);

		VBox outputLabelBox = new VBox();
		outputLabelBox.getChildren().add(outputLabel);
		outputLabelBox.setPadding(new Insets(10));

		StackPane outputPane = new StackPane(outputLabelBox);
		outputPane.setPrefWidth(630);
		outputPane.setPrefHeight(120);
		
		outputPane.getStyleClass().add("output-label");

		VBox resultBox = new VBox(10);
		resultBox.getChildren().addAll(resultText, outputPane);
		
		HBox resultContainer = new HBox();
		resultContainer.getChildren().add(resultBox);
		resultContainer.setAlignment(Pos.CENTER);

		// Root
		VBox root = new VBox(40);

		root.getChildren().addAll(titleBox, algorithmsSelectorContainer, userFunctionality, alphabetBox, resultContainer);
		root.setPadding(new Insets(25));

		// Scene
		Scene scene = new Scene(root);
		scene.getStylesheets().add("style.css");
		
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
