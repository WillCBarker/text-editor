package com.willcb.projects.texteditor;
import com.sun.jna.platform.win32.Wincon.KEY_EVENT_RECORD;

import java.util.Scanner;

public class TerminalTextEditor {
    private static Document document; // Keep the document as an instance variable

    public static void main(String[] args) {
        String filePath = args[0];
        document = FileHandler.LoadFileIntoBuffer(filePath);
        Terminal.clearScreen();
        updateTerminalDisplay();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            KEY_EVENT_RECORD keyEvent = Terminal.initializeConsoleInstance();
            
            if (keyEvent.bKeyDown) {
                if (keyEvent.wVirtualKeyCode == 0x1B) { // Escape key
                    enterCommandMode(scanner, filePath);

                } else {
                    handleTextEditing(keyEvent);
                }
            }
        }
    }

    public static void enterCommandMode(Scanner scanner, String filePath) {
        int totalLines = document.getTotalLines();
        Terminal.moveToBottomRow(totalLines + 1);
        System.out.println();
        System.out.print(":");
        String command = scanner.nextLine();
        processCommand(command, filePath);
    }

    public static void updateTerminalDisplay() {
        Terminal.setCursorTerminalPosition(document.getCursor());
        Terminal.saveCursorTerminalPosition();
        Terminal.moveCursorHome();
        document.displayText();
        System.out.flush();
        Terminal.restoreCursorTerminalPosiiton();
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
            // Only process printable keys
            document.addCharacter(keyChar);
        }
        updateTerminalDisplay();
    }

    private static void processCommand(String command, String filePath) {
        updateTerminalDisplay();
        GapBuffer gapBuffer = document.getGapBuffer();
        Terminal.moveCursorHome();
        switch (command) {
            case "w":
                FileHandler.saveFile(filePath, gapBuffer);
                Terminal.clearScreen();
                System.out.print("File saved.");
                break;
            case "q":
                Terminal.clearScreen();
                System.out.print("Exiting editor...");
                System.exit(0);
                break;
            case "wq":
                FileHandler.saveFile(filePath, gapBuffer);
                Terminal.clearScreen();
                System.out.print("File saved. Exiting editor...");
                System.exit(0);
            default:
                break;
        }
    }
}
