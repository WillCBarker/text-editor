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

                switch (keyEvent.wVirtualKeyCode) {
                    case 0x0D: // Enter key (VK_RETURN)
                        System.out.println("Enter");
                        break;
                    case 0x08: // Backspace Key (VK_BACK)
                        if (gapBuffer.getGapStart() > 0) {
                            gapBuffer.delete();
                            cursor.setPosition(--cursorPosition);
                        }
                        break;
                    case 0x25: // Left Arrow key (VK_LEFT)
                        if (cursorPosition > 0) {
                            cursor.setPosition(--cursorPosition);
                        }
                        break;
                    case 0x27: // Right Arrow key (VK_RIGHT)
                        if (cursorPosition < gapBuffer.getBufferSize()) {
                            cursor.setPosition(++cursorPosition);
                        }
                        break;
                    default: 
                        System.out.println("Key: " + keyChar);
                        gapBuffer.insert(keyChar, cursorPosition);
                        cursor.setPosition(++cursorPosition);
                        break;
                }
                gapBuffer.printNonGapText();
            }
        }
    }
}
