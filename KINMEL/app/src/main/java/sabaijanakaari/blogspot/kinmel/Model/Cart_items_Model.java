package sabaijanakaari.blogspot.kinmel.Model;

import java.util.ArrayList;
import java.util.List;

public class Cart_items_Model {

    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUNT = 1;

    private int type;

    /////////cart items Model

    private String product_id;
    private String productPrice;
    private String productImage;
    private String productTitle;
    private long freeCoupen;
    private String productCuttedprice;
    private Long productQuanity;
    private Long offersApplied;
    private Long coupenApplied;
    private boolean instock;
    private Long maxquanity;
    private Long minquanity;
    private List<String> quanityIds;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Cart_items_Model(int type, String product_id, String productPrice, String productCuttedprice,
                            String productTitle, Long freeCoupen, String productImage, Long productQuanity,
                            Long offersApplied, Long coupenApplied, boolean instock,Long maxquanity, Long minquanity) {
        this.type = type;
        this.productPrice = productPrice;
        this.productCuttedprice = productCuttedprice;
        this.productTitle = productTitle;
        this.freeCoupen = freeCoupen;
        this.productImage = productImage;
        this.productQuanity = productQuanity;
        this.offersApplied = offersApplied;
        this.coupenApplied = coupenApplied;
        this.product_id = product_id;
        this.instock = instock;
        this.maxquanity=maxquanity;
        this.minquanity=minquanity;
        quanityIds=new ArrayList<>();
    }

    public List<String> getQuanityIds() {
        return quanityIds;
    }

    public void setQuanityIds(List<String> quanityIds) {
        this.quanityIds = quanityIds;
    }

    public Long getMaxquanity() {
        return maxquanity;
    }

    public void setMaxquanity(Long maxquanity) {
        this.maxquanity = maxquanity;
    }

    public Long getMinquanity() {
        return minquanity;
    }

    public void setMinquanity(Long minquanity) {
        this.minquanity = minquanity;
    }

    public boolean isInstock() {
        return instock;
    }

    public void setInstock(boolean instock) {
        this.instock = instock;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setFreeCoupen(long freeCoupen) {
        this.freeCoupen = freeCoupen;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCuttedprice() {
        return productCuttedprice;
    }

    public void setProductCuttedprice(String productCuttedprice) {
        this.productCuttedprice = productCuttedprice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Long getFreeCoupen() {
        return freeCoupen;
    }

    public void setFreeCoupen(int freeCoupen) {
        this.freeCoupen = freeCoupen;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Long getProductQuanity() {
        return productQuanity;
    }

    public void setProductQuanity(Long productQuanity) {
        this.productQuanity = productQuanity;
    }

    public Long getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(Long offersApplied) {
        this.offersApplied = offersApplied;
    }

    public Long getCoupenApplied() {
        return coupenApplied;
    }

    public void setCoupenApplied(Long coupenApplied) {
        this.coupenApplied = coupenApplied;
    }

    /////////cart items Model
    public Cart_items_Model() {
    }


    //////cart total variable

    public Cart_items_Model(int type) {
        this.type = type;
    }


    //////cart total variable


}
