package com.willcb.projects.texteditor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class CursorTest {
    private Cursor cursor;

    @BeforeEach
    public void setUp() {
        cursor = new Cursor(0); // Initialize cursor with position 0
    }

    @Test
    public void testInitialCursorPosition() {
        assertEquals(0, cursor.getPosition());
        assertEquals(0, cursor.getCurrentLineNum());
        assertEquals(0, cursor.getCurrentColumn());
    }

    @Test
    public void testMoveToNextCharacter() {
        cursor.moveToNextCharacter();
        assertEquals(1, cursor.getPosition());
        assertEquals(0, cursor.getCurrentLineNum());
        assertEquals(1, cursor.getCurrentColumn());
    }

    @Test
    public void testMoveToNextLine() {
        cursor.moveToNextLine();
        assertEquals(1, cursor.getCurrentLineNum());
        assertEquals(0, cursor.getCurrentColumn());
        assertEquals(1, cursor.getPosition());
    }

    @Test
    public void testMoveToPreviousLine() {
        cursor.moveToNextLine(); // Move to line 1
        cursor.moveToPreviousLine(); // Move back to line 0
        assertEquals(0, cursor.getCurrentLineNum());
        assertEquals(0, cursor.getCurrentColumn());
        assertEquals(0, cursor.getPosition());
    }

    @Test
    public void testMoveLeft() {
        cursor.moveToNextCharacter(); // Move to position 1
        cursor.moveLeft(); // Move back to position 0
        assertEquals(0, cursor.getCurrentColumn());
        assertEquals(0, cursor.getPosition());
    }

    @Test
    public void testMoveRight() {
        cursor.insertLine(0, 0);
        cursor.moveToNextCharacter(); // Move to position 1
        cursor.moveRight(); // Move to position 2 (next line)
        assertEquals(2, cursor.getPosition());
        assertEquals(1, cursor.getCurrentLineNum());
        assertEquals(0, cursor.getCurrentColumn());
    }

    @Test
    public void testMoveUp() {
        cursor.moveToNextLine(); // Move to line 1
        cursor.moveUp(); // Move to line 0
        assertEquals(0, cursor.getCurrentLineNum());
        assertEquals(0, cursor.getCurrentColumn()); // Should be at the end of line 0
    }

    @Test
    public void testMoveDown() {
        cursor.moveDown(); // Should try to move down
        assertEquals(0, cursor.getCurrentLineNum()); // Should stay on line 1
        assertEquals(0, cursor.getCurrentColumn()); // Stay at position 1
    }

    @Test
    public void testModifyLineLength() {
        cursor.insertLine(0, 0);
        cursor.modifyLineLength(0, 5); // Modify line 0 length to 5
        assertEquals(5, cursor.getLineLength(0));
    }

    @Test
    public void testInsertLine() {
        cursor.insertLine(0, 3); // Insert a line with length 3 at index 0
        assertEquals(3, cursor.getLineLength(0));
    }

    @Test
    public void testDeleteLine() {
        cursor.insertLine(0, 3); // Insert a line with length 3 at index 0
        cursor.deleteLine(0); // Delete line at index 0
        assertEquals(0, cursor.getLineLength(0)); // Should be 0 since the line was deleted
    }

    @Test
    public void testSetPosition() {
        cursor.setPosition(10);
        assertEquals(10, cursor.getPosition());
    }

    @Test
    public void testSetDesiredColumn() {
        cursor.setDesiredColumn(5);
        assertEquals(5, cursor.getDesiredColumn());
    }
}
