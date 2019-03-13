/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:34
 * Deadwood
 */

import java.io.File;
import java.io.FileNotFoundException;
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
    public Board(int numPlayers) {

        // setup rooms
        this.rooms = new HashMap<>();
        try {
            File roomFile = new File("Assets/rooms.txt");
            Scanner sc = new Scanner(roomFile);
            //"Room Name", # of shot counters, "Extra Role Name", required rank (integer)
            sc.nextLine(); //Ignore first line of formatting rules
            while (sc.hasNextLine()) {

                String[] rmData = sc.nextLine().split(";");
                String rmName = rmData[0];
                int rmShotCounters = Integer.parseInt(rmData[1].trim());
                HashMap<String, Role> roles = new HashMap<>();

                for (int j = 2; j < rmData.length; j += 2) {
                    Role r = new Role(rmData[j].trim(),
                            Integer.parseInt(rmData[j + 1].trim()),
                            false);
                    roles.put(rmData[j].trim(), r);
                }

                //Now we have a string name, int shotcounters, and a map of <string roles, int rank>
                if (rmName.equals("Trailer")) {
                    this.rooms.put(rmName, new Trailer());
                } else if (rmName.equals("Casting Office")) {
                    this.rooms.put(rmName, new CastingOffice());
                } else {
                    this.rooms.put(rmName, new Room(rmName, rmShotCounters, roles));
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        numOfScenes = 0;    //haven't dealt scenes yet
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
                this.rooms.get(rm).getScene().setCounter(this.rooms.get(rm).shotCounters);
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
