package Controller;

import java.time.LocalDate;
import java.time.Month;
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
    private DateTimeFormatter datetimeformatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(Locale.FRENCH);
    
    public static ProjetPlanningController getProjetPlanningControllerInstance(){
        if(instance == null){
            instance = new ProjetPlanningController();
        }
        return instance;
    }
    
    public LocalDate getLowerDate(LocalDate date1, LocalDate date2) {
    	if(date1.isBefore(date2)) {
    		return date1;
    	} else {
    		return date2;
    	}
    }
    
    public LocalDate getMostRecentDate(LocalDate date1, LocalDate date2) {
    	if(date2.isBefore(date1)) {
    		return date1;
    	} else {
    		return date2;
    	}
    }
    
    public HashMap<RemainingDaysType, Integer> simulateProjectFeasibility(Entreprise entreprise, Project project) {
    	HashMap<RemainingDaysType, Integer> remainingDays = new HashMap<RemainingDaysType, Integer>();
    	
    	//récupérer la date la plus haute entre dev et mana pour début et fin
    	//int workDaysUntil = getWorkDaysUntil(getHigherDate(project.getDateStartDev(), project.getDateStartMana()), getLowerDate(project.getDateEndDev(), project.getDateEndMana()));
    	int workDaysUntil = getWorkDaysUntil(getMostRecentDate(project.getDateStartDev(), project.getDateStartMana()), project.getDateLivraison());
    	
    	System.out.println("Simulation du projet " + project.getName());
    	System.out.println("\n Le projet commence le : " + getMostRecentDate(project.getDateStartDev(), project.getDateStartMana()).toString());
    	System.out.println("\n Et finis le : " + getMostRecentDate(project.getDateEndDev(), project.getDateEndMana()).toString());
    	
    	int workloadPerDevelopmentInDays = 0;
    	int workloadPerProjectManagerInDays = 0;

    	workloadPerDevelopmentInDays = Math.round((getWorkloadPerDevelopmentInDays(project.getNbRemainingDevDay(), entreprise.getNumbersOfDeveloppers()) * project.getEfficiency()));
        workloadPerProjectManagerInDays = Math.round((getWorkloadPerProjectManagerInDays(project.getNbRemainingManagementDay(), entreprise.getNumbersOfManagers()) * project.getEfficiency()));
        
        System.out.println("\n Chaque développer devra avoir : " + workloadPerDevelopmentInDays + " jours de dev");
        System.out.println("\n Et il y a : " + workDaysUntil + " jours au total");
        
    	if(workloadPerDevelopmentInDays > workDaysUntil) {
    		remainingDays.put(RemainingDaysType.DEVELOPMENT, workloadPerDevelopmentInDays - workDaysUntil);
    	} else if(workloadPerProjectManagerInDays > workDaysUntil) {
    		remainingDays.put(RemainingDaysType.PROJECT_MANAGEMENT, workloadPerProjectManagerInDays - workDaysUntil);
    	} else {
    		remainingDays.put(RemainingDaysType.ACHIEVABLE_PROJECT, 0);
    	}
    	
    	//changement des dates de fin de dev et mana
    	project.setDateEndDev(project.getDateStartDev().plusDays(workloadPerDevelopmentInDays));
    	project.setDateEndMana(project.getDateStartMana().plusDays(workloadPerProjectManagerInDays));
    	
    	System.out.println("\n Le projet est supposé finir son dev le : " + project.getDateEndDev().toString());
    	System.out.println("\n Le projet est supposé finir son management le : " + project.getDateEndMana().toString()+"\n");
    	
    	return remainingDays; 	
    }

    public String getProjectFeasibility(Entreprise entreprise, Project project) {
    	HashMap<RemainingDaysType, Integer> resultProjectFeasibility = simulateProjectFeasibility(entreprise, project);
    	Entreprise entreprise_copy = entreprise; 
    	
    	String resultToDisplay = new String(); 
    	
    	if(resultProjectFeasibility.containsKey(RemainingDaysType.ACHIEVABLE_PROJECT)) {
    		resultToDisplay = "Le projet " + project.getName() + " peut être réalisable.";
    		
    	} else if (resultProjectFeasibility.containsKey(RemainingDaysType.PROJECT_MANAGEMENT)) {
    		ProjectFeasibilityWithMoreMana(entreprise_copy, project);
    		
    		resultToDisplay = "Le projet " + project.getName() + " ne peut pas être réalisable à cause du temps de gestion de projet."
    				+ "\nNous aurons un problème dans la gestion de projet à partir du "
    				+ getMostRecentDate(project.getDateEndDev(), project.getDateEndMana()).format(datetimeformatter) + "."; 
			
    		resultToDisplay = resultToDisplay + " " + ProjectFeasibilityWithMoreMana(entreprise_copy, project);
    		
    	} else if(resultProjectFeasibility.containsKey(RemainingDaysType.DEVELOPMENT))  { 
    		ProjectFeasibilityWithMoreDev(entreprise_copy, project);
    		
    		resultToDisplay = "Le projet " + project.getName() + "  ne peut pas être réalisable à cause du temps de développement."
    				+ "\nNous aurons un problème dans les développements à partir du "
    				+ getMostRecentDate(project.getDateEndDev(), project.getDateEndMana()).format(datetimeformatter) + "."; 
    		
    		resultToDisplay = resultToDisplay + " " + ProjectFeasibilityWithMoreDev(entreprise_copy, project);
    		
    	} else {
    		resultToDisplay = "Erreur dans le projet !";
    	}
    	
    	return resultToDisplay;
    }
    
    public String ProjectFeasibilityWithMoreDev(Entreprise entreprise, Project project) {
		//System.out.println("Nous allons ajouter un développeur.");
		Integer numberOfDevAdded = 0;
		HashMap<RemainingDaysType, Integer> resultNeeded = new HashMap<RemainingDaysType, Integer>();
		resultNeeded.put(RemainingDaysType.ACHIEVABLE_PROJECT, 0);
		
		while(!simulateProjectFeasibility(entreprise, project).equals(resultNeeded)) {
			numberOfDevAdded++;
			Employee emp_test = new Employee("DevFictif", "DevFictif", EmployeRole.DEVELLOPER, project.getDateStartMana().minusMonths(3));
			entreprise.addEmployee(emp_test);
		}
		
		return "\nLe nombre de développeurs manquants est de " + numberOfDevAdded 
				+ ", à ajouter le (sans compter les 3 mois) " + project.getDateStartMana()
				+ " (en comptant les 3 mois) " + project.getDateStartMana().minusMonths(3) + "." ;
    }
    
    public String ProjectFeasibilityWithMoreMana(Entreprise entreprise, Project project) {
		//System.out.println("Nous allons ajouter un manager.");
		Integer numberOfManaAdded = 0;
		HashMap<RemainingDaysType, Integer> resultNeeded = new HashMap<RemainingDaysType, Integer>();
		resultNeeded.put(RemainingDaysType.ACHIEVABLE_PROJECT, 0);
		
		while(!simulateProjectFeasibility(entreprise, project).equals(resultNeeded)) {
			numberOfManaAdded++;
			Employee emp_test = new Employee("ManaFictif", "ManaFictif", EmployeRole.PROJECT_MANAGER, project.getDateStartMana().minusMonths(3));
			entreprise.addEmployee(emp_test);
		}
		
		return "\nLe nombre de développeurs manquants est de " + numberOfManaAdded 
				+ ", à ajouter le (sans compter les 3 mois) " + project.getDateStartDev()
				+ " (en comptant les 3 mois) " + project.getDateStartDev().minusMonths(3) + ".";
    }
    
    
    public String getAllProjectsFeasibility(Entreprise e) {
    	Entreprise entreprise = e;
    	entreprise.getProjects().sort((d1, d2) -> d1.getDateLivraison().compareTo(d2.getDateLivraison()));
    	String resultToDisplay = new String(); 
    	
    	for(int i = 0; i < entreprise.getProjects().size(); i++) {
    		resultToDisplay = resultToDisplay +  "\nProjet " + (i+1) + " : " + getProjectFeasibility(entreprise, entreprise.getProjects().get(i)); 
    		
    		if(i < entreprise.getProjects().size()-1) {
    			entreprise.getProjects().get(i+1).setDateStartDev(entreprise.getProjects().get(i).getDateEndDev());
    			entreprise.getProjects().get(i+1).setDateStartMana(entreprise.getProjects().get(i).getDateEndMana());
    		} 
    	}
    	
    	return resultToDisplay;
    }
      
    public int getWorkDaysUntil(LocalDate startDate, LocalDate endDate) {
    	int numberOfDaysBetween = (int) ChronoUnit.DAYS.between(startDate, endDate);
    	int numberOfMounthBetween = (int) ChronoUnit.MONTHS.between(startDate, endDate);
    	//System.out.println("numberOfDaysBetween : " + numberOfDaysBetween + "\n");
    	return numberOfDaysBetween - (numberOfMounthBetween * 2);
    }
    
    public int getWorkloadPerDevelopmentInDays(int nbDevDays, int nbDev) {
    	//System.out.println("(nbDevDays/nbDev) : " + (nbDevDays/nbDev) + "\n");
    	return (nbDevDays/nbDev);
    }
    
    public int getWorkloadPerProjectManagerInDays(int nbProjectManagementDays, int nbProjectManager) {
    	//System.out.println("(nbProjectManagementDays/nbProjectManager) : " + (nbProjectManagementDays/nbProjectManager) + "\n");
    	return (nbProjectManagementDays/nbProjectManager);
    }
}