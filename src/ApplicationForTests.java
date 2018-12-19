import Controller.ProjetPlanningController;
import Domain.DataBase;

public class ApplicationForTests {

	public static void main(String[] args) {
		ProjetPlanningController pc = ProjetPlanningController.getProjetPlanningControllerInstance();

		DataBase.getDataBaseInstance().init();

		//System.out.println(planningController.getProjectFeasibility(DataBase.getDataBaseInstance().getEntreprise(), DataBase.instance.getEntreprise().getProjects().get(choix-1)) + "\n");
		System.out.println(pc.getAllProjectsFeasibility(DataBase.getDataBaseInstance().getEntreprise()) + "\n");
	}

}
