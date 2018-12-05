package Domain;

import java.time.LocalDateTime;
public class Project {

    private String name;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private int nbRemainingDevDay;
    private int nbRemainingManagementDay;
    private float efficiency;

    public Project(String name, LocalDateTime datedeb, LocalDateTime datefin, int nbRemainingDevDay, int nbRemainingManagementDay, float efficiency) {
        this.name = name;
        this.dateStart = datedeb;
        this.dateEnd = datefin;
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

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
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
