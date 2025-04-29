# Nand2Tetris - Project 8: VM Translator

This project implements a **VM Translator** for the Nand2Tetris course (Project 8).  
It translates Virtual Machine (VM) commands into Hack assembly language.

---

## 💻 Files

- `Ori VMTranslator/`
  - `VMTranslator.java` - The main program that manages the translation process.
  - `Parser.java` - Reads VM commands and parses their components.
  - `CodeWriter.java` - Writes the corresponding assembly code.
- `Tests/`
  - Contains `.vm`, `.tst`, `.cmp` files for testing the project.

---

## 🚀 How to Use

1. **Compile the code**:

    ```bash
    cd "Ori VMTranslator"
    javac *.java
    ```

2. **Run the translator**:

    - To translate a **single VM file**:
      ```bash
      java VMTranslator path/to/YourFile.vm
      ```
    
    - To translate a **directory containing multiple VM files**:
      ```bash
      java VMTranslator path/to/YourFolder/
      ```

---

## 🧪 How to Run Tests

1. **After generating the `.asm` file** using `Ori VMTranslator`,  
   open the **VM Emulator** from the Nand2Tetris tools.

2. **Load the `.tst` script**:

    - Example: To test `SimpleFunction`, load:
      ```
      Tests/FunctionCalls/SimpleFunction/SimpleFunction.tst
      ```

3. **Run the script** and compare the output:
    - The output `.out` file should match the `.cmp` file provided.

---

## 📋 Important Notes

- If you translate a **directory**:
  - The output `.asm` file will be created inside the directory.
  - The translator will initialize the stack (`SP=256`) and call `Sys.init`, **only if a `Sys.vm` file exists** inside the folder.

- If you translate a **single file**:
  - The stack will be initialized to 256, but **no call to `Sys.init`** will be made.

---

## 🧠 Project Highlights

- Full support for:
  - `push`, `pop`, `arithmetic`, `label`, `goto`, `if-goto`, `function`, `call`, and `return` commands.
- Works with both single `.vm` files and multiple `.vm` files in a folder.
- Smart bootstrap logic based on the presence of `Sys.vm`.
- Clean modular structure: clear separation between parsing and code writing.


