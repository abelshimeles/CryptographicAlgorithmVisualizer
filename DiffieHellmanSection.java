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

	private String[] alicePublicKeyParameters = {"Alice", "A", "g", "a"};
	private String[] bobPublicKeyParameters = {"Bob", "B", "g", "b"};

	private String[] aliceSharedSecretParameters = {"Alice", "S", "B", "a"};
	private String[] bobSharedSecretParameters = {"Bob", "S", "A", "b"};
	private VBox visualizationContainer = new VBox(40);

	private void visualize() {
		try {
			long p = Long.parseLong(primeField.getText());
			long g = Long.parseLong(generatorField.getText());
			long a = Long.parseLong(aliceSecretField.getText());
			long b = Long.parseLong(bobSecretField.getText());

			if (p <= 1 || g <= 0 || a <= 0 || b <= 0) {
				return;
			}

			visualizationContainer.getChildren().clear();

			long A = DiffieHellman.calculateKey(g, a, p);
			long B = DiffieHellman.calculateKey(g, b, p);

			long S = DiffieHellman.calculateKey(B, a, p);

			VBox alicePublicKeyCalculation = Components.getKeyCalculation(alicePublicKeyParameters,new long[]{g, a, p}, A);

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

			//visualizationContainer.getChildren().addAll(publicKeysContainer, exchangeContainer, sharedSecretContainer);
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

		} catch (NumberFormatException e) {
			System.out.println("Invalid Input");
		}
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

		VBox inputFieldsContainer = new VBox(20);
		inputFieldsContainer.getChildren().addAll(parametersMessageContainer, inputFields, exchangeButtonContainer);	

		VBox section = new VBox(40);
		section.getChildren().addAll(inputFieldsContainer, visualizationContainer); 

		return section;
	}
}
