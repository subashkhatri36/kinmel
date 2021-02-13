package sabaijanakaari.blogspot.kinmel.Model;

public class product_specification_Model {
    public static final int PRODUCT_SPECIFICATION_TITLE=0;
    public static final int PRODUCT_SPECIFICATION_BODY=1;

    private int type;

    //production Specification title
    private String title;

    public product_specification_Model(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    //production Specification title

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //Production Specification Body
    private String featureName;
    private String featureValue;

    public product_specification_Model(int type, String featureName, String featureValue) {
        this.type = type;
        this.featureName = featureName;
        this.featureValue = featureValue;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue;
    }

    //Production Specification Body

    public product_specification_Model() {
    }


}
