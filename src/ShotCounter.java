/*
 * Created by: Andrew Nguyen
 * Date: 2019-03-10
 * Time: 21:57
 * CS-345-Deadwood
 */

public class ShotCounter {

    public final int[] coordinates; // [x, y, width, height]
    public final int shotNum; //First shot counter, or second, third, etc...
    public boolean marked;

    public ShotCounter(int shotNum, int[] coords) {
        coordinates = coords;
        marked = false;
        this.shotNum = shotNum;
    }

    public void setMarked(boolean b) {
        marked = b;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public boolean isMarked() {
        return marked;
    }
}
