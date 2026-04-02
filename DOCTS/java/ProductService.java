import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class ProductService {

    private Web3j web3j;
    private YourContract contract;

    public ProductService(YourContract contract) {
        this.web3j = Web3j.build(new HttpService("http://127.0.0.1:7545"));
        this.contract = contract;
    }

    public boolean isAuthorized(Integer role) {
        return role != null;
    }

    public String searchProduct(String productId) {

        try {
            if (productId == null || productId.isEmpty()) {
                return "Invalid Product ID";
            }

            String result = contract.searchProduct(productId).send();

            if (result == null || result.isEmpty()) {
                return "No Data Found";
            }

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching product";
        }
    }
}