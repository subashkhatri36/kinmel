package sabaijanakaari.blogspot.kinmel.Model;

public class Whistlist_Model {
    private String productId;
    private String productImage;
    private String productTitle;
    private long freeCoupen;
    private String rating;
    private String totalrating;
    private String productPrice;
    private String productCuttedPrice;
    private boolean cod;
    private boolean instock;

    public Whistlist_Model() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Whistlist_Model(String productImage, String productTitle, long freeCoupen, String rating, String totalrating, String productPrice, String productCuttedPrice, boolean cod, String productId,boolean instock) {
        this.productId = productId;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupen = freeCoupen;
        this.rating = rating;
        this.totalrating = totalrating;
        this.productPrice = productPrice;
        this.productCuttedPrice = productCuttedPrice;
        this.cod = cod;
        this.instock=instock;
    }

    public boolean isInstock() {
        return instock;
    }

    public void setInstock(boolean instock) {
        this.instock = instock;
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

    public long getFreeCoupen() {
        return freeCoupen;
    }

    public void setFreeCoupen(long freeCoupen) {
        this.freeCoupen = freeCoupen;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTotalrating() {
        return totalrating;
    }

    public void setTotalrating(String totalrating) {
        this.totalrating = totalrating;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCuttedPrice() {
        return productCuttedPrice;
    }

    public void setProductCuttedPrice(String productCuttedPrice) {
        this.productCuttedPrice = productCuttedPrice;
    }

    public boolean isCod() {
        return cod;
    }

    public void setCod(boolean cod) {
        this.cod = cod;
    }
}
