import java.util.concurrent.*;
import java.util.*;

public class Evaluation {
    public void evaluate(Broker[] brokers, int publicationCount, int durationMinutes) {
        long startTime = System.nanoTime();
        long endTime = startTime + TimeUnit.MINUTES.toNanos(durationMinutes);

        int totalDeliveredPublications = 0;
        long totalLatency = 0;
        int totalLatenciesCount = 0;

        // Wait for the specified duration
        while (System.nanoTime() < endTime) {
            // This loop runs for the duration of the test.
        }

        // Collect and evaluate statistics after the duration
        for (Broker broker : brokers) {
            for (Map.Entry<Subscriber, List<Subscription>> entry : broker.getSubscriptions().entrySet()) {
                Subscriber subscriber = entry.getKey();
                totalDeliveredPublications += subscriber.getReceivedCount();
                for (long latency : subscriber.getLatencies()) {
                    totalLatency += latency;
                    totalLatenciesCount++;
                }
            }
        }

        // Calculate average latency
        double averageLatency = (totalLatenciesCount == 0) ? 0 : (double) totalLatency / totalLatenciesCount;

        // Print evaluation results
        System.out.println("Evaluation Results:");
        System.out.println("Total Publications Delivered: " + totalDeliveredPublications);
        System.out.println("Average Latency (ns): " + averageLatency);
        System.out.println("Total Publications Generated: " + publicationCount);

        // Matching rate calculations.
    }
    private int countMatchedPublications(LoadBalancer loadBalancer, int publicationCount) {
        int matchedCount = 0;
        PublicationGenerator generator = new PublicationGenerator(publicationCount);
        List<Publication> publications = generator.generatePublications();

        for (Publication publication : publications) {
            for (Broker broker : loadBalancer.getBrokers()) {
                if (broker.matchPublication(publication)) {
                    matchedCount++;
                }
            }
        }
        return matchedCount;
    }
}