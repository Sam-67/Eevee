package Controller;

public class ProjetPlanningController {
    public static ProjetPlanningController instance;

    public static ProjetPlanningController getProjetPlanningControllerInstance(){
        if(instance == null){
            instance = new ProjetPlanningController();
        }
        return instance;
    }

    public void getProjetReport(){
        System.out.println("Ce projet va être réalisé avec succès, bravo !");
    }
}
