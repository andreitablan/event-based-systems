import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {
        /**
         * The user is asked to enter the number of publications and subscriptions to generate.
         */
        int publicationCount = getInput("Enter the number of publications: ");
        int subscriptionCount = getInput("Enter the number of subscriptions: ");
        double companyFrequency = getInput("Enter company frequency: ");
        double valueFrequency = getInput("Enter value frequency: ");
        double dropFrequency = getInput("Enter drop frequency: ");
        double variationFrequency = getInput("Enter variation frequency: ");
        double dateFrequency = getInput("Enter date frequency: ");
        double equalOperatorFrequency = getInput("Enter the equal operator frequency: ");

        int option;
        do {
            TimeUnit.SECONDS.sleep(1);
            option = getInput("0 to exit\n1 to generate publications without threads\n2 to generate publications with threads\n3 to generate subscriptions without threads\n4 to generate subscriptions with threads\nEnter a number: ");
            executeOption(option, publicationCount, subscriptionCount, companyFrequency, valueFrequency, dropFrequency, variationFrequency, dateFrequency, equalOperatorFrequency);
        } while (option != 0);
    }

    private static int getInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextInt();
    }

    /**
     * This method executes the option selected by the user.
     * @param option
     * @param publicationCount
     * @param subscriptionCount
     * @param companyFrequency
     * @param valueFrequency
     * @param dropFrequency
     * @param variationFrequency
     * @param dateFrequency
     * @param equalOperatorFrequency
     * @throws IOException
     */
    private static void executeOption(int option, int publicationCount, int subscriptionCount, double companyFrequency, double valueFrequency, double dropFrequency, double variationFrequency, double dateFrequency, double equalOperatorFrequency) throws IOException {
        PublicationGenerator publicationGenerator = new PublicationGenerator(publicationCount);
        SubscriptionGenerator subscriptionGenerator = new SubscriptionGenerator(companyFrequency, valueFrequency, dropFrequency, variationFrequency, dateFrequency, subscriptionCount, equalOperatorFrequency);

        switch(option){
            case 0:
                System.out.println("Exiting...");
                break;
            case 1:
                System.out.println("Generating publications without threads");
                publicationGenerator.generatePublications();
                break;
            case 2:
                System.out.println("Generating publications with threads");
                publicationGenerator.generatePublicationsWithThreads();
                break;
            case 3:
                System.out.println("Generating subscriptions without threads");
                subscriptionGenerator.generateSubscriptions();
                break;
            case 4:
                System.out.println("Generating subscriptions with threads");
                subscriptionGenerator.run();
                break;
            default:
                System.out.println("Wrong input, options are: 0 - exit, 1,2,3,4 the others");
        }
    }
}