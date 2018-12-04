package Domain;

import java.util.List;

public class Entreprise {

    private String name;

    private List<Employee> employees;
    private List<Project> projects; 
    
    public Entreprise(String name, List<Employee> employees, List<Project> projects) {
        this.name = name;
        this.employees = employees;
        this.projects = projects;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	public int getNumbersOfDeveloppers() {
		return employees.size();
	}
	
	public int getNumbersOfManagers() {
		return employees.size();
	}
}
