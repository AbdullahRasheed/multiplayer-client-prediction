package me.abdullah.game.input;

import me.abdullah.game.files.GameFiles;

import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Keybinds {

    private final Map<Integer, Action> keybinds;

    private Keybinds(Map<Integer, Action> keybinds) {
        this.keybinds = keybinds;
    }

    public static Keybinds load(Class<? extends Action> type, String map) {

        Map<Integer, Action> keybinds = new HashMap<>();

        try {
            Method parse = type.getDeclaredMethod("valueOf", String.class);

            GameFiles.processLines("player_keybinds", s -> {
                String[] args = s.split("::");

                try {
                    int keyCode = KeyEvent.class.getField(args[0].toUpperCase()).getInt(null);
                    Action action = (Action) parse.invoke(null, args[1].toUpperCase());

                    keybinds.put(keyCode, action);
                } catch (IllegalAccessException | NoSuchFieldException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            });

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return new Keybinds(keybinds);
    }

    public Action get(int keyCode) {
        return keybinds.get(keyCode);
    }

    public void act(int keyCode) {
        get(keyCode).act();
    }

    public void release(int keyCode) {
        get(keyCode).release();
    }
}
