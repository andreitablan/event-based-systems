import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SubscriptionGenerator implements Runnable{
    private final String[] companies = { "Google", "Microsoft", "Apple", "Amazon", "Facebook", "Tesla", "Netflix", "Oracle",
            "IBM", "Intel", "Nvidia", "AMD", "Cisco", "Qualcomm", "Adobe", "Salesforce",
            "Zoom", "Slack", "Spotify", "Twitter"};
    private final String[] dates = {
            "2.02.2022", "3.03.2023", "4.04.2024", "5.05.2025", "6.06.2026", "7.07.2027",
            "8.08.2028", "9.09.2029", "10.10.2030", "11.11.2031", "12.12.2032", "1.01.2033",
            "2.02.2034", "3.03.2035", "4.04.2036", "5.05.2037", "6.06.2038", "7.07.2039",
            "8.08.2040", "9.09.2041"
    };
    private final String[] operators = {"=", "<", ">", "<=", ">="};

    private double companyFreq;
    private double valueFreq;
    private double dropFreq;
    private double variationFreq;
    private double dateFreq;
    private double equalOperatorFreq;
    private int subscriptionCount;

    private Subscription[] subscriptions;


    public SubscriptionGenerator(
            double companyFreq,
            double valueFreq,
            double dropFreq,
            double variationFreq,
            double dateFreq,
            int subscriptionCount,
            double equalOperatorFreq
    ) {
        this.companyFreq = companyFreq/10;
        this.valueFreq = valueFreq/10;
        this.dropFreq = dropFreq/10;
        this.variationFreq = variationFreq/10;
        this.dateFreq = dateFreq/10;
        this.subscriptionCount = subscriptionCount;

        subscriptions = new Subscription[subscriptionCount];
        this.equalOperatorFreq = equalOperatorFreq/10;

        for(int i = 0; i < subscriptionCount; i++){
            subscriptions[i] = new Subscription();
        }
    }

    public void generateSubscriptions() throws IOException {
        long startTime = System.currentTimeMillis();

        Random random = new Random();
        generateCompanyFields(random);
        generateValueFields(random);
        generateDropFields(random);
        generateVariationFields(random);
        generateDateFields(random);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Time taken: " + elapsedTime + " milliseconds");
        writeSubscriptionsToFile();

        long endTimeAfterWritingSubscriptions = System.currentTimeMillis();
        long elapsedTimeAfterWritingSubscriptions = endTimeAfterWritingSubscriptions - startTime;
        System.out.println("Time taken after writing subscriptions: " + elapsedTimeAfterWritingSubscriptions + " milliseconds");
    }

    private void generateDateFields(Random random) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        double dateEqualNotUsed = equalOperatorFreq;
        for(int i = 0; i < dateFreq; i++){
            int index = random.nextInt( subscriptionCount);
            if(subscriptions[index].getConditions().containsKey("date")){
                i--;
                continue;
            }

            if(dateEqualNotUsed > 0){
                Date newDate = new Date();
                subscriptions[index].addCondition("date", "=", dates[random.nextInt(dates.length)]);
                dateEqualNotUsed--;
            } else {
                subscriptions[index].addCondition("date", operators[random.nextInt(operators.length)], String.valueOf(random.nextDouble()));
            }
        }
    }

    private void generateVariationFields(Random random) {
        double variationEqualNotUsed = equalOperatorFreq;
        for(int i = 0; i < variationFreq; i++){
            int index = random.nextInt( subscriptionCount);
            if(subscriptions[index].getConditions().containsKey("variation")){
                i--;
                continue;
            }

            if(variationEqualNotUsed > 0){
                subscriptions[index].addCondition("variation", "=", String.valueOf(random.nextDouble()));
                variationEqualNotUsed--;
            } else {
                subscriptions[index].addCondition("variation", operators[random.nextInt(operators.length)], String.valueOf(random.nextDouble()));
            }
        }
    }

    private void generateDropFields(Random random) {
        double dropEqualNotUsed = equalOperatorFreq;
        for(int i = 0; i < dropFreq; i++){
            int index = random.nextInt( subscriptionCount);
            if(subscriptions[index].getConditions().containsKey("drop")){
                i--;
                continue;
            }

            if(dropEqualNotUsed > 0){
                subscriptions[index].addCondition("drop", "=", String.valueOf(random.nextDouble()));
                dropEqualNotUsed--;
            } else {
                subscriptions[index].addCondition("drop", operators[random.nextInt(operators.length)], String.valueOf(random.nextDouble()));
            }
        }
    }

    private void generateValueFields(Random random) {
        double valueEqualNotUsed = equalOperatorFreq;
        for(int i = 0; i < valueFreq; i++){
            int index = random.nextInt( subscriptionCount);
            if(subscriptions[index].getConditions().containsKey("value")){
                i--;
                continue;
            }

            if(valueEqualNotUsed > 0){
                subscriptions[index].addCondition("value", "=", String.valueOf(random.nextDouble()));
                valueEqualNotUsed--;
            } else {
                subscriptions[index].addCondition("value", operators[random.nextInt(operators.length)], String.valueOf(random.nextDouble()));
            }
        }
    }

    private void generateCompanyFields(Random random) {
        double companyEqualNotUsed = equalOperatorFreq;
        for(int i = 0; i < companyFreq; i++) {
            int index = random.nextInt( subscriptionCount);
            if(subscriptions[index].getConditions().containsKey("company")){
                i--;
                continue;
            }
            if(companyEqualNotUsed > 0){
                subscriptions[index].addCondition("company", "=", companies[random.nextInt(companies.length)]);
                companyEqualNotUsed--;
            } else {
                subscriptions[index].addCondition("company", operators[random.nextInt(operators.length)], companies[random.nextInt(companies.length)]);
            }
        }
    }


    private void writeSubscriptionsToFile(){

        try (FileWriter file = new FileWriter("subscriptions.txt")) {
        for (Subscription subscription : subscriptions) {
                file.write(subscription.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Random random = new Random();
        executor.submit(() -> {
            generateCompanyFields(random);
        });
        executor.submit(() -> {
            generateValueFields(random);
        });
        executor.submit(() -> {
            generateDropFields(random);
        });
        executor.submit(() -> {
            generateVariationFields(random);
        });
        executor.submit(() -> {
            generateDateFields(random);
        });
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("Time taken: " + elapsedTime + " milliseconds");
            writeSubscriptionsToFile();

            long endTimeAfterWritingSubscriptions = System.currentTimeMillis();
            long elapsedTimeAfterWritingSubscriptions = endTimeAfterWritingSubscriptions - startTime;
            System.out.println("Time taken after writing subscriptions: " + elapsedTimeAfterWritingSubscriptions + " milliseconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
