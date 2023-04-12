/**
 * @author <Kang Junsik - s3916884>
 */

public class Physical extends Product implements Gift {
    private double weight;
    private String message;

    public Physical(String name, String description, int quantity, double price, double weight) {
        super(name, description, quantity, price);
        this.weight = weight;
        this.message = "";
    }

    @Override
    public String printOut() {
        return "PHYSICAL - <" + super.getName() + ">";
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public void setMessage(String msg) {
        this.message = msg;
    }

    @Override
    public String getMessage() {
        if(message == null) {
            return "There is no message on this gift.";
        }
        return message;
    }
}
