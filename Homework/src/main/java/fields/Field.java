package fields;

public class Field {
    private String type;
    private String operator;

    public Field (String type, String operator) {
        this.type = type;
        this.operator = operator;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "['" + type + '\'' +
                ", '" + operator + '\'';
    }
}
