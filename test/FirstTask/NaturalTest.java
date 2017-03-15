package FirstTask;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class NaturalTest {
    @Test
    public void plus() throws Exception {
        assertEquals(new Natural(1), new Natural(1).plus(new Natural("0")));
        assertEquals(new Natural(3), new Natural(1).plus(new Natural("2")));
        assertEquals(new Natural(198), new Natural(99).plus(new Natural("99")));
        assertEquals(new Natural(1000999998), new Natural(999999999).plus(new Natural("999999")));
    }

    @Test
    public void minus() throws Exception {
        assertEquals(new Natural(0),new Natural(1).minus(new Natural("1")));
        assertEquals(new Natural(99),new Natural(99).minus(new Natural("0")));
        assertEquals(new Natural(900),new Natural(999).minus(new Natural("99")));
        assertEquals(new Natural(0),new Natural(10000).minus(new Natural("10000")));
        assertEquals(new Natural(999000000),new Natural(999999999).minus(new Natural("999999")));
    }

    @Test
    public void equals() throws Exception {
        assertEquals(new Natural("1"), new Natural(1));
        assertEquals(new Natural("1"), new Natural(1));
        assertEquals(new Natural(999999999), new Natural("999999999"));
    }

    @Test
    public void more() throws Exception {
        assertTrue(new Natural("2").more(new Natural(1)));
        assertTrue(new Natural("198").more(new Natural(99)));
        assertTrue(new Natural("9999999999999999999999999999999999999999").more(new Natural("9999999999999999999899999999999999999999")));
        assertTrue(new Natural("1891").more(new Natural("991")));
    }

    @Test
    public void less() throws Exception {
        assertTrue(new Natural("1").less(new Natural(2)));
        assertTrue(new Natural("10").less(new Natural(19)));
        assertTrue(new Natural("1").less(new Natural(2)));
        assertFalse(new Natural("9999999999999999999999999999999999999999").less(new Natural("9999999999999999991899999999999999999999")));
    }
    @Test
    public void multiply() {
        assertEquals(new Natural(0), new Natural(1).multiply(new Natural("0")));
        assertEquals(new Natural(2), new Natural(1).multiply(new Natural("2")));
        assertEquals(new Natural(9801), new Natural(99).multiply(new Natural("99")));
        assertEquals(new Natural("8999999991"), new Natural(999999999).multiply(new Natural("9")));
    }
}
