package com.willcb.projects.texteditor;
import java.util.List;
import java.util.logging.Logger;


public class Document {
    private static Logger logger = LoggerUtil.getLogger();
    private Cursor cursor;
    private GapBuffer gapBuffer;
    private String filePath;

    public Document(GapBuffer gapBuffer, String filePath) {
        this.filePath = filePath;
        this.gapBuffer = gapBuffer;
        this.cursor = new Cursor(0);
    }
    
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
        
        gapBuffer.insert(character, cursorPosition);
        if (character == '\n') {
            handleCarriageReturn();
        } else {
            // Adding non-return type character
            cursor.moveToNextCharacter();
        }
    }

    public void handleCarriageReturn() {
        int currentLineNum = cursor.getCurrentLineNum();
        int currentColumn = cursor.getCurrentColumn();
        int lineLength = cursor.getLineLength(currentLineNum);
        // Ensure to not insert into position out of bounds by getting minimum of array size and intended line + 1
        cursor.insertLine(Math.min(cursor.getLineLengthInfo().size(), currentLineNum + 1), lineLength - currentColumn);
        cursor.modifyLineLength(currentLineNum, currentColumn);
        cursor.moveToNextLine();
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

        logger.warning("DELETE BEFORE > CL: " + currentLine);

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
        logger.warning("DELETE AFTER > CL: " + cursor.getCurrentLineNum());
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

    public void displayText() {
        List<Character> buffer = gapBuffer.getNonGapText();
        Terminal.displayUI(getTotalLines()+1, cursor, buffer.size());
        Terminal.moveCursorHome();
        Terminal.eraseContentInRow();
        
        // logger.warning("Buffer: " + buffer + " CURSOR LN: " + cursor.getCurrentLineNum());
        
        int totalLines = Terminal.getTerminalRows(); // Total number of lines that can be displayed
        int currentLineNum = cursor.getCurrentLineNum(); // Current cursor line
        // HERE: Make it so it isn't reliant on currentLineNum UNTIL at the border of top or bottom, currently works for bottom border already, this impacts auto-shifting up negatively currently
        int startLine = Math.max(0, currentLineNum - (totalLines-3));
        int endLine = Math.min(buffer.size(), startLine + totalLines); // Calculate end line index
        int startPoint = getStartIndexOfLine(startLine);
        int endPoint = getStartIndexOfLine(endLine) + cursor.getLineLength(endLine);

        logger.warning("START LINE: " + startLine + " | END LINE: " + endLine);
        
        for (int i = startPoint; i < endPoint; i++) {
            char key = buffer.get(i);
            System.out.print(key);
            if (key == '\n') {
                Terminal.eraseContentInRow();
            }
        }
        Terminal.setCursorTerminalPosition(cursor);
    }
    
    public int getStartIndexOfLine(int lineNum) {
        List<Integer> LineLengthInfo = cursor.getLineLengthInfo();
        int index = 0;
        if (lineNum < LineLengthInfo.size()) {
            for (int i = 0; i < lineNum - 1; i++) {
                index += LineLengthInfo.get(i);
            }
            return Math.max(0, index + lineNum - 1);
        }
        for (int i = 0; i < LineLengthInfo.size(); i++) {
            index += LineLengthInfo.get(i);
        }
        return Math.max(0, index + LineLengthInfo.size() - 1);
    }

    public List<Character> getDisplayedTextList() {
        return gapBuffer.getNonGapText();
    }

    public int getTotalLines() {
        List<Integer> LineLengthInfo = cursor.getLineLengthInfo();
        return LineLengthInfo.size();
    }
}

