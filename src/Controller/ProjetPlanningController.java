package Controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import Domain.Entreprise;
import Domain.Project;

public class ProjetPlanningController {
    public static ProjetPlanningController instance;

    public static ProjetPlanningController getProjetPlanningControllerInstance(){
        if(instance == null){
            instance = new ProjetPlanningController();
        }
        return instance;
    }
    
    public void getProjetReport(Entreprise entreprise, Project project) {
    	
    	int workDaysUntil = getWorkDaysUntil(project.getDateStart(), project.getDateEnd());
    	int workloadPerDevelopmentInDays = getWorkloadPerDevelopmentInDays(project.getNbRemainingDevDay(), entreprise.getNumbersOfEmployees());
    	
    	int workloadPerProjectManagerInDays = getWorkloadPerProjectManagerInDays(project.getNbRemainingManagementDay(), entreprise.getNumbersOfEmployees());
    	
    	// 3. Compare the deadlines days and the workload days
    	if( )
    	
    	
    	// 4. If the deadlines days are superior than workload days : PROJECT IS OK  
    	
    	
    	// 5. If the workload days are superior then deadlines days : ADD ANOTHER DEV OR PROJECT MANAGER
    	
    }
      
    public int getWorkDaysUntil(LocalDateTime startDate, LocalDateTime endDate) {
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
    
    public boolean isDeadlineMatchable(int deadLineInDays, int workloadInDays) {
    	return  workloadInDays > deadLineInDays;
    }

}
