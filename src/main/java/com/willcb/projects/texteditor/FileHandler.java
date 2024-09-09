package com.willcb.projects.texteditor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


// HANDLES READING FILE INTO GAP BUFFER, SAVING GAP BUFFER INTO FILE, ETC.
public class FileHandler {
    public static GapBuffer LoadFileIntoBuffer(String filePath) {
        GapBuffer gapBuffer = new GapBuffer(200);
        try {
            File obj = new File(filePath);
            Scanner reader = new Scanner(obj);
            
            while (reader.hasNextLine()) {
                String lineData = reader.nextLine();

                for (int i = 0; i < lineData.length(); i++) {
                    gapBuffer.insert(lineData.charAt(i), i);
                }
            }
            reader.close();
            System.out.println(gapBuffer);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");

        }
        return gapBuffer;
    }
}
