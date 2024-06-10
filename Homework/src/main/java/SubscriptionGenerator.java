import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
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

    private UUID id;


    public SubscriptionGenerator(
            double companyFreq,
            double valueFreq,
            double dropFreq,
            double variationFreq,
            double dateFreq,
            int subscriptionCount,
            double equalOperatorFreq
    ) {
        this.companyFreq = companyFreq/100 * subscriptionCount;
        this.valueFreq = valueFreq/100 * subscriptionCount;
        this.dropFreq = dropFreq/100 * subscriptionCount;
        this.variationFreq = variationFreq/100 * subscriptionCount;
        this.dateFreq = dateFreq/100 * subscriptionCount;
        this.subscriptionCount = subscriptionCount;

        subscriptions = new Subscription[subscriptionCount];
        this.equalOperatorFreq = equalOperatorFreq/100;
        this.id = UUID.randomUUID();
        for(int i = 0; i < subscriptionCount; i++){
            subscriptions[i] = new Subscription();
            subscriptions[i].setSubscriberId(this.id);
        }
    }

    private int getEmptySubscriptionIndex(Random random){
        int j=0;
        boolean isNotEmpty = true;
        while(j<subscriptionCount && isNotEmpty == true){
            if(subscriptions[j].isEmpty()){
                isNotEmpty = false;
                break;
            }
            j++;
        }
        int index;
        if(!isNotEmpty)
        {
            index = j;
        }
        else {
            index = random.nextInt(subscriptionCount);
        }
        return index;
    }
    /**
     * Generates subscriptions and writes them to a file
     * @throws IOException
     */
    public Subscription[] generateSubscriptions() throws IOException {
        Random random = new Random();
        generateCompanyFields(random);
        generateValueFields(random);
        generateDropFields(random);
        generateVariationFields(random);
        generateDateFields(random);

        return subscriptions;
    }

    private void generateDateFields(Random random) {
        double dateEqualNotUsed = equalOperatorFreq * dateFreq;
        for(int i = 0; i < dateFreq; i++){
            int index = getEmptySubscriptionIndex(random);
            if(subscriptions[index].getConditions().containsKey("date")){
                i--;
                continue;
            }

            if(dateEqualNotUsed > 0){
                subscriptions[index].addCondition("date", "=", dates[random.nextInt(dates.length)]);
                dateEqualNotUsed--;
            } else {
                subscriptions[index].addCondition("date", operators[random.nextInt(operators.length)], String.valueOf(random.nextDouble()));
            }
        }
    }

    private void generateVariationFields(Random random) {
        double variationEqualNotUsed = equalOperatorFreq * variationFreq;
        for(int i = 0; i < variationFreq; i++){
            int index = getEmptySubscriptionIndex(random);
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
        double dropEqualNotUsed = equalOperatorFreq * dropFreq;
        for(int i = 0; i < dropFreq; i++){
            int index = getEmptySubscriptionIndex(random);
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
        double valueEqualNotUsed = equalOperatorFreq * valueFreq;
        for(int i = 0; i < valueFreq; i++){
            int index = getEmptySubscriptionIndex(random);
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
        double companyEqualNotUsed = equalOperatorFreq * companyFreq;
        for(int i = 0; i < companyFreq; i++) {
            int index = getEmptySubscriptionIndex(random);
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

    /**
     * Generates subscriptions and writes them to a file using multiple threads
     */
    @Override
    public void run() {
        ExecutorService executor;
        if (subscriptionCount >= 1000) {
            executor = Executors.newFixedThreadPool(subscriptionCount / 100);
        } else {
            executor = Executors.newFixedThreadPool(4);
        }
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
    }
}