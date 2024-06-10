package newImpl;

import java.util.ArrayList;
import java.util.List;

class EvaluationLogger {
    private static List<Long> deliveryTimes = new ArrayList<>();

    public static void logDelivery(long time) {
        deliveryTimes.add(time);
    }

    public static void printStatistics() {
        if (deliveryTimes.isEmpty()) {
            System.out.println("No publications were delivered.");
            return;
        }

        long sum = 0;
        for (long time : deliveryTimes) {
            sum += time;
        }

        double averageLatency = sum / (double) deliveryTimes.size();

        System.out.println("Total publications delivered: " + deliveryTimes.size());
        System.out.println("Average delivery latency: " + averageLatency + " ns");
    }

    public static void clear() {
        deliveryTimes.clear();
    }
}
