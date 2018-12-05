package Domain;

import java.time.LocalDate;
public class Project {

    private String name;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int nbRemainingDevDay;
    private int nbRemainingManagementDay;
    private float efficiency;

    public Project(String name, LocalDate datedeb, LocalDate datefin, int nbRemainingDevDay, int nbRemainingManagementDay, float efficiency) {
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

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
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

	@Override
	public String toString() {
		return "Project [name=" + name + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", nbRemainingDevDay="
				+ nbRemainingDevDay + ", nbRemainingManagementDay=" + nbRemainingManagementDay + ", efficiency="
				+ efficiency + "]";
	}
    
    
}
