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
    int[] area;

    public Role(String title, int requiredRank, boolean isOnCardRole, int[] area) {
        this.title = title;
        this.requiredRank = requiredRank;
        this.mainRole = isOnCardRole;
        this.area = area;
    }

    public String getTitle() {
        return title;
    }

    public boolean isMainRole() {
        return mainRole;
    }
}
