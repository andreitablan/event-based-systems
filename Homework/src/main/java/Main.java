
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("1 to generate publications without threads\n");
        System.out.print("2 to generate publications with threads\n");
        System.out.print("3 to generate subscriptions without threads\n");
        System.out.print("4 to generate subscriptions with threads\n");
        System.out.println("Enter a number: ");

        int number = scanner.nextInt();
        PublicationGenerator publications = new PublicationGenerator(10000);
        SubscriptionGenerator subscriptionGenerator = new SubscriptionGenerator(90.0, 60.0, 10.0, 70.0, 50.0, 10, 70.0);

        switch(number){
            case 1:
                System.out.println("Generating publications without threads");
                publications.runWithoutThreads();
                break;
            case 2:
                System.out.println("Generating publications with threads");
                publications.run();
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
                System.out.println("Invalid input");
        }
    }
}
