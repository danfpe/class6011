public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        // initialization
        Fraction fraction1 = new Fraction(1,-2);
        Fraction fraction2 = new Fraction(2,6);

        // addition
        Fraction testResult = fraction1.plus(fraction2);
        System.out.println(fraction1.convertToString() + " + " + fraction2.convertToString() + ": " + testResult.convertToString());

        // subtraction
        testResult = fraction1.minus(fraction2);
        System.out.println(fraction1.convertToString() + " - " + fraction2.convertToString() + ": " + testResult.convertToString());


        // multiplication
        testResult = fraction1.times(fraction2);
        System.out.println(fraction1.convertToString() + " * " + fraction2.convertToString() + ": " + testResult.convertToString());

        // division
        testResult = fraction1.dividedBy(fraction2);
        System.out.println(fraction1.convertToString() + " / " + fraction2.convertToString() + ": " + testResult.convertToString());

        // reciprocal
        testResult = fraction1.reciprocal();
        System.out.println(fraction1.convertToString()+ " reciprocal : " + testResult.convertToString());

        // real result;
        double result = fraction1.toDouble();
        System.out.println(fraction1.convertToString()+ " real number : " + result);

    }
}

