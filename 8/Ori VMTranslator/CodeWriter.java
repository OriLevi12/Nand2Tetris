import java.io.*;

public class CodeWriter{
    private BufferedWriter writer;
    private int labelCounter = 0;
    private String fileName;

    public CodeWriter(String outputFile) throws IOException {
        writer = new BufferedWriter(new FileWriter(outputFile));
        fileName = new File(outputFile).getName().replace(".asm", "");
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void writeArithmetic(String command) throws IOException {
        //Binary operations(requires two operands eg. x+y)
        if(command.equals("add") || command.equals("sub") || 
        command.equals("and") || command.equals("or")){

            writer.write("// " + command + "\n"); // Debugging line

            // SP--, D = y(topmost)
            popStackToD(); // Pop the top of the stack into D register

            // SP--, A = x(topmost-1)
            writer.write("@SP\n");
            writer.write("AM=M-1\n");

            switch(command){
                case "add":
                    writer.write("M=M+D\n"); // x = x + y
                    break;
                case "sub":
                    writer.write("M=M-D\n"); // x = x - y
                    break;
                case "and":
                    writer.write("M=M&D\n"); // x = x & y
                    break;
                case "or":
                    writer.write("M=M|D\n"); // x = x | y
                    break;
            }

            // SP++
            writer.write("@SP\n");
            writer.write("M=M+1\n");

        }
        //Unary operations(only one operand)
        else if(command.equals("neg") || command.equals("not")){
            writer.write("// " + command + "\n"); //Debugging Line

            //A = SP - 1 apply operation on the topmost element
            writer.write("@SP\n");
            writer.write("A=M-1\n");

            switch(command){
                case "neg":
                    writer.write("M=-M\n"); // x = -x
                    break;
                case "not":
                    writer.write("M=!M\n"); // x = !x
                    break;
            }
            //SP remains unchanged
        }
        
        //Comparison operations: eq, gt, lt
        else if(command.equals("eq") || command.equals("gt") || command.equals("lt")){
             
            String labelTrue = "TRUE_" + labelCounter;
            String labelEnd = "END_" + labelCounter;
            labelCounter = labelCounter + 1; // Increment label counter for next unique label

            writer.write("// " + command + "\n"); // Debugging line

            // SP--, D = y(topmost)
            popStackToD(); // Pop the top of the stack into D register

            //SP--, D = x - y
            writer.write("@SP\n");
            writer.write("AM=M-1\n");
            writer.write("D=M-D\n");

            //if condition is true, jump to the label
            writer.write("@" + labelTrue + "\n");
            switch(command){
                case "eq":
                    writer.write("D;JEQ\n"); // if x == y, jump to label
                    break;
                case "gt":
                    writer.write("D;JGT\n"); // if x > y, jump to label
                    break;
                case "lt":
                    writer.write("D;JLT\n"); // if x < y, jump to label
                    break;
            }

            //if condition is false, set the top of stack to 0
            writer.write("@SP\n");
            writer.write("A=M\n"); 
            writer.write("M=0\n");
            writer.write("@" + labelEnd + "\n"); 
            writer.write("0;JMP\n"); 

            //Label for true condition
            writer.write("(" + labelTrue + ")\n");
            writer.write("@SP\n");
            writer.write("A=M\n"); 
            writer.write("M=-1\n"); // Set the top of stack to -1 (true)

            //Label for end of comparison
            writer.write("(" + labelEnd + ")\n");
            writer.write("@SP\n");
            writer.write("M=M+1\n"); 
            
        }
    }
    
    ///Push and Pop operations
    public void writePushPop(String commandType, String segment, int index) throws IOException {
        if (commandType.equals("C_PUSH") && segment.equals("constant")) {
            writer.write("// push constant " + index + "\n"); // Debugging line
            writer.write("@" + index + "\n");  // Load constant value eg. 7
            writer.write("D=A\n");
            pushDtoStack(); 
        
        }
        else if(commandType.equals("C_PUSH") && (segment.equals("local") || segment.equals("argument") ||
                segment.equals("this") || segment.equals("that"))){

            writer.write("// push " + segment + " " + index + "\n"); // Debugging line eg. push local 2
            
            String base = "";
            switch(segment){
                case "local":
                    base = "LCL";
                    break;
                case "argument":
                    base = "ARG";
                    break;
                case "this":
                    base = "THIS";
                    break;
                case "that":
                    base = "THAT";
                    break;
            }
            writer.write("@" + index + "\n"); // Load index value
            writer.write("D=A\n"); // Store it in D register
            writer.write("@" + base + "\n"); // Load base address (LCL, ARG, THIS, THAT)
            writer.write("A=M\n"); // Get the base address
            writer.write("A=D+A\n"); // Add index to base address
            writer.write("D=M\n"); // Get the value at that address
            pushDtoStack(); // Push the value onto the stack
            

         
        }
        else if(commandType.equals("C_POP") && (segment.equals("local") || segment.equals("argument") ||
                segment.equals("this") || segment.equals("that"))){
            
            writer.write("// pop " + segment + " " + index + "\n"); // Debugging line eg. pop local 2
            
            String base = "";
            switch(segment){
                case "local":
                    base = "LCL";
                    break;
                case "argument":
                    base = "ARG";
                    break;
                case "this":
                    base = "THIS";
                    break;
                case "that":
                    base = "THAT";
                    break;
            }
            // compute the target adress and store it in R13
            writer.write("@" + index + "\n"); // Load index value
            writer.write("D=A\n"); // Store it in D register
            writer.write("@" + base + "\n"); // Load base address (LCL, ARG, THIS, THAT)
            writer.write("A=M\n"); // Get the base address
            writer.write("A=D+A\n"); // Add index to base address
            writer.write("D=A\n"); // Store the address in D register
            writer.write("@R13\n"); // Store the address in a temporary register R13
            writer.write("M=D\n"); // Store the address in R13
            
            // Pop the top of the stack into D register
            popStackToD(); // Pop the top of the stack into D register
            // Store the value in the address stored in R13 (base + index)
            writer.write("@R13\n"); // Load the address from R13
            writer.write("A=M\n"); // Get the address stored in R13
            writer.write("M=D\n"); // Store the value in the address stored in R13 (base + index)

         }

         else if (commandType.equals("C_PUSH") && segment.equals("temp")) {
            writer.write("// push temp " + index + "\n");
            int tempAddress = 5 + index;
            writer.write("@" + tempAddress + "\n"); // A = 5 + i
            writer.write("D=M\n");                  // D = RAM[5 + i]
            pushDtoStack(); // Push the value of RAM[5 + i] onto the stack
        }
        
        else if (commandType.equals("C_POP") && segment.equals("temp")) {
            writer.write("// pop temp " + index + "\n");
            int tempAddress = 5 + index;
            popStackToD(); // Pop the top of the stack into D register
            writer.write("@" + tempAddress + "\n");
            writer.write("M=D\n");            // RAM[5 + i] = D
        }

        else if (commandType.equals("C_PUSH") && segment.equals("pointer")) {
            writer.write("// push pointer " + index + "\n");
            String target = (index == 0) ? "THIS" : "THAT";
            writer.write("@" + target + "\n");
            writer.write("D=M\n");
            pushDtoStack(); // Push the value of THIS or THAT onto the stack
        }
    
        else if (commandType.equals("C_POP") && segment.equals("pointer")) {
            writer.write("// pop pointer " + index + "\n");
            String target = (index == 0) ? "THIS" : "THAT";
            popStackToD();
            writer.write("@" + target + "\n");
            writer.write("M=D\n");     // THIS or THAT = D
        }

        else if (commandType.equals("C_PUSH") && segment.equals("static")) {
            writer.write("// push static " + index + "\n");
            writer.write("@" + fileName + "." + index + "\n");
            writer.write("D=M\n");
            pushDtoStack();
        }
        
        else if (commandType.equals("C_POP") && segment.equals("static")) {
            writer.write("// pop static " + index + "\n");
            popStackToD();
            writer.write("@" + fileName + "." + index + "\n");
            writer.write("M=D\n");
        }
    
            else {
                throw new IllegalArgumentException("Invalid command type or segment: " + commandType + ", " + segment);
            }        
         
        

    }

    
    public void close() throws IOException {
        writer.close();
    }


    private void pushDtoStack() throws IOException {
        writer.write("@SP\n"); // Load address of SP
        writer.write("A=M\n"); // Get the address of the top of the stack
        writer.write("M=D\n"); // Store the value in the stack eg. *SP = 7
        writer.write("@SP\n"); // SP = SP + 1
        writer.write("M=M+1\n");
}
    private void popStackToD() throws IOException {
        writer.write("@SP\n"); // Load address of SP
        writer.write("AM=M-1\n"); // Decrement SP and get the address of the top of the stack
        writer.write("D=M\n"); // Get the value at the top of the stack
    }

    public void writeLabel(String label) throws IOException {
        writer.write("// label " + label + "\n"); // Debugging line
        writer.write("(" + label + ")\n"); // Write the label to the output file
    }

    public void writeGoto(String label) throws IOException {
        writer.write("// goto " + label + "\n"); // Debugging line
        writer.write("@" + label + "\n"); // Jump to the label
        writer.write("0;JMP\n"); // Unconditional jump
    }

    public void writeIf(String label) throws IOException {
        writer.write("// if-goto " + label + "\n"); // Debugging line
        popStackToD(); // Pop the top of the stack into D register
        writer.write("@" + label + "\n"); // Jump to the label
        writer.write("D;JNE\n"); // Jump if D is not zero (true)
    }

    public void writeFunction(String functionName, int numLocals) throws IOException {
        writer.write("// function " + functionName + " " + numLocals + "\n"); // Debugging line
        writer.write("(" + functionName + ")\n"); // Write the function label to the output file

        // Initialize local variables to 0
        for (int i = 0; i < numLocals; i++) {
            writer.write("@0\n"); // Load constant 0
            writer.write("D=A\n"); // Store it in D register
            pushDtoStack(); // Push the value onto the stack
        }
    }

    public void writeCall(String functionName, int numArgs) throws IOException {

        String returnLabel = "RETURN_LABEL" + labelCounter++;

        //push return address
        writer.write("// call " + functionName + " " + numArgs + "\n"); // Debugging line
        writer.write("@" + returnLabel + "\n"); 
        writer.write("D=A\n"); 
        pushDtoStack(); 

        //push LCL
        writer.write("@LCL\n");
        writer.write("D=M\n"); 
        pushDtoStack();

        //push ARG
        writer.write("@ARG\n");
        writer.write("D=M\n");
        pushDtoStack();

        //push THIS
        writer.write("@THIS\n");
        writer.write("D=M\n");
        pushDtoStack();

        //push THAT
        writer.write("@THAT\n");
        writer.write("D=M\n");
        pushDtoStack();

        // Set ARG = SP - 5 - numArgs
        writer.write("@SP\n");
        writer.write("D=M\n"); // D = SP
        writer.write("@" + (numArgs + 5) + "\n"); // D = SP - 5 - numArgs
        writer.write("D=D-A\n"); // D = SP - 5 - numArgs
        writer.write("@ARG\n");
        writer.write("M=D\n"); // ARG = SP - 5 - numArgs

        // Set LCL = SP
        writer.write("@SP\n");
        writer.write("D=M\n"); 
        writer.write("@LCL\n");
        writer.write("M=D\n"); // LCL = SP

        // Goto functionName
        writer.write("@" + functionName + "\n");
        writer.write("0;JMP\n");
        
        // Return label
        writer.write("(" + returnLabel + ")\n"); 
    }
    
    public void writeReturn() throws IOException {
    writer.write("// return\n");
    
    // FRAME = LCL
    writer.write("@LCL\n");
    writer.write("D=M\n");
    writer.write("@R13\n");
    writer.write("M=D\n");
    
    // RET = *(FRAME-5)
    writer.write("@5\n");
    writer.write("A=D-A\n");
    writer.write("D=M\n");
    writer.write("@R14\n");
    writer.write("M=D\n");
    
    // *ARG = pop()
    popStackToD();
    writer.write("@ARG\n");
    writer.write("A=M\n");
    writer.write("M=D\n");
    
    // SP = ARG+1
    writer.write("@ARG\n");
    writer.write("D=M+1\n");
    writer.write("@SP\n");
    writer.write("M=D\n");
    
    // Restore THAT
    writer.write("@R13\n");
    writer.write("AM=M-1\n");
    writer.write("D=M\n");
    writer.write("@THAT\n");
    writer.write("M=D\n");

    // Restore THIS
    writer.write("@R13\n");
    writer.write("AM=M-1\n");
    writer.write("D=M\n");
    writer.write("@THIS\n");
    writer.write("M=D\n");

    // Restore ARG
    writer.write("@R13\n");
    writer.write("AM=M-1\n");
    writer.write("D=M\n");
    writer.write("@ARG\n");
    writer.write("M=D\n");

    // Restore LCL
    writer.write("@R13\n");
    writer.write("AM=M-1\n");
    writer.write("D=M\n");
    writer.write("@LCL\n");
    writer.write("M=D\n");

    // goto RET
    writer.write("@R14\n");
    writer.write("A=M\n");
    writer.write("0;JMP\n");
}


    // Write the bootstrap code for the VM
    public void writeInit() throws IOException {
    writer.write("// bootstrap code\n");
    writer.write("@256\n");
    writer.write("D=A\n");
    writer.write("@SP\n");
    writer.write("M=D\n");
    }

}

