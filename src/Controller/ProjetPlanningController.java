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

		int workDaysUntil = getWorkDaysUntil(getMostRecentDate(project.getDateStartDev(), project.getDateStartMana()),
				project.getDateLivraison());

		int workloadPerDevelopmentInDays = 0;
		int workloadPerProjectManagerInDays = 0;

		workloadPerDevelopmentInDays = Math.round(
				(getWorkloadPerDevelopmentInDays(project.getNbRemainingDevDay(), entreprise.getNumbersOfDeveloppers())
						* project.getEfficiency()));
		workloadPerProjectManagerInDays = Math
				.round((getWorkloadPerProjectManagerInDays(project.getNbRemainingManagementDay(),
						entreprise.getNumbersOfManagers()) * project.getEfficiency()));

		if (workloadPerDevelopmentInDays > workDaysUntil) {
			remainingDays.put(RemainingDaysType.DEVELOPMENT, workloadPerDevelopmentInDays - workDaysUntil);
		} else if (workloadPerProjectManagerInDays > workDaysUntil) {
			remainingDays.put(RemainingDaysType.PROJECT_MANAGEMENT, workloadPerProjectManagerInDays - workDaysUntil);
		} else {
			remainingDays.put(RemainingDaysType.ACHIEVABLE_PROJECT, 0);
		}

		project.setDateEndDev(project.getDateStartDev().plusDays(workloadPerDevelopmentInDays));
		project.setDateEndMana(project.getDateStartMana().plusDays(workloadPerProjectManagerInDays));

		return remainingDays;
	}

	public String getProjectFeasibility(Entreprise entreprise, Project project) {
		HashMap<RemainingDaysType, Integer> resultProjectFeasibility = simulateProjectFeasibility(entreprise, project);
		Entreprise entreprise_copy = entreprise;

		String resultToDisplay = new String();

		if (resultProjectFeasibility.containsKey(RemainingDaysType.ACHIEVABLE_PROJECT)) {
			resultToDisplay = "\nLe projet " + project.getName() + " peut être réalisable dans les temps.\n"
					+ "Il commencera son dev le : "+project.getDateStartDev()+" et sa gestion de projet le : "+project.getDateStartMana()+" pour etre livré le "+project.getDateLivraison() + "\n";
		} else if (resultProjectFeasibility.containsKey(RemainingDaysType.PROJECT_MANAGEMENT)) {

			resultToDisplay = "Le projet " + project.getName()
					+ " ne peut pas être réalisable à cause du temps de gestion de projet."
					+ "\nNous aurons un problème dans la gestion de projet à partir du "
					+ getMostRecentDate(project.getDateEndDev(), project.getDateEndMana()).format(datetimeformatter)
					+ ".";

			resultToDisplay = resultToDisplay + " "
					+ ProjectFeasibilityWithMoreEmp(entreprise_copy, project, EmployeRole.PROJECT_MANAGER);

		} else if (resultProjectFeasibility.containsKey(RemainingDaysType.DEVELOPMENT)) {

			resultToDisplay = "Le projet " + project.getName()
					+ "  ne peut pas être réalisable à cause du temps de développement."
					+ "\nNous aurons un problème dans les développements à partir du "
					+ getMostRecentDate(project.getDateEndDev(), project.getDateEndMana()).format(datetimeformatter)
					+ ".";

			resultToDisplay = resultToDisplay + " "
					+ ProjectFeasibilityWithMoreEmp(entreprise_copy, project, EmployeRole.DEVELLOPER);

		} else {
			resultToDisplay = "Erreur dans le projet !";
		}

		return resultToDisplay;
	}

	public String ProjectFeasibilityWithMoreEmp(Entreprise entreprise, Project project, EmployeRole role) {
		Integer numberOfEmpAdded = 0;
		HashMap<RemainingDaysType, Integer> resultNeeded = new HashMap<RemainingDaysType, Integer>();
		resultNeeded.put(RemainingDaysType.ACHIEVABLE_PROJECT, 0);

		while (!simulateProjectFeasibility(entreprise, project).equals(resultNeeded)) {
			numberOfEmpAdded++;
			Employee emp_test = new Employee("Employé fictif", "Employé fictif", role,
					project.getDateStartMana().minusMonths(3));
			entreprise.addEmployee(emp_test);
		}

		return "\nLe nombre de " + role.toString() + " manquants est de " + numberOfEmpAdded + "."
				+ "\nIl faudra embaucher le " + project.getDateStartMana().format(datetimeformatter)
				+ " (sans compter les 3 mois)" + " et le "
				+ project.getDateStartMana().minusMonths(3).format(datetimeformatter) + " (en comptant les 3 mois).\n\n";
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
		int numberOfMounthBetween = (int) ChronoUnit.MONTHS.between(startDate, endDate);

		return numberOfDaysBetween - (numberOfMounthBetween * 2);
	}

	public int getWorkloadPerDevelopmentInDays(int nbDevDays, int nbDev) {
		return (nbDevDays / nbDev);
	}

	public int getWorkloadPerProjectManagerInDays(int nbProjectManagementDays, int nbProjectManager) {
		return (nbProjectManagementDays / nbProjectManager);
	}
}