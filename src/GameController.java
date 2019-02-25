/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 22:20
 * Deadwood
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
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
    private int daysToPlay;

    /**
     *
     * @param numPlayers
     */
    public GameController(int numPlayers) {

        this.board = new Board(numPlayers);
        this.scenes = createScenes();
        this.players = makePlayers(4);

        // So now the board is created (rooms are created in the process)
        // The scene cards are all created
        // And the players are all created...

        //TESTING CODE
        GameControllerTest.testAll(players, board, scenes);
    }

    /**
     * Reads in scenes.txt and creates all 40 Scene cards, with proper budget values, Roles, etc.
     * See the Scenes class for more detail on the definition of a Scene.
     * @return Returns an ArrayList of all the Scene cards
     */
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

    /**
     * Creates num Players and puts them in an Array for use by the GameController
     * @param num Parameter is the number of Players in this game session. Easy.
     * @return Return an Array, Player[num]
     */
    private Player[] makePlayers(int num) {
        int startingMoney = 0;
        int startingCredits = 0;
        int startingRank = 1;
        int numOfDays = 4;
        Room startingRoom = board.rooms.get("Casting Office");

        //Determining potential special rules
        if (isYBetweenXAndZ(2, num, 3)) {
            numOfDays = 3;
        } else if (num == 4) {
            // do nothing
        } else if (num == 5) {
            startingCredits = 2;
        } else if (num == 6) {
            startingCredits = 4;
        } else if (isYBetweenXAndZ(7, num, 8)) {
            startingRank = 2;
        }


        // Now actually create num players
        Player[] newPlayers = new Player[num];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < num; i++) {
            System.out.println("Please type player " + (i+1) + "'s name: ");
            String name = sc.next();
            newPlayers[i] = new Player(name, startingRank, startingMoney, startingCredits, startingRoom);
        }

        this.daysToPlay = numOfDays;
        return newPlayers;
    }

    private boolean isYBetweenXAndZ(int X, int Y, int Z) {
        return X <= Y && Y <= Z;
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