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
            if (prodName == null || username == null || chemType == null || nocNumber == null) {
                return "Invalid input";
            }

            String fullData = prodName +
                    "\nRegistered By: " + username +
                    "\nChemical Type: " + chemType +
                    "\nNOC Number: " + nocNumber;

            String date = LocalDate.now().toString();

            var receipt = contract.newItem(fullData, date).send();

            String productId = "UNKNOWN";

            if (receipt.getLogs() != null && !receipt.getLogs().isEmpty()) {
                productId = receipt.getTransactionHash();
            }

            return "Item Added Successfully. Product ID: " + productId;

        } catch (Exception e) {
            e.printStackTrace();
            return "Transaction failed";
        }
    }
}