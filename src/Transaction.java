public class Transaction {
    private final String id;
    private final String type;
    private final double amount;

    Transaction(String id, String type, double amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return id + "," + type + "," + amount;
    }

    public static Transaction fromString(String line) {
        String[] parts = line.split(",");
        return new Transaction(parts[0], parts[1], Double.parseDouble(parts[2]));
    }
}
