import java.time.format.DateTimeFormatter;
import java.util.*;

public class Subscription {
    private final Map<String, Condition> conditions = new TreeMap<>();

    public void addCondition(String field, String operator, String value) {
        conditions.put(field, new Condition(operator, value));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<String, Condition> entry : conditions.entrySet()) {
            sb.append("(");
            sb.append(entry.getKey());
            sb.append(",");
            sb.append(entry.getValue().getOperator());
            sb.append(",");
            sb.append(entry.getValue().getValue());
            sb.append("); ");

        }
        sb.append("}\n");
        return sb.toString();
    }

    static class Condition {
        private final String operator;
        private final String value;

        public Condition(String operator, String value) {
            this.operator = operator;
            this.value = value;
        }

        public String getOperator() {
            return operator;
        }

        public String getValue() {
            return value;
        }
    }

    public Map<String, Condition> getConditions() {
        return conditions;
    }
}