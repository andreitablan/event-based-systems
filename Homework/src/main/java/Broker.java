import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Broker {
    private Map<Subscriber, List<Subscription>> subscriptions = new HashMap<>();

    public void receiveSubscription(Subscriber subscriber, Subscription subscription) {
        subscriptions.get(subscriber).add(subscription);
    }

    public void receivePublication(Publication publication) {
        processPublication(publication);
    }

    public int getNumberOfSubscriptions(){
        int numberOfSubscriptions = 0;

        for (Subscriber key : subscriptions.keySet()) {
            numberOfSubscriptions += subscriptions.get(key).size();
        }

        return numberOfSubscriptions;
    }

    private void processPublication(Publication publication) {
        for (Subscriber key : subscriptions.keySet()) {
            for(Subscription subscription: subscriptions.get(key)){
                if (matches(subscription, publication)) {
                    notifySubscribers(key, subscription, publication);
                }
            }
        }
    }


    private boolean matches(Subscription subscription, Publication publication) {
        // Implement matching logic here
        return true;
    }

    private void notifySubscribers(Subscriber subscriber, Subscription subscription, Publication publication) {
        subscriber.processResult(subscription, publication);
    }
}
