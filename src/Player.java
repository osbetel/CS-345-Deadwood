/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:35
 * Deadwood
 */

public class Player {

    private GamePiece dice;
    public final String playerName;

    private Room currentRoom;
    private Scene currentScene;
    private Role currentRole;
    private CastingOffice value;
    public GameController credits;
    public GameController dollars;

    private int money;
    private int credits;

    public Player(String name, int startingRank,
                  int startingMoney, int startingCredits, Room startingRoom) {
        playerName = name;
        credits = startingCredits;
        money = startingMoney;
        dice = new GamePiece(startingRank);

        currentRoom = startingRoom;
    }

    private void move(Room location) {
        //todo: check currentRoom to see if target room is adjacent
        this.currentRoom = location;
    }

    private void takeRole(Scene scene, String role) {
        // roles are defined in Scene class as a Map<String, Integer>, where the integer value is the payout.
        // Consider defining a role class, a very short one to contain  more info about roles
        this.currentRole = scene.getRole(role);
    }

    private void doNothing() {
        // player takes no action
        // add something to remove their turn
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public int getRank() {
        return dice.getRank();
    }

    public double getTotalValue() {
        return -1.0;
    }
    ///*
    //question about current vals
    public void rankUp(int targetRank, boolean dollarOrCredit) {
        //dollarOrCredit.toLowerCase();
        try {
            if (currentRoom == CastingOffice) {

                //for user inputting "dollar" or "credit
                if (dollarOrCredit ==) { //if using credit
                    credits.getCredits(playerName);
                    int[] list = value.CastingOffice(); //but there's two lists in the constuctor, how to distinguish the two //
                    if (credits >= list[targetRank])
                        credits -= list[targetRank];
                    else {
                        System.out.println("Insufficient amount of credits");
                        dollarOrCredit = true;
                    }
                } else { //using dollar
                    //dollar.getDollars(playerName);
                    int[] list = value.CastingOffice(); //but there's two lists in the constuctor, how to distinguish the two //
                    if (dollars >= list.)
                        dollarOrCredit = false;
                }
            }
        } catch (Exception e) {
                System.out.println("Can only rank up in the Casting Office");
                System.exit(1);
            }



    }

//*/



}
