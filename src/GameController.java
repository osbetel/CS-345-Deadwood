/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 22:20
 * Deadwood
 */

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.*;

public class GameController {

    private ArrayList<Scene> scenes;
    private Board board;
    private Player[] players;   //Queue style, active player should always be players[0]
    private int daysToPlay;
    private UserView view;

    /**
     * The class that ties it all together
     */
    public GameController() {

        try {
            scenes = (ArrayList<Scene>) ParseXML.parseXML("Assets/cards.xml", false);
            board = new Board((HashMap<String, Room>) ParseXML.parseXML("Assets/board.xml", true));
        } catch (XMLStreamException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        daysToPlay = 4;

        view = new UserView(this);
        view.show();
        try {
            players = makePlayers(view.queryNumPlayers());
        } catch (InputMismatchException ex) {
            System.out.println("Input for number of Players must be an integer!");
        }

//        HashMap<String, Room> rooms = board.getRooms();
//        players[0].move(rooms.get("Church"));
//        players[1].move(rooms.get("Casting Office"));
//        players[2].move(rooms.get("Jail"));
//        players[3].move(rooms.get("General Store"));
//        players[4].move(rooms.get("Trailer"));
//        players[5].move(rooms.get("Bank"));
//        players[6].move(rooms.get("Ranch"));
//        players[7].move(rooms.get("Secret Hideout"));
        view = new UserView(this); //this
        view.show();
        playGame();
        view.update();

    }

    private void playGame() {
        /**
         * What is necessary when you need to play a game?
         * 1) Construct Rooms ==> Board, Scene cards            ✔︎
         * 2) Query how many players (in UserView!)             ✔︎
         * 3) Construct players                                 ✔︎
         * 4) Deal Scenes                                       ✔︎
         * 5) Draw players and draw Scene cards
         * 6) Player 1's turn ==> loop until Player n's turn
         *
         * Thus this function should only run after 3) is complete
         */
        board.dealScenes(scenes);

    }

    private void listRooms() {
        for (String rm : board.getRooms().keySet()) {
            Room r = board.getRooms().get(rm);
            System.out.println(r.roomName);
            System.out.println(r.currentScene);
            System.out.println(r.extraRoles);
            System.out.println(r.neighbors);
            System.out.println(r.shotCounters);
            System.out.println();
        }
    }

    private void listScenes() {
        for (Scene s : scenes) {
            System.out.println(s);
            System.out.println(s.sceneName);
            System.out.println(s.roles);
            System.out.println(s.budget);
            System.out.println(s.filepath);
            System.out.println();
        }
    }

    public Player getActivePlayer() {
        return players[0];
    }

    public Player[] getAllActivePlayers() {
        return players;
    }

    public HashMap<String, Room> getRoomsOnBoard() {
        return board.getRooms();
    }

    public HashMap<Room, ArrayList<Player>> getPlayerLocations() {

        HashMap<Room, ArrayList<Player>> locations = new HashMap<>();

        for (String r : board.getRooms().keySet()) {
            Room rm = board.getRooms().get(r);
            locations.put(rm, new ArrayList<>());
        }

        for (Player p : players) {
            locations.get(p.getCurrentRoom()).add(p);
        }
        return locations;
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
        Room startingRoom = board.getRooms().get("Trailer");

        //Determining potential special rules
        if (isYBetweenXAndZ(2, num, 3)) {
            this.daysToPlay = 3;
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
        String[] colors = {"b", "c", "g", "o", "p", "r", "v", "w", "y"};
        for (int i = 0; i < num; i++) {
            newPlayers[i] = new Player("Player " + i, startingRank, startingMoney,
                                        startingCredits, startingRoom, colors[i], i + 1);
        }

        return newPlayers;
    }

    //This is a really simple helper to determine if x <= y <= z is true
    private boolean isYBetweenXAndZ(int X, int Y, int Z) {
        return X <= Y && Y <= Z;
    }

    //random integer die roll
    private int[] rollDie(int numDiceToRoll) {
        int[] rolls = new int[numDiceToRoll];
        Random r = new Random();

        for (int i = 0; i < numDiceToRoll; i++) {
            rolls[i] = r.nextInt(6) + 1;
        }
        return rolls;
    }
}
