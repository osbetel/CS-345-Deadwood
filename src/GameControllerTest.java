/*
 * Created by: Andrew Nguyen
 * Date: 2019-02-25
 * Time: 00:02
 * CS-345-Deadwood
 */

import java.util.ArrayList;

/**
 * Just for testing
 */
public class GameControllerTest {

    /**
     * I don't have JUnit installed, and I don't want to introduce a dependency to the project compiler
     * So these are unit tests without the JUnit framework.
     * Note in order for assertions to function properly, you must use the -ea flag in the Java VM,
     * otherwise assertion tests will not throw AssertionErrors
     */

    public static void testPlayers(Player[] plyr, Board board) {

        for (Player p : plyr) {
            assert (p.playerName != null) : "Name is null";
            assert (p.getRank() >= 1) : "Players starting rank must be at least 1";
            assert (p.getCurrentRoom() == board.getRooms().get("Trailer")) :
                    "Players are not starting in the Casting Office";
        }
    }

    public static void testBoard(Board board) {
        for (String rm : board.getRooms().keySet()) {
            Room room = board.getRooms().get(rm);
            assert room.currentScene == null : "Starting scene not null";
            assert room.roomName != null : "Rooms should have non-null names";
            assert room.shotCounters.size() >= 0 : "All rooms have at least 1 shot counter, except Trailer and Casting Office";
        }
        Room trailer = board.getRooms().get("Trailer");
        Room castingOffice = board.getRooms().get("Casting Office");
        assert trailer instanceof Trailer : "Trailer is a Room object, but not a Trailer object";
        assert castingOffice instanceof CastingOffice : "Casting Office is a Room object, " +
                "but not a CastingOffice object, meaning it can't be used to check a PLayer's rankUp()";
    }

    public static void testAll(Player[] plyr, Board board, ArrayList<Scene> scenes) {
        testPlayers(plyr, board);
        testBoard(board);
    }






}
