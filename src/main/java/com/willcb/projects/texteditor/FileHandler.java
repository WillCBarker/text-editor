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
            int lineCount = 0;
            while (reader.hasNextLine()) {
                String lineData = reader.nextLine();
                
                for (int i = 0; i < lineData.length(); i++) {
                    gapBuffer.insert(lineData.charAt(i), lineCount + i);
                }
                lineCount += lineData.length()+1;
                gapBuffer.insert('\n', lineCount);
                lineCount += 1;
                
            }
            reader.close();
            System.out.println("File Loaded:\n");
            System.out.println(gapBuffer.getGapStart() + " | " + gapBuffer.getGapEnd() + " | " + gapBuffer.getBufferSize());
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");

        }
        return gapBuffer;
    }
}
