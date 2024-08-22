package will;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;

public class DocumentTest {
    private Document document;
    private List<List<Character>> expectedTextMatrix;

    @BeforeEach
    public void setup() {
        document = new Document();
        expectedTextMatrix = new ArrayList<>(); 
        expectedTextMatrix.add(new ArrayList<>());
    }

    @Test
    public void testAddCharacterInOrder() {
        document.addCharacter('a');
        document.addCharacter('b');
        document.addCharacter('c');
        expectedTextMatrix.get(0).add('a');
        expectedTextMatrix.get(0).add('b');
        expectedTextMatrix.get(0).add('c');

        assertEquals(expectedTextMatrix, document.getTextMatrix());
    }

    @Test
    public void testAddCharacterInBetweenCharacters() {
        document.addCharacter('a');
        document.addCharacter('c');
        document.addCharacter('d');
        expectedTextMatrix.get(0).add('a');
        expectedTextMatrix.get(0).add('b');
        expectedTextMatrix.get(0).add('c');
        expectedTextMatrix.get(0).add('d');

        document.setCursorPosition(0, 1);
        document.addCharacter('b');

        assertEquals(expectedTextMatrix, document.getTextMatrix());
    }

    @Test
    public void testDeleteCharacterEndOfString() {
        document.addCharacter('a');
        expectedTextMatrix.get(0).add('a');

        expectedTextMatrix.get(0).remove(0);
        document.deleteCharacter();

        assertEquals(expectedTextMatrix, document.getTextMatrix());
    }

    @Test
    public void testDeleteCharacterMiddleOfString() {
        document.addCharacter('a');
        document.addCharacter('b');
        document.addCharacter('c');
        expectedTextMatrix.get(0).add('a');
        expectedTextMatrix.get(0).add('b');
        expectedTextMatrix.get(0).add('c');

        expectedTextMatrix.get(0).remove(1);
        document.setCursorPosition(0, 2);
        document.deleteCharacter();

        assertEquals(expectedTextMatrix, document.getTextMatrix());
    }

    @Test
    public void testDeleteCharacterFromEmptyString() {
        document.deleteCharacter();

        assertEquals(expectedTextMatrix, document.getTextMatrix());
    }

}
