package cz.bcpraha.config;

import java.util.List;

public class BaseOpt<T> {

    private final String paramType;
    private final List<String> aliases;
    private final String description;
    private final Boolean required;
    private final T defaultParamValue;
    private T paramValue;

    public BaseOpt(String paramType, List<String> aliases, String description, Boolean required, T defaultParamValue) {
        this.paramType = paramType.toLowerCase();
        this.aliases = aliases;
        this.description = description;
        this.required = required;
        this.defaultParamValue = defaultParamValue;
    }

    public String getParamType() {
        return paramType;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getRequired() {
        return required;
    }

    public T getDefaultParamValue() {
        return defaultParamValue;
    }

    public T getParamValue() {
        return paramValue;
    }

    public void setParamValue(T paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public String toString() {
        return String.join("|", aliases);
    }
}
