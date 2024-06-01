import java.io.IOException;
import java.util.Random;

public class Subscriber implements Runnable {
    private Broker broker;
    private int subscriptionCount;
    private double companyFreq, valueFreq, dropFreq, variationFreq, dateFreq, equalOperatorFreq;

    public Subscriber(Broker broker, int subscriptionCount, double companyFreq, double valueFreq, double dropFreq,
                      double variationFreq, double dateFreq, double equalOperatorFreq) {
        this.broker = broker;
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
            generator.generateSubscriptions();
            for (Subscription subscription : generator.generateSubscriptions()) {
                broker.addSubscription(subscription);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
