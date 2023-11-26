package cz.bcpraha;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvVerifier {

    private static final List<String> VALUES_NOT_FILLED = List.of("", " ", "null", "NA");
    private static final Pattern ALIAS_PATTERN = Pattern.compile("^(-[a-zA-Z]|--[a-zA-Z]+)$");

    private CsvVerifier() {
    }

    static void verifyCell(List<String> parsedLine, int column, int row, String separator) {
        switch (column) {
            case 0:
            case 2:
                for (String value : VALUES_NOT_FILLED) {
                    if (value.equals(parsedLine.get(column))) {
                        int column1 = column;
                        throw new IllegalArgumentException("Missing value on row: " + row +
                                " and column: " + ++column1 + " in input file.");
                    }
                }
                break;
            case 1:
                List<String> listAlias = ParserUtils.parse(parsedLine.get(column), separator);
                for (String value : listAlias) {
                    Matcher matcher = ALIAS_PATTERN.matcher(value);
                    if (!matcher.matches()) {
                        int column1 = column;
                        throw new IllegalArgumentException("Incorrect value of ALIAS on row: " + row +
                                " and column: " + ++column1 + " in input file.");
                    }
                }
                break;
            case 3:
                if (!parsedLine.get(column).equalsIgnoreCase("true") &&
                        !parsedLine.get(column).equalsIgnoreCase("false")) {
                    int column1 = column;
                    throw new IllegalArgumentException("Incorrect value of REQUIRED on row: " + row + " and column: " + ++column1 + " in input file.");
                }
        }
    }

    static Integer verifyIntegerCellValue(String cellValue, int row) {
        for (String value : VALUES_NOT_FILLED) {
            if (value.equals(cellValue)) {
                return null;
            }
        }
        try {
            return Integer.parseInt(cellValue);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Incorrect value on row: " + row + " in input file.");
        }
    }

    static Boolean verifyBooleanCellValue(String cellValue, int row) {
        for (String value : VALUES_NOT_FILLED) {
            if (value.equals(cellValue)) {
                return null;
            }
        }
        if (cellValue.equalsIgnoreCase("true")) {
            return true;
        }
        if (cellValue.equalsIgnoreCase("false")) {
            return false;
        }
        throw new IllegalArgumentException("Invalid Boolean value on row: " + row + " in input file.");
    }

    static String verifyStringCellValue(String cellValue, int row) {
        for (String value : VALUES_NOT_FILLED) {
            if (value.equals(cellValue)) {
                return null;
            }
            return cellValue;
        }
        throw new IllegalArgumentException("Invalid value on row: " + row + " in input file.");
    }

    static String verifyEnumCellValue(String cellValue, int row) {
        for (String value : VALUES_NOT_FILLED) {
            if (value.equals(cellValue)) {
                throw new IllegalArgumentException("Missing value on row: " + row + " in input file.");
            }
        }
        return cellValue;
    }
}
