import Controller.ProjetPlanningController;
import Domain.DataBase;

public class ApplicationForTests {

	public static void main(String[] args) {
		ProjetPlanningController pc = ProjetPlanningController.getProjetPlanningControllerInstance();
		
		DataBase.getDataBaseInstance().init();
		
		//System.out.println("ONE PROJECT getProjectFeasibility : " + pc.getProjectFeasibility(DataBase.getDataBaseInstance().getEntreprise(), DataBase.getDataBaseInstance().getEntreprise().getProjects().get(1)) + "\n");
		System.out.println("\n");
		System.out.println("ALL PROJECTS getAllProjectsFeasibility : " + pc.getAllProjectsFeasibility(DataBase.getDataBaseInstance().getEntreprise()) + "\n");
	}

}
