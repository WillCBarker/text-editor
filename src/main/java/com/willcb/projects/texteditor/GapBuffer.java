package com.willcb.projects.texteditor;

public class GapBuffer {
    private char []buffer;
    private int gapStart; 
    private int gapEnd;

    public GapBuffer() {
        this.buffer = new char[200];
        this.gapStart = 0;
        this.gapEnd = 50;
    }

    public void delete() {
        if (this.gapStart > 0) {
            this.gapStart--;
        }
    }

    public void insert(char c) {
        if (this.gapStart == this.gapEnd) {
            resizeBuffer();
        }
        this.buffer[this.gapStart++] = c;
    }

    public void resizeBuffer() {
        int newSize = buffer.length * 2;
        char[] newBuffer = new char[newSize];
        System.arraycopy(buffer, 0, newBuffer, 0, gapStart);
        int newGapEnd = newSize - (this.buffer.length - this.gapEnd);
        System.arraycopy(this.buffer, this.gapEnd, newBuffer, newGapEnd, this.buffer.length - this.gapEnd);
        this.gapEnd = newGapEnd;
        this.buffer = newBuffer;
    }

    public void shiftGapToCursor(int cursorPosition) {
        if (cursorPosition == this.gapStart) {
            return;
        }
        if (cursorPosition > this.gapStart) {
            // Shift gap to the right
            int shiftDistance = cursorPosition - this.gapStart;
            System.arraycopy(this.buffer, this.gapStart, this.buffer, this.gapEnd, shiftDistance);
            this.gapStart += shiftDistance;
            this.gapEnd += shiftDistance;
        } else if (cursorPosition < this.gapStart) {
            // Shift gap to the left
            int shiftDistance = this.gapStart - cursorPosition;
            System.arraycopy(this.buffer, this.gapEnd - shiftDistance, this.buffer, this.gapStart - shiftDistance, shiftDistance);
            this.gapStart -= shiftDistance;
            this.gapEnd -= shiftDistance;
        } 
    }
}
