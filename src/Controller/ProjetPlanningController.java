package Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;

import Domain.EmployeRole;
import Domain.Employee;
import Domain.Entreprise;
import Domain.Project;
import Domain.RemainingDaysType;

public class ProjetPlanningController {
	public static ProjetPlanningController instance;
	private DateTimeFormatter datetimeformatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
			.withLocale(Locale.FRENCH);

	public static ProjetPlanningController getProjetPlanningControllerInstance() {
		if (instance == null) {
			instance = new ProjetPlanningController();
		}
		return instance;
	}

	public LocalDate getMostRecentDate(LocalDate date1, LocalDate date2) {
		if (date2.isBefore(date1)) {
			return date1;
		} else {
			return date2;
		}
	}

	public HashMap<RemainingDaysType, Integer> simulateProjectFeasibility(Entreprise entreprise, Project project) {
		HashMap<RemainingDaysType, Integer> remainingDays = new HashMap<RemainingDaysType, Integer>();
		System.out.println("Projet etudie : "+project.getName());
		System.out.println("Il commence son dev le "+project.getDateStartDev());
		System.out.println("Il commence son mana le "+project.getDateStartMana());

		int workDaysUntil = getWorkDaysUntil(getMostRecentDate(project.getDateStartDev(), project.getDateStartMana()),
				project.getDateLivraison());

		int workloadPerDevelopmentInDays = 0;
		int workloadPerProjectManagerInDays = 0;

		workloadPerDevelopmentInDays = Math.round(
				(getWorkloadPerDevelopmentInDays(project.getNbRemainingDevDay(), entreprise.getNumbersOfDeveloppers())
						/ project.getEfficiency()));
		workloadPerProjectManagerInDays = Math
				.round((getWorkloadPerProjectManagerInDays(project.getNbRemainingManagementDay(),
						entreprise.getNumbersOfManagers()) / project.getEfficiency()));

		if (workloadPerDevelopmentInDays > workDaysUntil) {
			remainingDays.put(RemainingDaysType.DEVELOPMENT, workloadPerDevelopmentInDays - workDaysUntil);
		} else if (workloadPerProjectManagerInDays > workDaysUntil) {
			remainingDays.put(RemainingDaysType.PROJECT_MANAGEMENT, workloadPerProjectManagerInDays - workDaysUntil);
		} else {
			remainingDays.put(RemainingDaysType.ACHIEVABLE_PROJECT, 0);
		}
		int numberOfWeeksBetweenDev = (int) ChronoUnit.WEEKS.between(project.getDateStartDev(), project.getDateStartDev().plusDays(workloadPerDevelopmentInDays));
		int numberOfWeeksBetweenMana = (int) ChronoUnit.WEEKS.between(project.getDateStartDev(), project.getDateStartDev().plusDays(workloadPerDevelopmentInDays));
		
		project.setDateEndDev(project.getDateStartDev().plusDays(workloadPerDevelopmentInDays + (numberOfWeeksBetweenDev*2)));
		project.setDateEndMana(project.getDateStartMana().plusDays(workloadPerProjectManagerInDays + (numberOfWeeksBetweenMana*2)));

		return remainingDays;
	}
	
	public HashMap<RemainingDaysType, Integer> simulateProjectFeasibilityDev(Entreprise entreprise, Project project) {
		HashMap<RemainingDaysType, Integer> remainingDays = new HashMap<RemainingDaysType, Integer>();

		int workDaysUntil = getWorkDaysUntil(getMostRecentDate(project.getDateStartDev(), project.getDateStartMana()),
				project.getDateLivraison());

		int workloadPerDevelopmentInDays = 0;

		workloadPerDevelopmentInDays = Math.round(
				(getWorkloadPerDevelopmentInDays(project.getNbRemainingDevDay(), entreprise.getNumbersOfDeveloppers())
						/ project.getEfficiency()));
	
		if (workloadPerDevelopmentInDays > workDaysUntil) {
			remainingDays.put(RemainingDaysType.DEVELOPMENT, workloadPerDevelopmentInDays - workDaysUntil);
		}else {
			remainingDays.put(RemainingDaysType.ACHIEVABLE_PROJECT, 0);
		}

		project.setDateEndDev(project.getDateStartDev().plusDays(workloadPerDevelopmentInDays));
	
		return remainingDays;
	}
	
	public HashMap<RemainingDaysType, Integer> simulateProjectFeasibilityManagement(Entreprise entreprise, Project project) {
		HashMap<RemainingDaysType, Integer> remainingDays = new HashMap<RemainingDaysType, Integer>();

		int workDaysUntil = getWorkDaysUntil(getMostRecentDate(project.getDateStartDev(), project.getDateStartMana()),
				project.getDateLivraison());

		int workloadPerProjectManagerInDays = 0;

		workloadPerProjectManagerInDays = Math
				.round((getWorkloadPerProjectManagerInDays(project.getNbRemainingManagementDay(),
						entreprise.getNumbersOfManagers()) / project.getEfficiency()));

		if (workloadPerProjectManagerInDays > workDaysUntil) {
			remainingDays.put(RemainingDaysType.PROJECT_MANAGEMENT, workloadPerProjectManagerInDays - workDaysUntil);
		} else {
			remainingDays.put(RemainingDaysType.ACHIEVABLE_PROJECT, 0);
		}

		project.setDateEndMana(project.getDateStartMana().plusDays(workloadPerProjectManagerInDays));

		return remainingDays;
	}
	
	
	public Entreprise copyEntreprise(Entreprise e) {
		Entreprise entreprise_copy = new Entreprise(e.getName(), e.getEmployees(), e.getProjects());
		return entreprise_copy;
	}

	public String getProjectFeasibility(Entreprise entreprise, Project project) {
		HashMap<RemainingDaysType, Integer> resultProjectFeasibility = simulateProjectFeasibility(entreprise, project);
		Entreprise entreprise_copy = copyEntreprise(entreprise);

		String resultToDisplay = new String();

		if (resultProjectFeasibility.containsKey(RemainingDaysType.ACHIEVABLE_PROJECT)) {
			resultToDisplay = "\nLe projet " + project.getName() + " peut être réalisable.";

		} 
		if (resultProjectFeasibility.containsKey(RemainingDaysType.PROJECT_MANAGEMENT)) {

			resultToDisplay += "\nLe projet " + project.getName()
					+ " ne peut pas être réalisable à cause du temps de gestion de projet."
					+ "\nNous aurons un problème dans la gestion de projet à partir du "
					+ getMostRecentDate(project.getDateEndDev(), project.getDateEndMana()).format(datetimeformatter)
					+ ".";

			resultToDisplay += resultToDisplay + " "
					+ ProjectFeasibilityWithMoreEmp(entreprise_copy, project, EmployeRole.PROJECT_MANAGER);

		} 
		if (resultProjectFeasibility.containsKey(RemainingDaysType.DEVELOPMENT)) {

			resultToDisplay += "\nLe projet " + project.getName()
					+ "  ne peut pas être réalisable à cause du temps de développement."
					+ "\nNous aurons un problème dans les développements à partir du "
					+ getMostRecentDate(project.getDateEndDev(), project.getDateEndMana()).format(datetimeformatter)
					+ ".";

			resultToDisplay += ProjectFeasibilityWithMoreEmp(entreprise_copy, project, EmployeRole.DEVELLOPER);

		} 

		if(!(resultToDisplay.length() > 0)){
			resultToDisplay = "Erreur dans le projet !";
		}

		return resultToDisplay;
	}

	public String ProjectFeasibilityWithMoreEmp(Entreprise entreprise, Project project, EmployeRole role) {
		Integer numberOfEmpAdded = 0;
		HashMap<RemainingDaysType, Integer> resultNeeded = new HashMap<RemainingDaysType, Integer>();
		HashMap<RemainingDaysType, Integer> resultObtained = new HashMap<RemainingDaysType, Integer>();
		resultNeeded.put(RemainingDaysType.ACHIEVABLE_PROJECT, 0);
		
		if(role.equals(EmployeRole.DEVELLOPER)) {
			while (!(resultObtained=simulateProjectFeasibilityDev(entreprise, project)).equals(resultNeeded)) {
				if(resultObtained.containsKey(RemainingDaysType.DEVELOPMENT)) {
					role = EmployeRole.DEVELLOPER;
				}else if(resultObtained.containsKey(RemainingDaysType.PROJECT_MANAGEMENT)){
					role = EmployeRole.PROJECT_MANAGER;
				}
				numberOfEmpAdded++;
				Employee emp_test = new Employee("Employé fictif", "Employé fictif", role,
						project.getDateStartMana().minusMonths(4));
				entreprise.addEmployee(emp_test);
				
				
				int workloadPerDevelopmentInDays = Math.round(
						(getWorkloadPerDevelopmentInDays(project.getNbRemainingDevDay(), entreprise.getNumbersOfDeveloppers())
								/ project.getEfficiency()));
				int workloadPerProjectManagerInDays = Math
						.round((getWorkloadPerProjectManagerInDays(project.getNbRemainingManagementDay(),
								entreprise.getNumbersOfManagers()) / project.getEfficiency()));
				
				int numberOfWeeksBetweenDev = (int) ChronoUnit.WEEKS.between(project.getDateStartDev(), project.getDateStartDev().plusDays(workloadPerDevelopmentInDays));
				int numberOfWeeksBetweenMana = (int) ChronoUnit.WEEKS.between(project.getDateStartDev(), project.getDateStartDev().plusDays(workloadPerDevelopmentInDays));
				
				
				project.setDateEndDev(project.getDateStartDev().plusDays(workloadPerDevelopmentInDays + (numberOfWeeksBetweenDev*2)));
				project.setDateEndMana(project.getDateStartMana().plusDays(workloadPerProjectManagerInDays + (numberOfWeeksBetweenMana*2)));

				
			}

			return "\nLe nombre de " + role.toString() + " manquants est de " + numberOfEmpAdded + "."
					+ "\nIl faudra embaucher le " + project.getDateStartMana().format(datetimeformatter)
					+ " (sans compter les 4 mois de processus d'embauche)" + " et le "
					+ project.getDateStartMana().minusMonths(4).format(datetimeformatter) + " (en comptant les 4 mois de processus d'embauche).";
		}
	
		if(role.equals(EmployeRole.PROJECT_MANAGER)) {
			while (!(resultObtained=simulateProjectFeasibilityManagement(entreprise, project)).equals(resultNeeded)) {
				numberOfEmpAdded++;
				Employee emp_test = new Employee("Employé fictif", "Employé fictif", role,
						project.getDateStartMana().minusMonths(4));
				entreprise.addEmployee(emp_test);

				return "\nLe nombre de " + role.toString() + " manquants est de " + numberOfEmpAdded + "."
						+ "\nIl faudra embaucher le " + project.getDateStartMana().format(datetimeformatter)
						+ " (sans compter les 4 mois de processus d'embauche)" + " et le "
						+ project.getDateStartMana().minusMonths(4).format(datetimeformatter) + " (en comptant les 4 mois de processus d'embauche).";
			}

		}
		
		return "";

	}

	public String getAllProjectsFeasibility(Entreprise e) {
		Entreprise entreprise = e;
		entreprise.getProjects().sort((d1, d2) -> d1.getDateLivraison().compareTo(d2.getDateLivraison()));
		String resultToDisplay = new String();

		for (int i = 0; i < entreprise.getProjects().size(); i++) {
			resultToDisplay = resultToDisplay + "\nProjet " + (i + 1) + " : "
					+ getProjectFeasibility(entreprise, entreprise.getProjects().get(i));

			if (i < entreprise.getProjects().size() - 1) {
				entreprise.getProjects().get(i + 1).setDateStartDev(entreprise.getProjects().get(i).getDateEndDev());
				entreprise.getProjects().get(i + 1).setDateStartMana(entreprise.getProjects().get(i).getDateEndMana());
			}
		}

		return resultToDisplay;
	}

	public int getWorkDaysUntil(LocalDate startDate, LocalDate endDate) {
		int numberOfDaysBetween = (int) ChronoUnit.DAYS.between(startDate, endDate);
		int numberOfWeeksBetween = (int) ChronoUnit.WEEKS.between(startDate, endDate);

		return numberOfDaysBetween - (numberOfWeeksBetween * 2);
	}

	public int getWorkloadPerDevelopmentInDays(int nbDevDays, int nbDev) {
		return (nbDevDays / nbDev);
	}

	public int getWorkloadPerProjectManagerInDays(int nbProjectManagementDays, int nbProjectManager) {
		return (nbProjectManagementDays / nbProjectManager);
	}
}