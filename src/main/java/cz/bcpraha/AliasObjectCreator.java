package cz.bcpraha;

import cz.bcpraha.config.*;

import java.util.List;
import java.util.Map;

public class AliasObjectCreator {

    public static CmdInputVerifier createAliasObject(List<String> parsedLine, List<String> listAlias, int row) {
        switch (ValidType.valueOfName(parsedLine.get(0))) {
            case INTEGER:
                return new IntegerOpt(
                        parsedLine.get(0),
                        listAlias,
                        parsedLine.get(2),
                        CsvVerifier.verifyBooleanCellValue(parsedLine.get(3), row),
                        parsedLine.size() > 4 ? CsvVerifier.verifyIntegerCellValue(parsedLine.get(4), row) : null,
                        parsedLine.size() > 5 ? CsvVerifier.verifyIntegerCellValue(parsedLine.get(5), row) : null,
                        parsedLine.size() > 6 ? CsvVerifier.verifyIntegerCellValue(parsedLine.get(6), row) : null
                );
            case STRING:
                return new StringOpt(
                        parsedLine.get(0),
                        listAlias,
                        parsedLine.get(2),
                        CsvVerifier.verifyBooleanCellValue(parsedLine.get(3), row),
                        parsedLine.size() > 4 ? CsvVerifier.verifyStringCellValue(parsedLine.get(4), row) : null,
                        parsedLine.size() > 5 ? CsvVerifier.verifyIntegerCellValue(parsedLine.get(5), row) : null,
                        parsedLine.size() > 6 ? CsvVerifier.verifyIntegerCellValue(parsedLine.get(6), row) : null
                );
            case ENUM:
                return new EnumOpt(
                        parsedLine.get(0),
                        listAlias,
                        parsedLine.get(2),
                        CsvVerifier.verifyBooleanCellValue(parsedLine.get(3), row),
                        parsedLine.size() > 4 ? CsvVerifier.verifyStringCellValue(parsedLine.get(4), row) : null,
                        parsedLine.size() > 5 ? CsvVerifier.verifyEnumCellValue(parsedLine.get(5), row) : null
                );
            case BOOLEAN:
                return new BooleanOpt(
                        parsedLine.get(0),
                        listAlias,
                        parsedLine.get(2),
                        CsvVerifier.verifyBooleanCellValue(parsedLine.get(3), row),
                        parsedLine.size() > 4 ? CsvVerifier.verifyBooleanCellValue(parsedLine.get(4), row) : null,
                        parsedLine.size() > 5 ? CsvVerifier.verifyStringCellValue(parsedLine.get(5), row) : null,
                        parsedLine.size() > 6 ? CsvVerifier.verifyStringCellValue(parsedLine.get(6), row) : null
                );
        }
        throw new IllegalArgumentException("Not supported option type on line " + row + " in input file.");
    }

    public static void addParamValueToOpt(Map<String, String> aliases,
                                          Map<String, CmdInputVerifier> aliasesOpts,
                                          Map<String, String> cmdInputs) {

        for (Map.Entry<String, String> input : cmdInputs.entrySet()) {
            CmdInputVerifier verifier = aliasesOpts.get(aliases.get(input.getKey()));
            final String val = input.getValue();
            if (verifier instanceof IntegerOpt) {
                ((IntegerOpt) verifier).setParamValue(val == null ? null : Integer.parseInt(val));
            }
            if (verifier instanceof StringOpt) {
                ((StringOpt) verifier).setParamValue(val);
            }
            if (verifier instanceof BooleanOpt) {
                ((BooleanOpt) verifier).setParamValue(val == null ? null : Boolean.parseBoolean(val));
            }
            if (verifier instanceof EnumOpt) {
                ((EnumOpt) verifier).setParamValue(val);
            }
        }
    }
}
