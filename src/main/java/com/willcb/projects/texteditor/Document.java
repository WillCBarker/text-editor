package com.willcb.projects.texteditor;

public class Document {
    private Cursor cursor;
    private GapBuffer gapBuffer;

    public Document(GapBuffer gapBuffer) {
        this.gapBuffer = gapBuffer;
        this.cursor = new Cursor(0);
    }

    public void arrowKeyHandler(String action) {
        int cursorPosition = cursor.getPosition();
        switch (action) {
            case "left":
                System.out.println("LEFT" + (cursorPosition - 1));
                if (cursorPosition > 0) {
                    cursor.setPosition(cursorPosition - 1);
                }
                break;
            case "right":
                System.out.println("RIGHT" + (cursorPosition + 1));

                if (cursorPosition < gapBuffer.getBufferSize()) {
                    cursor.setPosition(cursorPosition + 1);
                }
                break;
        }
    }

    /**
     * Adds a character at the current cursor position within the document.
     * If the cursor is at a valid position, the character is inserted at that position.
     * After insertion, the cursor moves to the next column.
     *
     * @param character The character to be added to the document.
     */
    public void addCharacter(Character character) {
        int cursorPosition = cursor.getPosition();

        System.out.println("Key: " + character);
        this.gapBuffer.insert(character, cursorPosition);
        cursor.setPosition(++cursorPosition);
    }

    public void deleteCharacter() {
        int cursorPosition = cursor.getPosition();

        if (cursorPosition >= 0) {
            cursor.setPosition(Math.max(cursorPosition-1, 0));
            gapBuffer.delete(cursorPosition);
        }
    }

    public void showText() {
        System.out.println("Cursor: " + this.cursor.getPosition());
        this.gapBuffer.printNonGapText();
    }
}
