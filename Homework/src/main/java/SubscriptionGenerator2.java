import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SubscriptionGenerator2 {
    private final String[] companies = { "Google", "Microsoft", "Apple", "Amazon", "Facebook", "Tesla", "Netflix", "Oracle",
            "IBM", "Intel", "Nvidia", "AMD", "Cisco", "Qualcomm", "Adobe", "Salesforce",
            "Zoom", "Slack", "Spotify", "Twitter"};
    private final String[] operators = {"=", "<", ">", "<=", ">="};

    private double companyFreq;
    private double valueFreq;
    private double dropFreq;
    private double variationFreq;
    private double dateFreq;
    private double equalOperatorFreq;
    private int subscriptionCount;

    private Subscription2[] subscriptions;


    public SubscriptionGenerator2(
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

        subscriptions = new Subscription2[subscriptionCount];
        this.equalOperatorFreq = equalOperatorFreq/10;

        for(int i = 0; i < subscriptionCount; i++){
            subscriptions[i] = new Subscription2();
        }
    }

    public void generateSubscriptions(){
        Random random = new Random();

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

        double dateEqualNotUsed = equalOperatorFreq;
        for(int i = 0; i < dateFreq; i++){
            int index = random.nextInt( subscriptionCount);
            if(subscriptions[index].getConditions().containsKey("date")){
                i--;
                continue;
            }

            if(dateEqualNotUsed > 0){
                subscriptions[index].addCondition("date", "=", String.valueOf(random.nextDouble()));
                dateEqualNotUsed--;
            } else {
                subscriptions[index].addCondition("date", operators[random.nextInt(operators.length)], String.valueOf(random.nextDouble()));
            }
        }

        System.out.println("Company subscriptions: ");
        for(int i = 0; i < subscriptionCount; i++){
            System.out.println(subscriptions[i]);
        }
    }
}
