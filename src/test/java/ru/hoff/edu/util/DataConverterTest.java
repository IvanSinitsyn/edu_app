package ru.hoff.edu.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DataConverterTest {

    static Stream<Object[]> provideInputAndExpectedOutput() {
        return Stream.of(
                new Object[]{
                        "xxx\nx x\nxxx",
                        new char[][]{
                                {'x', 'x', 'x'},
                                {'x', ' ', 'x'},
                                {'x', 'x', 'x'}
                        }
                },
                new Object[]{
                        "xx\nx xxxxx\nx",
                        new char[][]{
                                {'x', 'x', ' ', ' ', ' ', ' ', ' '},
                                {'x', ' ', 'x', 'x', 'x', 'x', 'x'},
                                {'x', ' ', ' ', ' ', ' ', ' ', ' '}
                        }
                },
                new Object[]{
                        "",
                        new char[][]{}
                },
                new Object[]{
                        "abc",
                        new char[][]{
                                {'a', 'b', 'c'}
                        }
                },
                new Object[]{
                        "\n\n\n",
                        new char[][]{}
                }
        );
    }

    @ParameterizedTest
    @MethodSource("provideInputAndExpectedOutput")
    void testConvertStringToForm(String input, char[][] expected) {
        char[][] result = DataConverter.convertStringToForm(input);
        assertArrayEquals(expected, result, "The converted char array does not match the expected output.");
    }
}
