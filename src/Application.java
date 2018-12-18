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

		while (choix != 0) {
			System.out.println("Que voulez-vous faire ?\n" + "\n1 : Verifier le planning" + "\n2 : Ajouter un projet"
					+ "\n3 : Modifier l'efficience d'un projet" + "\n0 : Quitter\n");

			choix = sc.nextInt();

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

		sc.close();

	}

	public static void showPlanningMenu(Scanner sc) {
		System.out.println("Quels projets voulez-vous choisir pour vérifier le planning ?\n" + "\n1 : Un projet"
				+ "\n2 : Tous les projets" + "\n9 : Retour" + "\n0 : Quitter");

		int choix = sc.nextInt();

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
			return;
		case 0:
			quit();
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

		sc = new Scanner(System.in);
		int choix = sc.nextInt();

		switch (choix) {
		case 1:
			chooseProject("efficience", sc);
			break;
		case 2:
			allProjectEfficiencyModification(sc);
			break;
		case 9:
			return;
		case 0:
			quit();
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

		int choix = sc.nextInt();

		if (choix < ProjectList.size()) {
			if (action == "planning") {
				System.out.println("Le projet va etre realise dans les temps !");
				// todo : checkPlanning(projet);
			} else if (action == "efficience") {
				System.out.println("Entrez une nouvelle efficience ");
				Float efficiency = sc.nextFloat();
				System.out.println("Le projet a désormais une efficience de " + efficiency);
				// todo : changerEfficience(projet);
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
			quit();
		} else {
			System.out.println("Veuillez choisir une des action proposé !");
		}

	}

	public static void allProjectEfficiencyModification(Scanner sc) {
		System.out.println("Entrez une efficience ");
		Float efficiency = sc.nextFloat();
		DataBase.getDataBaseInstance().getEntreprise().getProjects().stream()
				.forEach((x) -> x.setEfficiency(efficiency));
		System.out.println("Efficience modifiée : ");
		displayProjects();
	}

	public static void efficiencyModification(Project project, Scanner sc) {
		System.out.println("Entrez en efficience ");
		Float efficiency = sc.nextFloat();
		project.setEfficiency(efficiency);
		System.out.println("La nouvelle efficience du projet est " + project.getEfficiency());

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

		Project newProject = new Project(name, dateDebut, dateFin, dateDebut, dateFin, dateFin, nbJourDeDev, nbJourGestion, efficience);

		DataBase.instance.addProject(newProject);

		System.out.println("Projet ajouté avec succés : ");

		displayBaseInformations();

	}

	public static LocalDate askDate(String parameter, Scanner sc) {
		Boolean isNotOk = true;
		int day = 0;
		int month = 0;
		int year = 0;

		while (isNotOk) {
			System.out.println("Entrez un jour valide.");
			day = sc.nextInt();
			isNotOk = (checkDate(day, "day")) ? false : true;
		}
		isNotOk = true;

		while (isNotOk) {
			System.out.println("Entrez un mois valide.");
			month = sc.nextInt();
			isNotOk = (checkDate(month, "month")) ? false : true;
		}

		isNotOk = true;

		while (isNotOk) {
			System.out.println("Entrez une annee valide.");
			year = sc.nextInt();
			isNotOk = (checkDate(year, "year" + parameter)) ? false : true;
		}

		LocalDate date = LocalDate.of(year, month, day);

		return date;
	}

	public static void quit() {
		System.out.println("A bientot !" + "Sarah MESBAH, Charly MRAZECK, Ethan WENDEL, Tom WENDEL"
				+ "Projet réalisé dans le cadre du cours d'ERP 3A. CNAM 2016-2018.");
		System.exit(1);
	}

	public static boolean checkDate(int nb, String parameter) {
		Boolean isOk = false;

		switch (parameter) {
		case "day":
			isOk = (nb != 0 && nb < 32) ? true : false;
			break;
		case "month":
			isOk = (nb != 0 && nb < 13) ? true : false;
			break;
		case "yeardebut":
			isOk = (nb != 0 && nb < 2019) ? true : false;
			break;
		case "yearfin":
			isOk = (nb != 0 && nb >= 2018) ? true : false;
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
		;
	}

	public static void displayEmployees() {
		DataBase.getDataBaseInstance().getEntreprise().getEmployees().stream().forEach(System.out::println);
		;
	}
}
