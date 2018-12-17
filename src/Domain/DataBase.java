package Domain;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
	public static DataBase instance;
	private Entreprise entreprise;

    public Entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(Entreprise entreprise) {
		this.entreprise = entreprise;
	}

	public static DataBase getDataBaseInstance(){
        if(instance == null){
            instance = new DataBase();
        }
        return instance;
    }

    public DataBase(){}


    public void init()
    {
    	List<Employee> employeeList = new ArrayList<Employee>();;
    	List<Project> projectList = new ArrayList<Project>();
        // employe
        try{
            InputStream flux = new FileInputStream("./Eevee/src/Ressources/Employe.txt");
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            while ((ligne = buff.readLine())!=null){
                if(ligne.contains("#"))
                    continue;
                String[] param = ligne.split(";");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(param[2], format);
                EmployeRole role = null;
                switch(param[3]) {
	                case "CEO":
	                	role = EmployeRole.CEO;
	                break;
	                case "PROJECT_MANAGER":
	                	role = EmployeRole.PROJECT_MANAGER;
	                break;
	                case "DEVELLOPER":
	                	role = EmployeRole.DEVELLOPER;
	                break;
	                case "TECHNICAL_MANAGER":
	                	role = EmployeRole.TECHNICAL_MANAGER;
	                break;
                }             
                
                Employee emp = new Employee(param[0], param[1], role, date);
                employeeList.add(emp);
            }
            buff.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        //project
        try{
            InputStream flux = new FileInputStream("./Eevee/src/Ressources/Projets.txt");
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            while ((ligne = buff.readLine())!=null){
                if(ligne.contains("#"))
                    continue;
                String[] param = ligne.split(";");
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate datedeb = LocalDate.parse(param[1], format);
                LocalDate datefin = LocalDate.parse(param[2], format);
                Project p = new Project(param[0], datedeb, datefin, Integer.parseInt(param[3]), Integer.parseInt(param[4]), Float.parseFloat(param[5]));
                projectList.add(p);
            }
            buff.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        
        this.entreprise = new Entreprise("CoursErp",employeeList, projectList);

    }
    
    public void addProject(Project p)
    {
    	List<Project> allproject = this.entreprise.getProjects();
    	allproject.add(p);
    	this.entreprise.setProjects(allproject);    	
    }

}
