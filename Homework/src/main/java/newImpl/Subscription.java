package newImpl;

public class Subscription {
    private String company;
    private String valueOp;
    private Double value;
    private String variationOp;
    private Double variation;

    public Subscription(String company, String valueOp, Double value, String variationOp, Double variation) {
        this.company = company;
        this.valueOp = valueOp;
        this.value = value;
        this.variationOp = variationOp;
        this.variation = variation;
    }

    public String getCompany() {
        return company;
    }

    public String getValueOp() {
        return valueOp;
    }

    public Double getValue() {
        return value;
    }

    public String getVariationOp() {
        return variationOp;
    }

    public Double getVariation() {
        return variation;
    }

    @Override
    public String toString() {
        return String.format("{(company,=,\"%s\");(value,%s,%.2f);(variation,%s,%.2f)}\n",
                company != null ? company : "any",
                valueOp != null ? valueOp : "any",
                value != null ? value : 0.0,
                variationOp != null ? variationOp : "any",
                variation != null ? variation : 0.0
        );
    }
}
