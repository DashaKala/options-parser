package cz.bcpraha.config;

import java.util.List;

public class StringOpt extends BaseOpt<String> implements CmdInputVerifier {

    private final Integer minValue;
    private final Integer maxValue;

    public StringOpt(String paramType, List<String> alias, String description, Boolean required, String defaultParamValue,
                     Integer minValue, Integer maxValue) {
        super(paramType, alias, description, required, defaultParamValue);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
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

        if (value.matches("[a-zA-Z.]+")) {
            if (minValue != null && maxValue != null) {
                if (minValue > value.length() || value.length() > maxValue) {
                    throw new IndexOutOfBoundsException("Input number " + value + " is out of the valid range: "
                            + minValue + " - " + maxValue + " for option " + cmdKey + ".");
                }
            }
        } else {
            throw new IllegalArgumentException("Parameter can contain only letters of english alphabet. " + cmdValue + " is not valid.");
        }
    }
}
