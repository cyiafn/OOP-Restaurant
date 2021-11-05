package EntityClasses;


public class Staff {

    private String employeeID;

    private String name;

    private String gender;

    private String jobTitle;

    public Staff(String eid, String name, String gender, String jobTitle){
        this.employeeID = eid;
        this.name = name;
        this.gender = gender;
        this.jobTitle = jobTitle;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
