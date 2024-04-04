public class Main {
    public static void main(String[] args) {
        var publisher = new Publisher(10000);
        publisher.run();
        System.out.println("================Without threads=====================");
        publisher.runWithoutThreads();
    }
}
