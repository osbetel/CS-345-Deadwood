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
import java.util.Random;
import java.util.Scanner;

public class Board {
    private final HashMap<String, Room> rooms;
    private int numOfScenes;

    /**
     * Board constructor. Also constructs the Rooms (Board can't exist without rooms)
     */
    @SuppressWarnings("Check the typecast in the Board constructor if there are errors")
    public Board() {

        // setup rooms
        HashMap<String, Room> rms = null;
        try {
            rms = (HashMap<String, Room>) ParseXML.parseXML("Assets/board.xml", true);
            //Now rms is a HashMap of all the Rooms... But each Room has a list of neighboring Rooms,
            //and currently these are just Strings (in a Hashmap) that map to null.
            for (String r : rms.keySet()) {
                Room rm = rms.get(r);

                if (rm instanceof CastingOffice || rm instanceof Trailer) {
                    break;
                }

                for (String neighbor : rm.neighbors.keySet()) {
                    rm.neighbors.put(neighbor, rms.get(neighbor));
                }
            }
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

        Random randint = new Random();

        for (String rm : this.rooms.keySet()) {
            if (!(rm.equals("Casting Office") || rm.equals("Trailer"))) {
                int nextScene = randint.nextInt(available.size());
                this.rooms.get(rm).setScene(available.get(nextScene));
                available.remove(nextScene);
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
