package Api;
import java.util.List;

/** An API which does the following:
 1. Read and write topologies to and from disk.
 2. Stores multiple topologies in memory.
 3. Execute operations on topologies.
 */

public interface TopologyApiInterface {
    /* Read a topology from a given JSON file and store it in the memory, return true if stored successfully
    and false otherwise. */
    boolean readJson(String jsonFile);

    /* Write a given topology if it exists in the memory to a JSON file, return true if it has been
    written successfully and false otherwise. */
    boolean writeJson(String topologyId);

    /* Query about which topologies are currently in the memory. */
    List<Topology> queryTopologies();

    /* Delete a given topology from memory. */
    void deleteTopology(String topologyId);

    /* Query about which devices are in a given topology.*/
    List<Component> queryDevices(String topologyId);

    /* Query about which devices are connected to a given netlist node in
     a given topology. */
    List<Component> queryDevicesWithNetlistNode(String topologyId, String NetlistNode);
}
