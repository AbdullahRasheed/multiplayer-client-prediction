package me.abdullah.game.server.packets;

import java.io.Serializable;

public class PlayerInfoPacket implements Serializable {
    public String name;
    public int x, y;
    public int velX, velY;
    public PlayerInfoPacket(String name, int x, int y, int velX, int velY) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
    }
}
