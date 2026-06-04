import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class DiffieHellmanSection implements Section { 
	private TextField primeField;
	private TextField generatorField;
	private TextField aliceSecretField;
	private TextField bobSecretField;

	@Override

	public VBox getSection() {
		HBox parametersMessageContainer = Components.getColoredMessageLabel("PARAMETERS", false, 18);

		Label primeMessage = Components.getDefaultLabel("Prime", false, 18);
		Label primeSymbol = Components.getDefaultLabel("(p)", false, 18);
		primeSymbol.getStyleClass().add("colored-text");
		HBox primeMessageContainer = new HBox(5);
		primeMessageContainer.getChildren().addAll(primeMessage, primeSymbol);
		primeMessageContainer.setAlignment(Pos.CENTER);

		primeField = new TextField("23");
		primeField.setPrefWidth(110);
		primeField.setPrefHeight(40);
		primeField.getStyleClass().add("input-field");

		VBox primeContainer = new VBox(10);
		primeContainer.getChildren().addAll(primeMessageContainer, primeField);

		Label generatorMessage = Components.getDefaultLabel("Generator", false, 18);
		Label generatorSymbol = Components.getDefaultLabel("(g)", false, 18);
		generatorSymbol.getStyleClass().add("colored-text");
		HBox generatorMessageContainer = new HBox(5);
		generatorMessageContainer.getChildren().addAll(generatorMessage, generatorSymbol);
		generatorMessageContainer.setAlignment(Pos.CENTER);

		generatorField = new TextField("5");
		generatorField.setPrefWidth(110);
		generatorField.setPrefHeight(40);
		generatorField.getStyleClass().add("input-field");

		VBox generatorContainer = new VBox(10);
		generatorContainer.getChildren().addAll(generatorMessageContainer, generatorField);

		Label aliceSecretMessage = Components.getDefaultLabel("Alice's Secret", false, 18);
		Label aliceSecretSymbol = Components.getDefaultLabel("(a)", false, 18);
		aliceSecretSymbol.getStyleClass().add("colored-text");
		HBox aliceSecretMessageContainer = new HBox(5);
		aliceSecretMessageContainer.getChildren().addAll(aliceSecretMessage, aliceSecretSymbol);
		aliceSecretMessageContainer.setAlignment(Pos.CENTER);

		aliceSecretField = new TextField("6");
		aliceSecretField.setPrefWidth(110);
		aliceSecretField.setPrefHeight(40);
		aliceSecretField.getStyleClass().add("input-field");

		VBox aliceSecretContainer = new VBox(10);
		aliceSecretContainer.getChildren().addAll(aliceSecretMessageContainer, aliceSecretField);

		Label bobSecretMessage = Components.getDefaultLabel("Bob's Secret", false, 18);
		Label bobSecretSymbol = Components.getDefaultLabel("(b)", false, 18);
		bobSecretSymbol.getStyleClass().add("colored-text");
		HBox bobSecretMessageContainer = new HBox(5);
		bobSecretMessageContainer.getChildren().addAll(bobSecretMessage, bobSecretSymbol);
		bobSecretMessageContainer.setAlignment(Pos.CENTER);

		bobSecretField = new TextField("15");
		bobSecretField.setPrefWidth(110);
		bobSecretField.setPrefHeight(40);
		bobSecretField.getStyleClass().add("input-field");

		VBox bobSecretContainer = new VBox(10);
		bobSecretContainer.getChildren().addAll(bobSecretMessageContainer, bobSecretField);

		HBox inputFields = new HBox(50);
		inputFields.getChildren().addAll(primeContainer, generatorContainer, aliceSecretContainer, bobSecretContainer);
		inputFields.setAlignment(Pos.CENTER);

		VBox inputFieldsContainer = new VBox(20);
		inputFieldsContainer.getChildren().addAll(parametersMessageContainer, inputFields);	

		HBox publicKeysMessageContainer = Components.getColoredMessageLabel("PUBLIC KEYS", false, 18);

		Label alice = Components.getDefaultLabel("Alice Computes", false, 18);

		VBox publicKeysLine = new VBox();
		publicKeysLine.setPrefWidth(4);
		publicKeysLine.setPrefHeight(100);
		publicKeysLine.getStyleClass().add("line-default");
		publicKeysLine.setAlignment(Pos.CENTER);

		Label bob = Components.getDefaultLabel("Bob Computes", false, 18);

		HBox publicKeysCalculationContainer = new HBox(30);
		publicKeysCalculationContainer.getChildren().addAll(alice, publicKeysLine, bob);
		publicKeysCalculationContainer.setAlignment(Pos.CENTER);

		VBox publicKeysContainer = new VBox(20);
		publicKeysContainer.getChildren().addAll(publicKeysMessageContainer, publicKeysCalculationContainer);	

		HBox exchangeMessageContainer = Components.getColoredMessageLabel("EXCHANGE", false, 18);
		Label exchangeAlice = Components.getDefaultLabel("A = 8", false, 18);
		Label exchangeBob = Components.getDefaultLabel("Bob", false, 18);

		HBox exchangeCalculationContainer = new HBox(30);
		exchangeCalculationContainer.getChildren().addAll(exchangeAlice, exchangeBob);
		exchangeCalculationContainer.setAlignment(Pos.CENTER);

		VBox exchangeContainer = new VBox(20);
		exchangeContainer.getChildren().addAll(exchangeMessageContainer, exchangeCalculationContainer);	

		HBox sharedSecretMessageContainer = Components.getColoredMessageLabel("SHARED SECRET", false, 18);

		Label sharedSecretalice = Components.getDefaultLabel("Alice Computes", false, 18);

		VBox sharedSecretLine = new VBox();
		sharedSecretLine.setPrefWidth(4);
		sharedSecretLine.setPrefHeight(100);
		sharedSecretLine.getStyleClass().add("line-default");
		sharedSecretLine.setAlignment(Pos.CENTER);

		Label sharedSecretbob = Components.getDefaultLabel("Bob Computes", false, 18);

		HBox sharedSecretCalculationContainer = new HBox(30);
		sharedSecretCalculationContainer.getChildren().addAll(sharedSecretalice, sharedSecretLine, sharedSecretbob);
		sharedSecretCalculationContainer.setAlignment(Pos.CENTER);

		VBox sharedSecretContainer = new VBox(20);
		sharedSecretContainer.getChildren().addAll(sharedSecretMessageContainer, sharedSecretCalculationContainer);	

		VBox section = new VBox(40);
		section.getChildren().addAll(inputFieldsContainer, publicKeysContainer, exchangeContainer, sharedSecretContainer);

		return section;
	}
}
