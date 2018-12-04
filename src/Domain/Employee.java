package Domain;

import java.time.LocalDateTime;

public class Employee {

    private String lastName;
    private String firsName;
    private LocalDateTime dateReadyToWork;

    public Employee(String lastName, String firsName, LocalDateTime dateReadyToWork) {
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

    public LocalDateTime getDateReadyToWork() {
        return dateReadyToWork;
    }

    public void setDateReadyToWork(LocalDateTime dateReadyToWork) {
        this.dateReadyToWork = dateReadyToWork;
    }

}
