import java.io.*;

public class VMTranslator {
    public static void main(String[] args) throws IOException {
        File inputFile = new File(args[0]);
        CodeWriter codeWriter;

        if (inputFile.isDirectory()) {
            codeWriter = new CodeWriter(inputFile.getAbsolutePath() + "/" + inputFile.getName() + ".asm");
            codeWriter.writeInit();
            codeWriter.writeCall("Sys.init", 0);

            // Iterate through all .vm files in the directory
            for (File file : inputFile.listFiles()) {
                if (file.getName().endsWith(".vm")) {
                    translateFile(file, codeWriter);
                }
            }
        } else {
            codeWriter = new CodeWriter(inputFile.getAbsolutePath().replace(".vm", ".asm"));
            codeWriter.writeInit();
            translateFile(inputFile, codeWriter);
        }

        codeWriter.close();
    }

    // This method translates a single VM file into assembly code.
    private static void translateFile(File file, CodeWriter codeWriter) throws IOException {
        Parser parser = new Parser(file.getAbsolutePath());
        codeWriter.setFileName(file.getName().replace(".vm", ""));

        while (parser.hasMoreCommands()) {
            parser.advance();
            switch (parser.commandType()) {
                case "C_ARITHMETIC" -> codeWriter.writeArithmetic(parser.arg1());
                case "C_PUSH", "C_POP" -> codeWriter.writePushPop(parser.commandType(), parser.arg1(), parser.arg2());
                case "C_LABEL" -> codeWriter.writeLabel(parser.arg1());
                case "C_GOTO" -> codeWriter.writeGoto(parser.arg1());
                case "C_IF" -> codeWriter.writeIf(parser.arg1());
                case "C_FUNCTION" -> codeWriter.writeFunction(parser.arg1(), parser.arg2());
                case "C_CALL" -> codeWriter.writeCall(parser.arg1(), parser.arg2());
                case "C_RETURN" -> codeWriter.writeReturn();
            }
        }
    }
}

