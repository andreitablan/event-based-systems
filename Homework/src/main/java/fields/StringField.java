package fields;

public class StringField extends Field {
    private String value;

    public StringField(String type, String operator, String value) {
        super(type, operator);
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", '" + value + '\'' + ']';
    }
}
