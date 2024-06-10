package newImpl;

import java.util.ArrayList;
import java.util.List;

public class BrokerNetwork {
    private List<Broker> brokers;

    public BrokerNetwork(int brokerCount) {
        brokers = new ArrayList<>();
        for (int i = 0; i < brokerCount; i++) {
            brokers.add(new Broker(i));
        }
    }

    public void addSubscription(Subscription subscription) {
        int brokerId = subscription.hashCode() % brokers.size();  // Simple hash-based distribution
        brokers.get(brokerId).addSubscription(subscription);
    }

    public void publish(Publication publication) {
        for (Broker broker : brokers) {
            broker.routePublication(publication);
        }
    }

    public static void main(String[] args) {
        BrokerNetwork network = new BrokerNetwork(3);

        // Simulate subscribers
        SubscriptionGenerator generator = new SubscriptionGenerator();
        for (int i = 0; i < 10; i++) {
            Subscription sub = generator.generateRandomSubscription(0.9, 0.9, 0.9, 0.7);
            network.addSubscription(sub);
        }

        // Simulate publications
        PublicationGenerator pubGen = new PublicationGenerator();
        for (int i = 0; i < 10; i++) {
            Publication pub = pubGen.generateRandomPublication();
            network.publish(pub);
        }
    }
}
