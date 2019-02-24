/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:34
 * Deadwood
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Board {
    public Room[] rooms;
    private int day;

    public Board(int numPlayers) {

        // setup rooms
        this.rooms = new Room[12];
        try {
            File roomFile = new File("Assets/rooms.txt");
            Scanner sc = new Scanner(roomFile);
            //"Room Name", # of shot counters, "Extra Role Name", required rank (integer)
            sc.nextLine(); //Ignore first line of formatting rules
            for (int i = 0; i < 12; i++) {
                String[] rmData = sc.nextLine().split(";");
//                System.out.println(Arrays.toString(rmData));

                String rmName = rmData[0];
                int rmShotCounters = Integer.parseInt(rmData[1].strip());
                HashMap<String, Role> roles = new HashMap<>();

                for (int j = 2; j < rmData.length; j += 2) {
                    Role r = new Role(rmData[j],
                            Integer.parseInt(rmData[j + 1].strip()),
                            false);
                    roles.put(rmData[j], r);
                }

                //Now we have a string name, int shotcounters, and a map of <string roles, int rank>
                this.rooms[i] = new Room(rmName, rmShotCounters, roles);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public void newDay(ArrayList<Scene> availableScenes) {
        clearScenes();
        dealScenes(availableScenes);
    }

    private void clearScenes() {
        //removes all scenes from all rooms
        for (Room r : this.rooms) {
            r.clearScene();
        }
    }

    private void dealScenes(ArrayList<Scene> available) {
        //deal out scene cards / objects to each room. Called at start of a new day
        for (Room r : this.rooms) {

        }
    }

}
