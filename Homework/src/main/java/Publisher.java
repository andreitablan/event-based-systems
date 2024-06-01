import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Publisher implements Runnable {
    private int publicationCount;
    private LoadBalancer loadBalancer;

    public Publisher( int publicationCount)
    {
        this.loadBalancer = LoadBalancer.getInstance();
        this.publicationCount = publicationCount;
    }

    @Override
    public void run() {
        PublicationGenerator generator = new PublicationGenerator(publicationCount);

        for(int i = 0; i < publicationCount; i++){
            Publication publication = generator.generatePublication();
            loadBalancer.sendPublication(publication);
        }
    }
}
