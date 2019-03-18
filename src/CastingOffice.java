/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 13:00
 * Deadwood
 */

import java.util.HashMap;

public class CastingOffice extends Room {

    public final int[] dollarCost;
    public final int[] creditCost;

    public CastingOffice(HashMap<String, Room> neighbors) {
        super("Casting Office", neighbors, null, null, new int[]{9,459,208,209});
        this.dollarCost = new int[] {4, 10, 18, 28, 40};
        this.creditCost = new int[] {5, 10, 15, 20, 25};
    }

}
