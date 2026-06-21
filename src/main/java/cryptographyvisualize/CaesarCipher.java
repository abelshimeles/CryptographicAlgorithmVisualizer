package cryptographyvisualize;

public class CaesarCipher {
	public static char encrypt(char ch, int shift) {
		if (Character.isUpperCase(ch))
			return (char) ((ch - 0x41 + shift) % 0x1a + 0x41);
		if (Character.isLowerCase(ch))
			return (char) ((ch - 0x61 + shift) % 0x1a + 0x61);

		return ch;
	}

	public static char decrypt(char ch, int shift) {
		if (Character.isUpperCase(ch))
			return (char) ((ch - 0x41 - shift + 0x1a) % 0x1a + 0x41);
		if (Character.isLowerCase(ch))
			return (char) ((ch - 0x61 - shift + 0x1a) % 0x1a + 0x61);

		return ch;
	}
}
