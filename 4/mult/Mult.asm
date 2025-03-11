// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
// The algorithm is based on repetitive addition.

@R2
M=0          // Initialize R2 to 0 (result storage)

@R1
D=M          // Load R1 into D (counter for loop)
@END
D;JEQ        // If R1 == 0, jump to END (since anything multiplied by 0 is 0)

@i
M=0          // Initialize loop counter i = 0

(LOOP)
    @i
    D=M      // Load i into D
    @R1
    D=D-M    // Compute i - R1
    @END
    D;JEQ    // If i == R1, exit loop (multiplication is complete)

    @R0
    D=M      // Load R0 into D
    @R2
    M=M+D    // Add R0 to R2 (R2 = R2 + R0)

    @i
    M=M+1    // Increment i (i = i + 1)

    @LOOP
    0;JMP    // Jump back to LOOP to continue multiplication

(END)
@END
0;JMP        // Infinite loop to halt the program
