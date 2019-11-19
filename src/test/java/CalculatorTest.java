import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertAll;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CalculatorTest {
    private Calculator calculator;



    @BeforeEach
    void init(){
        calculator = new Calculator();
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName()+ "... is done.");
    }

    @Test
    @Order(1)
    @DisplayName("Testing Constructor")
    void testConstructor(){
        Calculator newCalculator = new Calculator("1 2 -2");
        Assertions.assertEquals("1 2 -2", newCalculator.getUserInput());
    }

    //ParametrizedTest & test private method.
    @ParameterizedTest
    @ValueSource(ints = {5,5})
    @Order(2)
    @SuppressWarnings("unchecked")
    @DisplayName(" Testing list's size 'Private method'")
    void testListSize(int number) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException  {
        Method method = Calculator.class.getDeclaredMethod("strings");
        method.setAccessible(true);
        List<String> actualList = (List<String>) method.invoke(calculator);
        Assertions.assertEquals(number, actualList.size());

    }

    //Test private method.
    @RepeatedTest(4)
    @Order(3)
    @SuppressWarnings("unchecked")
    @DisplayName("Testing the order of list's elements 'Private method'")
    void testOrderOfListElements() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        Method method = Calculator.class.getDeclaredMethod("strings");
        method.setAccessible(true);
        List<String> actualList = (List<String>) method.invoke(calculator);
        List<String> expectedList = new ArrayList<>(
                Arrays.asList(
                        "1: +",
                        "2: -",
                        "3. x",
                        "4. /",
                        "5. Exit"));
        Assertions.assertEquals(expectedList, actualList, "True");

    }

    @Test
    @Order(5)
    @DisplayName("Testing add method")
    void testAdd() {
        double result = calculator.add(new double[]{1, 3});
        if (result != 4) {
            Assert.fail();
        }
    }

    @Test
    @Order(6)
    @DisplayName("Testing multiplication method")
    void testMultiplication(){
        assertAll(
                () -> Assertions.assertEquals(3.0, calculator.multipli(new double[]{3,1})),
                () -> Assertions.assertEquals(0.0, calculator.multipli(new double[]{3,0})),
                () -> Assertions.assertEquals(-3.0, calculator.multipli(new double[]{3,-1}))
        );
    }

    @Test
    @Order(7)
    @DisplayName("Testing subtraction method")
    void testSubtraction(){
        assertAll(
                () -> Assertions.assertEquals(2, calculator.subtract(new double[]{3,1})),
                () -> Assertions.assertEquals(4, calculator.subtract(new double[]{3,-1})),
                () -> Assertions.assertEquals(-2, calculator.subtract(new double[]{-3,-1}))
        );
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class TestGetNumbersAndConvert {
        @Test
        @Order(1)
        @DisplayName("Testing the method converts lines to double")
        void testGetNumbersAndConvertToArrayOfDouble() {
            Assertions.assertArrayEquals(new double[]{-1, 2, 0}, calculator.getNumbersAndConvertToArrayOfDouble("-1 2 0"));
        }

        @Test
        @Order(2)
        @DisplayName("Testing throws exception when text doesn't contain only digits ")
        void testGetNumbersAndConvertToArrayOfDoubleException() {
            Assertions.assertThrows(NumberFormatException.class, () -> {
                calculator.getNumbersAndConvertToArrayOfDouble("A 3 E");
            });

        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class TestMenu {

        @Test
        @Order(1)
        @DisplayName("Testing the input string when we convert it to int")
        void testGetStringAndConvertToInt() {
            Assertions.assertEquals(1, calculator.getStringAndConvertToInt("1"));
        }

        @Test
        @Order(2)
        @DisplayName("Testing the exception when we convert the input for the menu's choices ")
        void testGetStringAndConvertToIntException() {
            Assertions.assertThrows(NumberFormatException.class, () -> {
                calculator.getStringAndConvertToInt("E");
            });
        }
    }

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class TestDivision {
        @Test
        @Order(1)
        @DisplayName("Testing division method")
        void testDivision() {
            assertAll(
                    () -> Assertions.assertEquals(2, calculator.divis(new double[]{2, 1})),
                    () -> Assertions.assertEquals(0, calculator.divis(new double[]{0, 1})),
                    () -> Assertions.assertEquals(-2, calculator.divis(new double[]{2, -1}))
            );
        }

        @Test
        @Order(2)
        @DisplayName("Testing exception when we divide by Zero")
        void testDivisionException() {
            Assertions.assertThrows(ArithmeticException.class, () -> calculator.divis(new double[]{6, 0, 1})
            );
        }
    }

    @Test
    @Order(4)
    @DisplayName("Testing throws exception choices of the menu if it's not from 1 to 5")
    void testBadChoiceException(){
        Assertions.assertThrows(NumberFormatException.class, () ->
                calculator.menu(8)
        );
    }
}
