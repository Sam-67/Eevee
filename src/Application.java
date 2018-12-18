import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import Controller.ProjetPlanningController;
import Domain.DataBase;
import Domain.Project;

public class Application {

	public static void main(String[] args) {
		ProjetPlanningController planningController = ProjetPlanningController.getProjetPlanningControllerInstance();

		System.out.println("Bienvenue dans le mini ERP EEVEE. \n");
		DataBase.getDataBaseInstance().init();
		System.out.println("Database initialisée, voici les informations de base : ");
		displayBaseInformations();
		Scanner sc = new Scanner(System.in);
		int choix = 1;
		mainMenu(sc);

		sc.close();

	}
	
	
	
	public static void mainMenu(Scanner sc) {	
		int choix = 0;
		System.out.println("Que voulez-vous faire ?\n" + "\n1 : Verifier le planning" + "\n2 : Ajouter un projet"
				+ "\n3 : Modifier l'efficience d'un projet" + "\n0 : Quitter\n");
		
		String choixString = sc.next();
		try {
			 choix = Integer.parseInt(choixString);
		}
		catch(Exception e) {
			System.out.println("Mauvais format ou choix indisponible, veuillez choisir un des choix proposé");
			mainMenu(sc);
		}


		switch (choix) {
		case 1:
			showPlanningMenu(sc);
			break;
		case 2:
			addProject(sc);
			break;
		case 3:
			showModifyEfficiencyMenu(sc);
			break;
		case 0:
			System.out.println("Vous quittez l'ERP.\n");
			break;
		default:
			System.out.println("Veuillez selectionner un des choix proposés.\n");
			break;
		}

	}
		


	public static void showPlanningMenu(Scanner sc) {
		System.out.println("Quels projets voulez-vous choisir pour vérifier le planning ?\n" + "\n1 : Un projet"
				+ "\n2 : Tous les projets" + "\n9 : Retour" + "\n0 : Quitter");
		
		String choixString = sc.next();
		int choix = 0;
		try {
			  choix = Integer.parseInt(choixString);
		}
		catch(Exception e) {
			System.out.println("Mauvais format, veuillez choisir un des choix proposé");
			showPlanningMenu(sc);
			
		}

		switch (choix) {
		case 1:
			System.out.println("Fonctionnalité non développée pour l'instant.\n");
			// todo : checkPlanning(touslesprojet);
			chooseProject("planning", sc);

			break;
		case 2:
			System.out.println("Fonctionnalité non développée pour l'instant.\n");
			showPlanningMenu(sc);
			break;
		case 9:
			mainMenu(sc);
		case 0:
			quit(sc);
			break;
		default:
			System.out.println("Veuillez choisir un des choix proposé !\n");
			showPlanningMenu(sc);
			break;
		}

	}
	
	

	public static void showModifyEfficiencyMenu(Scanner sc) {
		System.out.println("Quelle efficience voulez-vous changer? \n" + "\n1 : Celle d'un projet"
				+ "\n2 : Celle de tous les projets" + "\n9 : Retour" + "\n0 : Quitter");

		
		String choixString = sc.next();
		int choix = 0;
		try {
			  choix = Integer.parseInt(choixString);
		}
		catch(Exception e) {
			System.out.println("Mauvais format, veuillez choisir un des choix proposé");
			showModifyEfficiencyMenu(sc);
			
		}
		

		switch (choix) {
		case 1:
			chooseProject("efficience", sc);
			break;
		case 2:
			allProjectEfficiencyModification(sc);
			break;
		case 9:
			mainMenu(sc);
		case 0:
			quit(sc);
			break;
		default:
			System.out.println("Veuillez choisir un des choix proposé.\n");
			showModifyEfficiencyMenu(sc);
			break;
		}

	}
	

	public static void chooseProject(String action, Scanner sc) {
		// To do : lister la liste des projets
		System.out.println("Selectionnez un projet ");
		List<Project> ProjectList = DataBase.instance.getEntreprise().getProjects();

		int i = 1;
		for (Project p : ProjectList) {
			if (i == 9)
				i++;

			System.out.println(i + " : " + p.getName());
			i++;
		}

		System.out.println("9 : Retour");
		System.out.println("0 : quitter");

		String choixString = sc.next();
		int choix = 0;
		try {
			  choix = Integer.parseInt(choixString);
		}
		catch(Exception e) {
			System.out.println("Mauvais format ou choix indisponible, veuillez choisir un des choix proposé");
			showPlanningMenu(sc);
			
		}

		if (choix < ProjectList.size()) {
			if (action == "planning") {
				System.out.println("Le projet va etre realise dans les temps !");
				// todo : checkPlanning(projet);
			} else if (action == "efficience") {
            	Project project = ProjectList.get(choix-1);
            	efficiencyModification(project, sc);
			} else {
				System.out.println("Veuillez choisir une des action proposé !");
				chooseProject(action, sc);
			}
		} else if (choix == 9) {
			if (action == "planning") {
				showPlanningMenu(sc);
			} else if (action == "efficience") {
				showModifyEfficiencyMenu(sc);
			}
		} else if (choix == 0) {
			quit(sc);
		} else {
			System.out.println("Veuillez choisir une des action proposé !");
		}

	}

	public static void allProjectEfficiencyModification(Scanner sc) {
		System.out.println("Entrez une efficience ");
		sc.nextLine();
		String str = sc.nextLine();
		try {
			Float efficiency= Float.parseFloat(str);
			DataBase.getDataBaseInstance().getEntreprise().getProjects().stream()
				.forEach((x) -> x.setEfficiency(efficiency));
			System.out.println("Efficience modifiée : ");
			displayProjects();
		}catch (NumberFormatException nfe) {
			System.out.println("Ce n'est pas un nombre décimal");
		}

		mainMenu(sc);
	}

	public static void efficiencyModification(Project project, Scanner sc) {
		System.out.println("Entrez en efficience ");
		sc.nextLine();
		String str = sc.nextLine();
			try {
				Float efficiency= Float.parseFloat(str);
				project.setEfficiency(efficiency);
				System.out.println("La nouvelle efficience du projet est " + project.getEfficiency());
				displayProjects();
			} catch (NumberFormatException nfe) {
				System.out.println("Ce n'est pas un nombre décimal");
			}
	}

	public static void addProject(Scanner sc) {

		System.out.println("Entrez le nom du nouveau projet");
		String name = sc.next();

		System.out.println("Entrez la date de début du nouveau projet");
		LocalDate dateDebut = askDate("debut", sc);

		System.out.println("Entrez la date de fin du nouveau projet");
		LocalDate dateFin = askDate("fin", sc);

		while (dateFin.compareTo(dateDebut) < 0) {
			System.out.println("La date de debut dois etre inférieur a la date de fin!");

			System.out.println("Entrez la date de début du nouveau projet");
			dateDebut = askDate("debut", sc);

			System.out.println("Entrez la date de fin du nouveau projet");
			dateFin = askDate("fin", sc);
		}

		System.out.println("Entrez le nombre de jour de developpement");
		int nbJourDeDev = sc.nextInt();

		System.out.println("Entrez le nombre de jour de gestion de projet");
		int nbJourGestion = sc.nextInt();

		System.out.println("Entrez l'efficience du nouveau projet");
		float efficience = sc.nextFloat();

		Project newProject = new Project(name, dateDebut, dateFin, nbJourDeDev, nbJourGestion, efficience);

		DataBase.instance.addProject(newProject);

		System.out.println("Projet ajouté avec succés : ");

		displayBaseInformations();
		mainMenu(sc);

	}

	public static LocalDate askDate(String parameter, Scanner sc) {
		Boolean isNotOk = true;
		String day = "";
		String month = "";
		String year = "";

		while (isNotOk) {
			System.out.println("Entrez un jour valide.");
			day = sc.next();
			isNotOk = (checkDate(day, "day")) ? false : true;
		}
		isNotOk = true;

		while (isNotOk) {
			System.out.println("Entrez un mois valide.");
			month = sc.next();
			isNotOk = (checkDate(month, "month")) ? false : true;
		}

		isNotOk = true;

		while (isNotOk) {
			System.out.println("Entrez une annee valide.");
			year = sc.next();
			isNotOk = (checkDate(year, "year" + parameter)) ? false : true;
		}
		
		int y = Integer.parseInt(year);
		int m = Integer.parseInt(month);
		int d = Integer.parseInt(day);

		LocalDate date = LocalDate.of(y, m, d);

		return date;
	}

	public static void quit(Scanner sc) {
		System.out.println("A bientot !" + "Sarah MESBAH, Charly MRAZECK, Ethan WENDEL, Tom WENDEL"
				+ "Projet réalisé dans le cadre du cours d'ERP 3A. CNAM 2016-2018.");
		sc.close();
		System.exit(1);
	}

	public static boolean checkDate(String nb, String parameter) {
		Boolean isOk = false;

		int nombre = 0;
		try {
			  nombre = Integer.parseInt(nb);
		}
		catch(Exception e) {
			return isOk = false; 
			
		}

		switch (parameter) {
		case "day":
			isOk = (nombre != 0 && nombre < 32) ? true : false;
			break;
		case "month":
			isOk = (nombre != 0 && nombre < 13) ? true : false;
			break;
		case "yeardebut":
			isOk = (nombre != 0) ? true : false;
			break;
		case "yearfin":
			isOk = (nombre != 0) ? true : false;
			break;
		}
		return isOk;
	}

	public static void displayBaseInformations() {
		System.out.println("Projets : ");
		displayProjects();
		System.out.println("Employés : ");
		displayEmployees();
	}

	public static void displayProjects() {
		DataBase.getDataBaseInstance().getEntreprise().getProjects().stream().forEach(System.out::println);
		
	}

	public static void displayEmployees() {
		DataBase.getDataBaseInstance().getEntreprise().getEmployees().stream().forEach(System.out::println);
		
	}
}
