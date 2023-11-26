package cz.bcpraha.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnumOpt extends BaseOpt<String> implements CmdInputVerifier {
    private final Set<String> enumValues = new HashSet<>();

    public EnumOpt(String paramType, List<String> alias, String description, Boolean required, String defaultParamValue,
                   String enumValues) {
        super(paramType, alias, description, required, defaultParamValue);
        setEnumValues(enumValues);
    }

    private void setEnumValues(String enumValues) {
        if (enumValues != null) {
            String[] parts = enumValues.split("\\|");
            this.enumValues.addAll(Arrays.asList(parts));
        } else {
            throw new IllegalArgumentException("Enum values must be provided. Please fill them for option: " + getAliases());
        }
    }

    public Set<String> getEnumValues() {
        return enumValues;
    }

    public void verify(String cmdValue, String cmdKey) {
        if (getRequired() && cmdValue == null) {
            throw new IllegalArgumentException("Parameter for option " + cmdKey + " is REQUIRED. Put the valid parameter value.");
        }

        String value;
        if (cmdValue == null) {
            value = getDefaultParamValue();
        } else {
            value = cmdValue;
        }

        if (!enumValues.contains(value)) {
            throw new IllegalArgumentException("Parameter " + cmdValue + " is not valid. It can be one of these: "
                    + enumValues + ".");
        }
    }
}
