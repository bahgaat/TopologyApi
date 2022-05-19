package Api;

import java.util.List;

class Main {
    public static void main(String[] args) {
        TopologyApiInterface topologyApi= new TopologyApi();
        topologyApi.readJson("top.json");
        topologyApi.writeJson("top3");

    }
}

