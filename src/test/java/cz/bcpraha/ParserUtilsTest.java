package cz.bcpraha;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParserUtilsTest {

    @Test
    void parseCmdInput_testNormalOperation() {
        String[] input = {"-ll", "10", "--operand", "PLUS"};
        Map<String, String> result = ParserUtils.parseCmdInput(input);
        assertEquals("10", result.get("-ll"));
        assertEquals("PLUS", result.get("--operand"));
        assertEquals(2, result.size());
    }

    @Test
    void parseCmdInput_testSingleOption() {
        String[] input = {"-r", "50"};
        Map<String, String> result = ParserUtils.parseCmdInput(input);
        assertEquals("50", result.get("-r"));
    }

    @Test
    void parseCmdInput_testOptionWithoutValueAtEnd() {
        String[] input = {"-r", "50", "--pp"};
        Map<String, String> result = ParserUtils.parseCmdInput(input);
        assertEquals("50", result.get("-r"));
        assertNull(result.get("--pp"));
    }

    @Test
    void parseCmdInput_testConsecutiveOptions() {
        String[] input = {"-l", "--operand"};
        Map<String, String> result = ParserUtils.parseCmdInput(input);
        assertNull(result.get("-l"));
        assertNull(result.get("--operand"));
    }

    @Test
    void parseCmdInput_testInvalidInput() {
        String[] input = {"l", "180"};
        assertThrows(IllegalArgumentException.class, () -> ParserUtils.parseCmdInput(input));
    }

    @Test
    void parseCmdInput_testEmptyInput() {
        String[] input = {};
        assertTrue(ParserUtils.parseCmdInput(input).isEmpty());
    }

    @Test
    void parseCmdInput_testNullInput() {
        assertThrows(NullPointerException.class, () -> ParserUtils.parseCmdInput(null));
    }
}