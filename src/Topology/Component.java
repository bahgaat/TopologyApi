package Api;
import org.json.simple.JSONObject;
import java.util.*;

/** A component or device class which has components's type, id, and netlistIdToValue */
public class Component {
    private String type;
    private String id;
    private HashMap<String, String> netlistIdToValue;

    public String getType() {
        return type;
    }

    Component(String type, String id) {
        this.type = type;
        this.id = id;
        this.netlistIdToValue = new HashMap<>();
    }

    void addNetlist(JSONObject netlistJson) {
        for (Object keyStr : netlistJson.keySet()) {
            String value = (String) netlistJson.get(keyStr);
            netlistIdToValue.put((String) keyStr, value);
        }
    }


    boolean isConnectedToNetListNode(String netlistNode) {
        return netlistIdToValue.containsKey(netlistNode);
    }


}
