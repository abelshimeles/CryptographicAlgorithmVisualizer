package cryptographyvisualize;

import java.math.BigInteger;

/**
 * Provides utility methods for performing calculations related to the
 * Diffie-Hellman key exchange algorithm.
 * <p>
 * Diffie-Hellman is a public-key cryptographic protocol that allows
 * two parties to establish a shared secret over an insecure channel.
 * </p>
 */

public class DiffieHellman {
	/**
	 * Calculates a Diffie-Hellman public key or shared secret value using
	 * modular exponentiation.
	 * <p>
	 * The calculation performed is:
	 * </p>
	 *
	 * <pre>
	 * result = g^a mod p
	 * </pre>
	 *
	 * where {@code g} is the base, {@code a} is the exponent, and
	 * {@code p} is the modulus.
	 *
	 * @param g the base value (generator)
	 * @param a the exponent value (private key)
	 * @param p the modulus value (prime number)
	 * @return the result of {@code g^a mod p}
	 */

	public static long calculateKey(long g, long a, long p) {
		BigInteger base = BigInteger.valueOf(g);
		BigInteger exp = BigInteger.valueOf(a);
		BigInteger mod = BigInteger.valueOf(p);

		return base.modPow(exp, mod).longValue();
	}
}
