// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

@24576
D=A
@end
M=D        // Store screen end address (fixed value)

(LOOP)
    @KBD
    D=M            // Read keyboard input (D = RAM[24576])

    @DRAW_BLACK
    D;JNE         // If a key is pressed (D != 0), jump to DRAW_BLACK

    @DRAW_WHITE
    0;JMP         // Otherwise, jump to DRAW_WHITE (clear screen)

(DRAW_BLACK)
    @SCREEN
    D=A
    @addr
    M=D           // addr = SCREEN (first pixel)

(FILL_LOOP_BLACK)
    @addr
    D=M           // Load current pixel address
    @end
    D=D-M         // Check if addr == end
    @LOOP
    D;JEQ         // If so, return to LOOP

    @addr
    A=M           // Load current pixel address
    M=-1          // Set pixel to black (all 1s in 16-bit word)

    @addr
    M=M+1         // Move to next pixel

    @FILL_LOOP_BLACK
    0;JMP         // Repeat until screen is filled

(DRAW_WHITE)
    @SCREEN
    D=A
    @addr
    M=D           // addr = SCREEN (first pixel)

(FILL_LOOP_WHITE)
    @addr
    D=M           // Load current pixel address
    @end
    D=D-M         // Check if addr == end
    @LOOP
    D;JEQ         // If so, return to LOOP

    @addr
    A=M           // Load current pixel address
    M=0           // Set pixel to white (all 0s in 16-bit word)

    @addr
    M=M+1         // Move to next pixel

    @FILL_LOOP_WHITE
    0;JMP         // Repeat until screen is cleared