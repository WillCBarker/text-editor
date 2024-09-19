package com.willcb.projects.texteditor;

import java.util.ArrayList;
import java.util.List;

public class Cursor {
    private int position;
    private int currentColumn;
    private int currentLineNum;
    private int desiredColumn;
    private List<Integer> lineLengthInfo;

    public Cursor(int position) {
        this.position = 0;
        this.currentLineNum = 0;
        this.currentColumn = 0;
        this.desiredColumn = 0;
        this.lineLengthInfo = new ArrayList<>();
    }

    public List<Integer> getLineLengthInfo() {
        return lineLengthInfo;
    }

    public void moveToNextCharacter() {
        if (lineLengthInfo.size() == 0) {
            lineLengthInfo.add(0);
        }
        position++;
        currentColumn++;
        modifyLineLength(currentLineNum, getLineLength(currentLineNum) + 1);
        desiredColumn = currentColumn;
    }

    public void moveToNextLine() {
        currentLineNum++;

        if (currentLineNum >= lineLengthInfo.size()) {
            lineLengthInfo.add(0);
        }

        currentColumn = 0;
        desiredColumn = 0;
        position++;
    }

    public void moveToPreviousLine() {
        if (currentLineNum > 0) {
            currentLineNum--;
            currentColumn = getLineLength(currentLineNum);
            desiredColumn = currentColumn;
            position--;
        }
    }

    public void moveLeft() {
        if (currentColumn > 0) {
            currentColumn--;
            position--;
        } else if (currentLineNum > 0) {
            moveToPreviousLine();
        }
        desiredColumn = currentColumn;
    }

    public void moveRight() {
        if (currentColumn >= getLineLength(currentLineNum)) {
            moveToNextLine();
        } else {
            currentColumn++;
            desiredColumn = currentColumn;
            position++;
        }
    }

    public void moveUp() {
        if (currentLineNum > 0) {
            // Shift to start of current line (subtract current Column)
            position -= currentColumn;

            // update line number
            currentLineNum--;

            // Find whether above line is shorter than desired position, assign to min
            currentColumn = Math.min(desiredColumn, getLineLength(currentLineNum));
            int ColNewLineLenDiff = Math.max(getLineLength(currentLineNum), currentColumn)
                    - Math.min(getLineLength(currentLineNum), currentColumn);
            position -= ColNewLineLenDiff;
            position -= 1;
        }
    }

    public void moveDown() {
        if (currentLineNum < lineLengthInfo.size()) {
            int distanceToLineEnd = getLineLength(currentLineNum) - currentColumn;
            currentLineNum++;
            currentColumn = Math.min(desiredColumn, getLineLength(currentLineNum));
            position += distanceToLineEnd + currentColumn + 1;
        }
    }

    public void modifyLineLength(int lineIndex, int columnLength) {
        lineLengthInfo.set(lineIndex, columnLength);
    }

    public void insertLine(int lineIndex, int columnLength) {
        lineLengthInfo.add(lineIndex, columnLength);
    }

    public void deleteLine(int lineIndex) {
        lineLengthInfo.remove(lineIndex);
    }

    public Integer getLineLength(int lineIndex) {
        if (lineIndex < 0 || lineIndex >= lineLengthInfo.size()) {
            return 0;
        }
        return lineLengthInfo.get(lineIndex);
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public void setCurrentColumn(int column) {
        currentColumn = column;
    }

    public int getCurrentLineNum() {
        return currentLineNum;
    }

    public void setCurrentLineNum(int lineNum) {
        currentLineNum = lineNum;
    }

    public void setPosition(int newPosition) {
        position = newPosition;
    }

    public int getPosition() {
        return position;
    }

    public int getDesiredColumn() {
        return desiredColumn;
    }

    public void setDesiredColumn(int column) {
        desiredColumn = column;
    }

}
