package sabaijanakaari.blogspot.kinmel.Model;

public class addresses_Model {
    private String fullname;
    private String address;
    private String pinCode;
    private String mobileno;

    private boolean selected;


    public addresses_Model() {
    }

    public addresses_Model(String fullname, String address, String pinCode,boolean selected,String mobileno) {
        this.fullname = fullname;
        this.address = address;
        this.pinCode = pinCode;
        this.selected=selected;
        this.mobileno=mobileno;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
