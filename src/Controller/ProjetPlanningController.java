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
    	System.out.println(getWorkDaysUntil(LocalDateTime.of(2018, Month.DECEMBER, 25, 13, 37, 0)));
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
    
}
