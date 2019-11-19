import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Calculator {
    private String userInput;
    private double[] numbers;


    public Calculator() {
    }

    public Calculator(String userInput) {
        this.userInput = userInput;
    }

    public String getUserInput() {
        return userInput;
    }

    //Private method to test
    private List<String> strings(){
        List<String> listOfString = new ArrayList<>();
        listOfString.add("1: +");
        listOfString.add("2: -");
        listOfString.add("3. x");
        listOfString.add("4. /");
        listOfString.add("5. Exit");

        return listOfString;
    }

    public void menu(int choice) {
        while ( true ) {
            List<String> list = strings();
            for (String s: list) {
                System.out.println(s);
            }

            switch ( choice ) {
                case 1:
                    addition();
                    return;
                case 2:
                    subtraction();
                    return;
                case 3:
                    multiplication();
                    return;
                case 4:
                    division();
                    return;
                case 5:
                    System.exit(0);
                default:
                    badChoice();
                    return;
            }
        }
    }

    public int getStringAndConvertToInt(String str){
        int choice = 0;
        try {
            choice = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            convertError();
        }
        return choice;
    }

    public double[] getNumbersAndConvertToArrayOfDouble(String str) {
        try {
            numbers = Arrays.stream(str.split(" "))
                    .mapToDouble(Double::parseDouble).toArray();
        }catch (NumberFormatException e){
            formatError();
        }

        return numbers;
    }

    private void formatError(){
        System.out.println("it's not all your input are digits and use blank space to separate between numbers");
        throw new NumberFormatException();
    }
    private void arithmeticError(){
        System.out.println("Argument 'divisor' is 0");
        throw new ArithmeticException();
    }
    private void badChoice(){
        System.out.println("Please choose a number from 1 to 5");
        throw new NumberFormatException();
    }
    private void convertError(){
        System.out.println("the input should be a number");
        throw new NumberFormatException();
    }


    private void division() {
        double[] numberToCalculate = getNumbersAndConvertToArrayOfDouble(userInput);
        divis(numberToCalculate);
        menu(getStringAndConvertToInt(userInput));
    }

    private void multiplication() {
        double[] numberToCalculate = getNumbersAndConvertToArrayOfDouble(userInput);
        multipli(numberToCalculate);
        menu(getStringAndConvertToInt(userInput));
    }

    private void subtraction() {
        double[] numberToCalculate = getNumbersAndConvertToArrayOfDouble(userInput);
        subtract(numberToCalculate);
        menu(getStringAndConvertToInt(userInput));
    }

    public void addition() {
        double[] numberToCalculate = getNumbersAndConvertToArrayOfDouble(userInput);
        add(numberToCalculate);
        menu(getStringAndConvertToInt(userInput));
    }

    public double multipli ( double[] i){
        double result = 0.0;
        for (int j = 0; j < i.length; j++){
            if ( j == 0 ) {
                result += i[j];
            } else {
                result *= i[j];
            }

        }
        return result;
    }

    public double subtract ( double []i){
        double result = 0.0;
        for (int j = 0; j < i.length; j++){
            if (j == 0){
                result += i[j];
            }else {
                result -= i[j];
            }
        }
        return result;
    }

    public double add ( double []i){
        double result = 0.0;
        for (int j = 0; j < i.length; j++){
            result += i[j];
        }
        return result;
    }

    public double divis ( double [] i){
        double result = 0.0;
        for (int j = 1; j < i.length; j++) {
            if (i[j] == 0) {
                arithmeticError();
            }else{
                for (int y = 0; y < i.length; y++){
                    if (y == 0){
                        result += i[y];
                    }else {
                        result /= i[y];
                    }
                }
            }
        }
        return result;
    }

}
