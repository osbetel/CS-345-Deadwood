/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 22:20
 * Deadwood
 */

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.*;

/**
 * GameController class. This functions as the controller in the MVC design paradigm.
 */
public class GameController {

    private ArrayList<Scene> scenes;
    private Board board;
    private ArrayList<Player> players;   //Queue style, active player should always be players[0]
    private int daysToPlay;
    private UserView view;

    /**
     * Constructor for the controller
     */
    public GameController() {

        //This portion parses the XML files for the Board and the Scene cards.
        try {
            scenes = (ArrayList<Scene>) ParseXML.parseXML("Assets/cards.xml", false);
            board = new Board((HashMap<String, Room>) ParseXML.parseXML("Assets/board.xml", true));
        } catch (XMLStreamException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        //Parsing XML constructs rooms, but doesn't assign neighbors.
        //ie: Trailer has a hashmap of rooms saying it's next to the Hotel, etc
        //But you cannot actually access the Hotel Room object from Trailer (so you can't move there)
        //This loop assigns neighbors properly.
        HashMap<String, Room> rooms = board.getRooms();
        for (String key : rooms.keySet()) {
            Room rm = rooms.get(key);
            if (rm.neighbors != null) {
                for (String neighbor : rm.neighbors.keySet()) {
                    rm.neighbors.put(neighbor, rooms.get(neighbor));
                }
            }
        }

        daysToPlay = 4;

        //View is made before players, and then players are drawn upon the view.
        view = new UserView(this);
        try {
            players = makePlayers(view.queryNumPlayers());
        } catch (InputMismatchException ex) {
            System.out.println("Input for number of Players must be an integer!");
        }
    }

    /**
     * This draws 2 scenes on the board, and that's it. Use this if you want to test end of day functions
     * ie: moving all players back to the trailer, no-payout on the final scene, etc
     */
    public void playGameTestEndOfDay() {
        if (daysToPlay > 0) {
            ArrayList<Scene> test = new ArrayList<>();
            while (scenes.size() > 10) {
                if (test.size() < 2) {
                    test.add(scenes.remove(0));
                } else {
                    scenes.remove(0);
                }
            }
            board.dealScenes(test);
            view.update();
        }
    }

    /**
     * Function that kicks off a new day and initiates turns
     */
    public void playGame() {
        /**
         * What is necessary when you need to play a game?
         * 1) Construct Rooms ==> Board, Scene cards            ✔︎
         * 2) Query how many players (in UserView!)             ✔︎
         * 3) Construct players                                 ✔︎
         * 4) Deal Scenes                                       ✔︎
         * 5) Draw players and draw Scene cards                 ✔︎
        * 6) Player 1's turn ==> loop until Player n's turn     ✔︎
         *
         * So this function should only run after 3) is complete
         */
        if (daysToPlay > 0) {
            board.dealScenes(scenes);
            view.update();
        } else {
            System.out.println("Game over!");
            for (Player p: players) {
                System.out.println(p.playerName + " Score: "  + p.getScore());
            }
            System.exit(0);
        }
    }

    /**
     * Makes sure the View is displaying the proper data as stored in the Model.
     * In this case, UserView is the View portion, and Player, Room, Board, Role, Scene, etc etc are the Model.
     */
    private void updateAll() {
        Player currentActivePlayer = players.get(0);
        if (currentActivePlayer.getHasMoved() &&
                currentActivePlayer.getHasTakenRole() &&
                currentActivePlayer.getHasTakenAction()) {
            endTurn();
        }

        view.update();
    }

    /**
     * Because we transitioned to a MVC style, UserView calls methods upon the GameController to signal
     * intent to perform an action. The GameController always knows who the active player is, and will manage
     * turns and actions accordingly.
     * The next 5 methods are all actions a player can take.
     */
    public void rehearse() {
        Player currentActivePlayer = players.get(0);
        currentActivePlayer.rehearse();
        endTurn();
        updateAll();
    }

    public void act() {
        Player currentActivePlayer = players.get(0);
        if (currentActivePlayer.getHasTakenRole()) {
            Scene sc = currentActivePlayer.getCurrentScene();
            currentActivePlayer.act();
            if (sc.isWrapped()) {
                currentActivePlayer.getCurrentRoom().clearScene();
                view.clearScene(sc);
                if (board.getNumOfScenes() == 1) {
                    board.getFinalScene().setPayout(false);
                } else if (board.getNumOfScenes() == 0) {
                    daysToPlay -= 1;
                    for (Player p : players) {
                        p.forceMove(board.getRooms().get("Trailer"));
                    }
                    playGame();
                }
            }
        }
        endTurn();
        updateAll();
    }

    public void endTurn() {
        Player currentActivePlayer = players.remove(0);
        currentActivePlayer.setHasMoved(false);
        currentActivePlayer.setHasTakenAction(false);
        System.out.println(currentActivePlayer.playerName);
//        System.out.println(currentActivePlayer.getHasMoved());
//        System.out.println(currentActivePlayer.getHasTakenRole());
//        System.out.println(currentActivePlayer.getHasTakenAction());
        players.add(currentActivePlayer);
        System.out.println("turn ended");
        updateAll();
    }

    public void takeRole(String role) {
        Player currentActivePlayer = players.get(0);
        Scene sc = currentActivePlayer.getCurrentRoom().getScene();
        //if can take role
        if (!currentActivePlayer.getHasTakenRole()) {
            currentActivePlayer.takeRole(role);
            currentActivePlayer.setCurrentScene(sc);
            sc.roles.remove(role);
        }
        updateAll();
    }

    public void movePlayer(Room rm) {
        Player currentActivePlayer = players.get(0);
        if (!currentActivePlayer.getHasMoved()) {
            if (currentActivePlayer.getCurrentRoom().neighbors.containsKey(rm.getRoomName())) {
                currentActivePlayer.move(rm);
//                System.out.println(currentActivePlayer.getCurrentRoom().roomName);
            }
        }
        updateAll();
    }

    /**
     * @return returns the active player
     */
    public Player getActivePlayer() {
        return players.get(0);
    }

    /**
     * @return returns the arraylist of playerse. Necessary for drawing all players.
     */
    public ArrayList<Player> getAllActivePlayers() {
        return players;
    }

    /**
     * @return returns a map of all the rooms on the board. This is necessary to relay
     * the coordinate data to the View.
     */
    public HashMap<String, Room> getRoomsOnBoard() {
        return board.getRooms();
    }

    /**
     * @return Returns a map of <Room, List<Player>>. For example, if there are 0 players
     * in the Trailer, then using map.get(Trailer) will return a list of size 0.
     * This data is used to determine the offset necessary when drawing more than
     * one player in the same room.
     */
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
    private ArrayList<Player> makePlayers(int num) {
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
        ArrayList<Player> newPlayers = new ArrayList<>();
        String[] colors = {"b", "c", "g", "o", "v", "r", "w", "y", "p"};
        String color = "";
        for (int i = 0; i < num; i++) {
            switch (i) {
                case 0:
                    color = "Blue";
                    break;
                case 1:
                    color = "Cyan";
                    break;
                case 2:
                    color = "Green";
                    break;
                case 3:
                    color = "Orange";
                    break;
                case 4:
                    color = "Violet";
                    break;
                case 5:
                    color = "Red";
                    break;
                case 6:
                    color = "White";
                    break;
                case 7:
                    color = "Yellow";
                    break;
            }
            newPlayers.add(new Player(color + " Player", startingRank, startingMoney,
                                        startingCredits, startingRoom, colors[i], i + 1));
        }
        return newPlayers;
    }

    //This is a really simple helper to determine if x <= y <= z is true
    private boolean isYBetweenXAndZ(int X, int Y, int Z) {
        return X <= Y && Y <= Z;
    }
}
