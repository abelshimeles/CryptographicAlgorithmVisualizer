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

public class CaesarCipherSection implements Section { 
	private TextField inputField;
	private Spinner<Integer> spinner;
	private Label outputLabel;
	private Label[] alphabetLabels = new Label[26];
	private Button encryptButton;
	private Button decryptButton;
	private Button resetButton;

	private void visualize(boolean encrypt) {
		Components.resetHighlights(alphabetLabels);
		outputLabel.setText("");

		encryptButton.setDisable(true);
		decryptButton.setDisable(true);
		resetButton.setDisable(true);
		
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
						Components.resetHighlights(alphabetLabels);
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
					Components.resetHighlights(alphabetLabels);
					alphabetLabels[original].getStyleClass().remove("default-letter");
					alphabetLabels[original].getStyleClass().remove("output-letter");
					alphabetLabels[original].getStyleClass().add("input-letter");
				}
			);

			KeyFrame outputFrame = new KeyFrame(
				Duration.seconds(1.5 * currentStep + 0.5),
				e -> {
					Components.resetHighlights(alphabetLabels);
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
			e -> {
				Components.resetHighlights(alphabetLabels);
				encryptButton.setDisable(false);
				decryptButton.setDisable(false);
				resetButton.setDisable(false);
			}
		    )
		);

		timeline.play();
	}

	@Override

	public VBox getSection() {
		Label message = Components.getDefaultLabel("MESSAGE", false, 18);
	
		inputField = new TextField();
		inputField.setPrefWidth(630);
		inputField.setPrefHeight(50);
		inputField.getStyleClass().add("input-field");

		VBox textBox = new VBox(10);
		textBox.getChildren().addAll(message, inputField);

		spinner = new Spinner<>();
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25, 13);

		Label spinnerMessage = Components.getDefaultLabel("SHIFT VALUE", false, 18);

		spinner.setValueFactory(valueFactory);
		spinner.setEditable(false);
		spinner.setPrefWidth(125);
		spinner.setPrefHeight(45);
		spinner.getStyleClass().add("default-button");

		encryptButton = Components.getDefaultButton("ENCRYPT");
		encryptButton.setOnAction(e -> visualize(true));

		decryptButton = Components.getDefaultButton("DECRYPT");
		decryptButton.setOnAction(e -> visualize(false));

		resetButton = Components.getDefaultButton("RESET");
		resetButton.setOnAction(
			e -> {
				inputField.setText("");
				outputLabel.setText("");
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

		Label alphabetLabel = Components.getDefaultLabel("ALPHABET REFERENCE", false, 18);

		GridPane alphabetPane = Components.getAlphabetPane(alphabetLabels);

		VBox alphabetBox = new VBox(15);
		alphabetBox.getChildren().addAll(alphabetLabel, alphabetPane);
		alphabetBox.setAlignment(Pos.CENTER);

		Label resultText = Components.getDefaultLabel("RESULT", false, 18);
		outputLabel = Components.getDefaultLabel("", false, 18);

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

		VBox section = new VBox(40);
		section.getChildren().addAll(userFunctionality, alphabetBox, resultContainer);
		
		return section;
	}
}
