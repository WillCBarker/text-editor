package will;

import java.util.HashMap;
import java.util.ArrayList;


public class Document {
    private ArrayList<ArrayList<Character>> textMatrix;
    private HashMap<String, Integer> cursorMap;

    public Document() {
        // Each index represents a line
        this.textMatrix = new ArrayList<>();
        this.cursorMap = new HashMap<>();
        this.cursorMap.put("row", 0);
        this.cursorMap.put("column", 0);
    }

    public ArrayList<ArrayList<Character>> getText() {
        return textMatrix;
    }

    public void setCursorPosition(Integer rowValue, Integer columnValue) {
        // If selected row is past the final row, set selected row to last used line + 1
        if (rowValue > this.textMatrix.size() - 1) {
            ArrayList<Character> newLine = new ArrayList<>();
            rowValue = this.textMatrix.size();
            this.textMatrix.add(newLine);
        }

        // If selected column hasn't been created, set selected column to end of current line + 1.
        if (columnValue > this.textMatrix.get(rowValue).size() - 1) {
            columnValue = this.textMatrix.get(rowValue).size();
        }

        this.cursorMap.put("row", rowValue);
        this.cursorMap.put("column", columnValue);
        System.out.println("Row: " + this.cursorMap.get("row") + " | " + "Col: " + this.cursorMap.get("column"));
        // maybe return cursorMap?
    }

    public Integer getCursorRow() {
        return this.cursorMap.get("row");
    }

    public Integer getCursorColumn() {
        return this.cursorMap.get("column");
    }

    public ArrayList<ArrayList<Character>> getTextMatrix() {
        return this.textMatrix;
    }

    public void addText(Character character) {
        Integer row = this.cursorMap.get("row");
        Integer column = this.cursorMap.get("column");

        // TBD
        this.textMatrix.get(row).set(column, character);
    }

}
