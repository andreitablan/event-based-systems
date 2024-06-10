import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Subscriber implements Runnable {
    private LoadBalancer loadBalancer;
    private int subscriptionCount;
    private double companyFreq, valueFreq, dropFreq, variationFreq, dateFreq, equalOperatorFreq;
    private List<Long> latencies = new ArrayList<>();  // Store delivery latencies for evaluation.
    private int receivedCount = 0;  // Count of received publications for evaluation.
    public Subscriber( int subscriptionCount, double companyFreq, double valueFreq, double dropFreq,
                      double variationFreq, double dateFreq, double equalOperatorFreq) {

        this.loadBalancer = LoadBalancer.getInstance();
        this.subscriptionCount = subscriptionCount;
        this.companyFreq = companyFreq;
        this.valueFreq = valueFreq;
        this.dropFreq = dropFreq;
        this.variationFreq = variationFreq;
        this.dateFreq = dateFreq;
        this.equalOperatorFreq = equalOperatorFreq;
    }

    @Override
    public void run() {
        SubscriptionGenerator generator = new SubscriptionGenerator(companyFreq, valueFreq, dropFreq, variationFreq, dateFreq, subscriptionCount, equalOperatorFreq);
        try {
            Subscription[] subscriptions = generator.generateSubscriptions();
            for (Subscription subscription : subscriptions) {
                System.out.println("subscription " + subscription);
                loadBalancer.sendSubscription(this, subscription);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processResult(Subscription subscription, Publication publication){
        long currentTime = System.nanoTime();
        long latency = currentTime - publication.getTimestamp();  // Calculate the delivery latency.
        latencies.add(latency);
        receivedCount++;

        System.out.println("------------------------------------------" +
                "\nMatch between subscription and publication" +
                "\nSubscription: " + subscription.toString() +
                "\nPublication: " + publication.toString() +
                "\nLatency (ns): " + latency +
                "------------------------------------------");
    }
    public List<Long> getLatencies() {
        return latencies;
    }

    public int getReceivedCount() {
        return receivedCount;
    }
}
