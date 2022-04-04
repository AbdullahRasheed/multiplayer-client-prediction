package me.abdullah.game.handlers;

import javafx.util.Pair;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

public class Handler {

    enum ActionType {
        ADD(Collection::add),
        REMOVE(Collection::remove);

        private final BiConsumer<Collection<GameObject>, GameObject> handle;
        ActionType(BiConsumer<Collection<GameObject>, GameObject> handle){
            this.handle = handle;
        }
    }

    private final LinkedList<GameObject> objects;
    private final LinkedList<Pair<ActionType, GameObject>> actionQueue;

    public Handler(){
        objects = new LinkedList<>();
        actionQueue = new LinkedList<>();
    }

    public void render(Graphics2D g){
        for (GameObject object : objects) {
            object.render(g);
        }
    }

    public void tick(){
        for (GameObject object : objects) {
            object.tick();
        }
    }

    public void addObject(GameObject object){
        actionQueue.add(new Pair<>(ActionType.ADD, object));
    }

    public void removeObject(GameObject object){
        actionQueue.add(new Pair<>(ActionType.REMOVE, object));
    }

    public List<GameObject> getObjects(){
        return objects;
    }

    public void clearActionQueue(){
        synchronized (objects) { // FIXME I don't really like this
            for (Pair<ActionType, GameObject> pair : actionQueue) {
                pair.getKey().handle.accept(objects, pair.getValue());
            }

            actionQueue.clear();
        }
    }
}
