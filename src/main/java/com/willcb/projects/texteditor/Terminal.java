package com.willcb.projects.texteditor;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Wincon;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.Wincon.INPUT_RECORD;
import com.sun.jna.platform.win32.Wincon.KEY_EVENT_RECORD;
import com.sun.jna.ptr.IntByReference;

public class Terminal {
    public static KEY_EVENT_RECORD initializeConsoleInstance() {
        HANDLE hConsoleInput = Kernel32.INSTANCE.GetStdHandle(Wincon.STD_INPUT_HANDLE);
        INPUT_RECORD[] record = new INPUT_RECORD[1];
        IntByReference eventsRead = new IntByReference(0);
        Kernel32.INSTANCE.ReadConsoleInput(hConsoleInput, record, 1, eventsRead);
        return record[0].Event.KeyEvent;
    }


    public static void clearScreenAndMoveCursorHome() {
        String CLEAR_SCREEN = "\033[2J";
        String MOVE_CURSOR_HOME = "\033[H";
        System.out.print(CLEAR_SCREEN + MOVE_CURSOR_HOME);
    }

    public static void moveToBottomRow(int terminalRows) {
        System.out.print("\033[" + terminalRows + ";1H");
    }
}
