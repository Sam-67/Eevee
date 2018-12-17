package Controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

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
    
    public HashMap<RemainingDaysType, Integer> simulateProjectFeasibility(Entreprise entreprise, Project project, HashMap<RemainingDaysType, Integer> numberOfMissingEmployees) {
    	HashMap<RemainingDaysType, Integer> remainingDays = new HashMap<RemainingDaysType, Integer>();
    	int workDaysUntil = getWorkDaysUntil(project.getDateStart(), project.getDateEnd());
    	
    	int workloadPerDevelopmentInDays = 0;
    	int workloadPerProjectManagerInDays = 0;
    	
    	if(numberOfMissingEmployees.containsKey(RemainingDaysType.DEVELOPMENT)) {
    		workloadPerDevelopmentInDays = getWorkloadPerDevelopmentInDays(project.getNbRemainingDevDay(), entreprise.getNumbersOfDeveloppers() + numberOfMissingEmployees.get(RemainingDaysType.DEVELOPMENT));
        	workloadPerProjectManagerInDays = getWorkloadPerProjectManagerInDays(project.getNbRemainingManagementDay(), entreprise.getNumbersOfManagers());
    	} else if(numberOfMissingEmployees.containsKey(RemainingDaysType.PROJECT_MANAGEMENT)) {
    		workloadPerDevelopmentInDays = getWorkloadPerDevelopmentInDays(project.getNbRemainingDevDay(), entreprise.getNumbersOfDeveloppers());
        	workloadPerProjectManagerInDays = getWorkloadPerProjectManagerInDays(project.getNbRemainingManagementDay(), entreprise.getNumbersOfManagers()  + numberOfMissingEmployees.get(RemainingDaysType.PROJECT_MANAGEMENT));
    	} else {
    		workloadPerDevelopmentInDays = getWorkloadPerDevelopmentInDays(project.getNbRemainingDevDay(), entreprise.getNumbersOfDeveloppers());
        	workloadPerProjectManagerInDays = getWorkloadPerProjectManagerInDays(project.getNbRemainingManagementDay(), entreprise.getNumbersOfManagers());
    	}
    	
    	if(workloadPerDevelopmentInDays < workDaysUntil) {
    		remainingDays.put(RemainingDaysType.DEVELOPMENT, workloadPerDevelopmentInDays - workDaysUntil);
    	} else if(workloadPerProjectManagerInDays < workDaysUntil) {
    		remainingDays.put(RemainingDaysType.PROJECT_MANAGEMENT, workloadPerProjectManagerInDays - workDaysUntil);
    	} else {
    		remainingDays.put(RemainingDaysType.ACHIEVABLE_PROJECT, 0);
    	}
    	
    	return remainingDays; 	
    }

    public String getProjectFeasibility(Entreprise entreprise, Project project, HashMap<RemainingDaysType, Integer> numberOfMissingEmployees) {
    	HashMap<RemainingDaysType, Integer> resultProjectFeasibility = simulateProjectFeasibility(entreprise, project, numberOfMissingEmployees);
    	
    	String resultToDisplay = new String(); 
    	
    	if(resultProjectFeasibility.containsKey(RemainingDaysType.ACHIEVABLE_PROJECT)) {
    		resultToDisplay = "Super ! Le projet peut être réalisable dans les temps !";
    		
    	} else if (resultProjectFeasibility.containsKey(RemainingDaysType.DEVELOPMENT)) {
    		resultToDisplay = "Le projet ne peut pas être réalisable à cause du temps de développement, il reste : "+resultProjectFeasibility.toString();
    		HashMap<RemainingDaysType, Integer> addingDeveloppers = new HashMap<RemainingDaysType, Integer>();
    		System.out.println("Nous allons ajouter un développeur, il reste : "+resultProjectFeasibility.toString());
    		addingDeveloppers.put(RemainingDaysType.DEVELOPMENT, 1);
    		getProjectFeasibility(entreprise, project, addingDeveloppers);
    		
    	} else if(resultProjectFeasibility.containsKey(RemainingDaysType.PROJECT_MANAGEMENT))  { 
    		resultToDisplay = "Le projet ne peut pas être réalisable à cause du temps de gestion de projet.";
    		HashMap<RemainingDaysType, Integer> addingProjectManagers = new HashMap<RemainingDaysType, Integer>();
    		System.out.println("Nous allons ajouter un chef de projet.");
    		addingProjectManagers.put(RemainingDaysType.PROJECT_MANAGEMENT, 1);
    		getProjectFeasibility(entreprise, project, addingProjectManagers);
    		
    	} else {
    		resultToDisplay = "Erreur dans le projet !";
    	}
    	
    	return resultToDisplay;
    }
    
    public String getAllProjectsFeasibility(Entreprise entreprise) {
    	String resultToDisplay = new String(); 
    	
    	for(Project project : entreprise.getProjects()){
    		resultToDisplay = getProjectFeasibility(entreprise, project, new HashMap<RemainingDaysType, Integer>()); 
    	}
    	
    	return resultToDisplay;
    }
      
    public int getWorkDaysUntil(LocalDate startDate, LocalDate endDate) {
    	int numberOfDaysBetween = (int) ChronoUnit.DAYS.between(startDate, endDate);
    	int numberOfMounthBetween = (int) ChronoUnit.MONTHS.between(startDate, endDate);
    	return numberOfDaysBetween - (numberOfMounthBetween * 2);
    }
    
    public int getWorkloadPerDevelopmentInDays(int nbDevDays, int nbDev) {
    	return (nbDevDays/nbDev);
    }
    
    public int getWorkloadPerProjectManagerInDays(int nbProjectManagementDays, int nbProjectManager) {
    	return (nbProjectManagementDays/nbProjectManager);
    }
}