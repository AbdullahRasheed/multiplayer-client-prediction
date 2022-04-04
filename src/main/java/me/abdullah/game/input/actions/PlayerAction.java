package me.abdullah.game.input.actions;

import me.abdullah.game.Game;
import me.abdullah.game.input.Action;
import me.abdullah.game.objects.entity.Player;

import java.util.function.Consumer;

public enum PlayerAction implements Action {

    FORWARD(player -> player.setVelY(player.getVelY() - 5), player -> player.setVelY(player.getVelY() + 5)),
    BACKWARD(player -> player.setVelY(player.getVelY() + 5), player -> player.setVelY(player.getVelY() - 5)),
    LEFT(player -> player.setVelX(player.getVelX() - 5), player -> player.setVelX(player.getVelX() + 5)),
    RIGHT(player -> player.setVelX(player.getVelX() + 5), player -> player.setVelX(player.getVelX() - 5));

    private final Consumer<Player> action;
    private final Consumer<Player> release;

    PlayerAction(Consumer<Player> action, Consumer<Player> release){
        this.action = action;
        this.release = release;
    }

    @Override
    public void act(){
        action.accept(Game.player);
    }

    @Override
    public void release() {
        release.accept(Game.player);
    }

    public void actFor(Player player){
        action.accept(player);
    }

    public void releaseFor(Player player){
        release.accept(player);
    }
}
