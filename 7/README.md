# Nand2Tetris - Project 7: VM Translator (Part 1)

This folder contains my implementation of **Project 7** from the Nand2Tetris course.  
The goal of this stage is to translate basic VM commands into Hack assembly code.

---

## 💻 Components

### Ori VMTranslator
Contains the Java classes:

- `VMTranslator.java` – Main entry point for translation.
- `Parser.java` – Parses VM commands.
- `CodeWriter.java` – Generates Hack assembly output.

---

## 🧪 Tests

Test files are organized in two subfolders:

- **StackArithmetic** – Tests arithmetic VM commands such as `add`, `sub`, `eq`, `gt`, `lt`, `neg`, `not`, `and`, `or`.
- **MemoryAccess** – Tests `push` and `pop` commands for segments like `local`, `argument`, `this`, `that`, `constant`, `temp`, `pointer`, and `static`.

---

## 🚀 How to Use

1. **Compile the translator:**
   ```bash
   cd "Ori VMTranslator"
   javac *.java
   ```

2. **Run the translator on a test VM file:**
   ```bash
   java VMTranslator ../StackArithmetic/SimpleAdd.vm
   ```

   This will generate a corresponding `.asm` file.

3. **Load the `.tst` file in the VM Emulator** to run the test.

---

## 📢 Notes

- This version supports:
  - Arithmetic operations
  - Memory access commands (`push` / `pop`)
- It **does not support**:
  - Program control (`label`, `goto`, `if-goto`)
  - Function calling (`function`, `call`, `return`)

These features are implemented in project 8
