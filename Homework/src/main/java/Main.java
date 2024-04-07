import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, IOException {
        Scanner scanner = new Scanner(System.in);
        int number;

        do {
            System.out.print("0 to exit\n");
            System.out.print("1 to generate publications without threads\n");
            System.out.print("2 to generate publications with threads\n");
            System.out.print("3 to generate subscriptions without threads\n");
            System.out.print("4 to generate subscriptions with threads\n");
            System.out.println("Enter a number: ");

            number = scanner.nextInt();
            PublicationGenerator publicationGenerator = new PublicationGenerator(1000000);
            SubscriptionGenerator subscriptionGenerator = new SubscriptionGenerator(90.0, 60.0, 10.0, 70.0, 50.0, 1000000, 70.0);

            switch(number){
                case 0:
                    System.out.println("Exiting...");
                    break;
                case 1:
                    System.out.println("Generating publications without threads");
                    publicationGenerator.runWithoutThreads();
                    break;
                case 2:
                    System.out.println("Generating publications with threads");
                    publicationGenerator.run();
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
        } while (number != 0);
    }
}
