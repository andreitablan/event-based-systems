package newImpl;

public class Publication {
    private String company;
    private double value;
    private double drop;
    private double variation;
    private String date;

    public Publication(String company, double value, double drop, double variation, String date) {
        this.company = company;
        this.value = value;
        this.drop = drop;
        this.variation = variation;
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public double getValue() {
        return value;
    }

    public double getDrop() {
        return drop;
    }

    public double getVariation() {
        return variation;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("{(company,\"%s\");(value,%.2f);(drop,%.2f);(variation,%.2f);(date,%s)}\n",
                company, value, drop, variation, date);
    }
}
