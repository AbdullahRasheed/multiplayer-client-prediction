package me.abdullah.game.server;

import me.abdullah.game.objects.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Connections {

    private static final Map<Connection, Player> connections = new HashMap<>();

    public static void add(Connection connection, Player player) {
        connections.put(connection, player);
    }

    public static void remove(Connection connection) {
        connections.remove(connection);
    }

    public static Player getPlayer(Connection connection) {
        return connections.get(connection);
    }

    public static Map<Connection, Player> getConnections() {
        return connections;
    }
}
