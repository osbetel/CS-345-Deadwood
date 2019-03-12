/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:35
 * Deadwood
 */

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Room {

    public final String roomName;
    public HashMap<String, Room> neighbors;
    public final ArrayList<ShotCounter> shotCounters;
    public final HashMap<String, Role> extraRoles;
    public final int[] area;
    public Scene currentScene;

    public Room(String name, HashMap<String, Room> neighbors,
                ArrayList<ShotCounter> shotCounters, HashMap<String, Role> roles, int[] area) {
        this.roomName = name;
        this.neighbors = neighbors;
        this.shotCounters = shotCounters;
        this.extraRoles = roles;
        this.area = area;
//        this.sceneCardCoordinates = sceneCardCoordinates;
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

    public void properties() {

        if (roomName == null) {
            System.out.println("name: null");
        } else {
            System.out.println(roomName + ", " + Arrays.toString(area));
        }

        if (shotCounters == null) {
            System.out.println("shotCounters: null");
        } else {
            for (ShotCounter sc : shotCounters) {
                System.out.print(sc.shotNum + ", " + Arrays.toString(sc.coordinates) + ", " + sc.marked + "\n");
            }
        }

        if (extraRoles == null) {
            System.out.println("extraRoles: null");
        } else {
            System.out.println(extraRoles.keySet());
        }

        if (neighbors == null) {
            System.out.println("neighbors: null");
        } else {
            System.out.println(neighbors.keySet());
        }

        System.out.println();
    }

}
