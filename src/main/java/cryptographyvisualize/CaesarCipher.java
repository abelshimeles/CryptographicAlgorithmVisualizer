package cryptographyvisualize;

/**
 * Provides utility methods for encrypting and decrypting characters
 * using the Caesar Cipher algorithm.
 * <p>
 * The Caesar Cipher is a substitution cipher in which each letter is
 * shifted by a fixed number of positions in the alphabet. Non-alphabetic
 * characters are returned unchanged.
 * </p>
 */

public class CaesarCipher {
	/**
	 * Encrypts a character using the Caesar Cipher algorithm.
	 * <p>
	 * Alphabetic characters are shifted forward in the alphabet by the
	 * specified number of positions. Uppercase and lowercase letters are
	 * preserved. Non-alphabetic characters are returned unchanged.
	 * </p>
	 *
	 * @param ch the character to encrypt
	 * @param shift the number of positions to shift the character
	 * @return the encrypted character
	 */

	public static char encrypt(char ch, int shift) {
		if (Character.isUpperCase(ch))
			return (char) ((ch - 0x41 + shift) % 0x1a + 0x41);
		if (Character.isLowerCase(ch))
			return (char) ((ch - 0x61 + shift) % 0x1a + 0x61);

		return ch;
	}

	/**
	 * Decrypts a character that was encrypted using the Caesar Cipher
	 * algorithm.
	 * <p>
	 * Alphabetic characters are shifted backward in the alphabet by the
	 * specified number of positions. Uppercase and lowercase letters are
	 * preserved. Non-alphabetic characters are returned unchanged.
	 * </p>
	 *
	 * @param ch the character to decrypt
	 * @param shift the number of positions to shift the character back
	 * @return the decrypted character
	 */

	public static char decrypt(char ch, int shift) {
		if (Character.isUpperCase(ch))
			return (char) ((ch - 0x41 - shift + 0x1a) % 0x1a + 0x41);
		if (Character.isLowerCase(ch))
			return (char) ((ch - 0x61 - shift + 0x1a) % 0x1a + 0x61);

		return ch;
	}
}
