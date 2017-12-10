package sample.model;

public class DisplayableTask {
    private String description;
    private String startDate;
    private String slave;

    public DisplayableTask(String description, String startDate, String slave){
        this.description = description;
        this.startDate = startDate;
        this.slave = slave;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSlave() {
        return slave;
    }

    public void setSlave(String slave) {
        this.slave = slave;
    }
}
