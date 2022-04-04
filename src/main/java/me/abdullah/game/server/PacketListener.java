package me.abdullah.game.server;

import me.abdullah.game.handlers.Handler;
import me.abdullah.game.objects.entity.Player;
import me.abdullah.game.server.inputs.ServerActionHandler;
import me.abdullah.game.server.packets.ClientConfirmedPacket;
import me.abdullah.game.server.packets.PlayerConnectPacket;
import me.abdullah.game.server.packets.PlayerInfoPacket;
import me.abdullah.game.server.packets.PlayerInputPacket;

import java.util.*;
import java.util.function.BiConsumer;

public class PacketListener {

    private final Handler handler;
    private final ServerActionHandler actionHandler;

    private final Map<Class<?>, BiConsumer<Connection, Object>> handlers;

    public PacketListener(Handler handler){
        this.handler = handler;
        this.actionHandler = new ServerActionHandler();

        this.handlers = new HashMap<>();

        handlers.put(PlayerConnectPacket.class, this::handlePlayerConnectPacket);
        handlers.put(PlayerInputPacket.class, this::handlePlayerInputPacket);
    }

    public void accept(Connection connection, Object o) {
        handlers.get(o.getClass()).accept(connection, o);
    }

    public void process(){
        for (Connection c : Connections.getConnections().keySet()) {
            for (Connection c1 : Connections.getConnections().keySet()) {
                Player player = Connections.getPlayer(c1);
                c.sendPacket(new PlayerInfoPacket(player.getName(), player.getX(), player.getY(), player.getVelX(), player.getVelY()));
            }
        }
    }

    public void handlePlayerConnectPacket(Connection connection, Object o){
        PlayerConnectPacket packet = (PlayerConnectPacket) o;

        for (Connection c : Connections.getConnections().keySet()) {
            c.sendPacket(o);
        }

        Player player = new Player(packet.name, packet.x, packet.y, packet.velX, packet.velY);
        handler.addObject(player);
        Connections.add(connection, player);

        connection.sendPacket(new ClientConfirmedPacket(packet.name, packet.x, packet.y, packet.velX, packet.velY));
    }

    public void handlePlayerInputPacket(Connection connection, Object o){
        PlayerInputPacket packet = (PlayerInputPacket) o;

        Player player = Connections.getPlayer(connection);

        if(packet.pressed){
            actionHandler.keyPressed(packet.action, player);
        }else{
            actionHandler.keyReleased(packet.action, player);
        }
    }
}
