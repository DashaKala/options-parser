package cz.bcpraha.config;

public enum ValidType {
    INTEGER("integer"),
    STRING("string"),
    ENUM("enum"),
    BOOLEAN("boolean");

    ValidType(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    public static ValidType valueOfName(String name) {
        for (ValidType type : values()) {
            if (type.getValue().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Not supported ValidType: " + name);
    }
}

