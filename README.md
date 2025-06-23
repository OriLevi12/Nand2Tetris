# Nand2Tetris Projects 🚀

This repository contains my **Nand2Tetris** projects, where I learn how to build a computer from the ground up using the **Hack Assembly language**, **Virtual Machine translation**, and **digital logic**.

## 📌 Current Progress
I have completed **9 projects** of the course:

- **Project 1-3:** Designed basic logic gates, built the ALU, and implemented RAM using HDL.
- **Project 4:** Wrote Hack Assembly programs (`Mult.asm`, `Fill.asm`).
- **Project 5:** Built the complete Hack computer by implementing the `CPU`, `Memory`, and `Computer` chips in HDL.
- **Project 6:** Developed a two-pass Hack assembler in Java that translates `.asm` files into `.hack` binary machine code.
- **Project 7:** Implemented a **Virtual Machine (VM) Translator** in Java that converts high-level VM commands (e.g., `push`, `pop`, `add`, `eq`) into Hack assembly. This stage covers:
  - Translating stack arithmetic operations (`add`, `sub`, `eq`, `gt`, `lt`, `neg`, `not`, `and`, `or`)
  - Memory access commands: `push` and `pop` for segments like `constant`, `local`, `argument`, `this`, `that`, `temp`, `pointer`, and `static`
- **Project 8:** Extended the VM Translator to handle program control (`label`, `goto`, `if-goto`) and function calling (`function`, `call`, `return`) with full bootstrap logic and multi-file support.
- **Project 9:** Built an interactive game in Jack with keyboard input, scoring, and random enemy spawning.

I plan to continue with the next stages of the VM (Project 8 and onward), which include program control, function calls, and building a simple operating system.

## ⚡ Running the Projects
To test the assembly programs (`.asm` files), you can use the **CPU Emulator** provided in the Nand2Tetris software suite:
1. Open the `CPU Emulator`.
2. Load an `.asm` file.
3. Run the program and observe the results.

To simulate the hardware components (`.hdl` files), use the **Hardware Simulator**:
1. Open the `Hardware Simulator`.
2. Load a `.hdl` file and its corresponding `.tst` test script.
3. Run the test and verify the output.

To run `.jack` programs (like the game), use the **VM Emulator**:
1. Compile all `.jack` files to `.vm` using the Jack compiler.
2. Load the `Game.jack` (or main file) into the VM Emulator.
3. Play using the keyboard controls.

## 🛠 Tools Used
- **Hack Assembly Language** (Low-level programming)
- **Nand2Tetris Software Suite** (CPU Emulator, Hardware Simulator)
- **Hardware Description Language (HDL)** (For chip design)
- **Java** (For building the assembler and VM translator)

---

## 📢 Notes
This repository is a work in progress as I continue learning. Stay tuned for more updates! 😊🚀

## 📬 Contact Info
**Ori Levi**  
📧 Email: Leviori1218@gmail.com  
🐙 GitHub: [OriLevi12](https://github.com/OriLevi12) 
