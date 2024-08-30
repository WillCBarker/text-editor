package com.willcb.projects.texteditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GapBuffer {
    private char []buffer;
    private int gapStart; 
    private int gapEnd;

    public GapBuffer() {
        this.buffer = new char[50];
        this.gapStart = 0;
        this.gapEnd = 10;
    }

    public void delete(int cursorPosition) {
        shiftGapToCursor(cursorPosition);
        if (this.gapStart > 0) {
            this.gapStart--;
        }
    }

    public void insert(char c, int cursorPosition) {
        if (this.gapStart == this.gapEnd) {
            resizeBuffer();
        }
        shiftGapToCursor(cursorPosition);
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
            System.arraycopy(this.buffer, this.gapEnd + 1, this.buffer, this.gapStart, shiftDistance);
            this.gapStart += shiftDistance;
            this.gapEnd += shiftDistance;
        } else if (cursorPosition < this.gapStart) {
            // Shift gap to the left
                    
            int shiftDistance = this.gapStart - cursorPosition;
            System.arraycopy(this.buffer, cursorPosition, this.buffer, this.gapEnd - shiftDistance + 1, shiftDistance);
            this.gapStart -= shiftDistance;
            this.gapEnd -= shiftDistance;
        } 
    }

    public void printNonGapText() {
        List<Character> myList = new ArrayList<>();
        System.out.println(this);
        for (int i = 0; i < this.buffer.length; i++) {
            if (i < this.gapStart || i > this.gapEnd) {
                myList.add(this.buffer[i]);
            }
        }
        System.out.println(myList);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.buffer);
    }

    public char[] getBuffer() {
        return this.buffer;
    }

    public int getGapStart() {
        return this.gapStart;
    }

    public int getGapEnd() {
        return this.gapEnd;
    }

    public int getBufferSize(){
        return this.buffer.length;
    }

    public int getGapSize() {
        return this.gapEnd - this.gapStart;
        }
}
