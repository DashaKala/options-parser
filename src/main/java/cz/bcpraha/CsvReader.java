package cz.bcpraha;

import cz.bcpraha.config.CmdInputVerifier;
import cz.bcpraha.config.ReservedOpt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CsvReader {

    private static final int NUMBER_OF_COLUMNS = 4;
    private static final String ALIAS_SEPARATOR = "\\|";
    private static final String FILE_SEPARATOR = ";";

    private final String path;
    private final Map<String, String> aliases = new HashMap<>();
    private final Map<String, CmdInputVerifier> aliasesOpts = new HashMap<>();

    public CsvReader(String path) {
        this.path = path;
        setReservedOpts();
        csvReader();
    }

    private void setReservedOpts() {
        ReservedOpt helpOpt = new ReservedOpt("string", Arrays.asList("-h", "--help"),
                "list of all options with its configuration",
                false,
                null);

        aliases.put("-h", "-h|--help");
        aliases.put("--help", "-h|--help");
        aliasesOpts.put("-h|--help", helpOpt);
    }

    public Map<String, String> getAliases() {
        return aliases;
    }

    public Map<String, CmdInputVerifier> getAliasesOpts() {
        return aliasesOpts;
    }

    public void csvReader() {
        try (BufferedReader br = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {

            // Check for and remove BOM if present
            removeBOM(br);

            int row = 0;
            String line;
            while ((line = br.readLine()) != null) {
                row++;
                if (!line.startsWith("#")) {

                    List<String> parsedLine = ParserUtils.parse(line, FILE_SEPARATOR);

                    if (parsedLine.size() < NUMBER_OF_COLUMNS) {
                        throw new IndexOutOfBoundsException("Minimal columns not reached on row: " + row + " in input file.");
                    }

                    // check first 4 columns are filled with relevant values
                    for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
                        CsvVerifier.verifyCell(parsedLine, i, row, ALIAS_SEPARATOR);
                    }

                    // parsing of column with aliases values
                    List<String> listAlias = ParserUtils.parse(parsedLine.get(1), ALIAS_SEPARATOR);

                    for (String alias : listAlias) {
                        if (aliases.containsKey(alias)) {
                            throw new IllegalArgumentException("Duplicate key detected: " + alias + " on row: " + row + " in input file.");
                        }
                        aliases.put(alias, String.join("|", listAlias));
                    }

                    // create of Map with alias keys and related values
                    aliasesOpts.put(
                            parsedLine.get(1),
                            AliasObjectCreator.createAliasObject(parsedLine, listAlias, row)
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void removeBOM(BufferedReader br) {
        try {
            br.mark(1);
            int firstChar = br.read();
            if (firstChar != 0xFEFF) { // 0xFEFF is the UTF-8 BOM as a character
                br.reset(); // Not a BOM, reset the reader to start from the first character
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
