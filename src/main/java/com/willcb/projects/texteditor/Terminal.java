package com.willcb.projects.texteditor;

import java.util.logging.Logger;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Wincon;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.Wincon.INPUT_RECORD;
import com.sun.jna.platform.win32.Wincon.KEY_EVENT_RECORD;
import com.sun.jna.platform.win32.Wincon.CONSOLE_SCREEN_BUFFER_INFO;
import com.sun.jna.ptr.IntByReference;

public class Terminal {
    private static Logger logger = LoggerUtil.getLogger();
    private static int topRow = 1;
    private static int bottomRow = getTerminalRows();

    public static void decrementTopRow() {
        topRow = topRow - 1;
        topRow = Math.max(0, topRow);
    }

    public static void decrementBottomRow() {
        Terminal.bottomRow = bottomRow - 1;
    }

    public static void incrementTopRow() {
        topRow = topRow + 1;
    }

    public static void incrementBottomRow() {
        Terminal.bottomRow = bottomRow + 1;
    }

    public static void setTopRow(int topRow) {
        Terminal.topRow = topRow;
    }

    public static void setBottomRow(int bottomRow) {
        Terminal.bottomRow = bottomRow;
    }

    public static int getTopRow() {
        return topRow;
    }

    public static int getBottomRow() {
        return bottomRow;
    }

    public static KEY_EVENT_RECORD initializeConsoleInstance() {
        HANDLE hConsoleInput = Kernel32.INSTANCE.GetStdHandle(Wincon.STD_INPUT_HANDLE);
        INPUT_RECORD[] record = new INPUT_RECORD[1];
        IntByReference eventsRead = new IntByReference(0);
        Kernel32.INSTANCE.ReadConsoleInput(hConsoleInput, record, 1, eventsRead);
        return record[0].Event.KeyEvent;
    }

    public static int getTerminalRows() {
        Kernel32 kernel32 = Kernel32.INSTANCE;
        HANDLE hOutput = Kernel32.INSTANCE.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
        CONSOLE_SCREEN_BUFFER_INFO info = new CONSOLE_SCREEN_BUFFER_INFO();
        kernel32.GetConsoleScreenBufferInfo(hOutput, info);
        return info.srWindow.Bottom - info.srWindow.Top + 1;
    }

    // TODO: 
    // Handle when lines become triple, quadruple digit, etc.
    public static void displayUI(int documentRows, Cursor cursor, int bufferSize) {
        int totalLines = Terminal.getTerminalRows(); // Total number of lines that can be displayed
        int currentLineNum = cursor.getCurrentLineNum(); // Current cursor line
        // HERE: Make it so it isn't reliant on currentLineNum UNTIL at the border of top or bottom, currently works for bottom border already, this impacts auto-shifting up negatively currently
        int startLine = Math.max(0, currentLineNum - (totalLines-3));
        int endLine = Math.min(cursor.getLineLengthInfo().size(), startLine + totalLines); // Calculate end line index
        logger.info(("\nStartLine: " + startLine + " Endline: " + endLine));
        eraseRow();
        for (int i = startLine + 1; i < endLine; i++) {
            if (i < 10) {
                System.out.println(" " + i + " |");
            } else {
                System.out.println(i + " |");
            }
        }
        // Erase 
        for (int i = documentRows; i < getTerminalRows(); i++) {
            eraseRow();
            System.out.print("\u001B[1B");
        }
    }

    public static void eraseContentInRow() {
        System.out.print("\033[6G");
        System.out.print("\033[K");
    }

    public static void eraseRow() {
        System.out.print("\033[G");
        System.out.print("\033[K");
    }

    public static void clearScreen() {
        System.out.print("\033[2J");
    }

    public static void saveCursorTerminalPosition() {
        System.out.print("\033[s");
    }

    public static void restoreCursorTerminalPosiiton() {
        System.out.print("\033[u");

    }
    public static void moveCursorHome() {
        String MOVE_CURSOR_HOME = "\033[" + topRow + ";" + "0H";
        System.out.print(MOVE_CURSOR_HOME);
    }

    public static void moveToBottomRow(int terminalRows) {
        System.out.print("\033[" + terminalRows + ";1H");
    }

    public static void setCursorTerminalPosition(Cursor cursor) {
        int row = cursor.getCurrentLineNum() + 1;
        int col = cursor.getCurrentColumn() + 6; 

        String cursorPositionSeq = "\033[" + row + ";" + col + "H";
        System.out.print(cursorPositionSeq);
    }

    public static void displayCursorInfo(Cursor cursor) {
        int row = cursor.getCurrentLineNum() + 1;
        int col = cursor.getCurrentColumn(); 
        // moveToBottomRow(getTerminalRows());
        // System.out.print("Ln " + row + ", Col " + col + ", Total Rows: " + getTerminalRows() + " | CLL: " + cursor.getLineLength(cursor.getCurrentLineNum()) + " ARR: " + cursor.getLineLengthInfo());
        // System.out.print("Ln " + row + ", Col " + col + ", Total Rows: " + getTerminalRows() + " | TR: " + topRow + " | BR: " + bottomRow);
    }
}
