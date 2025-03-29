import java.util.HashMap;
import java.util.Map;

public class Code {
    // Lookup tables: maps from assembly mnemonics to binary strings
    private static final Map<String, String> destMap = new HashMap<>();
    private static final Map<String, String> compMap = new HashMap<>();
    private static final Map<String, String> jumpMap = new HashMap<>();

    // Static block: initializes the lookup tables once when the class loads
    static {
        destMap.put("null", "000");
        destMap.put("M", "001");
        destMap.put("D", "010");
        destMap.put("MD", "011");
        destMap.put("A", "100");
        destMap.put("AM", "101");
        destMap.put("AD", "110");
        destMap.put("AMD", "111");

        jumpMap.put("null", "000");
        jumpMap.put("JGT", "001");
        jumpMap.put("JEQ", "010");
        jumpMap.put("JGE", "011");
        jumpMap.put("JLT", "100");
        jumpMap.put("JNE", "101");
        jumpMap.put("JLE", "110");
        jumpMap.put("JMP", "111");

        String[][] compEntries = {
            {"0",   "0101010"}, {"1",   "0111111"}, {"-1",  "0111010"},
            {"D",   "0001100"}, {"A",   "0110000"}, {"!D",  "0001101"},
            {"!A",  "0110001"}, {"-D",  "0001111"}, {"-A",  "0110011"},
            {"D+1", "0011111"}, {"A+1", "0110111"}, {"D-1", "0001110"},
            {"A-1", "0110010"}, {"D+A", "0000010"}, {"D-A", "0010011"},
            {"A-D", "0000111"}, {"D&A", "0000000"}, {"D|A", "0010101"},
            {"M",   "1110000"}, {"!M",  "1110001"}, {"-M",  "1110011"},
            {"M+1", "1110111"}, {"M-1", "1110010"}, {"D+M", "1000010"},
            {"D-M", "1010011"}, {"M-D", "1000111"}, {"D&M", "1000000"},
            {"D|M", "1010101"}
        };
         // Add all comp entries to the compMap
        for (String[] pair : compEntries) {
            compMap.put(pair[0], pair[1]);
        }
    }

    // Returns the binary code for the dest mnemonic (3 bits)
    public String dest(String mnemonic) {
        return destMap.getOrDefault(mnemonic, "000");
    }

    // Returns the binary code for the comp mnemonic (7 bits)
    public String comp(String mnemonic) {
        return compMap.get(mnemonic);
    }

    // Returns the binary code for the jump mnemonic (3 bits)
    public String jump(String mnemonic) {
        return jumpMap.getOrDefault(mnemonic, "000");
    }
}
