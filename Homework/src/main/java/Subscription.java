import fields.Field;

import java.util.ArrayList;
import java.util.List;

public class Subscription {
    private List<Field> fields = new ArrayList<>();

    public void addField (Field field) {
        fields.add(field);
    }

    public boolean containsFieldWithType(String type) {
        for (Field f : fields) {
            if (f.getType().equals(type))
                return true;
        }
        return false;
    }

    public boolean fieldsEmpty() {
        return fields.isEmpty();
    }

    @Override
    public String toString() {
        return fields + "\n";
    }
}
