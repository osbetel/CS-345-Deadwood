/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:34
 * Deadwood
 */

import java.util.*;

/**
 * Scene class represents a Scene card.
 */
public class Scene {
    public final int sceneNumber;
    public final String sceneName;
    public final int budget;
    public final HashMap<String, Role> roles;   //Remember that Rooms and Scenes each have their own Roles
    public int shotCounters;

    public boolean payout;
    public ArrayList<Player> playersOnScene;

    public Scene(int sceneNumber, String sceneName,
                 int budget, HashMap<String, Role> roles) {
        this.sceneNumber = sceneNumber;
        this.sceneName = sceneName;
        this.budget = budget;
        this.roles = roles;
        this.payout = true;
        this.playersOnScene = new ArrayList<>();
    }


    public Role getRole(String r) {
        return roles.get(r);
    }


    /**
     * Called by Players. Lists the available Roles for them to take.
     */
    public void listRoles() {
        System.out.println("Available roles are: ");
        for (String key : roles.keySet()) {
            System.out.println(roles.get(key));
        }
    }


    /**
     * Scenes keep track of which Players are performing on them
     * in order to deal with paying out bonuses.
     * @param ply Player object
     */
    public void addPlayerOnScene(Player ply) {
        playersOnScene.add(ply);
    }


    /**
     * Finishes the Scene. Pays out the standard sum and then pays out bonuses.
     */
    public void wrapScene() {
        for (Player p :  playersOnScene) {
            p.addDollars(p.getCurrentRole().requiredRank);
        }
        payoutBonus();
        System.out.println("Scene \"" + sceneName + "\" wrapped!");
        System.out.println("Bonuses paid!");
    }


    /**
     * Deals with paying out bonuses to Players on the Scene card (extras do not get bonuses)
     */
    private void payoutBonus() {
        Random r = new Random();
        int[] dieRolls = new int[budget];
        for (int i = 0; i < budget; i++) {
            dieRolls[i] = r.nextInt(6);
        }
        Arrays.sort(dieRolls);
        //sort Ranks, todo: extras get their rank. Currently handles main role style.
        for (int j = 0; j < playersOnScene.size(); j++) {
            Player p = playersOnScene.get(j);
            p.addDollars(dieRolls[j]);
        }
    }

    /**
     * For use if this is the last Scene on the board for a given day.
     * @param payout boolean
     */
    public void setPayout(boolean payout) {
        this.payout = payout;
    }


    public void setCounter(int num) {
        this.shotCounters = num;
    }


    public int getCounter() {
        return shotCounters;
    }
}
