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
import javafx.scene.paint.Color;
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

	private String defaultTabStyle() {
		return	"-fx-background-color: transparent;" +
			"-fx-text-fill: #30384A;" +
			"-fx-background-radius: 18;" +
			"-fx-border-radius: 18;" +
			"-fx-padding: 7 22 7 22;" +
			"-fx-font-size: 18px;";
	}

	private String activeTabStyle() {
		return  "-fx-background-color: #172544;" +
			"-fx-text-fill: white;" +
			"-fx-background-radius: 18;" +
			"-fx-border-radius: 18;" +
			"-fx-padding: 7 22 7 22;" +
			"-fx-font-size: 18px;";
	}

	private String defaultButtonStyle() {
		return	"-fx-background-color: transparent;" +
			"-fx-border-color: #d3d3d3;" +
			"-fx-border-width: 1;" +
			"-fx-background-radius: 4;" +
			"-fx-border-radius: 4;" +
			"-fx-border-width: 2px;" +
			"-fx-text-fill: #30384A;" +
			"-fx-font-size: 14px;";
	}

	private String activeButtonStyle() {
		return	"-fx-background-color: #ff8c1a;" +
			"-fx-border-color: #ff8c1a;" +
			"-fx-border-width: 1;" +
			"-fx-background-radius: 4;" +
			"-fx-border-radius: 4;" +
			"-fx-border-width: 2px;" +
			"-fx-text-fill: white;" +
			"-fx-font-size: 14px;";
	}

	private String defaultLetterStyle() {
		return  "-fx-background-color: transparent;" +
			"-fx-border-color: #D8D8D8;" +
			"-fx-border-width: 2;" +
			"-fx-border-radius: 5;" +
			"-fx-background-radius: 5;" +
			"-fx-text-fill: #404040;" +
			"-fx-font-size: 14px;";
	}

	private String inputLetterStyle() {
		return  "-fx-background-color: transparent;" +
			"-fx-border-color: #ff9c33;" +
			"-fx-border-width: 2;" +
			"-fx-border-radius: 5;" +
			"-fx-background-radius: 5;" +
			"-fx-text-fill: #ff9c33;" +
			"-fx-font-size: 14px;";
	}

	private String outputLetterStyle() {
		return  "-fx-background-color: #152238;" +
			"-fx-border-width: 2;" +
			"-fx-border-radius: 5;" +
			"-fx-background-radius: 5;" +
			"-fx-text-fill: white;" +
			"-fx-font-size: 14px;";
	}

	private String outputLabelStyle() {
		return	"-fx-background-color: transparent;" +
			"-fx-border-color: #ff9c33;" +
			"-fx-border-width: 1;" +
			"-fx-background-radius: 4;" +
			"-fx-border-radius: 4;" +
			"-fx-border-width: 2px;" +
			"-fx-text-fill: #30384A;" +
			"-fx-font-size: 14px;";
	}

	private Label getDefaultLabel(String value, boolean bold, double fontSize) {
		Label label = new Label(value);

		if (bold) {
			label.setFont(Font.font(DEFAULT_FONT, FontWeight.BOLD, fontSize));
		} else {
			label.setFont(Font.font(DEFAULT_FONT, fontSize));
		}

		label.setStyle(String.format("-fx-text-fill: %s;", DEFAULT_FONT_COLOR));

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
			button.setStyle(defaultTabStyle());
			button.setPrefHeight(42);

			// TODO Make the choice buttons change they style when hovered over without affecting the clicked effect
			
			//button.setOnMouseEntered(
			//	e -> button.setStyle(activeTabStyle())		
			//);

			//button.setOnMouseExited(
			//	e -> button.setStyle(defaultTabStyle())		
			//);

			buttons.add(button);
			container.getChildren().add(button);

		}

		group.selectedToggleProperty().addListener(
			(obs, oldToggle, newToggle) -> {
				for(ToggleButton button: buttons) {
					button.setStyle(defaultTabStyle());
				}

				if (newToggle != null) {
					((ToggleButton) newToggle).setStyle(activeTabStyle());
				}
		});

		if (!buttons.isEmpty()) {
			buttons.get(0).setSelected(true);
			buttons.get(0).setStyle(activeTabStyle());
		}

		return container;
	}


	private Button getDefaultButton(String value) {
		Button button = new Button(value);
		button.setStyle(defaultButtonStyle());
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
			letter.setStyle(defaultLetterStyle());

			int row = i % 13;
			int column = i / 13;

			alphabetLabels[i] = letter;
			alphabetPane.add(letter, row, column);
		}

		return alphabetPane;
	}

	private void resetHighlights() {
		for (Label label : alphabetLabels) {
			label.setStyle(defaultLetterStyle());
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
					alphabetLabels[original].setStyle(inputLetterStyle());
				}
			);

			KeyFrame outputFrame = new KeyFrame(
				Duration.seconds(1.5 * currentStep + 0.5),
				e -> {
					resetHighlights();
					alphabetLabels[shifted].setStyle(outputLetterStyle());
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
		inputField.setStyle(
			"-fx-border-width: 2px;"
		);

		VBox textBox = new VBox(10);
		textBox.getChildren().addAll(message, inputField);

		spinner = new Spinner<>();
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 25, 13);

		Label spinnerMessage = getDefaultLabel("SHIFT VALUE", false, 18);

		spinner.setValueFactory(valueFactory);
		spinner.setEditable(true);
		spinner.setPrefWidth(125);
		spinner.setPrefHeight(45);
		spinner.setStyle(defaultButtonStyle());

		Button encryptButton = getDefaultButton("ENCRYPT");
		encryptButton.setOnAction(e -> run(true));
		encryptButton.setOnMouseEntered(
			e -> encryptButton.setStyle(activeButtonStyle())		
		);

		encryptButton.setOnMouseExited(
			e -> encryptButton.setStyle(defaultButtonStyle())		
		);

		Button decryptButton = getDefaultButton("DECRYPT");
		decryptButton.setOnAction(e -> run(false));
		decryptButton.setOnMouseEntered(
			e -> decryptButton.setStyle(activeButtonStyle())		
		);

		decryptButton.setOnMouseExited(
			e -> decryptButton.setStyle(defaultButtonStyle())		
		);

		Button resetButton = getDefaultButton("RESET");
		resetButton.setOnAction(
			e -> {
				inputField.setText("");
				outputLabel.setText("");
			}		
		);
		resetButton.setOnMouseEntered(
			e -> resetButton.setStyle(activeButtonStyle())		
		);

		resetButton.setOnMouseExited(
			e -> resetButton.setStyle(defaultButtonStyle())		
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
		
		outputPane.setStyle(outputLabelStyle());

		VBox resultBox = new VBox(10);
		resultBox.getChildren().addAll(resultText, outputPane);
		
		HBox resultContainer = new HBox();
		resultContainer.getChildren().add(resultBox);
		resultContainer.setAlignment(Pos.CENTER);

		// Root
		VBox root = new VBox(40);

		root.getChildren().addAll(titleBox, algorithmsSelectorContainer, userFunctionality, alphabetBox, resultContainer);
		root.setPadding(new Insets(25));

		root.setStyle(
		    "-fx-background-color: linear-gradient(to right, " +
		    "#f6f1d4 0%, " +
		    "#fefefc 50%, " +
		    "#f3edc7 100%);"
		);

		// Scene
		Scene scene = new Scene(root);
		
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
