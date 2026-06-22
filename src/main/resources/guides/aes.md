<h1 style="text-align:center; width:100%;"> AES Encryption </h1>

## Overview

The Advanced Encryption Standard (AES) is a symmetric-key encryption algorithm used worldwide to protect digital information.

AES operates on fixed-size blocks of data and uses the same secret key for both encryption and decryption.

Common key sizes include:

- 128 bits (the one implemented here)
- 192 bits
- 256 bits

AES is widely used in secure communications, file encryption, wireless networks, and many modern security systems.

## AES Encryption Process

### Step 1: Prepare the Plaintext

The plaintext is divided into blocks of 128 bits. Here on one block visualization.

Example:

```
HELLO WORLD
```

The data is converted into binary form and organized into a state matrix.

### Step 2: Generate Round Keys (Not implemnted yet in progress for visualization)

The original encryption key is expanded into multiple round keys using the Key Expansion process.

Each round of AES uses a different round key.

### Step 3: Initial AddRoundKey

The plaintext state is combined with the first round key using the XOR operation.

```
State XOR RoundKey
```

This begins the encryption process.

### Step 4: Perform Multiple Encryption Rounds

For AES-128, ten rounds are executed.

Each round consists of several transformations.

## Round Transformation 1: SubBytes

Each byte in the state matrix is replaced using a substitution table known as the S-Box.

Purpose:

- Introduces non-linearity.
- Prevents simple mathematical attacks.


## Round Transformation 2: ShiftRows

Rows within the state matrix are shifted by different offsets.

Purpose:

- Rearranges data.
- Increases diffusion.

Example:

```
Row 0 -> No shift
Row 1 -> Shift left by 1
Row 2 -> Shift left by 2
Row 3 -> Shift left by 3
```

## Round Transformation 3: MixColumns

Each column of the state matrix is transformed using finite field mathematics.

Purpose:

- Mixes information between bytes.
- Improves diffusion.

## Round Transformation 4: AddRoundKey

The current state is combined with the round key using XOR.

Purpose:

- Incorporates secret key material into the encryption process.


## Final Round

The final round differs slightly.

The MixColumns operation is omitted.

The final sequence is:

1. SubBytes
2. ShiftRows
3. AddRoundKey


## Ciphertext Generation

After the final round, the state matrix is converted into the ciphertext.

The resulting ciphertext appears random and unreadable without the secret key.


## AES Decryption Process

AES decryption reverses the encryption operations.

The following inverse transformations are used:

1. AddRoundKey
2. InvShiftRows
3. InvSubBytes
4. InvMixColumns

These operations are performed in reverse order until the original plaintext is recovered.

## Advantages

- Strong security.
- Efficient implementation.
- Fast encryption and decryption.
- Widely trusted and standardized.


## Applications

AES is commonly used in:

- Secure web connections (HTTPS)
- VPNs
- File encryption
- Database protection
- Wireless network security


## Summary

AES is a modern symmetric encryption algorithm that protects data through multiple rounds of substitution, permutation, and key mixing. Its combination of security and efficiency has made it one of the most widely used encryption standards in the world.
