public String searchProduct(String productId) {

    try {
        if (!ValidationUtil.isNotEmpty(productId)) {
            return "Invalid Product ID";
        }

        return contract.searchProduct(productId).send();

    } catch (Exception e) {
        return "Error fetching product";
    }
}