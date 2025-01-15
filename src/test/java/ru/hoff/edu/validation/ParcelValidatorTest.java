package ru.hoff.edu.validation;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ParcelValidatorTest {

    private static Stream<TestCase> provideFormsForValidation() {
        return Stream.of(
                new TestCase(
                        new char[][]{
                                {'#', ' ', ' ', ' ', ' ', ' ', '#'},
                                {' ', '#', '#', '#', '#', '#', ' '},
                                {'#', ' ', ' ', ' ', ' ', ' ', '#'}
                        },
                        false
                ),
                new TestCase(
                        new char[][]{
                                {'#', ' ', ' ', ' ', ' ', ' ', '#'},
                                {'#', '#', '#', '#', '#', '#', '#'},
                                {'#', ' ', ' ', ' ', ' ', ' ', '#'}
                        },
                        true
                ),
                new TestCase(
                        new char[][]{
                                {'#', ' ', '#'},
                                {' ', '#', ' '},
                                {'#', ' ', '#'}
                        },
                        false
                ),
                new TestCase(
                        new char[][]{
                                {' ', ' ', '#', '#', '#', ' '},
                                {'#', ' ', '#', '#', '#', '#'},
                                {'#', '#', '#', ' ', ' ', '#'},
                                {' ', '#', ' ', ' ', '#', '#'},
                                {'#', ' ', ' ', ' ', '#', ' '},
                                {'#', '#', '#', '#', '#', ' '},
                        },
                        true
                ),
                new TestCase(
                        new char[][]{
                                {'#', ' ', '#', ' ', '#', ' '},
                                {' ', '#', ' ', '#', ' ', '#'},
                                {'#', ' ', '#', ' ', '#', ' '},
                                {' ', '#', ' ', '#', ' ', '#'},
                                {'#', ' ', '#', ' ', '#', ' '},
                                {' ', '#', ' ', '#', ' ', '#'},
                        },
                        false
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideFormsForValidation")
    public void isParcelFormValid_CheckValidationResult(@NotNull TestCase testCase) {
        // Act
        boolean result = ParcelValidator.isParcelFormValid(testCase.form, '#');

        // Assert
        Assertions.assertEquals(testCase.expectedResult, result);
    }

    private static class TestCase {
        char[][] form;
        boolean expectedResult;

        TestCase(char[][] form, boolean expectedResult) {
            this.form = form;
            this.expectedResult = expectedResult;
        }
    }
}
