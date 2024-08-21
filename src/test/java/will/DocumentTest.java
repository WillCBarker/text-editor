package will;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class DocumentTest 
{

    @Test
    public void testSetCursorPosition() {
        Document document = new Document();

        document.setCursorPosition(12, 5);
        System.out.println("Step 1: " + document.getTextMatrix());
        assertEquals(0, document.getCursorRow());
        assertEquals(0, document.getCursorColumn());

        document.setCursorPosition(12, 5);
        System.out.println("Step 2: " + document.getTextMatrix());
        assertEquals(1, document.getCursorRow());
        assertEquals(0, document.getCursorColumn());


    }
}
