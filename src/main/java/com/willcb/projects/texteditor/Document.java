package com.willcb.projects.texteditor;

import java.util.List;

public class Document {
    private Cursor cursor;
    private GapBuffer gapBuffer;

    public Document(GapBuffer gapBuffer) {
        this.gapBuffer = gapBuffer;
        this.cursor = new Cursor(0);
    }
    
    public GapBuffer getGapBuffer() {
        return gapBuffer;
    }

    public Cursor getCursor() {
        return cursor;
    }

    /**
     * Handles arrow key movements based on the provided action character.
     * 
     * This method processes the directional input ('u' for up, 'd' for down,
     * 'l' for left, 'r' for right) and moves the cursor accordingly.
     * 
     * @param action A character representing the direction of the arrow key:
     *               'u' for up, 'd' for down, 'l' for left, and 'r' for right.
     */
    public void arrowKeyHandler(char action) {
        switch (action) {
            case 'u':
                cursor.moveUp();
                break;
            case 'd':
                cursor.moveDown();
                break;
            case 'l':
                cursor.moveLeft();
                break;
            case 'r':
                cursor.moveRight();
                break;
        }
    }
    
    /**
     * Inserts a character at the current cursor position in the document.
     * 
     * If the character is not a newline ('\n'), it is inserted into the gap buffer, and the 
     * cursor is moved one position to the right. 
     * 
     * If the character is a newline ('\n'), the current line is split at the cursor position:
     * the text after the cursor becomes part of the new line. The cursor then moves to 
     * the beginning of the next line, and the gap buffer is updated accordingly.
     * 
     * @param character The character to insert at the current cursor position.
     */
    public void addCharacter(Character character) {
        int cursorPosition = cursor.getPosition();
        int currentLineNum = cursor.getCurrentLineNum();
        int currentColumn = cursor.getCurrentColumn();
        
        gapBuffer.insert(character, cursorPosition);
        if (character == '\n') {
            // Return character 
            int lineLength = cursor.getLineLength(currentLineNum);

            // Ensure to not insert into position out of bounds by getting minimum of array size and intended line + 1
            cursor.insertLine(Math.min(cursor.getLineLengthInfo().size(), currentLineNum + 1), lineLength - currentColumn);
            cursor.modifyLineLength(currentLineNum, currentColumn);
            cursor.moveToNextLine(); 
        } else {
            // Adding non-return type character
            cursor.moveToNextCharacter();
        }
    }
    
    /**
     * Deletes the character at the current cursor position.
     * 
     * If the cursor is positioned within a line, the character at that position is deleted 
     * and the cursor moves left. The line's length is updated accordingly.
     * 
     * If the cursor is at the beginning of a line and there are previous lines, 
     * the current line is merged with the previous line. The cursor moves to the previous 
     * line and the lengths of both lines are adjusted in the lineLengthInfo.
     */
    public void deleteCharacter() {
        int cursorPosition = cursor.getPosition();
        int currentLine = cursor.getCurrentLineNum();
        int currentLineLength = cursor.getLineLength(currentLine);
        if (cursorPosition > 0 && cursor.getCurrentColumn() > 0) {
            gapBuffer.delete(cursorPosition);
            cursor.moveLeft();
            cursor.modifyLineLength(currentLine, currentLineLength - 1);
        } else if (currentLine > 0) {
            gapBuffer.delete(cursorPosition);
            cursor.moveLeft();
            cursor.modifyLineLength(currentLine - 1, cursor.getLineLength(currentLine - 1) + currentLineLength);
            cursor.deleteLine(currentLine);
        }
    }

    public void resetCursorPosition() {
        cursor.setPosition(0);
        cursor.setCurrentColumn(0);
        cursor.setCurrentLineNum(0);
        cursor.setDesiredColumn(0);
    }

    public void showTextInfo() {
        System.out.println("LINE NUMBER: " + cursor.getCurrentLineNum() + " | COL: " + cursor.getCurrentColumn() + " | Pos: " + cursor.getPosition());
        System.out.println("Current Line Len: " + cursor.getLineLength(cursor.getCurrentLineNum()) + " ARR: " + cursor.getLineLengthInfo());
    }

    public void displayText(){
        List<Character> buffer = gapBuffer.getNonGapText();
        Terminal.moveCursorHome();
        Terminal.eraseContentInRow();
        for (int i = 0; i < buffer.size(); i++) {
            char key = buffer.get(i);
            System.out.print(key);
            if (key == '\n') {
                Terminal.eraseContentInRow();
            }
        }
        Terminal.setCursorTerminalPosition(cursor);
    }

    public List<Character> getDisplayedTextList() {
        return gapBuffer.getNonGapText();
    }

    public int getTotalLines() {
        List<Integer> LineLengthInfo = cursor.getLineLengthInfo();
        return LineLengthInfo.size();
    }
}
