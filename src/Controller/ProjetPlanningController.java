package Controller;

public class ProjetPlanningController {
    public static ProjetPlanningController instance;

    public static ProjetPlanningController getProjetPlanningControllerInstance(){
        if(instance == null){
            instance = new ProjetPlanningController();
        }
        return instance;
    }
    
    public int getNbDaysbetween() {
    	return 0; 
    }
    
    public int getNbDev(int nbDevDays, int nbDev) {
    	return (nbDevDays/nbDev);
    }
    
    public int getNbProjetManagement(int nbProjectManagementDays, int nbProjectManager) {
    	return (nbProjectManagementDays/nbProjectManager);
    }
    
    
    
    
    
}
