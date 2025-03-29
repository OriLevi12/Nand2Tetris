import java.io.*;
import java.util.*;

public class Parser {
    private List<String> lines;  // List to store all cleaned lines
    private int currentIndex = -1;  // Index of the current command
    private String currentCommand;

    //constructor
    public Parser(String fileName) throws IOException {
        lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));   // Open the file for reading
        String line;

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("//.*", "").trim(); //Remove comments and whitespaces
            if (!line.isEmpty()) { //Only keep non-empty lines
                lines.add(line);
            }
        }

        reader.close();
    }

    public boolean hasMoreCommands() {
        return currentIndex + 1 < lines.size();
    }

    public void advance() {
        currentIndex++;
        currentCommand = lines.get(currentIndex);
    }

    public void reset() {
        currentIndex = -1;
        currentCommand = null;
    }

    // Determine the type of the current command (A, C, or L)
    public CommandType commandType() {
        if (currentCommand.startsWith("@")) {
            return CommandType.A_COMMAND;
        } else if (currentCommand.startsWith("(") && currentCommand.endsWith(")")) {
            return CommandType.L_COMMAND;
        } else {
            return CommandType.C_COMMAND;
        }
    }

    // Get the symbol from an A- or L-command
    // For @value returns "value", for (LABEL) returns "LABEL"
    public String symbol() {
        if (commandType() == CommandType.A_COMMAND) {
            return currentCommand.substring(1);
        } else if (commandType() == CommandType.L_COMMAND) {
            return currentCommand.substring(1, currentCommand.length() - 1);
        }
        return null;
    }

     // Get the 'dest' part of a C-command (left of '=')
    public String dest() {
        if (currentCommand.contains("=")) {
            return currentCommand.split("=")[0];
        }
        return "null";
    }

     // Get the 'comp' part of a C-command (either after '=' or before ';')
    public String comp() {
        String[] parts = currentCommand.split("=");
        String right = parts.length == 2 ? parts[1] : parts[0]; // if no '=', use entire command
        return right.contains(";") ? right.split(";")[0] : right;
    }

    // Get the 'jump' part of a C-command (after ';')
    public String jump() {
        if (currentCommand.contains(";")) {
            return currentCommand.split(";")[1];
        }
        return "null";
    }
}
