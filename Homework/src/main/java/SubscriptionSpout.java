import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.IOException;
import java.util.Map;

public class SubscriptionSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private SubscriptionGenerator subscriptionGenerator;
    private int subscriptionCount;

    public SubscriptionSpout(int subscriptionCount, double companyFreq, double valueFreq, double dropFreq, double variationFreq, double dateFreq, double equalOperatorFreq) {
        this.subscriptionCount = subscriptionCount;
        this.subscriptionGenerator = new SubscriptionGenerator(companyFreq, valueFreq, dropFreq, variationFreq, dateFreq, subscriptionCount, equalOperatorFreq);
    }

    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    public void nextTuple() {
        try {
            for (Subscription subscription : subscriptionGenerator.generateSubscriptions()) {

                collector.emit(new Values(subscription));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Emit once then sleep for a while
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("subscription"));
    }
}
