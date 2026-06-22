
<h1 style="text-align:center; width:100%;"> Caesar Cipher </h1>

## Overview

The Caesar Cipher is one of the oldest and simplest encryption techniques. It derives its name from one of history’s most famous military leaders: Julius Caesar. Named after Julius Caesar, who reportedly used it to protect his military communications, this technique involves shifting the letters of the alphabet by a fixed number of places. Historical accounts, particularly from the writings of Suetonius, suggest that Caesar employed this method around 50 BCE to encode sensitive military correspondence during his campaigns in Gaul.

It is a substitution cipher in which each letter in the plaintext is shifted by a fixed number of positions in the alphabet. 

For example, with a shift value of 3:

- A becomes D
- B becomes E
- C becomes F

The same shift value is used for both encryption and decryption.


## Encryption Process

### Step 1: Choose a Shift Value

A number known as the key is selected.

Example:

Key = 3


### Step 2: Examine Each Character

The plaintext is processed one character at a time.

Example plaintext:
```
HELLO
```
### Step 3: Shift Each Letter

Each letter is moved forward through the alphabet by the key value, which is 3 in this case.

```
| Plaintext |   Shift  |  Ciphertext  |
|     H     |    +3    |      K       |
|     E     |    +3    |      H       |
|     L     |    +3    |      O       |
|     L     |    +3    |      O       |
|     O     |    +3    |      R       |
```


### Step 4: Produce the Ciphertext

The encrypted message becomes:

```
KHOOR
```


## Decryption Process

### Step 1: Obtain the Key

The same key used during encryption must be known.

Example:

Key = 3


### Step 2: Process Each Ciphertext Character

Ciphertext:

```
KHOOR
```


### Step 3: Shift Backward

Each letter is moved backward by the key value.

```
| Ciphertext |   Shift  |  Plaintext   |
|     K      |    -3    |      H       |
|     H      |    -3    |      E       |
|     O      |    -3    |      L       |
|     O      |    -3    |      L       |
|     R      |    -3    |      O       |
```


### Step 4: Recover the Original Message

Recovered plaintext:

```
HELLO
```

## Advantages

- Easy to understand.
- Easy to implement.
- Useful for learning basic cryptographic concepts.


## Limitations

- Very small key space.
- Easily broken using brute force.
- Vulnerable to frequency analysis.
- Not suitable for protecting sensitive information.

## Summary

The Caesar Cipher encrypts data by shifting letters through the alphabet by a fixed amount. Although historically important, it is considered insecure by modern cryptographic standards.
