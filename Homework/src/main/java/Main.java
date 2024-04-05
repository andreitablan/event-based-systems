public class Main {
    public static void main(String[] args) {
        /**

        var publisher = new Publisher(10000);
        publisher.run();
        System.out.println("================Without threads=====================");
        publisher.runWithoutThreads();
         *
         */
        var subscriptionGenerator = new SubscriptionGenerator(10, new double[]{90.0, 80.0, 70.0, 60.0, 50.0});
        subscriptionGenerator.generateSubs();
        subscriptionGenerator.printSubs();
    }
}
