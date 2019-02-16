/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:34
 * Deadwood
 */

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Board {
    public Room[] rooms;
    private Player[] players;
    private int day;

    public Board(int numPlayers) {
        //initial setup
        this.day = 0;
        // setup rooms
        this.rooms = new Room[12];
        this.rooms[0] = new CastingOffice();
        this.rooms[1] = new Trailer();
        try {
            File in = new File("src/rooms.txt");
            Scanner sc = new Scanner(in);
            for (int i = 2; i < 12; i++) {
                String[] rmData = sc.nextLine().split(",");
//                System.out.println(Arrays.toString(rmData));
                rooms[i] = new Room(rmData[0],
                        Integer.parseInt(rmData[1]),
                        Integer.parseInt(rmData[2]));
            }
            //setupGame(numPlayers);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //setup players
        this.players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            this.players[i] = new Player("default",0,
                                        0,0);
            // add a switch to create players based on the number of players
            // also don't forget to allow system input "enter player 1's name" etc.
        }
    }

    private int nextDay() {
        this.day += 1;
        return this.day;
    }

    private void clearScenes() {
        //removes all scenes from all rooms
        for (Room r : this.rooms) {
            r.clearScene();
        }
    }

    private void dealScenes() {
        //deal out scene cards / objects to each room. Called at start of a new day
        for (Room r : this.rooms) {
            //todo: r.newScene... from remaining unused scenes
        }
    }

    public Player[] getPlayers() {
        return this.players;
    }

}
