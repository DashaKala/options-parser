package cz.bcpraha;

import java.util.*;

public class ParserUtils {

    private ParserUtils() {
    }

    public static List<String> parse(String input, String separator) {
        String[] parts = input.split(separator);
        List<String> list = Arrays.asList(parts);
        List<String> convertedList = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {
            if (i == 2) {
                convertedList.add(list.get(i).trim());
            } else {
                convertedList.add(list.get(i).replaceAll(" ", ""));
            }
        }
        return convertedList;
    }

    public static Map<String, String> parseCmdInput(String[] userInput) {
        List<String> cmdInputList = new ArrayList<>(Arrays.asList(userInput));
        Map<String, String> cmdInput = new HashMap<>();

        for (int i = 0; i < cmdInputList.size(); i++) {
            if (!cmdInputList.get(i).startsWith("-")) {
                throw new IllegalArgumentException("Invalid input: " + cmdInputList.get(i) + "; " +
                        "we expect OPTION on this position, which must start with - or --.");
            }
            if (i != cmdInputList.size() - 1) {
                if (cmdInputList.get(i + 1).startsWith("-")) {
                    cmdInput.put(cmdInputList.get(i), null);
                } else {
                    cmdInput.put(cmdInputList.get(i), cmdInputList.get(i + 1));
                    i++;
                }
            } else {
                cmdInput.put(cmdInputList.get(i), null);
            }
        }
        return cmdInput;
    }
}
