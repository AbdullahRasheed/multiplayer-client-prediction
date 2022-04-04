package me.abdullah.game.server.packets;

import me.abdullah.game.input.actions.PlayerAction;

import java.io.Serializable;

public class PlayerInputPacket implements Serializable {
    public PlayerAction action;
    public boolean pressed;
    public PlayerInputPacket(PlayerAction action, boolean pressed) {
        this.action = action;
        this.pressed = pressed;
    }
}
