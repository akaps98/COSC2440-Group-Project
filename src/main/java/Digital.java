/**
 * @author <Kang Junsik - s3916884>
 */

public class Digital extends Product implements Gift {
    private String message;

    public Digital(String name, String description, int quantity, double price) {
        super(name, description, quantity, price);
        this.message = "";
    }

    @Override
    public String printOut() {
        return "DIGITAL - <" + super.getName() + ">";
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
