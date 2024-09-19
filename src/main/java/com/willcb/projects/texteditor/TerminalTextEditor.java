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

        // Check if keyChar is printable
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
                case BACKSPACE: // Backspace key
                    document.deleteCharacter();
                    break;
                case LEFT_ARROW: // Left arrow key
                    document.arrowKeyHandler('l');
                    break;
                case UP_ARROW: // Up arrow key
                    document.arrowKeyHandler('u');
                    break;
                case RIGHT_ARROW: // Right arrow key
                    document.arrowKeyHandler('r');
                    break;
                case DOWN_ARROW: // Down arrow key
                    document.arrowKeyHandler('d');
                    break;

            }
        }
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
