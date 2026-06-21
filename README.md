# Cryptographic Algorithm Visualizer

A JavaFX-based desktop application for visualizing and interacting with common Cryptographic Algorithms such as Caesar Cipher, Diffie-Hellman Key Exchange, and AES.

## Features

- Interactive Step by Step visualization of Cryptographic Algorithms
- Modular section-based architecture
- JavaFX GUI interface
- Easily extendable design

## Project Structure
```text
.
├── LICENSE
├── pom.xml
├── README.md
└── src
    └── main
        ├── java
        │   └── cryptographyvisualize
        └── resources
            └── style.css
```

## Requirements

- Java JDK 17 or higher
- Maven 3.8+


## How to Run

### Step 1: Clone repository
```sh
git clone https://github.com/abelshimeles/CryptographicAlgorithmVisualizer.git
cd CryptographicAlgorithmVisualizer
```

## Install Maven

### Windows (winget)
## Install Maven (Windows - ZIP Method)

### 1. Download Maven
Download the official binary ZIP archive:

https://maven.apache.org/download.cgi

Select:
- Binary zip archive

---

### 2. Extract the ZIP file

Extract the downloaded archive anywhere on your system.

---

### 3. Run Maven directly (no PATH setup required)

Open Command Prompt (cmd) or PowerShell, then navigate to the cloned folder:

```sh
cd path\to\CryptographicAlgorithmVisualizer
```
Run Maven using the path to the extracted folder:
```sh
path\to\extracted\apache-maven-x.x.x\bin\mvn clean javafx:run
```

### Linux (Ubuntu/Debian)
```sh
sudo apt update
sudo apt install maven
```
### macOS (Homebrew)
```sh
brew install maven
```

## Verify Maven Installation
```sh
mvn -v
```

### Step 2: Run application

```sh
mvn clean javafx:run
```

Maven will automatically:
- Download JavaFX dependencies
- Compile source code
- Launch the application

## Build Only (Optional)
```sh
mvn clean compile
```

# License

MIT License

