package com.willcb.projects.texteditor;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DocumentTest {
    private Document document;
    private GapBuffer gapBuffer;

    @BeforeEach
    public void setUp() {
        gapBuffer = new GapBuffer(200);
        document = new Document(gapBuffer);
    }

    @Test
    public void testAddCharacterToDocument() {
        document.addCharacter('H');
        document.addCharacter('e');
        document.addCharacter('l');
        document.addCharacter('l');
        document.addCharacter('o');
        assertEquals(Arrays.asList('H', 'e', 'l', 'l', 'o'), gapBuffer.getNonGapText()); // Assuming GapBuffer has a getNonGapText method
        assertEquals(5, document.getCursor().getPosition());
    }

    @Test
    public void testAddNewLineCharacter() {
        document.addCharacter('H');
        document.addCharacter('i');
        document.addCharacter('\n');
        document.addCharacter('T');
        document.addCharacter('h');
        document.addCharacter('e');
        document.addCharacter('r');
        document.addCharacter('e');

        assertEquals(Arrays.asList('H', 'i', '\n', 'T', 'h', 'e', 'r', 'e'), gapBuffer.getNonGapText());
        assertEquals(1, document.getCursor().getCurrentLineNum());
        assertEquals(5, document.getCursor().getCurrentColumn());
    }

    @Test
    public void testDeleteCharacterFromDocument() {
        document.addCharacter('T');
        document.addCharacter('e');
        document.addCharacter('s');
        document.addCharacter('t');
        document.deleteCharacter();

        assertEquals(Arrays.asList('T', 'e', 's'), gapBuffer.getNonGapText());
        assertEquals(3, document.getCursor().getPosition());
    }

    @Test
    public void testDeleteLineMergeWithPrevious() {
        document.addCharacter('L');
        document.addCharacter('i');
        document.addCharacter('n');
        document.addCharacter('e');
        document.addCharacter('\n');
        document.addCharacter('N');
        document.addCharacter('e');
        document.addCharacter('x');
        document.addCharacter('t');
        int pos = document.getCursor().getPosition();
        pos -= 4;
        document.getCursor().setPosition(pos);
        document.deleteCharacter(); // Deletes newline, merging lines

        assertEquals(Arrays.asList('L', 'i', 'n', 'e', 'N', 'e', 'x', 't'), gapBuffer.getNonGapText());

        assertEquals(1, document.getCursor().getCurrentLineNum());
        assertEquals(4, document.getCursor().getPosition());
    }

    @Test
    public void testResetDocument() {
        document.addCharacter('R');
        document.addCharacter('e');
        document.addCharacter('s');
        document.addCharacter('e');
        document.addCharacter('t');
        document.resetCursorPosition();

        assertEquals(0, document.getCursor().getPosition());
        assertEquals(0, document.getCursor().getCurrentLineNum());
        assertEquals(0, document.getCursor().getCurrentColumn());
    }

    @Test
    public void testShowText() {
        document.addCharacter('H');
        document.addCharacter('i');
        document.addCharacter('\n');
        document.addCharacter('T');
        document.addCharacter('h');
        document.addCharacter('e');
        document.addCharacter('r');
        document.addCharacter('e');

        // document.showText();
        // Verify manually
    }
}
