public class DiffieHellman {
	public static int calculateKey(int g, int a, int p) {
		return (int) (Math.pow(g, a) % p);
	}
}
