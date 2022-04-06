package me.abdullah.game.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardListener extends KeyAdapter {

    private final Keybinds keybinds;
    private final ActionHandler actionHandler;

    public KeyboardListener(Keybinds keybinds, ActionHandler actionHandler) {
        this.keybinds = keybinds;
        this.actionHandler = actionHandler;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        Action action = keybinds.get(key);
        actionHandler.keyPressed(action);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        Action action = keybinds.get(key);
        actionHandler.keyReleased(action);
    }
}