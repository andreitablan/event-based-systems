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
        long startTime = System.nanoTime();
        brokers.get(0).routePublication(publication, visitedBrokers, startTime);  // Start routing from the first broker
    }

    // for evaluation purposes
    public static void main(String[] args) {
        // Parameters
        int numberOfBrokers = 3;
        int numberOfPublications = 10000;
        long evaluationInterval = 3 * 60 * 1000; // 3 minutes in milliseconds

        BrokerNetwork network = new BrokerNetwork(numberOfBrokers);

        // Initialize subscribers
        Subscriber subscriber1 = new Subscriber(1, network);
        Subscriber subscriber2 = new Subscriber(2, network);
        Subscriber subscriber3 = new Subscriber(3, network);

        // Subscribe with 10,000 simple subscriptions
        for (int i = 0; i < 10000; i++) {
            if (i % 3 == 0) {
                subscriber1.subscribe(0.9, 0.9, 0.9, 0.7);
            } else if (i % 3 == 1) {
                subscriber2.subscribe(0.8, 0.7, 0.6, 0.5);
            } else {
                subscriber3.subscribe(0.7, 0.8, 0.9, 0.6);
            }
        }

        // Start publication generation
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < evaluationInterval) {
            Publication pub = PublicationGenerator.generateRandomPublication();
            network.publish(pub);
        }

        // Print statistics
        EvaluationLogger.printStatistics();
    }
}
