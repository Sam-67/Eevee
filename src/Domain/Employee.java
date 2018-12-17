package Domain;

import java.time.LocalDate;

public class Employee {

    private String lastName;
    private String firsName;
    private EmployeRole role;
    private LocalDate dateReadyToWork;

    public Employee(String lastName, String firsName, EmployeRole role, LocalDate dateReadyToWork) {
        this.lastName = lastName;
        this.firsName = firsName;
        this.role = role;
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

    public LocalDate getDateReadyToWork() {
        return dateReadyToWork;
    }

    public void setDateReadyToWork(LocalDate dateReadyToWork) {
        this.dateReadyToWork = dateReadyToWork;
    }

	@Override
	public String toString() {
		return "Employee [lastName=" + lastName + ", firsName=" + firsName + ", role=" + role + ", dateReadyToWork="
				+ dateReadyToWork + "]";
	}

    
    
}
