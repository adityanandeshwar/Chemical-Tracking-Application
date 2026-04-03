import java.time.LocalDate;

public class ScanShipmentService {

    public boolean isAuthorized(int role) {
        return role == 0 || role == 1;
    }

    public String updateShipment(String prodId, String quantity, String location) {

    try {
        if (!ValidationUtil.isNotEmpty(prodId)) {
            return "Product ID required";
        }

        String date = java.time.LocalDate.now().toString();

        String info = "Date: " + date +
                ", Location: " + location +
                ", Quantity: " + quantity;

        contract.addState(prodId, info).send();

        return "Item updated";

    } catch (Exception e) {
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