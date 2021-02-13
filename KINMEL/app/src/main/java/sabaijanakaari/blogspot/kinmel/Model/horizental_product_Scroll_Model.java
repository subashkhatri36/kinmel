package sabaijanakaari.blogspot.kinmel.Model;

public class horizental_product_Scroll_Model {
    private String productImage;
    private String productTitle;
    private String productDescription;
    private String productPrice;
    private String productId;

    public horizental_product_Scroll_Model() {
    }
    //String productId,String productImage, String productTitle, String productDescription, String productPrice
    public horizental_product_Scroll_Model(String productId,String productImage, String productTitle, String productDescription, String productPrice) {
        this.productId = productId;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

}
