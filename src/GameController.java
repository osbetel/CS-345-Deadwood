/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 22:20
 * Deadwood
 */

import javax.xml.catalog.Catalog;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
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

        int numPlayers = 4; //DEFAULT TEST CASE ONLY
        players = makePlayers(numPlayers);

        view = new UserView(this); //this
        view.show();
        playGame();
    }

    private void test() {
        for (Scene s : scenes) {
            System.out.println(s);
            System.out.println(s.sceneName);
            System.out.println(s.roles);
            System.out.println(s.budget);
            System.out.println(s.filepath);
            System.out.println();
        }

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

    public Player getActivePlayer() {
        return players[0];
    }

    /**
     * Reads in scenes.txt and creates all 40 Scene cards, with proper budget values, Roles, etc.
     * See the Scenes class for more detail on the definition of a Scene.
     * @return Returns an ArrayList of all the Scene cards
     */

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
        for (int i = 0; i < num; i++) {
            newPlayers[i] = new Player("p" + Integer.toString(i), startingRank,
                    startingMoney, startingCredits, startingRoom);
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

    private void playGame() {
        /**
         * What is necessary when you need to play a game?
         */
        System.out.println(scenes.size());
        board.dealScenes(scenes);
        System.out.println(scenes.size());
        test();
    }

    /**
     * Parses player's input string into different game actions, like moving, taking a role, etc
     * @param p Player object
     * @param sc Scanner
     * @param commands List of the possible commands a player can give. I should make this a final, static var.
     * @param action String input from player
     * @param currentDay Relevant information for certain commands
     * @param hasMoved Same as above
     * @param hasTakenRole same
     * @return Returns a true/false (just for testing purposes)
     * todo: game loop needs to be modified to interact with the UserView
     */
    @Deprecated // Will be removed after 2 commits
    private boolean parseInput(Player p, Scanner sc, String[] commands, String action,
                               int currentDay, boolean hasMoved, boolean hasTakenRole) {

        String mainOut, extraOut;
        HashMap<String, Role> mains, extras;

        switch (action) {

            case "help":
                System.out.println("Here is a list of valid commands: " + Arrays.toString(commands));
                break;

            case "endturn":
                return true;

            case "move":    //todo: if a player types in the wrong case it breaks via null pointer ex

                if (p.getHasMoved()) {
                    System.out.println("You've already moved this turn!");
                    break;
                }

                System.out.print("Available rooms: ");
                String avrms = "";
                for (String rm : board.getRooms().keySet()) {
                    avrms += (rm + ", ");
                }
                System.out.println(avrms.strip());
                System.out.println("What room would you like to move to? : ");
                Room target = board.getRooms().get(sc.nextLine()); //todo need to add error checking for Assignment 3
                p.move(target);
                System.out.println("Moved to: " + target.roomName);
                p.setHasMoved(true);
                if (target.currentScene != null) {
//                    System.out.println("Current Scene: " + target.currentScene.sceneName);
                    p.setCurrentScene(target.currentScene);
                }
                break;

            case "whereami":
                System.out.println("Current room: " + p.getCurrentRoom().roomName);
                break;

            case "whoami":
                System.out.println("Current player: " + p.playerName);
                break;

            case "getdollars":
                System.out.println("You have " + p.getDollars() + " dollars available.");
                break;

            case "getcredits":
                System.out.println("You have " + p.getCredits() + " credits available.");
                break;

            case "listrooms":
                System.out.print("All rooms: ");
                String allrm = "";
                for (String rm : board.getRooms().keySet()) {
                    allrm += (rm + ", ");
                }
                System.out.println(allrm.strip());
                break;

            case "listroles":
                if ((p.getCurrentRoom() instanceof CastingOffice) || (p.getCurrentRoom() instanceof Trailer)) {
                    System.out.println("You are currently in the: " + p.getCurrentRoom().roomName + "," +
                            " which has no scenes/roles.");
                    break;
                }

                mains = p.getCurrentScene().roles;
                extras = p.getCurrentRoom().extraRoles;

                mainOut = "Main Roles: [";
                extraOut = "Extra Roles: [";

                for (String mainKey : mains.keySet()) {
                    mainOut += mains.get(mainKey).title +", ";
                }

                for (String extraKey : extras.keySet()) {
                    extraOut += extras.get(extraKey).title + ", ";
                }

                System.out.println(mainOut.strip() + "]");
                System.out.println(extraOut.strip() + "]");
                break;

            case "currentscene":
                if (p.getCurrentScene() != null) {
                    System.out.println("The current Scene is: " + p.getCurrentScene().sceneName);
                } else {
                    System.out.println("You are currently in the: " + p.getCurrentRoom().roomName + "," +
                            " which has no scenes/roles.");
                }
                break;

            case "takerole":
                if (p.getCurrentScene() != null && p.getCurrentRole() == null) {
                    System.out.println("Your current Scene is: " + p.getCurrentScene().sceneName);
                    mains = p.getCurrentScene().roles;
                    extras = p.getCurrentRoom().extraRoles;

                    mainOut = "Main Roles: [";
                    extraOut = "Extra Roles: [";

                    for (String mainKey : mains.keySet()) {
                        mainOut += mains.get(mainKey).title +", ";
                    }

                    for (String extraKey : extras.keySet()) {
                        extraOut += extras.get(extraKey).title + ", ";
                    }

                    System.out.println(mainOut.strip() + "]");
                    System.out.println(extraOut.strip() + "]");

                    System.out.println("Main or Extra role? : ");
                    Role take = null;
                    try {
                        String mainOrExtra = sc.nextLine();
                        String choice = null;
                        if (mainOrExtra.equalsIgnoreCase("main")) {
                            System.out.println("Which role? : ");
                            choice = sc.nextLine();
                            take = mains.get(choice);
                            mains.remove(choice);
                        } else if (mainOrExtra.equalsIgnoreCase("extra")) {
                            System.out.println("Which role? : ");
                            choice = sc.nextLine();
                            take = extras.get(choice);
                            extras.remove(choice);
                        } else {
                            System.out.println("Must specify a main or extra role!");
                            break;
                        }

                        if (take == null) {throw new NullPointerException();}
                    } catch (NullPointerException ex) {
                        System.out.println("Typing the role you want to take is case-sensitive.");
                        break;
                    }
                    p.setCurrentRole(take);
                    System.out.println("Took role: " + take.title);
                    p.setHasTakenRole(true);
                    p.getCurrentScene().addPlayerOnScene(p);
                } else {
                    System.out.println("This room has no Scenes or Roles, or you already have one!");
                }
                break;

            case "currentday":
                System.out.println("Current day: " + currentDay);
                break;

            case "currentrole":
                System.out.println(p.getCurrentRole().title);
                break;

            case "rehearse":
                if (p.getCurrentRole() != null) {
                    p.rehearse();
                    System.out.println("You now have " + p.getPracticeChips() + " practice chips.");
                } else {
                    System.out.println("You have not taken a role yet!");
                }
                break;

            case "act":
                if (p.getCurrentRole() != null) {
                    int dieRoll = rollDie(1)[0];
                    boolean didSucceed = p.act(dieRoll);

                    if (didSucceed) {
                        System.out.println("You've successfully performed a shot!");
                        System.out.println("Remaining shots on this scene: " + p.getCurrentScene());
                        System.out.println("Credits earned: 2");
                    } else {
                        System.out.println("Acting failed!");
                        //todo extra roles and main roles need to be differentiated
                    }
                }
                break;

            case "rankup":
                System.out.println("Using credits or dollars? (input \"credits\" or \"dollars\")");
                String creditsOrDollars = sc.nextLine();
                if (creditsOrDollars.equalsIgnoreCase("credits")) {
                    p.rankUp(true);
                } else if (creditsOrDollars.equalsIgnoreCase("dollars")) {
                    p.rankUp(false);
                }
                break;

            case "score":
                System.out.println("Your current score is: " + p.getScore());
                break;

            default:
                System.out.println("Here is a list of valid commands: " + Arrays.toString(commands));
                break;
        }
        return p.getHasMoved() && p.getHasTakenRole();
    }

    /**
     * Actual game loop.
     */
    @Deprecated // will be removed after 2 commits
    public void playgameDeprecated() {

        Scanner sc = new Scanner(System.in);
        String[] commands = {"help", "endturn", "move", "whereami", "whoami",
                 "getdollars", "getcredits", "listrooms", "listroles", "currentscene",
                 "takerole", "currentday", "rehearse", "score","act", "currentrole"};
        Arrays.sort(commands);

        for (int currentDay = 1; currentDay <= daysToPlay; currentDay++) {

            //DEAL OUT SCENES AT START OF A NEW DAY
            board.newDay(scenes);
            for (Player p : players) {  //Move back to trailers for new day
                p.move(board.getRooms().get("Trailer"));
            }

            while (board.getNumOfScenes() > 1) {

                if (board.getNumOfScenes() == 1) {
                    Scene finalScene = board.getFinalScene();
                    finalScene.setPayout(false);    //last scene has no payout
                }

                //for each players' turn
                for (Player p : players) {
                    boolean endTurn = false;

                    System.out.println("========== It is " + p.playerName + "'s turn. ==========");
                    System.out.print("Please input an action (type \"help\"): ");
                    while (!endTurn) {
                        String action = sc.nextLine().toLowerCase();

                        endTurn = parseInput(p, sc, commands, action, currentDay, p.getHasMoved(), p.getHasTakenRole());
                        System.out.println(); //a gap between the next block of input for the same player (or next one)
                    }
                    System.out.println(p.playerName + "'s turn has ended.");
                    p.setHasMoved(false);
                    p.setHasTakenRole(false);
                }
            }
            // ONLY GETS HERE WHEN NUMBER OF SCENES = 1
            System.out.println("Day " + currentDay + " has ended.");
            System.out.println();
        }
        //end game, print scores
        System.out.println("Game is over!");
        for (Player p : players) {
            System.out.println(p.playerName + " has a score of: " + p.getScore());
        }
    }
}
