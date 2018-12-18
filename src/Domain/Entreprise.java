package Domain;

import java.util.List;
import java.util.stream.Collectors;

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
	
	public void addEmployee(Employee employee) {
		employees.add(employee);
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	public int getNumbersOfDeveloppers() {
		List<Employee> developpers = employees.stream().filter(e -> e.getRole().equals(EmployeRole.DEVELLOPER) || e.getRole().equals(EmployeRole.TECHNICAL_MANAGER)).collect(Collectors.toList());
		return developpers.size();
	}
	
	public int getNumbersOfManagers() {
		List<Employee> managers = employees.stream().filter(e -> e.getRole().equals(EmployeRole.PROJECT_MANAGER)).collect(Collectors.toList());
		return managers.size();
	}
}
