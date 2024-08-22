package will;

import java.util.ArrayList;
import java.util.List;


public class Document {
    private List<List<Character>> textMatrix;
    private Cursor cursor;

    public Document() {
        // Each index represents a line
        // Each index within each line represents a character
        this.textMatrix = new ArrayList<>();
        textMatrix.add(new ArrayList<>());
        this.cursor = new Cursor(0, 0);
    }

    public List<List<Character>> getTextMatrix() {
        return this.textMatrix;
    }

    public void setCursorPosition(int row, int column) {
        this.cursor.setRow(row, this.textMatrix.size() - 1);
        int currentRow = this.cursor.getRow();
        this.cursor.setColumn(column, this.textMatrix.get(currentRow).size());
    }

    public void addCharacter(Character character) {
        Integer currentRow = cursor.getRow();
        Integer currentColumn = cursor.getColumn();
        if (currentRow >= 0 && currentRow < this.textMatrix.size()) {
            this.textMatrix.get(currentRow).add(currentColumn, character);
        } 
        else if (currentRow >= 0) {
            this.textMatrix.get(currentRow).set(currentColumn, character);
        }

        // Ensure to account for end of line causing row increment later on
        this.cursor.setColumn(currentColumn + 1, this.textMatrix.get(currentRow).size());
    }

    public void deleteCharacter() {
        Integer currentRow = cursor.getRow();
        Integer currentColumn = cursor.getColumn();

        if (currentColumn > 0) {
            this.textMatrix.get(currentRow).remove(currentColumn - 1);
            this.cursor.setColumn(currentColumn - 1, this.textMatrix.get(currentRow).size());
        }
    }
}
