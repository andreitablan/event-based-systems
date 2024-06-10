package newImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PublicationGenerator {
    private static final String[] COMPANIES = {"Google", "Apple", "Amazon"};
    private static final double VALUE_MIN = 50.0;
    private static final double VALUE_MAX = 150.0;
    private static final double DROP_MIN = 5.0;
    private static final double DROP_MAX = 15.0;
    private static final double VARIATION_MIN = 0.5;
    private static final double VARIATION_MAX = 1.0;
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    public static Publication generateRandomPublication() {
        Random random = new Random();
        String company = COMPANIES[random.nextInt(COMPANIES.length)];
        double value = VALUE_MIN + (VALUE_MAX - VALUE_MIN) * random.nextDouble();
        double drop = DROP_MIN + (DROP_MAX - DROP_MIN) * random.nextDouble();
        double variation = VARIATION_MIN + (VARIATION_MAX - VARIATION_MIN) * random.nextDouble();
        String date = new SimpleDateFormat(DATE_FORMAT).format(new Date());

        return new Publication(company, value, drop, variation, date);
    }

    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("publications.txt", true);  // Append to file
        for (int i = 0; i < 100; i++) {  // Generate 100 publications
            Publication pub = generateRandomPublication();
            writer.write(pub.toString());
        }
        writer.close();
    }
}
