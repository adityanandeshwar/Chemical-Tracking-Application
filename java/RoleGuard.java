public class RoleGuard {

    public static boolean isManufacturer(Integer role) {
        return role != null && role == 0;
    }

    public static boolean canScan(Integer role) {
        return role != null && (role == 0 || role == 1);
    }

    public static boolean isLoggedIn(Integer role) {
        return role != null;
    }
}