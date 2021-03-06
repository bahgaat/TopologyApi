package Topology;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/** A topology class which has topology id and its components (Devices). */
public class Topology {
    private String id;
    private List<Component> components;

    public Topology(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<Component> getComponents() {
        return this.components;
    }


    public void setComponents(JSONArray componentsJson) {
        this.components = new ArrayList<>() ;
        Iterator<JSONObject> iterator = componentsJson.iterator();
        while (iterator.hasNext()) {
           JSONObject component = iterator.next();
           addComponent(component);
        }
    }

    private void addComponent(JSONObject componentJson) {
        String deviceType = (String) componentJson.get("type");
        String deviceId  = (String) componentJson.get("id");
        JSONObject netlist = (JSONObject) componentJson.get("netlist");
        Component component = new Component(deviceType, deviceId);
        components.add(component);
        component.addNetlist(netlist);
    }


    public List<Component> queryComponentsWithNetlistNode(String netlistNode) {
        List<Component> componentsConnectedToNetlistNode = new ArrayList<>();
        for (int i = 0; i < components.size(); i++) {
            Component component = components.get(i);
            if (component.isConnectedToNetListNode(netlistNode)) {
                componentsConnectedToNetlistNode.add(component);
            }
        }
        return componentsConnectedToNetlistNode;
    }

}
