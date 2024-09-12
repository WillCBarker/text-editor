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
    public static final String CLEAR_SCREEN = "\033[2J";
    public static final String MOVE_CURSOR_HOME = "\033[H";
    public static void main(String[] args) {
        String filePath = "C:\\Users\\Willb\\Desktop\\text-editor\\test.txt";
        document = FileHandler.LoadFileIntoBuffer(filePath);

        reloadTerminalDisplay();
        Scanner scanner = new Scanner(System.in);

            KEY_EVENT_RECORD keyEvent = initializeConsoleInstance();
            
            if (keyEvent.bKeyDown) {
                if (keyEvent.wVirtualKeyCode == 0x1B) { // Escape key
                    System.out.print(":");
                    String command = scanner.nextLine(); // Read full command
                    processCommand(command, filePath);
                } else {
                    handleTextEditing(keyEvent);
                }
            }
        }
    }

    public static KEY_EVENT_RECORD initializeConsoleInstance() {
        HANDLE hConsoleInput = Kernel32.INSTANCE.GetStdHandle(Wincon.STD_INPUT_HANDLE);
        INPUT_RECORD[] record = new INPUT_RECORD[1];
        IntByReference eventsRead = new IntByReference(0);
        Kernel32.INSTANCE.ReadConsoleInput(hConsoleInput, record, 1, eventsRead);
        KEY_EVENT_RECORD keyEvent = record[0].Event.KeyEvent;
        return keyEvent;
    }

    public static void reloadTerminalDisplay(){
        System.out.print(CLEAR_SCREEN + MOVE_CURSOR_HOME);
        document.showText();
        System.out.flush();
    }

    private static void handleTextEditing(KEY_EVENT_RECORD keyEvent) {
        char keyChar = keyEvent.uChar;
        
        // Check if the keyChar is a printable character
        if (Character.isISOControl(keyChar)) {
            // Handle control keys (Enter, Backspace, Arrow keys, etc.)
            switch (keyEvent.wVirtualKeyCode) {
                case 0x0D: // Enter key
                    document.addCharacter('\n');
                    break;
                case 0x08: // Backspace key
                    document.deleteCharacter();
                    break;
                case 0x25: // Left arrow key
                    document.arrowKeyHandler('l');
                    break;
                case 0x26: // Up arrow key
                    document.arrowKeyHandler('u');
                    break;
                case 0x27: // Right arrow key
                    document.arrowKeyHandler('r');
                    break;
                case 0x28: // Down arrow key
                    document.arrowKeyHandler('d');
                    break;
            }
        } else {
            // Only process actual printable characters
            document.addCharacter(keyChar);
        }

        reloadTerminalDisplay();
    }


    private static void processCommand(String command, String filePath) {
        reloadTerminalDisplay();
        GapBuffer gapBuffer = document.getGapBuffer();
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
