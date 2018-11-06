package Domain;

import java.util.Date;

public class Employee {

    private String lastName;
    private String firsName;
    private Date dateReadyToWork;

    public Employee(){

    }

    public Employee(String lastName, String firsName, Date dateReadyToWork) {
        this.lastName = lastName;
        this.firsName = firsName;
        this.dateReadyToWork = dateReadyToWork;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public Date getDateReadyToWork() {
        return dateReadyToWork;
    }

    public void setDateReadyToWork(Date dateReadyToWork) {
        this.dateReadyToWork = dateReadyToWork;
    }



}
