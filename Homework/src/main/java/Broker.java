import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Broker {
    private Map<Subscriber, List<Subscription>> subscriptions = new HashMap<>();

    public void receiveSubscription(Subscriber subscriber, Subscription subscription) {
        if(subscriptions.get(subscriber) != null){
            subscriptions.get(subscriber).add(subscription);
            return;
        }

        List<Subscription> subscriptionsList = new ArrayList<>();
        subscriptionsList.add(subscription);
        subscriptions.put(subscriber, subscriptionsList);
    }

    public void receivePublication(Publication publication) {
        System.out.println("Received publication: " + publication);
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
        Map<String, Subscription.Condition> conditions = subscription.getConditions();

        for (Map.Entry<String, Subscription.Condition> entry : conditions.entrySet()) {
            String field = entry.getKey();
            Subscription.Condition condition = entry.getValue();
            String operator = condition.getOperator();
            String value = condition.getValue();

            boolean match;
            switch (field) {
                case "company":
                    match = matchString(publication.getCompany(), operator, value);
                    break;
                case "value":
                    match = matchDouble(publication.getValue(), operator, Double.parseDouble(value));
                    break;
                case "drop":
                    match = matchDouble(publication.getDrop(), operator, Double.parseDouble(value));
                    break;
                case "variation":
                    match = matchDouble(publication.getVariation(), operator, Double.parseDouble(value));
                    break;
                case "date":
                    match = matchString(publication.getDate(), operator, value);
                    break;
                default:
                    match = false;
            }

            if (!match) {
                return false;
            }
        }
        return true;
    }

    private boolean matchString(String fieldValue, String operator, String value) {
        switch (operator) {
            case "=":
                return fieldValue.equals(value);
            case "<":
                return fieldValue.compareTo(value) < 0;
            case ">":
                return fieldValue.compareTo(value) > 0;
            case "<=":
                return fieldValue.compareTo(value) <= 0;
            case ">=":
                return fieldValue.compareTo(value) >= 0;
            default:
                return false;
        }
    }

    private boolean matchDouble(double fieldValue, String operator, double value) {
        switch (operator) {
            case "=":
                return fieldValue == value;
            case "<":
                return fieldValue < value;
            case ">":
                return fieldValue > value;
            case "<=":
                return fieldValue <= value;
            case ">=":
                return fieldValue >= value;
            default:
                return false;
        }
    }

    public boolean matchPublication(Publication publication){
        for (Subscriber key : subscriptions.keySet()) {
            for(Subscription subscription: subscriptions.get(key)){
                if (matches(subscription, publication)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void notifySubscribers(Subscriber subscriber, Subscription subscription, Publication publication) {
        subscriber.processResult(subscription, publication);
    }

    public Map<Subscriber, List<Subscription>> getSubscriptions() {
        return subscriptions;
    }
}
