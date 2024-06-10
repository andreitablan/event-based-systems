import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class PublishSubscribeTopology {
    public static void main(String[] args) throws Exception {
        // Creare topologie
        TopologyBuilder builder = new TopologyBuilder();

        // Adăugare PublisherSpout
        builder.setSpout("publisherSpout", new PublisherSpout(10000));

        // Adăugare SubscriptionSpout
        builder.setSpout("subscriptionSpout", new SubscriptionSpout(10000, 20, 20, 20, 20, 30, 70));

        // Adăugare BrokerBolt
        builder.setBolt("brokerBolt1", new BrokerBolt()).shuffleGrouping("subscriptionSpout", "subscriptions").shuffleGrouping("publisherSpout", "publications");
        builder.setBolt("brokerBolt2", new BrokerBolt()).shuffleGrouping("subscriptionSpout", "subscriptions").shuffleGrouping("publisherSpout", "publications");
        builder.setBolt("brokerBolt3", new BrokerBolt()).shuffleGrouping("subscriptionSpout", "subscriptions").shuffleGrouping("publisherSpout", "publications");

        // Adăugare SubscriberBolt
        builder.setBolt("subscriberBolt", new SubscriberBolt()).shuffleGrouping("brokerBolt1", "matches").shuffleGrouping("brokerBolt2", "matches").shuffleGrouping("brokerBolt3", "matches");

        // Configurare și rulare topologie
        Config conf = new Config();
        conf.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("PublishSubscribeTopology", conf, builder.createTopology());

        // Rulare timp de 3 minute
        Thread.sleep(180000);

        // Oprire topologie
        cluster.killTopology("PublishSubscribeTopology");
        cluster.shutdown();
    }
}
