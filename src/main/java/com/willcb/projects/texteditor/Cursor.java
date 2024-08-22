package com.willcb.projects.texteditor;

public class Cursor {
    private int row;
    private int column;

    public Cursor(int row, int column) {
        this.row = 0;
        this.column = 0;
    }

    /**
     * Accepts a row value and updates the cursors row location.
     * @param selectedRow The value of the row selected.
     * @param maxRow The index of the last row in the document.
     */
    public void setRow(int selectedRow, int maxRow) {
        if (selectedRow > maxRow) {
            this.row = maxRow;
        } else {
            this.row = selectedRow;
        }
    }

    /**
     * Accepts a column value and updates the cursors column location.
     * @param selectedColumn The value of the column selected.
     * @param maxColumn The index of the last column in its specified row.
     */
    public void setColumn(int selectedColumn, int maxColumn) {
        if (selectedColumn > maxColumn) {
            this.column = maxColumn;
        } else {
            this.column = selectedColumn;
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }
}
