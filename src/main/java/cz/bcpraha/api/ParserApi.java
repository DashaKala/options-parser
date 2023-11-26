package cz.bcpraha.api;

import java.util.Map;

public interface ParserApi {
    Map<String, Object> getAllParamsWithValues();

    <T> T getParamValue(String alias, Class<T> clazz);
}
