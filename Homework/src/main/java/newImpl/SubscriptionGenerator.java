package newImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SubscriptionGenerator {
    private static final String[] COMPANIES = {"Google", "Apple", "Amazon"};
    private static final double VALUE_MIN = 50.0;
    private static final double VALUE_MAX = 150.0;
    private static final double VARIATION_MIN = 0.5;
    private static final double VARIATION_MAX = 1.0;

    public static Subscription generateRandomSubscription(double companyFrequency, double valueFrequency, double variationFrequency, double equalityFrequency) {
        Random random = new Random();
        String company = random.nextDouble() < companyFrequency ? COMPANIES[random.nextInt(COMPANIES.length)] : null;
        String valueOp = random.nextDouble() < equalityFrequency ? "=" : (random.nextBoolean() ? ">=" : "<=");
        Double value = random.nextDouble() < valueFrequency ? VALUE_MIN + (VALUE_MAX - VALUE_MIN) * random.nextDouble() : null;
        String variationOp = random.nextBoolean() ? "<" : ">=";
        Double variation = random.nextDouble() < variationFrequency ? VARIATION_MIN + (VARIATION_MAX - VARIATION_MIN) * random.nextDouble() : null;

        return new Subscription(company, valueOp, value, variationOp, variation);
    }

    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("subscriptions.txt", true);  // Append to file
        for (int i = 0; i < 100; i++) {  // Generate 100 subscriptions
            Subscription sub = generateRandomSubscription(0.9, 0.9, 0.9, 0.7);
            writer.write(sub.toString());
        }
        writer.close();
    }
}
