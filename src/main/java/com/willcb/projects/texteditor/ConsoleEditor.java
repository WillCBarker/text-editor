package com.willcb.projects.texteditor;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Wincon;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.Wincon.INPUT_RECORD;
import com.sun.jna.platform.win32.Wincon.KEY_EVENT_RECORD;
import com.sun.jna.ptr.IntByReference;
import java.util.Scanner;

public class ConsoleEditor {
    private static Document document; // Keep the document as an instance variable
    private static GapBuffer gapBuffer;
    
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Willb\\Desktop\\text-editor\\test.txt";
        gapBuffer = FileHandler.LoadFileIntoBuffer(filePath);
        document = new Document(gapBuffer);

        HANDLE hConsoleInput = Kernel32.INSTANCE.GetStdHandle(Wincon.STD_INPUT_HANDLE);
        INPUT_RECORD[] record = new INPUT_RECORD[1];
        IntByReference eventsRead = new IntByReference(0);

        // Display initially loaded file to user
        document.showText();
        
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            Kernel32.INSTANCE.ReadConsoleInput(hConsoleInput, record, 1, eventsRead);
            KEY_EVENT_RECORD keyEvent = record[0].Event.KeyEvent;

            if (keyEvent.bKeyDown) {
                if (keyEvent.wVirtualKeyCode == 0x1B) { // Escape key
                    System.out.print(":");
                    String command = scanner.nextLine(); // Read full command
                    processCommand(command, filePath);
                    document.showText(); // Refresh the document display
                } else {
                    handleTextEditing(keyEvent);
                }
            }
        }
    }

    private static void handleTextEditing(KEY_EVENT_RECORD keyEvent) {
        char keyChar = keyEvent.uChar;
        switch (keyEvent.wVirtualKeyCode) {
            case 0x0D: // Enter key
                document.addCharacter('\n');
                break;
            case 0x08: // Backspace key
                document.deleteCharacter();
                break;
            case 0x25: // Left arrow key
                document.arrowKeyHandler("left");
                break;
            case 0x27: // Right arrow key
                document.arrowKeyHandler("right");
                break;
            default: // Insert character
                document.addCharacter(keyChar);
                break;
        }
        document.showText();
    }

    private static void processCommand(String command, String filePath) {
        switch (command) {
            case "w":
                FileHandler.saveFile(filePath, gapBuffer);
                System.out.println("File saved.");
                break;
            case "q":
                System.out.println("Exiting editor...");
                System.exit(0);
                break;
            case "wq":
                FileHandler.saveFile(filePath, gapBuffer);
                System.out.println("File saved. Exiting editor...");
                System.exit(0);
            default:
                System.out.println("Unknown command: " + command);
                break;
        }
    }
}
