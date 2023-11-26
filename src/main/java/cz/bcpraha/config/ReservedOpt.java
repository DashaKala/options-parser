package cz.bcpraha.config;

import java.util.*;

public class ReservedOpt extends BaseOpt<String> implements CmdInputVerifier {

    public ReservedOpt(String paramType, List<String> alias, String description, Boolean required, String defaultParamValue) {
        super(paramType, alias, description, required, defaultParamValue);
    }

    public static void printHelpOpt(Map<String, String> cmdInputs,
                                    Map<String, CmdInputVerifier> aliasesOpts) {
        Set<String> keys = cmdInputs.keySet();
        if (keys.contains("-h") || keys.contains("--help")) {
            for (Map.Entry<String, CmdInputVerifier> entry : aliasesOpts.entrySet()) {
                String key = entry.getKey();
                CmdInputVerifier value = entry.getValue();
            }
        }
    }

    public void verify(String cmdValue, String cmdKey) {
    }
}
