package cz.bcpraha;

import cz.bcpraha.api.ParserApi;
import cz.bcpraha.config.*;

import java.util.HashMap;
import java.util.Map;

import static cz.bcpraha.config.ReservedOpt.printHelpOpt;

public class OptionsParser implements ParserApi {

    Map<String, String> aliases;
    Map<String, CmdInputVerifier> aliasesOpts;
    Map<String, String> cmdInputs;

    public OptionsParser(String path, String[] cmdIn) {
        CsvReader csvReader = new CsvReader(path);

        aliases = csvReader.getAliases();
        aliasesOpts = csvReader.getAliasesOpts();
        cmdInputs = ParserUtils.parseCmdInput(cmdIn);

        for (Map.Entry<String, CmdInputVerifier> input : aliasesOpts.entrySet()) {
            String key = input.getKey();
            if (!key.equals("-h|--help")) {
                AliasObjectCreator.addParamValueToOpt(aliases, aliasesOpts, cmdInputs);
            }
        }

        compareCmdWithCsv(aliases, cmdInputs);
        verifyOptionParams(cmdInputs, aliases, aliasesOpts);
        printHelpOpt(cmdInputs, aliasesOpts);
    }

    @Override
    public <T> T getParamValue(String alias, Class<T> clazz) {
        if (!aliases.containsKey(alias)) {
            throw new IllegalArgumentException("Alias does not exist.");
        }
        CmdInputVerifier verifier = aliasesOpts.get(aliases.get(alias));
        if (clazz == Integer.class) {
            return clazz.cast(((IntegerOpt) verifier).getParamValue());
        }
        if (clazz == String.class) {
            if (verifier instanceof StringOpt) {
                return clazz.cast(((StringOpt) verifier).getParamValue());
            }
            if (verifier instanceof EnumOpt) {
                return clazz.cast(((EnumOpt) verifier).getParamValue());
            }
        }
        if (clazz == Boolean.class) {
            return clazz.cast(((BooleanOpt) verifier).getParamValue());
        }
        throw new IllegalArgumentException("The value for alias '" + alias +
                "' is not of type " + clazz.getSimpleName());
    }

    @Override
    public Map<String, Object> getAllParamsWithValues() {
        Map<String, Object> paramsWithValues = new HashMap<>();

        for (Map.Entry<String, String> entry : aliases.entrySet()) {
            String key = entry.getKey();
            Object civ = aliasesOpts.get(aliases.get(key));
            Object value = ((BaseOpt<?>) civ).getParamValue();
            paramsWithValues.put(key, value);
        }
        return paramsWithValues;
    }

    private void compareCmdWithCsv(Map<String, String> aliases, Map<String, String> cmdInputs) {

        for (Map.Entry<String, String> entry : cmdInputs.entrySet()) {
            String key = entry.getKey();
            if (!aliases.containsKey(key)) {
                throw new IllegalArgumentException("This OPTION: " + key + " does not exist.");
            }
        }
    }

    private void verifyOptionParams(Map<String, String> cmdInputs,
                                    Map<String, String> aliases,
                                    Map<String, CmdInputVerifier> aliasesOpts) {

        for (Map.Entry<String, String> entry : cmdInputs.entrySet()) {
            String cmdKey = entry.getKey();
            String cmdValue = entry.getValue();

            String aliasesKey = aliases.get(cmdKey);
            CmdInputVerifier aliasesKeyOpts = aliasesOpts.get(aliasesKey);

            aliasesKeyOpts.verify(cmdValue, cmdKey);
        }
    }
}
