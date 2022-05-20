package Api;

import java.io.FileNotFoundException;
import java.util.*;
import org.json.simple.JSONObject;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/** TopologyApi class which implements TopologyApiinterface and is responsible for
 1. Read and write topologies to and from disk.
 2. Stores multiple topologies in memory.
 3. Execute operations on topologies.
 */
public class TopologyApi implements TopologyApiInterface {
    private HashMap<String, JSONObject> topologyIdToJsonObject = new HashMap<>();
    private HashMap<String, Topology> topologyIdToObject = new HashMap<>();
    private List<Topology> topologies = new ArrayList<>();

    /* Read a topology from a given JSON file and store it in the memory. */
    public boolean readJson(String jsonFile) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(jsonFile))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject topologyObject = (JSONObject) obj;

            String topologyId = (String) topologyObject.get("id");
            JSONArray components = (JSONArray) topologyObject.get("components");

            Topology topology = new Topology(topologyId);
            topology.setComponents(components);

            //save topology and its id in memory
            topologies.add(topology);
            topologyIdToJsonObject.put(topologyId, topologyObject);
            topologyIdToObject.put(topologyId, topology);

            boolean savedSuccessfully = topologies.contains(topology) &&
                    topologyIdToJsonObject.containsKey(topologyId) && topologyIdToObject.containsKey(topologyId);
            return savedSuccessfully;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* Write a given topology if it exists in the memory to a JSON file. */
    public boolean writeJson(String topologyId) {
        if (topologyIdToJsonObject.containsKey(topologyId)) {
            JSONObject topology = topologyIdToJsonObject.get(topologyId);
            //Write JSON file
            try (FileWriter file = new FileWriter(topologyId +".json")) {
                file.write(topology.toString());
                file.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /* Query about which topologies are currently in the memory. */
    public List<Topology> queryTopologies() {
        return this.topologies;
    }

    /* Delete a given topology from memory. */
    public void deleteTopology(String topologyId) {
        Topology topology = topologyIdToObject.get(topologyId);
        topologyIdToJsonObject.remove(topologyId);
        topologyIdToObject.remove(topologyId);
        topologies.remove(topology);
    }

    /* Query about which devices are in a given topology. */
    public List<Component> queryDevices(String topologyId) {
        Topology topology = topologyIdToObject.get(topologyId);
        return topology.getComponents();
    }

    /* Query about which devices are connected to a given netlist node in a given topology. */
    public List<Component> queryDevicesWithNetlistNode(String topologyId, String netlistNode) {
        Topology topology = topologyIdToObject.get(topologyId);
        return topology.queryComponentsWithNetlistNode(netlistNode);
    }

}
