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
     * @throws XMLStreamException If the input file is not an XML file
     * @throws IOException Same
     */
    public static Object parseXML(String filename, boolean isInputRooms)
            throws XMLStreamException, IOException {
        FileInputStream fis = new FileInputStream(filename);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader sr = null;
        Object o;
        if (isInputRooms) {
            o = new HashMap<String, Room>();
        } else {
            o = new ArrayList<Scene>();
        }
        try {
            sr = factory.createXMLStreamReader(fis);
            readRooms(sr, (HashMap<String, Room>) o);
        } finally {
            if (sr != null) {
                sr.close();
            }
        }
        return o;
    }


    private static void readRooms(XMLStreamReader sr, HashMap<String, Room> rms)
            throws XMLStreamException {

        HashMap<String, Room> neighbors = new HashMap<>();
        ArrayList<ShotCounter> shotCounters = new ArrayList<>();
        HashMap<String, Role> extraRoles = new HashMap<>();
        String rmName = null;
        int[] area = new int[4];

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
                            int shotNum = Integer.parseInt(sr.getAttributeValue(null, "number"));
                            int[] coords = readArea(sr);
                            shotCounters.add(new ShotCounter(shotNum, coords));
                            break;
                        case "part":
                            String partName = sr.getAttributeValue(null, "name");
                            int reqRank = Integer.parseInt(sr.getAttributeValue(null, "level"));
//                            int[] area = readArea(sr); //todo fix
//                            String line = readLine(sr); //todo fix
                            extraRoles.put(partName, new Role(partName, reqRank, false));
                            break;
                    }

                    break;
                case XMLStreamReader.END_ELEMENT:
                    if (sr.getLocalName().equalsIgnoreCase("set")) {
                        rms.put(rmName, new Room(rmName, neighbors, shotCounters, extraRoles, area));
                        neighbors = new HashMap<>();
                        shotCounters = new ArrayList<>();
                        extraRoles = new HashMap<>();
                    } else if (sr.getLocalName().equalsIgnoreCase("trailer")) {
                        rms.put("Trailer", new Trailer());
                    } else if (sr.getLocalName().equalsIgnoreCase("office")) {
                        rms.put("Casting Office", new CastingOffice());
                    }
                    break;
            }
        }
//        throw new XMLStreamException("Premature end of file");
    }

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

    private static String readLine(XMLStreamReader sr) throws XMLStreamException {
        sr.next();
        String s = sr.getElementText();
        return s;
    }
}
