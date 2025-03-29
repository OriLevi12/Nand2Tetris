# 🧠 Hack Assembler in Java — Nand2Tetris Project 6

This is a Java implementation of an **Assembler** for the [Hack assembly language](https://www.nand2tetris.org/project06), built as part of the Nand2Tetris course (Project 6).

It translates `.asm` files into `.hack` binary files that can be run in the Hack CPU Emulator.

---

### 🧪 Sample Assembly Programs
These are `.asm` files you can run with the assembler:
- `Add.asm`
- `Max.asm`
- `Rect.asm`
- `Pong.asm`

Each file will produce a corresponding `.hack` output:
- `Add.hack`, `Max.hack`, `Rect.hack`, `Pong.hack`

---

## 🚀 How to Compile and Run

> Make sure Java is installed on your system (`javac` and `java` should be in your PATH).

### 🛠 Compile all `.java` files:

```bash
javac *.java
```
## ▶️ Run the assembler on any .asm file:
```bash:
java Assembler Add.asm
java Assembler Max.asm
java Assembler Rect.asm
java Assembler Pong.asm