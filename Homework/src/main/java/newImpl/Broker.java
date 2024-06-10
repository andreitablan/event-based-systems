package newImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Broker {
    private int id;
    private List<Subscription> subscriptions;
    private List<Broker> nextBrokers;

    public Broker(int id) {
        this.id = id;
        this.subscriptions = new ArrayList<>();
        this.nextBrokers = new ArrayList<>();
    }

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public void routePublication(Publication publication, Set<Integer> visitedBrokers) {
        // Add the current broker to the set of visited brokers
        visitedBrokers.add(this.id);

        // Check subscriptions and notify subscribers
        for (Subscription sub : subscriptions) {
            if (matches(sub, publication)) {
                System.out.println("Broker " + id + " matches subscription: " + sub + " with publication: " + publication);
            }
        }

        // Forward publication to next brokers if they haven't already seen it
        for (Broker broker : nextBrokers) {
            if (!visitedBrokers.contains(broker.getId())) {
                broker.routePublication(publication, visitedBrokers);
            }
        }
    }

    private boolean matches(Subscription sub, Publication pub) {
        if (sub.getCompany() != null && !sub.getCompany().equals(pub.getCompany())) {
            return false;
        }
        if (sub.getValue() != null && !compare(pub.getValue(), sub.getValueOp(), sub.getValue())) {
            return false;
        }
        if (sub.getVariation() != null && !compare(pub.getVariation(), sub.getVariationOp(), sub.getVariation())) {
            return false;
        }
        return true;
    }

    private boolean compare(double fieldValue, String operator, double subscriptionValue) {
        switch (operator) {
            case "=":
                return fieldValue == subscriptionValue;
            case ">=":
                return fieldValue >= subscriptionValue;
            case "<=":
                return fieldValue <= subscriptionValue;
            default:
                return false;
        }
    }

    public void addNextBroker(Broker broker) {
        nextBrokers.add(broker);
    }

    public int getId() {
        return id;
    }
}
