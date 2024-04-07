import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.json.*;

public class PublicationGenerator implements Runnable{
    private int numberOfMessages;
    private List<Publication> publications = new ArrayList<>();

    private final String[] companies = {
            "Google", "Microsoft", "Apple", "Amazon", "Facebook", "Tesla", "Netflix", "Oracle",
            "IBM", "Intel", "Nvidia", "AMD", "Cisco", "Qualcomm", "Adobe", "Salesforce",
            "Zoom", "Slack", "Spotify", "Twitter"
    };

    private final String[] dates = {
            "2.02.2022", "3.03.2023", "4.04.2024", "5.05.2025", "6.06.2026", "7.07.2027",
            "8.08.2028", "9.09.2029", "10.10.2030", "11.11.2031", "12.12.2032", "1.01.2033",
            "2.02.2034", "3.03.2035", "4.04.2036", "5.05.2037", "6.06.2038", "7.07.2039",
            "8.08.2040", "9.09.2041"
    };


    public PublicationGenerator(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public Publication generatePublication() {
        Random rand = new Random();

        String company = companies[rand.nextInt(companies.length)];
        double value = 50.0 + (100.0 - 50.0) * rand.nextDouble();
        double drop = 5.0 + (15.0 - 5.0) * rand.nextDouble();
        double variation = 0.5 + (1.0 - 0.5) * rand.nextDouble();
        String date = dates[rand.nextInt(dates.length)];

        return new Publication(company, value, drop, variation, date);
    }

    public void runWithoutThreads() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfMessages; i++) {
                Publication publication = generatePublication();
                    publications.add(publication);
        }
        try {
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("Time taken: " + elapsedTime + " milliseconds");
            writePublicationsToFile();

            long endTimeAfterWritingPublications = System.currentTimeMillis();
            long elapsedTimeAfterWritingPublications = endTimeAfterWritingPublications - startTime;
            System.out.println("Time taken after writing publications: " + elapsedTimeAfterWritingPublications + " milliseconds");
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(numberOfMessages/200);
        for (int i = 0; i < numberOfMessages; i++) {
            executor.submit(() -> {
                Publication publication = generatePublication();
                synchronized (publications) {
                    publications.add(publication);
                }
            });

        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println("Time taken: " + elapsedTime + " milliseconds");
            writePublicationsToFile();

            long endTimeAfterWritingPublications = System.currentTimeMillis();
            long elapsedTimeAfterWritingPublications = endTimeAfterWritingPublications - startTime;
            System.out.println("Time taken after writing publications: " + elapsedTimeAfterWritingPublications + " milliseconds");
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void writePublicationsToFile() throws IOException {
        ArrayList<String> results = new ArrayList<String>();

        synchronized (publications) {
            for (Publication publication : publications) {
                results.add(publication.toString());
            }
        }
        try (FileWriter file = new FileWriter("publications.txt")) {
            for(String result : results){
                file.write(result);
            }
        }
    }
}
