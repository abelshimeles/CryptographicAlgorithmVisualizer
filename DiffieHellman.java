public class DiffieHellman {
	public static long calculateKey(long g, long a, long p) {
		return (long) (Math.pow(g, a) % p);
	}
}
