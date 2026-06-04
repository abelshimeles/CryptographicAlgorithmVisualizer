public class DiffieHellman {
	public static int calculatePublicKey(int p, int g, int a) {
		return (int) (Math.pow(g, a) % p);
	}

	public static int calculateSharedKey(int p, int B, int a) {
		return (int) (Math.pow(B, a) % p);
	}
}
