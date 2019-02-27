/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-15
 * Time: 20:34
 * Deadwood
 */

public class Role {
    String title;
    int requiredRank;
    boolean mainRole;

    public Role(String title, int requiredRank, boolean isOnCardRole) {
        this.title = title;
        this.requiredRank = requiredRank;
        this.mainRole = isOnCardRole;
    }
}
