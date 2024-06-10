import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class PubSubTopology {
    static int publicationCount = 100;
    static int subscriptionCount = 100;
    static double companyFrequency = 20;
    static double valueFrequency = 20;
    static double dropFrequency = 20;
    static double variationFrequency = 20;
    static double dateFrequency = 30;
    static double equalOperatorFrequency = 70;
    int evaluationDurationMinutes = 3;
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        // Add Spouts
        builder.setSpout("publicationSpout", new PublisherSpout( publicationCount));
        builder.setSpout("subscriberSpout", new SubscriptionSpout( subscriptionCount, companyFrequency, valueFrequency, dropFrequency, variationFrequency, dateFrequency, equalOperatorFrequency));


        // Add Broker Bolts
        builder.setBolt("broker1", new BrokerBolt(), 1).fieldsGrouping("subscriberSpout", "subscriptionStream", new Fields("subscription"));
        builder.setBolt("broker2", new BrokerBolt(), 1).fieldsGrouping("subscriberSpout", "subscriptionStream", new Fields("subscription"));
        builder.setBolt("broker3", new BrokerBolt(), 1).fieldsGrouping("subscriberSpout", "subscriptionStream", new Fields("subscription"));

        // Connect publication Spout to Brokers
        builder.setBolt("broker1", new BrokerBolt(), 1).shuffleGrouping("publicationSpout", "publicationStream");
        builder.setBolt("broker2", new BrokerBolt(), 1).shuffleGrouping("publicationSpout", "publicationStream");
        builder.setBolt("broker3", new BrokerBolt(), 1).shuffleGrouping("publicationSpout", "publicationStream");

        Config conf = new Config();
        conf.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("pub-sub-topology", conf, builder.createTopology());

        Thread.sleep(60000); // Run the topology for 1 minute
        cluster.killTopology("pub-sub-topology");
        cluster.shutdown();
    }
}
