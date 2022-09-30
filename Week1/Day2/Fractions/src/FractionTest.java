import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FractionTest {

    @Test
    void testDividedBy() {
        Fraction fraction1 = new Fraction(1,0);
        Fraction fraction2 = new Fraction(1,6);

        Fraction testResult = fraction1.dividedBy(fraction2);
        Assertions.assertEquals(testResult.convertToString(), "-3/2");
    }
}