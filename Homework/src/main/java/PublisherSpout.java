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

    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        this.publicationGenerator = new PublicationGenerator(publicationCount);
    }

    @Override
    public void nextTuple() {
        Publication publication = publicationGenerator.generatePublication();
        collector.emit(new Values(publication.getCompany(), publication.getValue(), publication.getDrop(), publication.getVariation(), publication.getDate(), publication.getTimestamp()));
        // Sleep for a bit to simulate time intervals between publications
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("company", "value", "drop", "variation", "date", "timestamp"));
    }
}
