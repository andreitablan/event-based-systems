package newImpl;

class Subscriber {
    private int id;
    private BrokerNetwork network;

    public Subscriber(int id, BrokerNetwork network) {
        this.id = id;
        this.network = network;
    }

    public void subscribe(double companyFrequency, double valueFrequency, double variationFrequency, double equalityFrequency) {
        Subscription sub = SubscriptionGenerator.generateRandomSubscription(companyFrequency, valueFrequency, variationFrequency, equalityFrequency);
        network.addSubscription(sub, id);
        System.out.println("Subscriber " + id + " subscribed with: " + sub);
    }
}
