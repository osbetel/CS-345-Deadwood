/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:35
 * Deadwood
 */

import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

public class Room {

    public Scene currentScene;
    public final String roomName;
    public final int shotCounters;
    public final HashMap<String, Role> extraRoles;


    public Room(String name, int shotCounters, HashMap<String, Role> roles) {
        this.roomName = name;
        this.shotCounters = shotCounters;
        this.extraRoles = roles;
    }


    public void setScene(Scene newScene) {
        this.currentScene = newScene;
    }


    public void clearScene() {
        this.currentScene = null;
    }


    public Scene getScene() {
        return this.currentScene;
    }


    public String getRoomName() {
        return this.roomName;
    }

}
