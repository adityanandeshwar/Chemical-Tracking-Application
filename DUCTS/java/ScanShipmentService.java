import java.time.LocalDate;

public class ScanShipmentService {

    public boolean isAuthorized(int role) {
        return role == 0 || role == 1;
    }

    public String updateShipment(String prodId, String quantity, String location) {

        LocalDate today = LocalDate.now();
        String thisDate = today.toString();

        String info = "Date: " + thisDate +
                      ", Location: " + location +
                      ", Quantity: " + quantity;

        boolean success = addStateToBlockchain(prodId, info);

        if (success) {
            return "Item has been updated";
        } else {
            return "Update failed";
        }
    }

    private boolean addStateToBlockchain(String prodId, String info) {
        System.out.println("Sending to blockchain...");
        System.out.println("Product ID: " + prodId);
        System.out.println("Info: " + info);

        return true;
    }
}