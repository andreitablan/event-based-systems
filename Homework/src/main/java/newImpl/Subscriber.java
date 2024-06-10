package newImpl;

import java.util.Random;

class Subscriber {
    private String id;
    private BrokerNetwork network;

    public Subscriber(String id, BrokerNetwork network) {
        this.id = id;
        this.network = network;
    }

    public void subscribe(double companyFrequency, double valueFrequency, double variationFrequency, double equalityFrequency) {
        Subscription sub = SubscriptionGenerator.generateRandomSubscription(companyFrequency, valueFrequency, variationFrequency, equalityFrequency);
        network.addSubscription(sub);
        System.out.println("Subscriber " + id + " subscribed with: " + sub);
    }
}

