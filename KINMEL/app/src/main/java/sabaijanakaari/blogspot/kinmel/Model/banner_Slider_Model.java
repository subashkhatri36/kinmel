package sabaijanakaari.blogspot.kinmel.Model;

public class banner_Slider_Model {
    private String bannerLink;
    private String backgroundColor;

    public banner_Slider_Model() {
    }

    public banner_Slider_Model(String bannerLink, String backgroundColor) {
        this.bannerLink = bannerLink;
        this.backgroundColor = backgroundColor;
    }

    public String getBannerLink() {
        return bannerLink;
    }

    public void setBannerLink(String bannerLink) {
        this.bannerLink = bannerLink;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
