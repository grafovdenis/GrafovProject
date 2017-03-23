package FirstTask;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class NaturalTest {
    @Test
    public void plus() {
        assertEquals(new Natural(0), new Natural(0).plus(new Natural("0")));
        assertEquals(new Natural(1), new Natural(1).plus(new Natural("0")));
        assertEquals(new Natural(3), new Natural(1).plus(new Natural("2")));
        assertEquals(new Natural(198), new Natural(99).plus(new Natural("99")));
        assertEquals(new Natural(1000999998), new Natural(999999999).plus(new Natural("999999")));
    }

    @Test
    public void minus() {
        assertEquals(new Natural(0), new Natural(0).minus(new Natural("0")));
        assertEquals(new Natural(0), new Natural(1).minus(new Natural("1")));
        assertEquals(new Natural(99), new Natural(99).minus(new Natural("0")));
        assertEquals(new Natural(900), new Natural(999).minus(new Natural("99")));
        assertEquals(new Natural(0), new Natural(10000).minus(new Natural("10000")));
        assertEquals(new Natural(999000000), new Natural(999999999).minus(new Natural("999999")));
    }

    @Test
    public void equals() {
        assertEquals(new Natural("1"), new Natural(1));
        assertEquals(new Natural("1"), new Natural(1));
        assertEquals(new Natural(999999999), new Natural("999999999"));
    }

    @Test
    public void compareTo() {
        assertEquals(1, new Natural(2).compareTo(new Natural(1)));
        assertEquals(-1, new Natural(1).compareTo(new Natural(2)));
        assertEquals(0, new Natural(2).compareTo(new Natural(2)));
        assertEquals(1, new Natural(999999999).compareTo(new Natural(999189990)));
        assertEquals(-1, new Natural(99999999).compareTo(new Natural(999189999)));
        assertEquals(1, new Natural("9999999999999999999999999999999999999999").compareTo
                (new Natural("9999999999999999999899999999999999999999")));
    }

    @Test
    public void multiply() {
        assertEquals(new Natural(0), new Natural(1).multiply(new Natural("0")));
        assertEquals(new Natural(2), new Natural(1).multiply(new Natural("2")));
        assertEquals(new Natural(9801), new Natural(99).multiply(new Natural("99")));
        assertEquals(new Natural("8999999991"), new Natural(999999999).multiply(new Natural("9")));
    }
    @Test
    public void div() {
        assertEquals(new Natural(1),new Natural("99999999999999999")
                .div(new Natural("99999999999999999")));
    }
}
