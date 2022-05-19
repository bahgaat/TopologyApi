package Api;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestTopologyApi {
    TopologyApiInterface topologyApi = new TopologyApi();

    @Test
    public void readJsonWhereFileExistsTest() {
        boolean readSuccessfully = topologyApi.readJson("top.json");
        Assert.assertEquals(true, readSuccessfully);
    }

    @Test
    public void readJsonWhereFileNotExistsTest() {
        boolean readSuccessfully = topologyApi.readJson("top9.json");
        Assert.assertEquals(false, readSuccessfully);
    }


    @Test
    public void writeJsonExistedInMemoryTest() {
        topologyApi.writeJson("top3");
        boolean readSuccessfully = topologyApi.readJson("top3.json");
        Assert.assertEquals(true, readSuccessfully);
    }

    @Test
    public void writeJsonNotExistedInMemoryTest() {
        topologyApi.writeJson("top13");
        boolean readSuccessfully = topologyApi.readJson("top13.json");
        Assert.assertEquals(false, readSuccessfully);
    }


    @Test
    public void queryTopologiesTest() {
        topologyApi.readJson("top1.json");
        topologyApi.readJson("top.json");
        topologyApi.readJson("top3.json");
        List<Topology> topologies = topologyApi.queryTopologies();
        Assert.assertEquals(3, topologies.size());
    }

    @Test
    public void deleteTopologyTest() {
        topologyApi.readJson("top1.json");
        topologyApi.readJson("top3.json");
        topologyApi.readJson("top8.json");
        topologyApi.deleteTopology("top1");
        List<Topology> topologies = topologyApi.queryTopologies();
        Assert.assertEquals(2, topologies.size());
        topologyApi.writeJson("top1");
        boolean readSuccessfully = topologyApi.readJson("top1.json");
        Assert.assertEquals(false, readSuccessfully);
        topologyApi.deleteTopology("top8");
        topologies = topologyApi.queryTopologies();
        Assert.assertEquals(1, topologies.size());
        topologyApi.writeJson("top8");
        readSuccessfully = topologyApi.readJson("top8.json");
        Assert.assertEquals(false, readSuccessfully);
    }












}
