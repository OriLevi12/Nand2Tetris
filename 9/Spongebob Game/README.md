# SpongeBob Jellyfish Catcher 🪼

This is a mini-game written in the Jack programming language, designed for the Nand2Tetris virtual machine platform.

## 🎮 Game Concept

You control SpongeBob, who must catch falling jellyfish while avoiding missing them. As you catch more jellyfish, the game gets progressively faster and harder. Occasionally, a rare "Mother Jellyfish" appears, giving more points but moving unpredictably.

## 🚀 How to Play

- **Start the game**: The game waits for you to press any key. The timing of your key press is used to generate a random seed for jellyfish spawning.
- **Move**:  
  - Use the **left arrow** (←) and **right arrow** (→) keys to move SpongeBob.
- **Quit**:  
  - Press the **'Q' key** at any time to exit the game.
- **Score**:  
  - +1 point for each regular jellyfish caught  
  - +3 points for a Mother Jellyfish

## 🧠 Features

- Simple random number generation using `Random.jack` based on Mark Armbrust's LCG implementation
- Increasing difficulty: jellyfish fall faster as the score increases
- Randomized spawning locations for jellyfish
- Waits for user interaction before starting, using a seed based on input timing
- Clean modular code: separate classes for `Game`, `SpongeBob`, `Jellyfish`, and `Random`

## 📁 Files Included

- `Game.jack` – Main game logic and loop
- `SpongeBob.jack` – The player character logic
- `Jellyfish.jack` – Regular falling enemy
- `MotherJellyfish.jack` – Rare special enemy
- `Random.jack` – Pseudo-random number generator with optional user input seeding

## 🧪 How to Run

1. Open the **Nand2Tetris IDE**
2. Navigate to the project folder
3. Compile all `.jack` files
4. Open `Game.jack` in the **VM Emulator**
5. Start the game

## 🛠 Credits

- `Random.jack` is based on a public implementation by **Mark Armbrust**  
  Adapted and extended by **Ori Levi** to support random seeding via keyboard input.

## ✅ Requirements

- Nand2Tetris Software Suite
- Jack Compiler
- VM Emulator

---

Enjoy catching jellyfish and good luck! 🧽🐙
