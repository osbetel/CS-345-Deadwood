/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 22:20
 * Deadwood
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class GameController {

    public int score;
    public int pChip;
    public int credits; //data shared w/in other classes that update it
    public int remScenes;
    public int remCount;
    private ArrayList<Scene> scenes;
    private Board board;
    private Player[] players;



    public GameController(int numPlayers) {
        //Needs to create the board, players, scenes, rooms, etc.
        //Constructing the Board should also construct the Rooms
        //Constructing Players should also construct GamePieces
        //The Scenes are separate and tacked onto the rooms each day
        this.board = new Board(numPlayers);
        this.scenes = createScenes();
        this.players = makePlayers(4);

        // FOR TESTING
//        for (Player p : players) {
//            System.out.println(p);
//        }
//        for (Room r : board.rooms) {
//            System.out.println(r.getRoomName() + ", extras: " + r.extraRoles + ", shot counters: " +  r.shotCounters);
//        }
    }

    public ArrayList<Scene> createScenes() {
        // This should read in a text file and generate all the scenes,
        // Place them into an array and return it to the game controller
        scenes = new ArrayList<>();

        try {
            File sceneFile = new File("Assets/scenes.txt");
            Scanner sc = new Scanner(sceneFile);
            sc.nextLine();

            while (sc.hasNextLine()) {
                String[] sceneData = sc.nextLine().split(";");
                int sceneNum = Integer.parseInt(sceneData[0]);
                String sceneTitle = sceneData[1];
                int sceneBudget = Integer.parseInt(sceneData[2].strip());

                HashMap<String, Role> roles = new HashMap<>();

                for (int j = 3; j < sceneData.length; j += 2) {
                    Role r = new Role(sceneData[j],
                            Integer.parseInt(sceneData[j + 1].strip()),
                            true);
                    roles.put(sceneData[j], r);
                }
                scenes.add(new Scene(sceneNum, sceneTitle, sceneBudget, roles));
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        return scenes;
    }

    private Player[] makePlayers(int num) {

        Player[] newPlayers = new Player[num];
        switch (num) {
            case 2:

        }

        return null;
    }

    //display credits of current player
    public int getCredits(Player player) {
        return this.credits;
    }

    //display amount of practice chips a player has
    public int getPChip(Player player) {
        return this.pChip;
    }

    //calculating score at the moment
    public int getScore(Player player) {
        //todo calc current score
        return this.score;
    }

    //see how many counters left on a given scene
    //***counter could be returned using this.__ or retrieved 
    public int getCounters(Room room) {
        return this.remCount;
    }

    //know how many scenes are left
    public int scenesLeft() {
        return this.remScenes;
    }
}