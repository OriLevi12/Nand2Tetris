import java.io.*;

public class Assembler {
    public static void main(String[] args) throws IOException {
         if (args.length != 1) {
            System.out.println("Usage: java Assembler <file.asm>");
            return;
        }

        // Get input and output file names
        String inputFile = args[0];
        String outputFile = inputFile.replace(".asm", ".hack");

        // Create parser and symbol table
        Parser parser = new Parser(inputFile);
        SymbolTable symbolTable = new SymbolTable();

        // First pass: find label definitions (L_COMMAND) and store them with their ROM addresses
        int romAddress = 0;
        while (parser.hasMoreCommands()) {
            parser.advance();
            if (parser.commandType() == CommandType.L_COMMAND) {
                symbolTable.addEntry(parser.symbol(), romAddress);
            } else {
                romAddress++;
            }
        }

        // Reset parser for second pass
        parser.reset();
        Code code = new Code();
        int ramAddress = 16; // Start RAM addresses from 16 for variables

        // Open output file for writing
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        // Second pass: translate A and C commands into binary
        while (parser.hasMoreCommands()) {
            parser.advance();

            if (parser.commandType() == CommandType.A_COMMAND) {
                String symbol = parser.symbol();
                int address;

                if (isNumeric(symbol)) {
                    address = Integer.parseInt(symbol);
                } else {
                    if (!symbolTable.contains(symbol)) {
                        symbolTable.addEntry(symbol, ramAddress++);
                    }
                    address = symbolTable.getAddress(symbol);
                }

                writer.write(String.format("%16s", Integer.toBinaryString(address)).replace(' ', '0'));
                writer.newLine();

            } else if (parser.commandType() == CommandType.C_COMMAND) {
                String dest = code.dest(parser.dest());
                String comp = code.comp(parser.comp());
                String jump = code.jump(parser.jump());
                writer.write("111" + comp + dest + jump);
                writer.newLine();
                // Note: L_COMMANDs are skipped during the second pass
            }
        }

        writer.close();
        System.out.println("Finished writing to " + outputFile);
    }

    // Helper: returns true if the string is numeric
    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}
