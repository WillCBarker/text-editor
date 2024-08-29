package com.willcb.projects.texteditor;

public class Cursor {
    private int position;

    public Cursor(int position) {
        this.position = 0;
    }

    public void setPosition(int newPosition) {
        this.position = newPosition;
    }

    public int getPosition() {
        return this.position;
    }
}
