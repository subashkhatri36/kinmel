package sabaijanakaari.blogspot.kinmel.Model;

import java.util.List;

public class HomepageModel {
    public static final int BANNER_SLIDER=0;
    public static final int STRIP_ADS_BANNER=1;
    public static final int HORIZENTAL_PRODUCT_VIEW=2;
    public static final int GRIDVIEW_PRODCUT_VIEW=3;

    private int type;
    //Girdview Product Layout
    //Girdview Product Layout

    //HOrizental product
    private String product_title;
    List<horizental_product_Scroll_Model> horizental_product_scroll_models;
    List<Whistlist_Model> viewAllproductlist;

    private String layout_background;

    public HomepageModel(int type, String product_title,String layout_background, List<horizental_product_Scroll_Model> horizental_product_scroll_models,List<Whistlist_Model> viewAllproductList) {
        this.layout_background = layout_background;
        this.type = type;
        this.product_title = product_title;
        this.horizental_product_scroll_models = horizental_product_scroll_models;
        this.viewAllproductlist=viewAllproductList;
    }
    public List<Whistlist_Model> getViewAllproductlist() {
        return viewAllproductlist;
    }

    public void setViewAllproductlist(List<Whistlist_Model> viewAllproductlist) {
        this.viewAllproductlist = viewAllproductlist;
    }
    //HOrizental product
//grid produc list
    public HomepageModel(int type, String product_title,String layout_background, List<horizental_product_Scroll_Model> horizental_product_scroll_models) {
        this.layout_background = layout_background;
        this.type = type;
        this.product_title = product_title;
        this.horizental_product_scroll_models = horizental_product_scroll_models;

    }
    //grid produc list


    //grid produc list & horizenal product list
    public String getLayout_background() {
        return layout_background;
    }

    public void setLayout_background(String layout_background) {
        this.layout_background = layout_background;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public List<horizental_product_Scroll_Model> getHorizental_product_scroll_models() {
        return horizental_product_scroll_models;
    }

    public void setHorizental_product_scroll_models(List<horizental_product_Scroll_Model> horizental_product_scroll_models) {
        this.horizental_product_scroll_models = horizental_product_scroll_models;
    }

    //HOrizental product



    //strip ad banner
    private String resources;
    private String backgroundcolor;

    public HomepageModel(int type, String resources, String backgroundcolor) {
        this.type = type;
        this.resources = resources;
        this.backgroundcolor = backgroundcolor;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }
    ///Strip ad banner

    ////Banner Slider View Pager
    private List<banner_Slider_Model> bannerSlideModelList;

    public HomepageModel(int type, List<banner_Slider_Model> bannerSlideModelList) {
        this.type = type;
        this.bannerSlideModelList = bannerSlideModelList;
    }

    public HomepageModel() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<banner_Slider_Model> getBannerSlideModelList() {
        return bannerSlideModelList;
    }

    public void setBannerSlideModelList(List<banner_Slider_Model> bannerSlideModelList) {
        this.bannerSlideModelList = bannerSlideModelList;
    }

    ////Banner Slider View Pager



}
