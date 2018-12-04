package Domain;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataBase {
    private List<Employee> EmployeeList;
    private List<Project> ProjectList;


    public DataBase()
    {
        this.EmployeeList = new List<Employee>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Employee> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Employee employee) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Employee> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Employee> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Employee get(int index) {
                return null;
            }

            @Override
            public Employee set(int index, Employee element) {
                return null;
            }

            @Override
            public void add(int index, Employee element) {

            }

            @Override
            public Employee remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Employee> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Employee> listIterator(int index) {
                return null;
            }

            @Override
            public List<Employee> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        this.ProjectList = new List<Project>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Project> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Project project) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Project> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Project> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Project get(int index) {
                return null;
            }

            @Override
            public Project set(int index, Project element) {
                return null;
            }

            @Override
            public void add(int index, Project element) {

            }

            @Override
            public Project remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Project> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Project> listIterator(int index) {
                return null;
            }

            @Override
            public List<Project> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
    }


    public List<Employee> getEmployeeList() {
        return EmployeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        EmployeeList = employeeList;
    }

    public List<Project> getProjectList() {
        return ProjectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.ProjectList = projectList;
    }

    public void init()
    {

        // employe
        try{
            InputStream flux = new FileInputStream("./src/Ressources/Employe.txt");
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            while ((ligne = buff.readLine())!=null){
                if(ligne.contains("#"))
                    continue;
                String[] param = ligne.split(";");
                SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
                Date date = format.parse(param[2]);
                Employee emp = new Employee(param[0], param[1], date);
                EmployeeList.add(emp);
            }
            buff.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        //project
        try{
            InputStream flux = new FileInputStream("./src/Ressources/Project.txt");
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            while ((ligne = buff.readLine())!=null){
                if(ligne.contains("#"))
                    continue;
                String[] param = ligne.split(";");
                SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy");
                Date datedeb = format.parse(param[1]);
                Date datefin = format.parse(param[2]);
                Project p = new Project(param[0], datedeb, datefin, Integer.parseInt(param[3]), Integer.parseInt(param[4]), Float.parseFloat(param[3]));
                ProjectList.add(p);
            }
            buff.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

}
