package cz.bcpraha.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BooleanOpt extends BaseOpt<Boolean> implements CmdInputVerifier {

    private final Set<String> paramValuesTrue = new HashSet<>();
    private final Set<String> paramValuesFalse = new HashSet<>();

    public BooleanOpt(String paramType, List<String> alias, String description, Boolean required, Boolean defaultParamValue,
                      String paramValuesTrue, String paramValuesFalse) {
        super(paramType, alias, description, required, defaultParamValue);
        setParamValuesTrue(paramValuesTrue);
        setParamValuesFalse(paramValuesFalse);
    }

    private void setParamValuesTrue(String paramValuesTrue) {
        if (paramValuesTrue != null) {
            String[] parts = paramValuesTrue.split("\\|");
            this.paramValuesTrue.addAll(Arrays.asList(parts));
        }
    }

    private void setParamValuesFalse(String paramValuesFalse) {
        if (paramValuesFalse != null) {
            String[] parts = paramValuesFalse.split("\\|");
            this.paramValuesFalse.addAll(Arrays.asList(parts));
        }
    }

    public Set<String> getParamValuesTrue() {
        return paramValuesTrue;
    }

    public Set<String> getParamValuesFalse() {
        return paramValuesFalse;
    }

    public void verify(String cmdValue, String cmdKey) {
        if (getRequired() && cmdValue == null) {
            throw new IllegalArgumentException("Parameter for option " + cmdKey + " is REQUIRED. Put the valid parameter value.");
        }
        if (!paramValuesTrue.isEmpty() || !paramValuesFalse.isEmpty()) {
            if (!paramValuesTrue.contains(cmdValue) && !paramValuesFalse.contains(cmdValue)) {
                throw new IllegalArgumentException("Parameter " + cmdValue + " is not valid. It can be one of these: "
                        + paramValuesTrue + " for true values, or " + paramValuesFalse + " for false values.");
            }
        }
    }
}
