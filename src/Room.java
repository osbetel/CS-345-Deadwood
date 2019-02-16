/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:35
 * Deadwood
 */

public class Room {

    private Scene currentScene;
    private String roomName;
    public int shotCounters;
    public int extraRoles;

    public Room(String name, int shotCounters, int extraRoles) {
        this.roomName = name;
        this.shotCounters = shotCounters;
        this.extraRoles = extraRoles;
    }

    private void setScene(Scene newScene) {
        // to be executed when new scenes are dealt
        this.currentScene = newScene;
        this.shotCounters = 0;
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
