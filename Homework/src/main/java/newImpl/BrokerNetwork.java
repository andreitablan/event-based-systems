package newImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BrokerNetwork {
    private List<Broker> brokers;
    private int brokerCount;

    public BrokerNetwork(int brokerCount) {
        this.brokerCount = brokerCount;
        brokers = new ArrayList<>();
        for (int i = 0; i < brokerCount; i++) {
            brokers.add(new Broker(i));
        }
        // Setting up the next brokers for routing
        for (int i = 0; i < brokerCount; i++) {
            brokers.get(i).addNextBroker(brokers.get((i + 1) % brokerCount));
        }
    }

    public void addSubscription(Subscription subscription, int subscriberId) {
        int brokerId = (subscriberId + subscription.hashCode()) % brokerCount;  // Distribute subscriptions evenly
        brokers.get(brokerId).addSubscription(subscription);
    }

    public void publish(Publication publication) {
        Set<Integer> visitedBrokers = new HashSet<>();
        brokers.get(0).routePublication(publication, visitedBrokers);  // Start routing from the first broker
    }

    public static void main(String[] args) {
        BrokerNetwork network = new BrokerNetwork(3);

        // Initialize subscribers
        Subscriber subscriber1 = new Subscriber(1, network);
        Subscriber subscriber2 = new Subscriber(2, network);
        Subscriber subscriber3 = new Subscriber(3, network);

        // Subscribers subscribe to the network
        subscriber1.subscribe(0.9, 0.9, 0.9, 0.7);
        subscriber2.subscribe(0.8, 0.7, 0.6, 0.5);
        subscriber3.subscribe(0.7, 0.8, 0.9, 0.6);

        // Generate and publish publications
        for (int i = 0; i < 10; i++) {
            Publication pub = PublicationGenerator.generateRandomPublication();
            network.publish(pub);
        }
    }
}
