import java.util.ArrayList;
import java.util.List;

public class NavbarService {

    public List<String> getMenuItems(Integer role) {

        List<String> menu = new ArrayList<>();

        if (role != null) {

            menu.add("Check Products");

            if (role == 0) {
                menu.add("Add Products");
            }

            if (role == 0 || role == 1) {
                menu.add("Scan Shipment");
            }
        }

        menu.add("About");
        menu.add("Logout");

        return menu;
    }
}