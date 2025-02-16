# Terminal Tetris

## Overview

This is a custom implementation of **Tetris** with additional gameplay mechanics, including power-ups that influence the game board's gravity and tetromino sequencing. The game features classic Tetris functionality, tetromino rotations, and new mechanics that enhance the experience.

## Features

- **Classic Tetromino Shapes**: Includes all standard Tetris shapes and additional ones like **Double L**.
- **Tetromino Rotation**: Supports clockwise and counterclockwise rotation.
- **Power-Ups**:
  - **Sequence Power-Up**: Determines the next few tetromino shapes in a predictable order.
  - **Left Gravity Power-Up**: Shifts all blocks one space to the left.
  - **Right Gravity Power-Up**: Shifts all blocks one space to the right.
- **Randomized Tetromino Selection**: Ensures variety while preventing consecutive duplicates.
- **Text-Based Tetromino Display**: Uses ASCII-like symbols to represent tetrominoes in a terminal.

## Installation & Setup

### Prerequisites

- Java Development Kit (**JDK 8+**)
- An IDE (IntelliJ IDEA, Eclipse, VS Code, etc.) or terminal for running Java programs

### Clone the Repository

```sh
git clone https://github.com/AyazH11106/Terminal-Tetris.git
cd Terminal-Tetris
```

### Compile & Run

```sh
javac -d bin src/**/*.java
java -cp bin Main
```

## Class Descriptions

### **Tetromino.java**

- Represents tetromino shapes and their behaviors.
- Supports rotation and sequential shape selection.
- Provides a string representation of the shape.

### **Item.java**

- Implements power-ups affecting the game board.
- Manages power-up placement and activation logic.

### **Board.java**

- Manages the game grid and collision detection.
- Integrates power-ups and their effects.

### **ScoreLog.java**

- Tracks player scores throughout the game.
- Stores and updates high scores.
- Provides methods to retrieve and display score history.
