package com.willcb.projects.texteditor;

import java.util.HashMap;
import java.util.Map;

public enum KeyCode {
    ENTER(0x0D, '\n'),
    BACKSPACE(0x08, '\b'),
    LEFT_ARROW(0x25, 'l'),
    UP_ARROW(0x26, 'u'),
    RIGHT_ARROW(0x27, 'r'),
    DOWN_ARROW(0x28, 'd'),
    ESCAPE(0x1B, 'x');

    private final int code;
    private final char command;

    private static final Map<Integer, KeyCode> codeLookupMap = new HashMap<>();

    static {
        for(KeyCode keyCode: KeyCode.values()) {
            codeLookupMap.put(keyCode.getCode(), keyCode);
        }
    }

    KeyCode(int code, char command) {
        this.code = code;
        this.command = command;
    }

    public int getCode() {
        return code;
    }

    public char getCommand() {
        return command;
    }

    public static KeyCode fromCode(int code) {
        return codeLookupMap.get(code);
    }
}
