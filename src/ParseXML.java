/*
 * Created by: Andrew Nguyen
 * Date: 2019-03-09
 * Time: 15:13
 * Deadwood
 */

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ParseXML {

    /**
     * Reads an XML file, parses input, returns list/map of relevant objects
     * @param filename directory path to the file
     * @param isInputRooms True if reading rooms file, False if reading scenes file
     * @throws XMLStreamException Throws if the XML Stream gets messed up (premature end of file, no closing tag, etc.)
     * @throws IOException Usually thrown if the filename input is incorrect, doesn't exist (also FileNotFoundException)
     */
    public static Object parseXML(String filename, boolean isInputRooms)
            throws XMLStreamException, IOException {
        FileInputStream fis = new FileInputStream(filename);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader sr = null;
        Object o;
        if (isInputRooms) {
            o = new HashMap<String, Room>();
            try {
                sr = factory.createXMLStreamReader(fis);
                readRooms(sr, (HashMap<String, Room>) o);
            } finally {
                if (sr != null) {
                    sr.close();
                }
            }
        } else {
            o = new ArrayList<Scene>();
            try {
                sr = factory.createXMLStreamReader(fis);
                readScenes(sr, (ArrayList<Scene>) o);
            } finally {
                if (sr != null) {
                    sr.close();
                }
            }
        }
        return o;
    }

    /**
     * Reads the board.xml file and extracts and constructs all the room objects
     * @param sr XML Stream Reader object
     * @param rms A hashmap of String keys and Room values
     * @throws XMLStreamException throws the exception if the XML file isn't in the expected format or doesn't exist.
     */
    private static void readRooms(XMLStreamReader sr, HashMap<String, Room> rms)
            throws XMLStreamException {

        HashMap<String, Room> neighbors = new HashMap<>();
        ArrayList<ShotCounter> shotCounters = new ArrayList<>();
        HashMap<String, Role> extraRoles = new HashMap<>();
        String rmName = null;
        int[] area = new int[4];
        int[] rmArea = new int[4];

        while (sr.hasNext()) {

            int eventType = sr.next();

            switch (eventType) {

                case XMLStreamReader.START_ELEMENT:

                    String elementName = sr.getLocalName();

                    switch (elementName) {
                        case "board":
                            break;
                        case "set":
                            rmName = sr.getAttributeValue(null, "name");
                            break;
                        case "neighbor":
                            String neighborName = sr.getAttributeValue(null, "name");

                            if (neighborName.equalsIgnoreCase("trailer")) {
                                neighbors.put("Trailer", null);
                            }
                            else if (neighborName.equalsIgnoreCase("office")) {
                                neighbors.put("Casting Office", null);
                            }
                            else {
                                neighbors.put(neighborName, null);
                            }
                            break;
                        case "area":
                            area = readArea(sr);
                            break;
                        case "take":
                            rmArea = area;
                            int shotNum = Integer.parseInt(sr.getAttributeValue(null, "number"));
                            int[] coords = readArea(sr);
                            shotCounters.add(new ShotCounter(shotNum, coords));
                            break;
                        case "part":
                            String partName = sr.getAttributeValue(null, "name");
                            int reqRank = Integer.parseInt(sr.getAttributeValue(null, "level"));
//                            area = readArea(sr); //todo fix
//                            String line = readLine(sr); //todo fix
                            extraRoles.put(partName, new Role(partName, reqRank, false, null));
                            break;
                    }
                    break;

                case XMLStreamReader.END_ELEMENT:
                    if (sr.getLocalName().equalsIgnoreCase("set")) {
                        rms.put(rmName, new Room(rmName, neighbors, shotCounters, extraRoles, rmArea));
                        neighbors = new HashMap<>();
                        shotCounters = new ArrayList<>();
                        extraRoles = new HashMap<>();
                    } else if (sr.getLocalName().equalsIgnoreCase("trailer")) {
                        rms.put("Trailer", new Trailer(neighbors));
                        neighbors = new HashMap<>();
                    } else if (sr.getLocalName().equalsIgnoreCase("office")) {
                        rms.put("Casting Office", new CastingOffice(neighbors));
                        neighbors = new HashMap<>();
                    }
                    break;
            }
        }
    }

    /**
     * Reads only the area tags and returns their value so we can have rooms with specific area (to click and move there)
     * @param sr XML Stream Reader object
     * @throws XMLStreamException throws the exception if the XML file isn't in the expected format or doesn't exist.
     */
    private static int[] readArea(XMLStreamReader sr) throws XMLStreamException {

        if (!sr.getLocalName().equalsIgnoreCase("area")) {
            sr.next();
        }

        int x = Integer.parseInt(sr.getAttributeValue(null, "x"));
        int y = Integer.parseInt(sr.getAttributeValue(null, "y"));
        int h = Integer.parseInt(sr.getAttributeValue(null, "h"));
        int w = Integer.parseInt(sr.getAttributeValue(null, "w"));
        int[] coords = {x,y,h,w};
        return coords;
    }

    /**
     * Reads regular text between XML tags
     */
    private static String readLine(XMLStreamReader sr) throws XMLStreamException {
        sr.next();
        String s = sr.getElementText();
        return s;
    }

    /**
     * Reads the cards.xml file and extracts and constructs all the room objects
     * @param sr XML Stream Reader object
     * @param scenes A list of scenes
     * @throws XMLStreamException throws the exception if the XML file isn't in the expected format or doesn't exist.
     */
    private static void readScenes(XMLStreamReader sr, ArrayList<Scene> scenes) throws XMLStreamException {

        //For Scene objects
        String sceneName = "";
        String filePath = "";
        int budget = -1;
        int sceneNum = -1;
        String sceneText = "";
        HashMap<String, Role> roles = new HashMap<>();

        //For Role objects
        String roleName = "";
        int reqRank = -1;
        int[] area = new int[4];

        while (sr.hasNext()) {

            int eventType = sr.next();

            switch (eventType) {

                case XMLStreamReader.START_ELEMENT:

                    String elementName = sr.getLocalName();

                    switch (elementName) {
                        case "card":
                            sceneName = sr.getAttributeValue(null, "name");
                            filePath = "Assets/cards/" + sr.getAttributeValue(null, "img");
                            budget = Integer.parseInt(sr.getAttributeValue(null, "budget"));
                            break;
                        case "scene":
                            sceneNum = Integer.parseInt(sr.getAttributeValue(null, "number"));
                            sceneText = sr.getElementText();
                            break;
                        case "part":
                            roleName = sr.getAttributeValue(null, "name");
                            reqRank = Integer.parseInt(sr.getAttributeValue(null, "level"));
                            break;

                    }
                    break;

                case XMLStreamReader.END_ELEMENT:
                    if (sr.getLocalName().equalsIgnoreCase("card")) {
                        scenes.add(new Scene(sceneName, filePath, budget, sceneNum, sceneText, roles));
                        sceneName = "";
                        budget = -1;
                        sceneNum = -1;
                        roles = new HashMap<>();
                    } else if (sr.getLocalName().equalsIgnoreCase("part")) {
                        //int[] area = readArea(sr); //todo fix
                        //String line = readLine(sr); //todo fix
                        roles.put(roleName, new Role(roleName, reqRank, true, null));
                        roleName = "";  //probably unnecessary
                        reqRank = -1;
                    }
                    break;
            }
        }
    }
}
