package EntityClasses;

/**
 * Staff entity class
 @author Tan Ge Wen, Gotwin
 @version 1.0
 @since 2021-10-30
 */

/**
 Attributes of Staff
 */
public class Staff {
    /**
     * The employeeID
     */
    private String employeeID;
    /**
     *  name of employee
     */
    private String name;
    /**
     * employee gender
     */
    private String gender;
    /**
     * employee job title
     */
    private String jobTitle;

    /**
     * Constructor of Staff
     * @param eid eid
     * @param name Staff Name
     * @param gender gender
     * @param jobTitle jobTitle
     */
    public Staff(String eid, String name, String gender, String jobTitle){
        this.employeeID = eid;
        this.name = name;
        this.gender = gender;
        this.jobTitle = jobTitle;
    }

    /**
     * employeeid getter
     * @return employeeID
     */
    public String getEmployeeID() {
        return employeeID;
    }
    /**
     * employeeid object setter
     * @param employeeID employeeID
     */
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    /**
     * employee name getter
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * employee name object setter
     * @param name employee name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * employee name getter
     * @return gender
     */
    public String getGender() {
        return gender;
    }
    /**
     * employee gender object setter
     * @param gender employee gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * employee jobtitle getter
     * @return jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * employee jobtitle object setter
     * @param jobTitle employee jobTitle
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
