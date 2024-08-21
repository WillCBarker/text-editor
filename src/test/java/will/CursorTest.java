package will;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class CursorTest {
    private Cursor cursor;

    @BeforeAll
    public void init() {
        this.cursor = new Cursor(0, 0);
    }

    @Test
    public void testSetCursorToInitialRow() {
        this.cursor.setRow(0, 0);
        assertEquals(0, this.cursor.getRow());
    }

    @Test
    public void testSetCursorToInitialColumnInRow() {
        this.cursor.setColumn(0, 0);
        assertEquals(0, this.cursor.getColumn());
    }

    @Test
    public void testSetCursorBeyondMaxRow() {
        this.cursor.setRow(5, 0);
        assertEquals(0, this.cursor.getRow());
    }

    @Test
    public void testSetCursorBeyondMaxColumnInRow() {
        this.cursor.setColumn(5, 0);
        assertEquals(0, this.cursor.getColumn());
    }

    @Test
    public void testSetRowToExistingRow() {
        this.cursor.setRow(3, 5);
        assertEquals(3, this.cursor.getRow());
    }

    @Test
    public void testSetColumnToExistingColumnInRow() {
        this.cursor.setColumn(3, 5);
        assertEquals(3, this.cursor.getColumn());
    }
}
