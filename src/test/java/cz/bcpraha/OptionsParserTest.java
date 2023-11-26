package cz.bcpraha;

import java.util.Map;

public class OptionsParserTest {

    public static void main(String[] args) {
        OptionsParser op = new OptionsParser("./doc/options-config-template.csv", args);
        Integer right = op.getParamValue("--right", Integer.class);
        System.out.println("--right: " + right);
        Map<String, Object> allParams = op.getAllParamsWithValues();
        System.out.println("allParams: " + allParams);
    }
}
