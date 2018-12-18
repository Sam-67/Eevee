import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import Controller.ProjetPlanningController;
import Domain.EmployeRole;
import Domain.Employee;
import Domain.Entreprise;
import Domain.Project;

public class ApplicationForTests {

	public static void main(String[] args) {
		ProjetPlanningController pc = ProjetPlanningController.getProjetPlanningControllerInstance();
		
		EmployeRole role_dev = EmployeRole.DEVELLOPER;
		EmployeRole role_mana = EmployeRole.PROJECT_MANAGER;
		
		Employee emp_sarah = new Employee("MESBAH", "Sarah", role_dev, LocalDate.of(2019, Month.MAY, 15));
		Employee emp_chef = new Employee("DUPONT", "Jean-Claude", role_mana, LocalDate.of(2018, Month.MAY, 01));
		
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(emp_sarah); 
		employees.add(emp_chef); 
		
		Project proj_google = new Project("Google", LocalDate.of(2017, Month.MAY, 15), LocalDate.of(2017, Month.MAY, 20), 550, 20, 1);
		Project proj_facebook = new Project("Facebook", LocalDate.of(2017, Month.MAY, 15), LocalDate.of(2017, Month.MAY, 20), 770, 20, 1);
		
		List<Project> projects = new ArrayList<Project>();
		projects.add(proj_google); 
		projects.add(proj_facebook); 
		
		Entreprise entreprise = new Entreprise("Eevee", employees, projects);
		
		System.out.println("ONE PROJECT getProjectFeasibility : " + pc.getProjectFeasibility(entreprise, proj_google) + "\n");
		System.out.println("\n");
		System.out.println("ALL PROJECTS getAllProjectsFeasibility : " + pc.getAllProjectsFeasibility(entreprise) + "\n");
	}

}
