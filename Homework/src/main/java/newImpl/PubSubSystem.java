package newImpl;

public class PubSubSystem {
    public static void main(String[] args) {
        // Initialize broker network
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
