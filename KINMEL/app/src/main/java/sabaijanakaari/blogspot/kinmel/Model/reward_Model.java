package sabaijanakaari.blogspot.kinmel.Model;

public class reward_Model {
    private String rewardTitle;
    private String experyDate;
    private String rewardBody;

    public reward_Model() {
    }

    public reward_Model(String rewardTitle, String experyDate, String rewardBody) {
        this.rewardTitle = rewardTitle;
        this.experyDate = experyDate;
        this.rewardBody = rewardBody;
    }

    public String getRewardTitle() {
        return rewardTitle;
    }

    public void setRewardTitle(String rewardTitle) {
        this.rewardTitle = rewardTitle;
    }

    public String getExperyDate() {
        return experyDate;
    }

    public void setExperyDate(String experyDate) {
        this.experyDate = experyDate;
    }

    public String getRewardBody() {
        return rewardBody;
    }

    public void setRewardBody(String rewardBody) {
        this.rewardBody = rewardBody;
    }
}
