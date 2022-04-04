package me.abdullah.game.files;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GameFiles {

    private static final Map<String, FileIO> gameFiles = new HashMap<>();

    public static void load(File file, String id){
        try {
            gameFiles.put(id, new FileIO(file));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void processLines(String file, Consumer<String> consumer){
        FileIO fio = gameFiles.get(file);
        fio.processAllLines(consumer);
    }
}
