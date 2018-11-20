package Controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class ProjetPlanningController {
    public static ProjetPlanningController instance;

    public static ProjetPlanningController getProjetPlanningControllerInstance(){
        if(instance == null){
            instance = new ProjetPlanningController();
        }
        return instance;
    }
    
    public void getProjetReport() {
    	// 1. Calculate number of days between two deadlines
    	
    	// 2. Calculate number of developpment days per developpers 
    	
    	// 3. Compare the deadlines days and the workload days
    	
    	// 4. If the deadlines days are superior than workload days : PROJECT IS OK  
    	
    	
    	// 5. If the workload days are superior then deadlines days : ADD ANOTHER DEV OR PROJECT MANAGER
    	
    }
    
    public int getNbDaysbetween() {
    	System.out.println(getWorkDaysUntil(LocalDateTime.of(2018, Month.DECEMBER, 25, 13, 37, 0)));
    	return 0; 
    }
        
    public int getWorkDaysUntil(LocalDateTime endDate) {
    	LocalDateTime currentTime = LocalDateTime.now();
    	int numberOfDaysBetween = (int) ChronoUnit.DAYS.between(currentTime, endDate);
    	int numberOfMounthBetween = (int) ChronoUnit.MONTHS.between(currentTime, endDate);
    	return numberOfDaysBetween - (numberOfMounthBetween * 2);
    }
    
    public int getNbDev(int nbDevDays, int nbDev) {
    	return (nbDevDays/nbDev);
    }
    
    public int getNbProjetManagement(int nbProjectManagementDays, int nbProjectManager) {
    	return (nbProjectManagementDays/nbProjectManager);
    }
    
    public int compare(int deadlines_date, int workload_date) {
    	if(deadlines_date > workload_date) {
    		return 1;
    	} else {
    		return 0;
    	}
    }
    
    public void compareProjects() {
    	
    }
}
