package com.willcb.projects.texteditor;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

// HANDLES READING FILE INTO GAP BUFFER, SAVING GAP BUFFER INTO FILE, ETC.
public class FileHandler {
    
    public static Document LoadFileIntoBuffer(String filePath) {
        GapBuffer gapBuffer = new GapBuffer(200);
        Document document = new Document(gapBuffer, filePath);
        try {
            File obj = new File(filePath);
            Scanner reader = new Scanner(obj);
            while (reader.hasNextLine()) {
                String lineData = reader.nextLine();
                
                for (int i = 0; i < lineData.length(); i++) {
                    document.addCharacter(lineData.charAt(i));
                }
                document.addCharacter('\n');
                
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");

        }
        document.resetCursorPosition();
        return document;
    }

    public static void saveFile(String filePath, GapBuffer gapBuffer) {
        List<Character> gaplessBuffer = gapBuffer.getNonGapText();
        try (FileWriter writer = new FileWriter(filePath);) {
            for (int i = 0; i < gaplessBuffer.size(); i++) {
                writer.write(gaplessBuffer.get(i));
            }
            writer.close();
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}
