import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class DiffieHellmanSection implements Section { 
	private TextField primeField;
	private TextField generatorField;
	private TextField aliceSecretField;
	private TextField bobSecretField;

	private String[] alicePublicKeyParameters = {"Alice", "A", "g", "a"};
	private String[] bobPublicKeyParameters = {"Bob", "B", "g", "b"};

	private String[] aliceSharedSecretParameters = {"Alice", "S", "B", "a"};
	private String[] bobSharedSecretParameters = {"Bob", "S", "A", "b"};

	@Override

	public VBox getSection() {
		HBox parametersMessageContainer = Components.getColoredMessageLabel("PARAMETERS", false, 18);
		
		VBox primeContainer = Components.getDiffieHellmanParameter(primeField, "Prime", "(p)");
		VBox generatorContainer = Components.getDiffieHellmanParameter(generatorField, "Generator", "(g)");
		VBox aliceSecretContainer = Components.getDiffieHellmanParameter(primeField, "Alice's Secret", "(a)");
		VBox bobSecretContainer = Components.getDiffieHellmanParameter(primeField, "Bob's Secret", "(b)");

		Button exchangeButton = Components.getDefaultButton("START");
		HBox exchangeButtonContainer = new HBox();
		exchangeButtonContainer.getChildren().add(exchangeButton);
		exchangeButtonContainer.setAlignment(Pos.CENTER);

		HBox inputFields = new HBox(50);
		inputFields.getChildren().addAll(primeContainer, generatorContainer, aliceSecretContainer, bobSecretContainer);
		inputFields.setAlignment(Pos.CENTER);

		VBox inputFieldsContainer = new VBox(20);
		inputFieldsContainer.getChildren().addAll(parametersMessageContainer, inputFields, exchangeButtonContainer);	

		int[] alicePublicKeyValues = {5, 6, 23};
		int A = DiffieHellman.calculateKey(5, 6, 23);

		int[] bobPublicKeyValues = {5, 15, 23};
		int B = DiffieHellman.calculateKey(5, 15, 23);

		VBox alicePublicKeyCalculation = Components.getKeyCalculation(
			alicePublicKeyParameters,
			alicePublicKeyValues,
			A
		); 

		VBox bobPublicKeyCalculation = Components.getKeyCalculation(
			bobPublicKeyParameters,
			bobPublicKeyValues,
			B

		); 

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

		int[] aliceSharedSecretValues = {B, 6, 23};
		int[] bobSharedSecretValues = {A, 15, 23};
		int S = DiffieHellman.calculateKey(B, 6, 23);

		VBox aliceSharedSecretCalculation = Components.getKeyCalculation(
			aliceSharedSecretParameters,
			aliceSharedSecretValues,
			S
		); 

		VBox bobSharedSecretCalculation = Components.getKeyCalculation(
			bobSharedSecretParameters,
			bobSharedSecretValues,
			S

		); 

		HBox sharedSecretMessageContainer = Components.getColoredMessageLabel("SHARED SECRET", false, 18);
		HBox sharedSecretCalculationsSection = Components.getKeyCalculationSection(aliceSharedSecretCalculation, bobSharedSecretCalculation);

		VBox sharedSecretContainer = new VBox(20);
		sharedSecretContainer.getChildren().addAll(sharedSecretMessageContainer, sharedSecretCalculationsSection);	

		VBox section = new VBox(40);
		section.getChildren().addAll(inputFieldsContainer, publicKeysContainer, exchangeContainer, sharedSecretContainer);

		return section;
	}
}
