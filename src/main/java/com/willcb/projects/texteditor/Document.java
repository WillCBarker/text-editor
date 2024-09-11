package com.willcb.projects.texteditor;

public class Document {
    private Cursor cursor;
    private GapBuffer gapBuffer;

    public Document(GapBuffer gapBuffer) {
        this.gapBuffer = gapBuffer;
        this.cursor = new Cursor(0);
    }

    public void arrowKeyHandler(String action) {
        switch (action) {
            case "up":
                cursor.moveUp();
                break;
            case "down":
                cursor.moveDown();
                break;
            case "left":
                cursor.moveLeft();
                break;
            case "right":
                cursor.moveRight();
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
        int currentLineNum = cursor.getCurrentLineNum();
        int currentColumn = cursor.getCurrentColumn();
        
        gapBuffer.insert(character, cursorPosition);
        
        if (character != '\n') {
            // When adding a regular character, update the line length and move the cursor
            cursor.moveToNextCharacter();
        } else {
            // If adding a newline character:
            int lineLength = cursor.getLineLength(currentLineNum);
            // 1. Update the current line length before moving to the next line
            cursor.updateLine(currentLineNum, currentColumn);
            
            // 2. Insert a new line in lineLengthInfo for the next line with an initial length of 0
            cursor.insertLine(currentLineNum + 1, lineLength);
            
            // 3. Move the cursor to the next line
            cursor.moveToNextLine();
            
            // 4. Update the cursor position
            cursor.setPosition(++cursorPosition);
        }
    }
    

    public void deleteCharacter() {
        int cursorPosition = cursor.getPosition();
        int currentLine = cursor.getCurrentLineNum();
        if (cursorPosition > 0 && cursor.getCurrentColumn() > 0) {
            gapBuffer.delete(cursorPosition);
            cursor.moveLeft();
            cursor.updateLine(currentLine, cursor.getLineLength(currentLine) - 1);
        } else if (cursor.getCurrentLineNum() > 0) {
            gapBuffer.delete(cursorPosition);
            cursor.moveLeft();
            cursor.updateLine(currentLine - 1, cursor.getLineLength(currentLine-1) + cursor.getLineLength(currentLine));
            cursor.removeLine(currentLine);
            // cursor.moveLeft();
        }
    }

    public void reset() {
        cursor.setPosition(0);
        cursor.setCurrentColumn(0);
        cursor.setCurrentLineNum(0);
        cursor.setDesiredColumn(0);
    }

    public void showText() {
        gapBuffer.displayNonGapText();
        System.out.println("LINE NUMBER: " + cursor.getCurrentLineNum() + " | COL: " + cursor.getCurrentColumn() + " | Pos: " + cursor.getPosition());
        System.out.println("Current Line Len: " + cursor.getLineLength(cursor.getCurrentLineNum()) + " ARR: " + cursor.getLineLengthInfo());
        int row = cursor.getCurrentLineNum() + 1;
        int col = cursor.getCurrentColumn() + 1;
        String seq = "\033[" + row + ";" + col + "H";
        System.out.print(seq);
    }
}
