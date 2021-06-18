package sugar.annotationHandlers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractHandlerTest {

    @Test
    void getNumber() {
        Integer first = 1;
        Long second = 12312L;
        Short third = 213;
        Byte fourth = 21;
        int fifth = 123;
        long sixth = 123213;

        assertEquals(AbstractHandler.getNumber(first), (long) first);
        assertEquals(AbstractHandler.getNumber(second), (long) second);
        assertEquals(AbstractHandler.getNumber(third), (long) third);
        assertEquals(AbstractHandler.getNumber(fourth), (long) fourth);
        assertEquals(AbstractHandler.getNumber(fifth), fifth);
        assertEquals(AbstractHandler.getNumber(sixth), sixth);

    }
}