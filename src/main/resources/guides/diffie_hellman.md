
<h1 style="text-align:center; width:100%;"> Diffie-Hellman Key Exchange </h1>

## Overview

The Diffie-Hellman Key Exchange algorithm allows two parties to establish a shared secret over an insecure communication channel.

Unlike many cryptographic algorithms, Diffie-Hellman does not directly encrypt messages. Instead, it creates a shared secret key that can later be used with an encryption algorithm such as AES.

The two communicating parties are commonly referred to as Alice and Bob.


## Goal

Alice and Bob want to generate the same secret key without sending that key directly across the network.

An attacker may observe all transmitted values but should not be able to determine the shared secret.


## Key Exchange Process

### Step 1: Select Public Parameters

Two public values are chosen:

- A large prime number (p)
- A generator value (g)

Example:

```
p = 23
g = 5
```

These values are publicly known.


### Step 2: Alice Chooses a Private Value

Alice selects a secret number.

Example:

```
a = 6
```

This value remains private.


### Step 3: Bob Chooses a Private Value

Bob selects another secret number.

Example:

```
b = 15
```

This value also remains private.


### Step 4: Alice Computes Her Public Value

Alice calculates:

```
A = g^a mod p
```

Example:

```
A = 5^6 mod 23
A = 8
```

Alice sends A to Bob.


### Step 5: Bob Computes His Public Value

Bob calculates:

```
B = g^b mod p
```

Example:

```
B = 5^15 mod 23
B = 19
```

Bob sends B to Alice.


### Step 6: Alice Computes the Shared Secret

Alice uses Bob's public value:

```
Secret = B^a mod p
```

Example:

```
Secret = 19^6 mod 23
Secret = 2
```


### Step 7: Bob Computes the Shared Secret

Bob uses Alice's public value:

```
Secret = A^b mod p
```

Example:

```
Secret = 8^15 mod 23
Secret = 2
```


### Step 8: Verify the Result

Both parties arrive at the same value:

```
Shared Secret = 2
```

This value can now be used to derive an encryption key.


## Why It Works

The mathematical properties of modular exponentiation ensure that:

```
(g^a mod p)^b mod p
=
(g^b mod p)^a mod p
```

As a result, both parties independently calculate the same secret.


## Advantages

- Enables secure key exchange over insecure channels.
- No secret key needs to be transmitted.
- Forms the basis of many secure communication protocols.


## Limitations

- Does not provide encryption by itself.
- Vulnerable to man-in-the-middle attacks if authentication is not used.
- Requires sufficiently large parameters for security.


## Summary

Diffie-Hellman allows two parties to generate a shared secret without directly exchanging that secret. The generated secret can then be used by encryption algorithms to secure communication.
