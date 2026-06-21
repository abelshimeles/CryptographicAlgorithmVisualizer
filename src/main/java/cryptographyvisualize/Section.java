package cryptographyvisualize;

import javafx.scene.layout.VBox;

/**
 * Represents a visualizer section that can be displayed within the
 * application.
 */

public interface Section {
	/**
	 * Returns the JavaFX container representing this section.
	 *
	 * @return the section's root layout node
	 */

	VBox getSection();
}
