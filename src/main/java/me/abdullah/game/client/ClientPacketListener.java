package me.abdullah.game.client;

import me.abdullah.game.Game;
import me.abdullah.game.handlers.GameObject;
import me.abdullah.game.handlers.Handler;
import me.abdullah.game.objects.entity.Player;
import me.abdullah.game.server.packets.ClientConfirmedPacket;
import me.abdullah.game.server.packets.PlayerConnectPacket;
import me.abdullah.game.server.packets.PlayerInfoPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ClientPacketListener implements Consumer<Object> {

    private final Map<Class<?>, Consumer<Object>> handlers;

    private final Handler handler;
    public ClientPacketListener(Handler handler){
        handlers = new HashMap<>();
        this.handler = handler;

        handlers.put(PlayerConnectPacket.class, this::handlePlayerConnectPacket);
        handlers.put(ClientConfirmedPacket.class, this::handleClientConfirmedPacket);
        handlers.put(PlayerInfoPacket.class, this::handlerPlayerInfoPacket);
    }

    @Override
    public void accept(Object o) {
        handlers.get(o.getClass()).accept(o);
    }

    public void handlePlayerConnectPacket(Object o){
        PlayerConnectPacket packet = (PlayerConnectPacket) o;
        Player player = new Player(packet.name, packet.x, packet.y, packet.velX, packet.velY);
        handler.addObject(player);
    }

    public void handleClientConfirmedPacket(Object o){
        ClientConfirmedPacket packet = (ClientConfirmedPacket) o;
        Game.player = new Player(packet.name, packet.x, packet.y, packet.velX, packet.velY);
        handler.addObject(Game.player);
    }

    public void handlerPlayerInfoPacket(Object o){
        PlayerInfoPacket packet = (PlayerInfoPacket) o;

        for (GameObject object : handler.getObjects()) {
            if(object instanceof Player){
                Player player = (Player) object;
                if(player.getName().equals(packet.name)){
                    player.setX(packet.x);
                    player.setY(packet.y);
                    player.setVelX(packet.velX);
                    player.setVelY(packet.velY);
                }
            }
        }
    }
}
