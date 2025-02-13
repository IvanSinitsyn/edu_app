package ru.hoff.edu.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ParcelValidatorTest {

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
    public void isParcelFormValid_CheckValidationResult(TestCase testCase) {
        // Act
        boolean result = new ParcelValidator().isParcelFormValid(testCase.form, '#');

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
