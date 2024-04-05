package fields;

public class DoubleField extends Field {
    private double value;

    public DoubleField(String type, String operator, double value) {
        super(type, operator);
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", " + value + ']';
    }
}
