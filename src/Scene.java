/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:34
 * Deadwood
 */

import java.util.HashMap;
import java.util.Map;

public class Scene {
    public final int sceneNumber;
    public final String sceneName;
    public final int budget;
    public final HashMap<String, Role> roles;
    //MAP IS NOT A CLASS. It's an interface. Choose HashMap or TreeMap (red-black tree) for actual usage

//    private int[] payouts; // [main role, side role, etc...]
//    private int[] bonusPay;

    public Scene(int sceneNumber, String sceneName,
                 int budget, HashMap<String, Role> roles) {
        //todo: check rank see if they succeed
        this.sceneNumber = sceneNumber;
        this.sceneName = sceneName;
        this.budget = budget;
        this.roles = roles;
    }

//    DEPRECATED: Shots are counted on the room, not on the scene card, will be removed in two commits
//    public int removeShot() {
//        this.shotsRemaining -= 1;
//        return this.shotsRemaining;
//    }

    public Role getRole(String r) {
        return roles.get(r);
    }

    public void listRoles() {
        System.out.println("Available roles are: ");
        for (String key : roles.keySet()) {
            System.out.println(roles.get(key));
        }
    }

    public void rehearse(Player actor) {

    }

    private void wrapScene() {

    }

    private void payout() {
        //todo: for each role filled in this.roles, payout
    }

    private void payoutBonus() {
        //todo: should only be used upon wrapping the scene
    }



}
