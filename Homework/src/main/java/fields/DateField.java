package fields;

import java.util.Date;

public class DateField extends Field {
    private Date value;

    public DateField(String type, String operator, Date value) {
        super(type, operator);
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", " + value + ']';
    }
}
