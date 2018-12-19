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
    
    public HashMap<RemainingDaysType, Integer> simulateProjectFeasibility(Entreprise entreprise, Project project) {
    	HashMap<RemainingDaysType, Integer> remainingDays = new HashMap<RemainingDaysType, Integer>();
    	int workDaysUntil = getWorkDaysUntil(project.getDateStart(), project.getDateEnd());
    	
    	int workloadPerDevelopmentInDays = 0;
    	int workloadPerProjectManagerInDays = 0;

    	workloadPerDevelopmentInDays = getWorkloadPerDevelopmentInDays(project.getNbRemainingDevDay(), entreprise.getNumbersOfDeveloppers());
        workloadPerProjectManagerInDays = getWorkloadPerProjectManagerInDays(project.getNbRemainingManagementDay(), entreprise.getNumbersOfManagers());

    	if(workloadPerDevelopmentInDays < workDaysUntil) {
    		remainingDays.put(RemainingDaysType.DEVELOPMENT, workloadPerDevelopmentInDays - workDaysUntil);
    	} else if(workloadPerProjectManagerInDays < workDaysUntil) {
    		remainingDays.put(RemainingDaysType.PROJECT_MANAGEMENT, workloadPerProjectManagerInDays - workDaysUntil);
    	} else {
    		remainingDays.put(RemainingDaysType.ACHIEVABLE_PROJECT, 0);
    	}
    	
    	return remainingDays; 	
    }

    public String getProjectFeasibility(Entreprise entreprise, Project project) {
    	HashMap<RemainingDaysType, Integer> resultProjectFeasibility = simulateProjectFeasibility(entreprise, project);
    	
    	String resultToDisplay = new String(); 
    	
    	if(resultProjectFeasibility.containsKey(RemainingDaysType.ACHIEVABLE_PROJECT)) {
    		resultToDisplay = "Super ! Le projet peut être réalisable dans les temps !";
    		
    	} else if (resultProjectFeasibility.containsKey(RemainingDaysType.PROJECT_MANAGEMENT)) {
    		resultToDisplay = "Le projet ne peut pas être réalisable à cause du temps de gestion de projet.";

    		//ProjectFeasibilityWithMoreDev(entreprise, project);
    	} else if(resultProjectFeasibility.containsKey(RemainingDaysType.DEVELOPMENT))  { 
    		resultToDisplay = "Le projet ne peut pas être réalisable à cause du temps de développement.";
    		ProjectFeasibilityWithMoreDev(entreprise, project);
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
		}
		
		return "Le nombre de dev manquant est de +"+ numberOfDevAdded;
    }
    
    
    public String getAllProjectsFeasibility(Entreprise entreprise) {
    	String resultToDisplay = new String(); 
    	
    	for(Project project : entreprise.getProjects()){
    		resultToDisplay = getProjectFeasibility(entreprise, project); 
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