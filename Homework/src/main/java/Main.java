import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        int publicationCount = 10;
        int subscriptionCount = 10;
        double companyFrequency = 20;
        double valueFrequency = 20;
        double dropFrequency = 20;
        double variationFrequency = 20;
        double dateFrequency = 30;
        double equalOperatorFrequency = 70;

        LoadBalancer loadBalancer = LoadBalancer.getInstance();

        loadBalancer.addBroker(new Broker());
        loadBalancer.addBroker(new Broker());
        loadBalancer.addBroker(new Broker());

        // Create subscribers
        Subscriber subscriber2 = new Subscriber( subscriptionCount, companyFrequency, valueFrequency, dropFrequency, variationFrequency, dateFrequency, equalOperatorFrequency);
        Subscriber subscriber1 = new Subscriber( subscriptionCount, companyFrequency, valueFrequency, dropFrequency, variationFrequency, dateFrequency, equalOperatorFrequency);
        Subscriber subscriber3 = new Subscriber( subscriptionCount, companyFrequency, valueFrequency, dropFrequency, variationFrequency, dateFrequency, equalOperatorFrequency);

        // Create publishers
        Publisher publisher1 = new Publisher(publicationCount);
        Publisher publisher2 = new Publisher(publicationCount);

        ExecutorService executor = Executors.newFixedThreadPool(6);
        executor.submit(subscriber1);
        executor.submit(subscriber2);
        executor.submit(subscriber3);
        executor.submit(publisher1);
        executor.submit(publisher2);

        executor.shutdown();

        executor.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Simulation complete.");
    }
}
    //PublicationGenerator publicationGenerator = new PublicationGenerator(publicationCount);
    //SubscriptionGenerator subscriptionGenerator = new SubscriptionGenerator(companyFrequency, valueFrequency, dropFrequency, variationFrequency, dateFrequency, subscriptionCount, equalOperatorFrequency);