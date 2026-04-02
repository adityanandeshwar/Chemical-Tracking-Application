import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.DefaultGasProvider;

import java.time.LocalDate;

public class ProductRegistrationService {

    private Web3j web3j;
    private Credentials credentials;
    private YourContract contract;

    public ProductRegistrationService(String contractAddress, Credentials credentials) {
        this.web3j = Web3j.build(new HttpService("http://127.0.0.1:7545"));
        this.credentials = credentials;

        this.contract = YourContract.load(
                contractAddress,
                web3j,
                credentials,
                new DefaultGasProvider()
        );
    }

    public boolean isAuthorized(Integer role) {
        return role != null && role == 0;
    }

    public String registerProduct(String prodName, String username,
                              String chemType, String nocNumber) {

    try {
        if (!ValidationUtil.isNotEmpty(prodName)) {
            return "Product name required";
        }

        String fullData = prodName +
                "\nRegistered By: " + username +
                "\nChemical Type: " + chemType +
                "\nNOC Number: " + nocNumber;

        String date = java.time.LocalDate.now().toString();

        var receipt = contract.newItem(fullData, date).send();

        var events = contract.getAddedEvents(receipt);
        if (!events.isEmpty()) {
            return "Product ID: " + events.get(0).id;
        }

        return "Item added";

    } catch (Exception e) {
        return "Transaction failed";
    }
}
}