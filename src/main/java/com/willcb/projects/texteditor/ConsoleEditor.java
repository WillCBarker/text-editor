package com.willcb.projects.texteditor;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Wincon;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.Wincon.INPUT_RECORD;
import com.sun.jna.platform.win32.Wincon.KEY_EVENT_RECORD;
import com.sun.jna.ptr.IntByReference;

public class ConsoleEditor {
    public static void main(String[] args) {
        Document document = new Document();

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

                switch (keyEvent.wVirtualKeyCode) {
                    case 0x0D: // Enter key (VK_RETURN)
                        System.out.println("Enter");
                        break;
                    case 0x08: // Backspace Key (VK_BACK)
                        document.deleteCharacter();
                        break;
                    case 0x25: // Left Arrow key (VK_LEFT)
                        document.arrowKeyHandler("left");
                        break;
                    case 0x27: // Right Arrow key (VK_RIGHT)
                        document.arrowKeyHandler("right");
                        break;
                    default: 
                        document.addCharacter(keyChar);
                        break;
                }
                document.showText();
            }
        }
    }
}
