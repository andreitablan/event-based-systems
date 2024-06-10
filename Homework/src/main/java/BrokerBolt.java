import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BrokerBolt extends BaseBasicBolt {
    private List<Subscription> subscriptions;
    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        this.subscriptions = new ArrayList<>();
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String streamId = input.getSourceStreamId();
        if (streamId.equals("subscriptions")) {
            Subscription subscription = (Subscription) input.getValueByField("subscription");
            subscriptions.add(subscription);
        } else if (streamId.equals("publications")) {
            Publication publication = new Publication(
                    input.getStringByField("company"),
                    input.getDoubleByField("value"),
                    input.getDoubleByField("drop"),
                    input.getDoubleByField("variation"),
                    input.getStringByField("date")
            );

            for (Subscription subscription : subscriptions) {
                if (matches(subscription, publication)) {
                    // Emit matching publication to subscribers
                    collector.emit("matches", new Values(subscription, publication));
                }
            }
        }
    }

    private boolean matches(Subscription subscription, Publication publication) {
        // Implement matching logic
        for (Map.Entry<String, Subscription.Condition> entry : subscription.getConditions().entrySet()) {
            String field = entry.getKey();
            Subscription.Condition condition = entry.getValue();

            if (field.equals("company") && !publication.getCompany().equals(condition.getValue())) {
                return false;
            }
            // Add other field matching logic based on the operator
        }
        return true;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream("matches", new Fields("subscription", "publication"));
    }
}
