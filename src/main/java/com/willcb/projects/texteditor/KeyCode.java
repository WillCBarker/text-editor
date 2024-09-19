package com.willcb.projects.texteditor;
import java.util.HashMap;
import java.util.Map;

public enum KeyCode {
    ENTER(0x0D, '\n') {
        @Override
        public void performAction(Document document) {
            document.addCharacter(getCommand());
        }
    },
    BACKSPACE(0x08, '\b') {
        @Override
        public void performAction(Document document) {
            document.deleteCharacter();
        }
    },
    LEFT_ARROW(0x25, 'l') {
        @Override
        public void performAction(Document document) {
            document.arrowKeyHandler(getCommand());
        }
    },
    UP_ARROW(0x26, 'u') {
        @Override
        public void performAction(Document document) {
            document.arrowKeyHandler(getCommand());
        }
    },
    RIGHT_ARROW(0x27, 'r') {
        @Override
        public void performAction(Document document) {
            document.arrowKeyHandler(getCommand());
        }
    },
    DOWN_ARROW(0x28, 'd') {
        @Override
        public void performAction(Document document) {
            document.arrowKeyHandler(getCommand());
        }
    };

    private final int code;
    private final char command;

    private static final Map<Integer, KeyCode> codeLookupMap = new HashMap<>();

    static {
        for (KeyCode keyCode : KeyCode.values()) {
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

    public abstract void performAction(Document document);

    public static KeyCode fromCode(int code) {
        return codeLookupMap.get(code);
    }
}
