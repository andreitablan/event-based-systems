import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class PublisherSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private PublicationGenerator publicationGenerator;
    private int publicationCount;

    public PublisherSpout(int publicationCount) {
        this.publicationCount = publicationCount;
    }

    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        this.publicationGenerator = new PublicationGenerator(publicationCount);
    }

    public void nextTuple() {
        Publication publication = publicationGenerator.generatePublication();
        collector.emit(new Values(publication));
        // Sleep for a bit to simulate time intervals between publications
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("publication"));
    }
}
