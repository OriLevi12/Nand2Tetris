import java.io.*;
import java.util.*;

public class Parser{
    private List<String> lines; // List to store the lines of the file
    private int currentIndex = -1; 
    private String currentCommand; // Current command being processed

    public Parser(String filePath) throws IOException {
        lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = br.readLine()) != null){
                String clean = line.split("//")[0].trim(); // Remove comments and trim whitespace
                if(!clean.isEmpty()){
                    lines.add(clean);
                }
            }
        }
    }
        
    public boolean hasMoreCommands(){
        return currentIndex + 1 < lines.size();
    }

    public void advance(){
        currentIndex = currentIndex + 1;
        currentCommand = lines.get(currentIndex);
    }

    public String commandType(){
        if(currentCommand.startsWith("push")) return "C_PUSH";
        if(currentCommand.startsWith("pop")) return "C_POP";
        if(currentCommand.startsWith("label")) return "C_LABEL";
        if(currentCommand.startsWith("goto")) return "C_GOTO";
        if(currentCommand.startsWith("if-goto")) return "C_IF";
        if(currentCommand.startsWith("function")) return "C_FUNCTION";
        if(currentCommand.startsWith("call")) return "C_CALL";
        if(currentCommand.startsWith("return")) return "C_RETURN";
        return "C_ARITHMETIC";
    }

    public String arg1(){
        if(commandType().equals("C_ARITHMETIC")){ // For arithmetic commands, return the command itself(e.g., "add", "sub", etc.)
            return currentCommand;
        }
        return currentCommand.split(" ")[1]; // Get the first argument (e.g., "local", "argument", etc.)
    }

    public int arg2(){
        if(commandType().equals("C_PUSH") || commandType().equals("C_POP") || commandType().equals("C_FUNCTION")
        || commandType().equals("C_CALL")){
            return Integer.parseInt(currentCommand.split(" ")[2]); 
        }
        throw new IllegalStateException("arg2() can only be called for C_PUSH, C_POP, C_FUNCTION or C_CALL commands.");
    }
}