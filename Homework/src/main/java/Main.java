
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        

//        PublicationGenerator publisher = new PublicationGenerator(10000);
//        publisher.run();
//        System.out.println("================Without threads=====================");
//        publisher.runWithoutThreads();
        
        /*SubscriptionGenerator subscriptionGenerator = new SubscriptionGenerator(10, new double[]{90.0, 80.0, 70.0, 60.0, 50.0});
        subscriptionGenerator.generateSubs();
        subscriptionGenerator.printSubs();*/

        SubscriptionGenerator2 subscriptionGenerator2 = new SubscriptionGenerator2(100.0, 100.0, 100.0, 100.0, 100.0, 10, 70.0);
        subscriptionGenerator2.generateSubscriptions();
    }
}
