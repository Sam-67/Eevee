import Controller.ProjetPlanningController;
import Domain.Project;

import java.text.SimpleDateFormat;
import java.util.*;

public class Application {

    public static void main (String[] args){
        System.out.println("Bienvenue dans notre ERP");
        ProjetPlanningController planningController = ProjetPlanningController.getProjetPlanningControllerInstance();
        planningController.getProjetReport();

        System.out.println("Que voulez-vous faire");

        Scanner sc = new Scanner(System.in);
        int choix = 1;


        while(choix != 0)
        {
            System.out.println("1: Verifier le planning");
            System.out.println("2 : Ajoutez un projet ");
            System.out.println("3 : Modifiez l'éfficience d'un projet");
            System.out.println("0 :  Quitter");

            choix = sc.nextInt();

            switch (choix){
                case 1 :
                    showPlanningMenu();
                    break;
                case 2 :
                    addProject();
                    break;
                case 3 :
                    showModifyEfficiencyMenu();
                    break;
                case 0 :
                    System.out.println("Vous quittez l'ERP");
                    break;
                default:
                    System.out.println("Veuillez selectionnez un des choix proposés");

            }

        }
    }


    public static void showPlanningMenu() {
        System.out.println("1 : Vérifier le planning de tout les projet ?");
        System.out.println("2 : Un seul projet ");
        System.out.println("0 :  Quitter");

        Scanner sc = new Scanner(System.in);

        int choix = sc.nextInt();

        switch(choix){
            case 1 :
                System.out.println("Tout les projet vont être réalisé dans les temps !");
                // todo : checkPlanning(toutlesprojet);
                break;
            case 2 :
                chooseProject("planning");
                break;
            case 9 :
                return;
            case 0 :
                quit();
                break;
            default:
                System.out.println("Veuillez choisir un des choix proposé !");
                showPlanningMenu();
                break;
        }
    }

    public static void showModifyEfficiencyMenu() {
        System.out.println("1 : Modifiez l'efficience de tout les projet ?");
        System.out.println("2 : Un seul projet ");
        System.out.println("9 :  retour");
        System.out.println("0 :  Quitter");

        Scanner sc = new Scanner(System.in);

        int choix = sc.nextInt();

        switch(choix){
            case 1 :
                System.out.println("Entrez une nouvelle efficience ");
                Float efficiency =  sc.nextFloat();
                System.out.println("La noouvelle efficience de tout les projet est " + efficiency);
                // todo : modification de l'efficience(toutlesprojet);
                break;
            case 2 :
                chooseProject("efficience");
                break;
            case 9 :
                return;
            case 0 :
                quit();
                break;
            default:
                System.out.println("Veuillez choisir un des choix proposé !");
                showModifyEfficiencyMenu();
                break;
        }
    }

    public static void chooseProject( String action) {
        // To do : lister la liste des projet ajouté
        System.out.println("Selectionnez un projet ");
        System.out.println("1 : Projet 1");
        System.out.println("2 : Projet 2");
        System.out.println("3 : Projet 3");
        System.out.println("9 : Retour");
        System.out.println("9 : Retour");

        Scanner sc = new Scanner(System.in);
        int choix = sc.nextInt();

        if(choix == 1 || choix == 2 || choix == 3) // en dur a changer par (choix < nbProject))
        {
            if(action == "planning")
            {
                System.out.println("Le projet " + choix + " va être réalisé dans les temps !");
                // todo : checkPlanning(projet);
            }
            else if(action == "efficience")
            {
                System.out.println("Entrez une nouvelle efficience ");
                Float efficiency =  sc.nextFloat();
                System.out.println("Le projet " + choix + " a désomrmais une éfficience de " + efficiency);
                // todo : changerEfficience(projet);
            }
            else{
            System.out.println("Veuillez une des action proposée !");
            chooseProject(action);
        }
        }
        else if(choix == 9)
        {
            return;
        }
        else if(choix == 0){
            System.exit(1);
        }

    }

    public static void addProject(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Entrez le nom du nouveau projet");
        String name = sc.next();

        System.out.println("Entrez la date de début du nouveau projet");
        Date dateDebut = askDate();

        System.out.println("Entrez la date de fin du nouveau projet");
        Date dateFin = askDate();

        int nbJourDeDev = 10; // toDo : calcul

        int nbJourGestion = 10; // todo : calcul

        System.out.println("Entrez l'efficience du nouveau projet");
        float efficience = sc.nextFloat();

        Project newProject = new Project(name, dateDebut, dateFin, nbJourDeDev, nbJourGestion, efficience);

        System.out.println("Projet ajouté avec succès : ");
        System.out.println("(nom : " + newProject.getName());
        System.out.println("(date de debut  : " + newProject.getDateStart());
        System.out.println("(date de fin : " + newProject.getDateStart());
        System.out.println("(nombre de jour restant de developpement : " + newProject.getNbRemainingDevDay());
        System.out.println("(nombre de jour restant de gestion : " + newProject.getNbRemainingManagementDay());
        System.out.println("(efficience : " +  newProject.getEfficiency());

    }


    public static Date askDate() {
        Boolean isNotOk = true;
        Scanner sc = new Scanner(System.in);
        int day = 0;
        int month = 0;
        int year = 0;

        while(isNotOk){
            System.out.println("Entrez un jour valide");
            day = sc.nextInt();
            isNotOk = (checkDate(day, "day")) ? false : true;
        }

        while(isNotOk){
            System.out.println("Entrez un mois valide");
            month = sc.nextInt();
            isNotOk = (checkDate(month, "month")) ? false : true;
        }

        while(isNotOk){
            System.out.println("Entrez une année valide");
            year = sc.nextInt();
            isNotOk = (checkDate(year, "year")) ? false : true;
        }

        Date date = new Date(day,month,year);

        return date;
    }

    public static void quit(){
        System.out.println("A bientot !");
        System.exit(1);
    }

    public static boolean checkDate(int nb, String parameter){
        Boolean isOk = false;

        switch(parameter){
            case "day" :
                isOk = (nb != 0 && nb < 32) ? true : false;
                break;
            case "month" :
                isOk = (nb != 0 && nb < 13) ? true : false;
                break;
            case "year" :
                isOk = (nb != 0 && nb <= new Date().getYear()) ? true : false;
                break;
        }

        return isOk;
    }
}
