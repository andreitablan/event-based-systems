public class Publication {
    private String company;
    private double value;
    private double drop;
    private double variation;
    private String date;
    private long timestamp;  // Added to record the time when the publication was created.


    public Publication(String company, double value, double drop, double variation, String date) {
        this.company = company;
        this.value = value;
        this.drop = drop;
        this.variation = variation;
        this.date = date;
        this.timestamp = System.nanoTime();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getDrop() {
        return drop;
    }

    public void setDrop(double drop) {
        this.drop = drop;
    }

    public double getVariation() {
        return variation;
    }

    public void setVariation(double variation) {
        this.variation = variation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("{(company,\"%s\");(value,%.2f);(drop,%.2f);(variation,%.2f);(date,%s)}\n",
                company,
                value,
                drop,
                variation,
                date
        );
    }

    public long getTimestamp() {
        return timestamp;
    }
}