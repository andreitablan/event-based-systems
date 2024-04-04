import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.json.*;

public class Publisher implements Runnable{
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


    public Publisher(int numberOfMessages) {
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
        ExecutorService executor = Executors.newFixedThreadPool(2);
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
        JSONArray jsonArray = new JSONArray();
        synchronized (publications) {
            for (Publication publication : publications) {
                JSONObject json = new JSONObject();
                json.put("company", publication.getCompany());
                json.put("value", publication.getValue());
                json.put("drop", publication.getDrop());
                json.put("variation", publication.getVariation());
                json.put("date", publication.getDate());
                jsonArray.put(json);
            }
        }
        try (FileWriter file = new FileWriter("publications.json")) {
            file.write(jsonArray.toString(2)); // Indented JSON for better readability
        }
    }
}
