package me.abdullah.game.server.inputs;

import me.abdullah.game.input.Action;
import me.abdullah.game.input.actions.PlayerAction;
import me.abdullah.game.objects.Player;

import java.util.HashSet;
import java.util.Set;

public class ServerActionHandler {

    private final Set<Action> pressed;

    public ServerActionHandler(){
        this.pressed = new HashSet<>();
    }

    public void keyPressed(PlayerAction action, Player player){
        if(pressed.contains(action)) return;

        action.actFor(player);

        pressed.add(action);
    }

    public void keyReleased(PlayerAction action, Player player){
        action.releaseFor(player);

        pressed.remove(action);
    }
}
