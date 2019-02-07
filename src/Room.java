/*
 * Created by: Andrew Nguyen
 * Date: 2019-02-05
 * Time: 16:35
 * Deadwood
 */

public class Room {

    private Scene currentScene;
    private String roomName;

    public Room() {
        //todo: there's only 10 rooms in total, so define a text file somewhere for use during setup
    }

    private void setScene(Scene newScene) {
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
