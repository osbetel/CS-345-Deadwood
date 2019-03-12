/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:33
 * Deadwood
 */

public class Trailer extends Room {

    public Trailer() {
        super("Trailer", null, null, null, new int[]{991,248,194,201});
        //The purpose of this class is because Trailer is a special room
        //players return specifically to this room at the end of a day
    }
}
