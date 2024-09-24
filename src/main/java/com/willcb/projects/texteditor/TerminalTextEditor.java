package com.willcb.projects.texteditor;

import com.sun.jna.platform.win32.Wincon.KEY_EVENT_RECORD;
import java.util.Scanner;

public class TerminalTextEditor {
    private static Document document;

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
        Terminal.moveToBottomRow(Terminal.getTerminalRows()-1);
        System.out.print(":");
        String command = scanner.nextLine();
        Terminal.eraseRow();
        processCommand(command, filePath);
    }

    public static void updateTerminalDisplay() {
        saveAndResetTerminalCursorPosition();
        displayUIAndText();
        flushAndRestoreCursor();
    }
    
    private static void saveAndResetTerminalCursorPosition() {
        Terminal.setCursorTerminalPosition(document.getCursor());
        Terminal.saveCursorTerminalPosition();
        Terminal.moveCursorHome();
    }
    
    private static void displayUIAndText() {
        Terminal.displayUI(document.getTotalLines());
        Terminal.displayCursorInfo(document.getCursor());
        document.displayText();
    }
    
    private static void flushAndRestoreCursor() {
        System.out.flush();
        Terminal.restoreCursorTerminalPosiiton();
    }
    

    private static void handleTextEditing(KEY_EVENT_RECORD keyEvent) {
        char keyChar = keyEvent.uChar;

        // Check if keyChar is not typically printable
        if (Character.isISOControl(keyChar)) {
            handleControlKey(keyEvent);
        } else {
            document.addCharacter(keyChar);
        }
        updateTerminalDisplay();
    }

    private static void handleControlKey(KEY_EVENT_RECORD keyEvent) {
        KeyCode key = KeyCode.fromCode(keyEvent.wVirtualKeyCode);
        if (key != null) {
            switch (key) {
                case ENTER:
                    document.addCharacter('\n');
                    break;
                case BACKSPACE:
                    document.deleteCharacter();
                    break;
                case LEFT_ARROW:
                    document.arrowKeyHandler('l');
                    break;
                case UP_ARROW:
                    document.arrowKeyHandler('u');
                    break;
                case RIGHT_ARROW:
                    document.arrowKeyHandler('r');
                    break;
                case DOWN_ARROW:
                    document.arrowKeyHandler('d');
                    break;
            }
        }
    }

    private static void processCommand(String command, String filePath) {
        updateTerminalDisplay();
        GapBuffer gapBuffer = document.getGapBuffer();
        
        switch (command) {
            case "w":
                FileHandler.saveFile(filePath, gapBuffer);
                break;
            case "q":
                exitEditor("Exiting editor...");
                break;
            case "wq":
                FileHandler.saveFile(filePath, gapBuffer);
                exitEditor("File saved. Exiting editor...");
                break;
            default:
                break;
        }
    }
    
    private static void exitEditor(String message) {
        Terminal.clearScreen();
        Terminal.moveCursorHome();
        System.out.print(message);
        System.exit(0);
    }
}
