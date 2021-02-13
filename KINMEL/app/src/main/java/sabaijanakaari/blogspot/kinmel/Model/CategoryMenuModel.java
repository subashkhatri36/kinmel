package sabaijanakaari.blogspot.kinmel.Model;

public class CategoryMenuModel {

    private String categoryIconlink;
    private String categoryname;

    public CategoryMenuModel() {
    }

    public CategoryMenuModel(String categoryIconlink, String categoryname) {
        this.categoryIconlink = categoryIconlink;
        this.categoryname = categoryname;
    }

    public String getCategoryIconlink() {
        return categoryIconlink;
    }

    public void setCategoryIconlink(String categoryIconlink) {
        this.categoryIconlink = categoryIconlink;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
