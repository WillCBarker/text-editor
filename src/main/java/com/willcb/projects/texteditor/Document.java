package com.willcb.projects.texteditor;

import java.util.ArrayList;
import java.util.List;


public class Document {
    private List<List<Character>> textMatrix;
    private Cursor cursor;

    public Document() {
        // Each index represents a line
        // Each index within each line represents a character
        this.textMatrix = new ArrayList<>();
        addLine();
        this.cursor = new Cursor(0, 0);
    }

    /**
     * Sets the position of the cursor within the document.
     * Adjusts the row and column to valid positions within the text matrix.
     *
     * @param row    The desired row to set the cursor to. 
     *               If the value is out of bounds, it is adjusted to the nearest valid value.
     * @param column The desired column to set the cursor to. 
     *               If the value is out of bounds, it is adjusted to the nearest valid value within the row.
     */
    public void setCursorPosition(int row, int column) {
        this.cursor.setRow(row, this.textMatrix.size() - 1);
        int currentRow = this.cursor.getRow();
        this.cursor.setColumn(column, this.textMatrix.get(currentRow).size());
    }

    /**
     * Adds a character at the current cursor position within the document.
     * If the cursor is at a valid position within a row, the character is inserted at that position.
     * If the cursor is positioned at the end of a row, the character is appended.
     * After insertion, the cursor moves to the next column.
     *
     * @param character The character to be added to the document.
     */
    public void addCharacter(Character character) {
        int currentRow = cursor.getRow();
        int currentColumn = cursor.getColumn();
        if (currentRow >= 0 && currentRow < this.textMatrix.size()) {
            this.textMatrix.get(currentRow).add(currentColumn, character);
        } 
        else if (currentRow >= 0) {
            this.textMatrix.get(currentRow).set(currentColumn, character);
        }

        // Ensure to account for end of line causing row increment later on
        this.cursor.setColumn(currentColumn + 1, this.textMatrix.get(currentRow).size());
    }

    /**
     * Deletes the character at the position immediately before the current cursor position.
     * If the cursor is at the beginning of a row, no character is deleted.
     * After deletion, the cursor moves to the previous column.
     */
    public void deleteCharacter() {
        int currentRow = cursor.getRow();
        int currentColumn = cursor.getColumn();

        if (currentColumn > 0) {
            this.textMatrix.get(currentRow).remove(currentColumn - 1);
            this.cursor.setColumn(currentColumn - 1, this.textMatrix.get(currentRow).size());
        }
    }

    /*
     * Appends an ArrayList to the textMatrix.
     */
    public void addLine() {
        this.textMatrix.add(new ArrayList<>());
    }

    /**
     * Retrieves the current state of the text matrix.
     *
     * @return A list of lists where each inner list represents a line of characters in the document.
     */
    public List<List<Character>> getTextMatrix() {
        return this.textMatrix;
    }
}
