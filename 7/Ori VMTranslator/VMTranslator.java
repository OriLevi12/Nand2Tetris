import java.io.IOException;

public class VMTranslator {
    public static void main(String[] args) throws IOException {
        String inputFile = args[0];
        String outputFile = inputFile.replace(".vm", ".asm");

        Parser parser = new Parser(inputFile);
        CodeWriter codeWriter = new CodeWriter(outputFile);
        codeWriter.writeInit(); // Initialize the stack pointer to start at 256

        while (parser.hasMoreCommands()) {
            parser.advance();
            String type = parser.commandType();

            if (type.equals("C_ARITHMETIC")) { //eg. add, sub, neg, not, eq, gt, lt
                codeWriter.writeArithmetic(parser.arg1());
            } else if (type.equals("C_PUSH") || type.equals("C_POP")) { //eg. push constant 7
                codeWriter.writePushPop(type, parser.arg1(), parser.arg2());
            }
        }

        codeWriter.close();
    }
}
