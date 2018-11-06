package Domain;

import java.util.Date;

public class Project {

    private  String name;
    private Date dateStart;
    private  Date dateEnd;
    private int nbRemainingDevDay;
    private int nbRemainingManagementDay;
    private float efficiency;

    public Project(String name, Date dateStart, Date dateEnd, int nbRemainingDevDay, int nbRemainingManagementDay, float efficiency) {
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.nbRemainingDevDay = nbRemainingDevDay;
        this.nbRemainingManagementDay = nbRemainingManagementDay;
        this.efficiency = efficiency;
    }

    public Project(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getNbRemainingDevDay() {
        return nbRemainingDevDay;
    }

    public void setNbRemainingDevDay(int nbRemainingDevDay) {
        this.nbRemainingDevDay = nbRemainingDevDay;
    }

    public int getNbRemainingManagementDay() {
        return nbRemainingManagementDay;
    }

    public void setNbRemainingManagementDay(int nbRemainingManagementDay) {
        this.nbRemainingManagementDay = nbRemainingManagementDay;
    }

    public float getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(float efficiency) {
        this.efficiency = efficiency;
    }
}
