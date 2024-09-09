package com.willcb.projects.texteditor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// HANDLES READING FILE INTO GAP BUFFER, SAVING GAP BUFFER INTO FILE, ETC.
public class FileHandler {
    
    public static GapBuffer LoadFileIntoBuffer(String filePath) {
        GapBuffer gapBuffer = new GapBuffer(200);
        Document document = new Document(gapBuffer);
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
            System.out.println("File Loaded:\n");
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");

        }
        return gapBuffer;
    }
}
