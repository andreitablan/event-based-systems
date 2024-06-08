import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoadBalancer {
    private static LoadBalancer loadBalancer;

    private List<Broker> brokers = new ArrayList<>();

    public static LoadBalancer getInstance(){
        if(loadBalancer == null){
            loadBalancer = new LoadBalancer();
        }

        return loadBalancer;
    }

    public void addBroker(Broker broker){
        this.brokers.add(broker);
    }

    public void sendSubscription(Subscriber subscriber, Subscription subscription){
        int brokerIndex = 0;
        int min = Integer.MAX_VALUE;

        int listSize = brokers.size();

        // Find the broker with the least number of subscriptions
        for(int i = 0; i < listSize; i++){
            Broker broker = brokers.get(i);

            int numberOfSubscriptions = broker.getNumberOfSubscriptions();
            if(min > numberOfSubscriptions) {
                min = numberOfSubscriptions;
                brokerIndex = i;
            }
        }
        // Round-robin distribution logic
        // Find the next broker in the round-robin sequence for this subscriber
        int nextBrokerIndex = (brokers.indexOf(brokers.get(brokerIndex)) + 1) % brokers.size();
        this.brokers.get(nextBrokerIndex).receiveSubscription(subscriber, subscription);

        //this.brokers.get(brokerIndex).receiveSubscription(subscriber, subscription);
    }

    public void sendPublication(Publication publication){
        for( Broker broker: brokers){
            broker.receivePublication(publication);
        }
    }

    public List<Broker> getBrokers() {
        return brokers;
    }
}
