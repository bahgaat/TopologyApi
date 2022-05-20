package TestFiles;
import Topology.Component;
import Topology.Topology;
import Api.TopologyApi;
import Api.TopologyApiImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestTopologyApi {
    TopologyApi topologyApi = new TopologyApiImpl();

    @Test
    public void readJsonWhereFileExistsTest() {
        boolean savedSuccessfully = topologyApi.readJson("top.json");
        Assert.assertEquals(true, savedSuccessfully);
    }

    @Test
    public void readJsonWhereFileNotExistsTest() {
        boolean savedSuccessfully = topologyApi.readJson("top18.json");
        Assert.assertEquals(false, savedSuccessfully);
    }


    @Test
    public void writeJsonTest() {
        topologyApi.readJson("top.json");
        topologyApi.writeJson("top3");
        boolean readSuccessfully = topologyApi.readJson("top3.json");
        Assert.assertEquals(true, readSuccessfully);
    }


    @Test
    public void queryTopologiesTest() {
        topologyApi.readJson("top1.json");
        topologyApi.readJson("top8.json");
        topologyApi.readJson("top9.json");
        List<Topology> topologies = topologyApi.queryTopologies();
        Assert.assertEquals(3, topologies.size());
        String top1Id = topologies.get(0).getId();
        String top8Id = topologies.get(1).getId();
        String top9Id = topologies.get(2).getId();
        boolean check = top1Id.equals("top1") && top8Id.equals("top8") &&
                top9Id.equals("top9");
        Assert.assertEquals(true, check);
    }

    @Test
    public void deleteTopologyTest() {
        topologyApi.readJson("top1.json");
        topologyApi.readJson("top9.json");
        topologyApi.readJson("top8.json");
        topologyApi.deleteTopology("top1");
        List<Topology> topologies = topologyApi.queryTopologies();
        Assert.assertEquals(2, topologies.size());
        boolean exists = topologyApi.writeJson("top1");
        Assert.assertEquals(false, exists);
        topologyApi.deleteTopology("top8");
        topologies = topologyApi.queryTopologies();
        Assert.assertEquals(1, topologies.size());
        exists = topologyApi.writeJson("top8");
        Assert.assertEquals(false, exists);
    }

    @Test
    public void testQueryDevicesTest() {
        topologyApi.readJson("top1.json");
        List<Component> components = topologyApi.queryDevices("top1");
        Assert.assertEquals(2, components.size());
        String component1Type = components.get(0).getType();
        String component2Type = components.get(1).getType();
        boolean check = component1Type.equals("resistor") && component2Type.equals("nmos");
        Assert.assertEquals(true, check);
    }

    @Test
    public void  queryComponentsWithNetlistNodeTest() {
        topologyApi.readJson("top1.json");
        List<Component> components = topologyApi.queryDevicesWithNetlistNode("top1", "drain");
        Assert.assertEquals(1, components.size());
        String componentType = components.get(0).getType();
        Assert.assertEquals(true, componentType.equals("nmos"));
        topologyApi.readJson("top8.json");
        components = topologyApi.queryDevicesWithNetlistNode("top8", "t1");
        Assert.assertEquals(2, components.size());
        String componentType1 = components.get(0).getType();
        String componentType2 = components.get(1).getType();
        Assert.assertEquals(true, componentType1.equals("resistor"));
        Assert.assertEquals(true, componentType2.equals("nmos"));
    }

}
