package Controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

import Domain.EmployeRole;
import Domain.Employee;
import Domain.Entreprise;
import Domain.Project;
import Domain.RemainingDaysType;

public class ProjetPlanningController {
    public static ProjetPlanningController instance;

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
    
    public LocalDate getHigherDate(LocalDate date1, LocalDate date2) {
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
    	int workDaysUntil = getWorkDaysUntil(getHigherDate(project.getDateStartDev(), project.getDateStartMana()), project.getDateLivraison());
    	
    	System.out.println("Simulation du projet " + project.getName());
    	System.out.println("\n Le projet commence le : " + getHigherDate(project.getDateStartDev(), project.getDateStartMana()).toString());
    	System.out.println("\n Et finis le : " + getHigherDate(project.getDateEndDev(), project.getDateEndMana()).toString());
    	
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
    	//Entreprise entreprise_copy = entreprise; 
    	
    	String resultToDisplay = new String(); 
    	
    	if(resultProjectFeasibility.containsKey(RemainingDaysType.ACHIEVABLE_PROJECT)) {
    		resultToDisplay = "Le projet peut être réalisable.";
    		
    	} else if (resultProjectFeasibility.containsKey(RemainingDaysType.PROJECT_MANAGEMENT)) {
    		resultToDisplay = "Le projet ne peut pas être réalisable à cause du temps de gestion de projet.";

    		//ProjectFeasibilityWithMoreDev(entreprise_copy, project);
    	} else if(resultProjectFeasibility.containsKey(RemainingDaysType.DEVELOPMENT))  { 
    		resultToDisplay = "Le projet ne peut pas être réalisable à cause du temps de développement.";
    		//ProjectFeasibilityWithMoreDev(entreprise_copy, project);
    	} else {
    		resultToDisplay = "Erreur dans le projet !";
    	}
    	
    	return resultToDisplay;
    }
    
    public String ProjectFeasibilityWithMoreDev(Entreprise entreprise, Project project) {
		System.out.println("Nous allons ajouter un développeur.");
		Integer numberOfDevAdded = 0;
		HashMap<RemainingDaysType, Integer> resultNeeded = new HashMap<RemainingDaysType, Integer>();
		resultNeeded.put(RemainingDaysType.ACHIEVABLE_PROJECT, 0);
		
		while(simulateProjectFeasibility(entreprise, project) != resultNeeded) {
			numberOfDevAdded++;
			Employee emp_test = new Employee("TestDev", "TestDev", EmployeRole.DEVELLOPER, LocalDate.of(2010, Month.MAY, 15));
			entreprise.addEmployee(emp_test);
		}
		
		return "Le nombre de dev manquant est de " + numberOfDevAdded;
    }
    
    public String ProjectFeasibilityWithMoreMana(Entreprise entreprise, Project project) {
		System.out.println("Nous allons ajouter un manager.");
		Integer numberOfManagerAdded = 0;
		HashMap<RemainingDaysType, Integer> resultNeeded = new HashMap<RemainingDaysType, Integer>();
		resultNeeded.put(RemainingDaysType.ACHIEVABLE_PROJECT, 0);
		
		while(simulateProjectFeasibility(entreprise, project) != resultNeeded) {
			numberOfManagerAdded++;
			Employee emp_test = new Employee("TestMana", "TestMana", EmployeRole.PROJECT_MANAGER, LocalDate.of(2010, Month.MAY, 15));
			entreprise.addEmployee(emp_test);
		}
		
		return "Le nombre de mana manquant est de " + numberOfManagerAdded;
    }
    
    
    public String getAllProjectsFeasibility(Entreprise e) {
    	Entreprise entreprise = e;
    	entreprise.getProjects().sort((d1, d2) -> d1.getDateLivraison().compareTo(d2.getDateLivraison()));
    	String resultToDisplay = new String(); 
    	
    	for(int i = 0; i < entreprise.getProjects().size(); i++) {
    		resultToDisplay = resultToDisplay +  "\nProjet " + i + " : " + getProjectFeasibility(entreprise, entreprise.getProjects().get(i)); 
    		
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