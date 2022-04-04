package me.abdullah.game.objects;

import me.abdullah.game.Game;
import me.abdullah.game.handlers.GameObject;

import java.awt.*;

public class Player extends GameObject {

    private final String name;
    public Player(String name, int x, int y, int velX, int velY) {
        super(x, y, velX, velY);
        this.name = name;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(x, y, (int)(Game.UNIT*16), (int)(Game.UNIT*16));
        g.drawString(name, x, y - (int)(Game.UNIT*12));
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
    }

    public String getName(){
        return name;
    }
}
