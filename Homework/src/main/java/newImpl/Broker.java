package newImpl;

import java.util.ArrayList;
import java.util.List;

class Broker {
    private int id;
    private List<Subscription> subscriptions = new ArrayList<>();

    public Broker(int id) {
        this.id = id;
    }

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public void routePublication(Publication publication) {
        // Check subscriptions and notify subscribers
        for (Subscription sub : subscriptions) {
            if (matches(sub, publication)) {
                System.out.println("Broker " + id + " matches subscription: " + sub + " with publication: " + publication);
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
}
