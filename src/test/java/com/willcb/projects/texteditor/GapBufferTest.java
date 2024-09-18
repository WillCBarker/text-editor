package com.willcb.projects.texteditor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.List;

public class GapBufferTest {
    private GapBuffer gapBuffer;

    @BeforeEach
    public void setUp() {
        gapBuffer = new GapBuffer(50); // Initialize with a buffer size of 50
    }

    @Test
    public void testInitialGapStartAndEnd() {
        assertEquals(0, gapBuffer.getGapStart());
        assertEquals(40, gapBuffer.getGapEnd()); // Assuming initial gap size is 40
    }

    @Test
    public void testInsertCharacterWithinGap() {
        gapBuffer.insert('a', 0);
        assertEquals('a', gapBuffer.getBuffer()[0]);
        assertEquals(1, gapBuffer.getGapStart()); // Gap start should move to the right
    }

    @Test
    public void testInsertCharacterAtCursorPosition() {
        gapBuffer.insert('b', 0);
        gapBuffer.insert('c', 1); // Insert at position 1
        assertEquals('b', gapBuffer.getBuffer()[0]);
        assertEquals('c', gapBuffer.getBuffer()[1]);
    }

    @Test
    public void testDeleteCharacterWithinGap() {
        gapBuffer.insert('x', 0);
        gapBuffer.insert('y', 1);
        gapBuffer.delete(1); // Delete character at position 1
        assertEquals('x', gapBuffer.getBuffer()[0]);
        assertEquals(0, gapBuffer.getGapStart()); // Gap start should move left
    }

    @Test
    public void testResizeBuffer() {
        for (int i = 0; i < 40; i++) {
            gapBuffer.insert((char) ('a' + i), i);
        }
        assertEquals(50, gapBuffer.getBufferSize()); // Check initial buffer size
        gapBuffer.insert('z', 40); // This should trigger a resize
        assertEquals(100, gapBuffer.getBufferSize()); // After resizing, buffer size should be 100
    }

    @Test
    public void testShiftGapToCursor() {
        gapBuffer.insert('a', 0);
        gapBuffer.insert('b', 1);
        gapBuffer.insert('c', 2);
        gapBuffer.shiftGapToCursor(1); // Shift gap to position 1
        assertEquals(1, gapBuffer.getGapStart());
        assertEquals(38, gapBuffer.getGapEnd()); // Gap end should remain unchanged
    }

    @Test
    public void testGetNonGapText() {
        gapBuffer.insert('a', 0);
        gapBuffer.insert('b', 1);
        gapBuffer.insert('c', 2);
        gapBuffer.delete(2); // Delete 'b'
        List<Character> nonGapText = gapBuffer.getNonGapText();
        assertEquals(2, nonGapText.size());
        assertEquals('a', (char) nonGapText.get(0));
        assertEquals('c', (char) nonGapText.get(1));
    }

    @Test
    public void testGetBuffer() {
        gapBuffer.insert('x', 0);
        char[] buffer = gapBuffer.getBuffer();
        assertEquals('x', buffer[0]);
        assertEquals(50, buffer.length); // Check that the buffer size is 50
    }

    @Test
    public void testGetGapSize() {
        assertEquals(40, gapBuffer.getGapSize()); // Initial gap size
        gapBuffer.insert('x', 0); // Insert one character
        assertEquals(39, gapBuffer.getGapSize()); // Gap size should decrease by one
    }
}
