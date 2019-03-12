/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:34
 * Deadwood
 */

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Board {
    private final HashMap<String, Room> rooms;
    private int numOfScenes;

    /**
     * Board constructor. Also constructs the Rooms (Board can't exist without rooms)
     * @param numPlayers An integer specifying the number of players.
     */
    @SuppressWarnings("Check the typecast in the Board constructor if there are errors")
    public Board(int numPlayers) {

        // setup rooms
        HashMap<String, Room> rms = null;
        try {
            rms = (HashMap<String, Room>) ParseXML.parseXML("Assets/board.xml", true);
        } catch (XMLStreamException ex) {
            System.out.println(ex);
            System.exit(1);
        } catch (IOException ex) {
            System.out.println(ex);
            System.exit(1);
        }
        this.rooms = rms;
//        System.out.println("In Board class, line 39");
//        for (String r : rooms.keySet()) {
//            rooms.get(r).properties();
//        }
    }


    /**
     * Clears the board and deals new scenes.
     * @param availableScenes
     */
    public void newDay(ArrayList<Scene> availableScenes) {
        clearScenes();
        dealScenes(availableScenes);
        numOfScenes = 10;
    }


    public void clearScenes() {
        //removes all scenes from all rooms
        for (String rm : rooms.keySet()) {
            rooms.get(rm).clearScene();
        }
    }


    public void dealScenes(ArrayList<Scene> available) {

//        if (true) {
//            this.rooms.get("Church").setScene(available.get(0));
//            this.rooms.get("Bank").setScene(available.get(1));
//
//            this.rooms.get("Church").getScene().setCounter(this.rooms.get("Church").shotCounters);
//            this.rooms.get("Bank").getScene().setCounter(this.rooms.get("Bank").shotCounters);
//
//            return;
//        }

        for (String rm : this.rooms.keySet()) {
            //todo:select random scene, assign to room, delete from available. Currently takes first scene on list
            if (!(rm.equals("Casting Office") || rm.equals("Trailer"))) {
                this.rooms.get(rm).setScene(available.get(0));
                available.remove(0);
//                this.rooms.get(rm).getScene().setCounter(this.rooms.get(rm).shotCounters);
            }
        }
    }


    public int getNumOfScenes() {
        return numOfScenes;
    }


    public void setNumOfScenes(int numOfScenes) {
        this.numOfScenes = numOfScenes;
    }


    public Scene getFinalScene() {
        for (String r : rooms.keySet()) {
            Room curr = rooms.get(r);
            if (curr.currentScene != null) {
                return curr.currentScene;
            }
        }
        return null;
    }


    public HashMap<String, Room> getRooms() {
        return rooms;
    }
}
