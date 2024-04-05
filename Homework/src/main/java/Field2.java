public class Field2 {
    private String type;
    private String operator;
    private String value;

    public Field2(String type, String operator, String value) {
        this.type = type;
        this.operator = operator;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
