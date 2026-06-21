import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class DiffieHellmanSection implements Section {
        private TextField primeField = new TextField();
        private TextField generatorField = new TextField();
        private TextField aliceSecretField = new TextField();
        private TextField bobSecretField = new TextField();
        private Button exchangeButton;
        private Label validationMessage;

        private String[] alicePublicKeyParameters = {"Alice", "A", "g", "a"};
        private String[] bobPublicKeyParameters = {"Bob", "B", "g", "b"};

        private String[] aliceSharedSecretParameters = {"Alice", "S", "B", "a"};
        private String[] bobSharedSecretParameters = {"Bob", "S", "A", "b"};
        private VBox visualizationContainer = new VBox(40);

        private void clearValidation() {
                validationMessage.setVisible(false);

                primeField.getStyleClass().remove("input-field-error");
                generatorField.getStyleClass().remove("input-field-error");
                aliceSecretField.getStyleClass().remove("input-field-error");
                bobSecretField.getStyleClass().remove("input-field-error");
        }

        private void markInvalid(TextField field) {
                if (!field.getStyleClass().contains("input-field-error")) {
                        field.getStyleClass().add("input-field-error");
                }
        }

        private void visualize() {
                clearValidation();

                boolean hasError = false;

                Long p = null;
                Long g = null;
                Long a = null;
                Long b = null;

                try {
                        p = Long.parseLong(primeField.getText().trim());

                        if (p <= 1) {
                                markInvalid(primeField);
                                hasError = true;
                        }

                } catch (NumberFormatException ex) {
                        markInvalid(primeField);
                        hasError = true;
                }

                try {
                        g = Long.parseLong(generatorField.getText().trim());

                        if (g <= 0) {
                                markInvalid(generatorField);
                                hasError = true;
                        }

                } catch (NumberFormatException ex) {
                        markInvalid(generatorField);
                        hasError = true;
                }

                try {
                        a = Long.parseLong(aliceSecretField.getText().trim());

                        if (a <= 0) {
                                markInvalid(aliceSecretField);
                                hasError = true;
                        }

                } catch (NumberFormatException ex) {
                        markInvalid(aliceSecretField);
                        hasError = true;
                }

                try {
                        b = Long.parseLong(bobSecretField.getText().trim());

                        if (b <= 0) {
                                markInvalid(bobSecretField);
                                hasError = true;
                        }

                } catch (NumberFormatException ex) {
                        markInvalid(bobSecretField);
                        hasError = true;
                }

                if (hasError) {
                        validationMessage.setText(
                                        "All parameters must be valid positive numbers. Prime (p) must be greater than 1."
                        );
                        validationMessage.setVisible(true);
                        return;
                }

                visualizationContainer.getChildren().clear();

                long A = DiffieHellman.calculateKey(g, a, p);
                long B = DiffieHellman.calculateKey(g, b, p);

                long S = DiffieHellman.calculateKey(B, a, p);

                VBox alicePublicKeyCalculation = Components.getKeyCalculation(alicePublicKeyParameters, new long[]{g, a, p}, A);

                VBox bobPublicKeyCalculation = Components.getKeyCalculation(bobPublicKeyParameters, new long[]{g, b, p}, B);

                HBox publicKeysMessageContainer = Components.getColoredMessageLabel("PUBLIC KEY", false, 18);

                HBox publicKeyCalculationsSection = Components.getKeyCalculationSection(alicePublicKeyCalculation, bobPublicKeyCalculation);

                VBox publicKeysContainer = new VBox(20);
                publicKeysContainer.getChildren().addAll(publicKeysMessageContainer, publicKeyCalculationsSection);

                HBox exchangeMessageContainer = Components.getColoredMessageLabel("EXCHANGE", false, 18);

                HBox exchangeAlice = Components.getKeyExchangeSection("Alice", "Bob", "A", A);

                HBox exchangeBob = Components.getKeyExchangeSection("Bob", "Alice", "B", B);

                VBox exchangeSection = new VBox(30);
                exchangeSection.getChildren().addAll(exchangeAlice, exchangeBob);
                exchangeSection.setAlignment(Pos.CENTER);

                VBox exchangeContainer = new VBox(20);
                exchangeContainer.getChildren().addAll(exchangeMessageContainer, exchangeSection);

                VBox aliceSharedSecretCalculation = Components.getKeyCalculation(aliceSharedSecretParameters, new long[]{B, a, p}, S);

                VBox bobSharedSecretCalculation = Components.getKeyCalculation(bobSharedSecretParameters, new long[]{A, b, p}, S);

                HBox sharedSecretMessageContainer = Components.getColoredMessageLabel("SHARED SECRET", false, 18);

                HBox sharedSecretCalculationsSection = Components.getKeyCalculationSection(
                                aliceSharedSecretCalculation,
                                bobSharedSecretCalculation
                );

                VBox sharedSecretContainer = new VBox(20);
                sharedSecretContainer.getChildren().addAll(sharedSecretMessageContainer, sharedSecretCalculationsSection);
                exchangeButton.setDisable(true);

                Timeline timeline = new Timeline(
                                new KeyFrame(
                                                Duration.seconds(2),
                                                e -> {
                                                        visualizationContainer.getChildren().add(publicKeysContainer);
                                                }
                                ),

                                new KeyFrame(
                                                Duration.seconds(4),
                                                e -> {
                                                        visualizationContainer.getChildren().add(exchangeContainer);
                                                }
                                ),

                                new KeyFrame(
                                                Duration.seconds(6),
                                                e -> {
                                                        visualizationContainer.getChildren().add(sharedSecretContainer);
                                                        exchangeButton.setDisable(false);
                                                }
                                )
                );

                timeline.play();
        }

        @Override
        public VBox getSection() {
                HBox parametersMessageContainer = Components.getColoredMessageLabel("PARAMETERS", false, 18);

                VBox primeContainer = Components.getDiffieHellmanParameter(primeField, "Prime", "(p)");
                VBox generatorContainer = Components.getDiffieHellmanParameter(generatorField, "Generator", "(g)");
                VBox aliceSecretContainer = Components.getDiffieHellmanParameter(aliceSecretField, "Alice's Secret", "(a)");
                VBox bobSecretContainer = Components.getDiffieHellmanParameter(bobSecretField, "Bob's Secret", "(b)");

                exchangeButton = Components.getDefaultButton("START");
                exchangeButton.setOnAction(e -> visualize());

                HBox exchangeButtonContainer = new HBox();
                exchangeButtonContainer.getChildren().add(exchangeButton);
                exchangeButtonContainer.setAlignment(Pos.CENTER);

                HBox inputFields = new HBox(50);
                inputFields.getChildren().addAll(primeContainer, generatorContainer, aliceSecretContainer, bobSecretContainer);
                inputFields.setAlignment(Pos.CENTER);

                validationMessage = new Label();
                validationMessage.getStyleClass().add("validation-message");
                validationMessage.setVisible(false);

		HBox validationMessageBox = new HBox();
		validationMessageBox.getChildren().add(validationMessage);
		validationMessageBox.setAlignment(Pos.CENTER);

                VBox inputFieldsContainer = new VBox(20);
                inputFieldsContainer.getChildren().addAll(parametersMessageContainer, inputFields, validationMessageBox, exchangeButtonContainer);

                VBox section = new VBox(20);
                section.getChildren().addAll(inputFieldsContainer, visualizationContainer);

                return section;
        }
}
