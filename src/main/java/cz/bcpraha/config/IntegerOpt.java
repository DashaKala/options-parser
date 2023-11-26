package cz.bcpraha.config;

import java.util.List;

public class IntegerOpt extends BaseOpt<Integer> implements CmdInputVerifier {

    private final Integer minValue;
    private final Integer maxValue;

    public IntegerOpt(String paramType, List<String> alias, String description, Boolean required, Integer defaultParamValue,
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
        try {
            int value;
            if (cmdValue == null) {
                value = getDefaultParamValue();
            } else {
                value = Integer.parseInt(cmdValue);
            }
            if (minValue != null && maxValue != null) {
                if (minValue > value || value > maxValue) {
                    throw new IndexOutOfBoundsException("Input number " + value + " is out of the valid range: "
                            + minValue + " - " + maxValue + " for option " + cmdKey + ".");
                }
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Option " + cmdKey + " parameter " + cmdValue + " is not a type of Integer.");
        }
    }
}
