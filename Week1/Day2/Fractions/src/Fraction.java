public class Fraction {
    private long numerator, denominator;

    // default constructor
    public Fraction() {
        numerator = 0;
        denominator = 1;
    }

    // constructor with parameters
    public Fraction( long n, long d ){
        numerator = n;
        denominator = d;
    }

    public long getNumerator() {
        return numerator;
    }

    public long getDenominator() {
        return denominator;
    }

    private long GCD(long num, long den){
        long gcd = num;
        long remainder = den;
        while( remainder != 0 ) {
            long temp = remainder;
            remainder = gcd % remainder;
            gcd = temp;
        }
        return gcd;
    }

    // get the least common denominator
    private long getLCD(long den1, long den2) {
        long temp = den1;
        while (den1 % den2 != 0) {
            den1 += temp;
        }
        return den1;
    }

    // make fractions have same denominator by lcd
    public Fraction toCommonDenominator(long lcm) {
        Fraction newFraction = new Fraction();
        long factor = lcm/denominator;
        newFraction.numerator = factor*numerator;
        newFraction.denominator = lcm;
        return newFraction;
    }

    public Fraction reduce() {
        Fraction result = new Fraction();
        long gcd = GCD(numerator, denominator);
        result.numerator = numerator / gcd;
        result.denominator = denominator / gcd;
        return result;
    }

    public Fraction plus( Fraction rhs ){
        if ((denominator == 0) || (rhs.denominator == 0)) {
            System.out.println("denominator is invalid");
            return null;
        }
        long lcm = getLCD(denominator, rhs.denominator);
        Fraction fractionLeft = toCommonDenominator(lcm);
        Fraction fractionRight = rhs.toCommonDenominator(lcm);
        Fraction result = new Fraction();
        result.numerator = fractionLeft.numerator + fractionRight.numerator;
        result.denominator = lcm;
        result = result.reduce();
        return result;
    }

    public Fraction minus(Fraction rhs) {
        if ((denominator == 0) || (rhs.denominator == 0)) {
            System.out.println("denominator is invalid");
            return null;
        }
        long lcm = getLCD(denominator, rhs.denominator);
        Fraction fractionLeft = toCommonDenominator(lcm);
        Fraction fractionRight = rhs.toCommonDenominator(lcm);
        Fraction result = new Fraction();
        result.numerator = fractionLeft.numerator - fractionRight.numerator;
        result.denominator = lcm;
        result = result.reduce();
        return result;
    }

    public Fraction times(Fraction rhs) {
        if ((denominator == 0) || (rhs.denominator == 0)) {
            System.out.println("denominator is invalid");
            return null;
        }
        Fraction result = new Fraction();
        result.numerator = numerator*rhs.numerator;
        result.denominator = denominator*rhs.denominator;
        result = result.reduce();
        return result;
    }

    public Fraction dividedBy(Fraction rhs) {
        if ((denominator == 0) || (rhs.numerator == 0)) {
            System.out.println("denominator is invalid");
            return null;
        }
        Fraction result = new Fraction();
        result.numerator = numerator*rhs.denominator;
        result.denominator = denominator*rhs.numerator;
        result = result.reduce();
        return result;
    }

    public Fraction reciprocal() {
        if (numerator == 0) {
            System.out.println("denominator is invalid");
            return null;
        }
        Fraction result = new Fraction();
        result.numerator = denominator;
        result.denominator = numerator;
        result = result.reduce();
        return result;
    }

    public String convertToString() {
        if (numerator < 0 || denominator < 0) {
            return "-" + Math.abs(numerator) + "/" + Math.abs(denominator);
        }
        return numerator + "/" + denominator;
    }

    public double toDouble() {
        return (double)numerator/denominator;
    }
}