import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class Subscriber implements Runnable {
    private LoadBalancer loadBalancer;
    private int subscriptionCount;
    private double companyFreq, valueFreq, dropFreq, variationFreq, dateFreq, equalOperatorFreq;
    public Subscriber( int subscriptionCount, double companyFreq, double valueFreq, double dropFreq,
                      double variationFreq, double dateFreq, double equalOperatorFreq) {

        this.loadBalancer = LoadBalancer.getInstance();
        this.subscriptionCount = subscriptionCount;
        this.companyFreq = companyFreq;
        this.valueFreq = valueFreq;
        this.dropFreq = dropFreq;
        this.variationFreq = variationFreq;
        this.dateFreq = dateFreq;
        this.equalOperatorFreq = equalOperatorFreq;
    }

    @Override
    public void run() {
        SubscriptionGenerator generator = new SubscriptionGenerator(companyFreq, valueFreq, dropFreq, variationFreq, dateFreq, subscriptionCount, equalOperatorFreq);
        try {
            generator.generateSubscriptions();
            for (Subscription subscription : generator.generateSubscriptions()) {
                loadBalancer.sendSubscription(this, subscription);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processResult(Subscription subscription, Publication publication){
        System.out.println("Match between subscription and publication");
        System.out.println("Subscription: " + subscription.toString());
        System.out.println("Publication: " + publication.toString());
        System.out.println("------------------------------------------");
    }
}
