import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Publisher implements Runnable {
    private int publicationCount;

    public Publisher(int publicationCount) {
        this.publicationCount = publicationCount;
    }

    @Override
    public void run() {
        PublicationGenerator generator = new PublicationGenerator(publicationCount);
        generator.generatePublications();
    }
}
