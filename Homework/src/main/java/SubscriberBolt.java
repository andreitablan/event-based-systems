import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class SubscriberBolt extends BaseBasicBolt {
    private OutputCollector collector;
    private Map<String, Integer> subscriberMessageCount;
    private long totalLatency;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        this.subscriberMessageCount = new HashMap<>();
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        Subscription subscription = (Subscription) input.getValueByField("subscription");
        Publication publication = (Publication) input.getValueByField("publication");

        // Înregistrăm timpul de primire pentru a măsura latenta
        long receiveTime = System.currentTimeMillis();
        long publishTime = publication.getTimestamp();
        long latency = receiveTime - publishTime;

        // Actualizăm numărul de mesaje pentru subscriber și latenta totală
        String subscriberId = subscription.getSubscriberId();
        subscriberMessageCount.put(subscriberId, subscriberMessageCount.getOrDefault(subscriberId, 0) + 1);
         totalLatency = latency;

        // Emitem o valoare pentru a semnala că am livrat publicația
        collector.emit(new Values(subscriberId, publication));
    }

    @Override
    public void cleanup() {
        // Afișăm câte mesaje au fost livrate fiecărui subscriber și latenta medie
        for (Map.Entry<String, Integer> entry : subscriberMessageCount.entrySet()) {
            System.out.println("Subscriber " + entry.getKey() + " a primit " + entry.getValue() + " mesaje.");
        }
        double averageLatency = totalLatency / (double) subscriberMessageCount.size();
        System.out.println("Latenta medie este: " + averageLatency + " ms.");
    }


    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("subscriberId", "publication"));
    }

}
