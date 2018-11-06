import Controller.ProjetPlanningController;

public class Application {

    public static void main (String[] args){
        System.out.println("Bienvenue dans notre ERP");
        ProjetPlanningController planningController = ProjetPlanningController.getProjetPlanningControllerInstance();
        planningController.getProjetReport();
    }
}
