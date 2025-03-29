import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, Integer> table;

    //constructor
    public SymbolTable() {
        table = new HashMap<>();
        for (int i = 0; i <= 15; i++) { // Add R0 to R15 (registers)
            table.put("R" + i, i);
        }
        // Add predefined pointers
        table.put("SP", 0);// Stack pointer
        table.put("LCL", 1);// Local segment base
        table.put("ARG", 2);// Argument segment base
        table.put("THIS", 3);// THIS pointer
        table.put("THAT", 4);// THAT pointer
        table.put("SCREEN", 16384);
        table.put("KBD", 24576);
    }

    public void addEntry(String symbol, int address) {
        table.put(symbol, address);
    }

    public boolean contains(String symbol) {
        return table.containsKey(symbol);
    }

    public int getAddress(String symbol) {
        return table.get(symbol);
    }
}
