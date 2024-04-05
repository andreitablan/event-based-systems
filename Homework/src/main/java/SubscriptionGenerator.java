import fields.DateField;
import fields.DoubleField;
import fields.Field;
import fields.StringField;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SubscriptionGenerator {
    private final String[] TYPES = {"company", "value", "drop", "variation", "date"};
    private final String[] OPERATORS = {"=", "<", ">", "<=", ">="};
    
    private double[] fieldFreq;
    private int[] fieldCount;
    private int subscriptionCount;
    private List<Subscription> subscriptions;
    private List<Field> fieldsPool;
    
    public SubscriptionGenerator(int subscriptionCount, double[] fieldFrequencies) {
        fieldFreq = fieldFrequencies;
        this.subscriptionCount = subscriptionCount;
        subscriptions = new ArrayList<>();
        fieldsPool = new ArrayList<>();
        fieldCount= new int[5];
        calculateFieldCount();
    }
    
    public void setFieldFreq(double[] values) {
        fieldFreq = values;
    }

    public void generateSubs() {
        for (int i = 0; i < subscriptionCount; i++) {
            subscriptions.add(new Subscription());
        }

        int totalFields = 0;
        for (int j : fieldCount) totalFields += j;

        // genereaza fieldurile
        for (int i = 0; i < totalFields; i++) {
            String fieldType = findValidType();
            Field newField;

            assert fieldType != null;
            if (fieldType.equals("company"))
                newField = new StringField(fieldType, "<", "Google");
            else if (fieldType.equals("date"))
                newField = new DateField(fieldType, "<", new Date());
            else
                newField = new DoubleField(fieldType, "<", new Random().nextDouble());

            fieldsPool.add(newField);
            //subscriptions.get(i % subscriptionCount).addField(newField);
        }

        Random rand = new Random();
        int pos;

        // asigneaza random fiecare field la o subscriptie
        for (Field f : fieldsPool) {
            // adauga fielduri la subscriptiile goale intai
             pos = findEmptySubPos();

            if (pos != -1)
                subscriptions.get(pos).addField(f);
            else {
                pos = rand.nextInt(0, subscriptionCount);

                while (subscriptions.get(pos).containsFieldWithType(f.getType())) {
                    pos = rand.nextInt(0, subscriptionCount);
                }
                subscriptions.get(pos).addField(f);
            }
        }
    }

    public void printSubs() {
        System.out.println(subscriptions);
    }

    private void calculateFieldCount() {
        for (int i = 0; i < fieldFreq.length; i++)
            fieldCount[i] = (int) (fieldFreq[i] * subscriptionCount / 100);
    }

    private String findValidType() {
        for (int i = 0; i < fieldCount.length; i++)
            if (fieldCount[i] > 0) {
                fieldCount[i]--;
                return TYPES[i];
            }
        return null;
    }

    private int findEmptySubPos() {
        for (int i = 0; i < subscriptionCount; i++)
            if (subscriptions.get(i).fieldsEmpty())
                return i;
        return -1;
    }
}
// Partea cu threadurile, operatorul, si scrierea in fisier