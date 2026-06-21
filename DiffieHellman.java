import java.math.BigInteger;

public class DiffieHellman {
	public static long calculateKey(long g, long a, long p) {
		BigInteger base = BigInteger.valueOf(g);
		BigInteger exp = BigInteger.valueOf(a);
		BigInteger mod = BigInteger.valueOf(p);

		return base.modPow(exp, mod).longValue();
	}
}
