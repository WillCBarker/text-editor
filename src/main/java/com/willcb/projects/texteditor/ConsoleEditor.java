package com.willcb.projects.texteditor;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Wincon;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.Wincon.INPUT_RECORD;
import com.sun.jna.platform.win32.Wincon.KEY_EVENT_RECORD;
import com.sun.jna.ptr.IntByReference;

public class ConsoleEditor {
    public static void main(String[] args) {
        GapBuffer gapBuffer = new GapBuffer();
        Cursor cursor = new Cursor(0);

        HANDLE hConsoleInput = Kernel32.INSTANCE.GetStdHandle(Wincon.STD_INPUT_HANDLE);
        // Use hConsole to read input and write output

        INPUT_RECORD[] record = new INPUT_RECORD[1];
        IntByReference eventsRead = new IntByReference(0);

        while (true) {
            Kernel32.INSTANCE.ReadConsoleInput(hConsoleInput, record, 1, eventsRead);
            System.out.println(record[0].Event.KeyEvent);
            KEY_EVENT_RECORD keyEvent = record[0].Event.KeyEvent;
            if (keyEvent.bKeyDown) {
                char keyChar = keyEvent.uChar;
                int cursorPosition = cursor.getPosition();
                if (keyChar == '\r') {
                    System.out.println("Enter");
                } else if (keyChar == '\b') {
                    if (gapBuffer.getGapStart() > 0) {
                        gapBuffer.delete(cursor.getPosition());
                        cursor.setPosition(--cursorPosition);
                    }
                } else {
                    System.out.println("Key: " + keyChar);
                    gapBuffer.insert(keyChar, cursorPosition);
                    cursor.setPosition(++cursorPosition);
                }
                System.out.println(gapBuffer);
                gapBuffer.printNonGapText();
                System.out.println(cursor.getPosition());
            }
        }
    }
}


//  misses second to last letter