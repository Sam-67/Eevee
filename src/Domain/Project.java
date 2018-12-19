package Domain;

import java.time.LocalDate;

public class Project {

	private String name;
	private LocalDate dateStartDev;
	private LocalDate dateEndDev;
	private LocalDate dateStartMana;
	private LocalDate dateEndMana;
	private LocalDate dateLivraison;
	private int nbRemainingDevDay;
	private int nbRemainingManagementDay;
	private float efficiency;

	public Project(String name, LocalDate datedebdev, LocalDate datefindev, LocalDate datedebmana,
			LocalDate datefinmana, LocalDate datelivraison, int nbRemainingDevDay, int nbRemainingManagementDay,
			float efficiency) {
		this.name = name;
		this.dateStartDev = datedebdev;
		this.dateEndDev = datefindev;
		this.dateStartMana = datedebmana;
		this.dateEndMana = datefinmana;
		this.dateLivraison = datelivraison;
		this.nbRemainingDevDay = nbRemainingDevDay;
		this.nbRemainingManagementDay = nbRemainingManagementDay;
		this.efficiency = efficiency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateStartDev() {
		return dateStartDev;
	}

	public void setDateStartDev(LocalDate dateStartDev) {
		this.dateStartDev = dateStartDev;
	}

	public LocalDate getDateEndDev() {
		return dateEndDev;
	}

	public void setDateEndDev(LocalDate dateEndDev) {
		this.dateEndDev = dateEndDev;
	}

	public LocalDate getDateStartMana() {
		return dateStartMana;
	}

	public void setDateStartMana(LocalDate dateStartMana) {
		this.dateStartMana = dateStartMana;
	}

	public LocalDate getDateEndMana() {
		return dateEndMana;
	}

	public void setDateEndMana(LocalDate dateEndMana) {
		this.dateEndMana = dateEndMana;
	}

	public LocalDate getDateLivraison() {
		return dateLivraison;
	}

	public void setDateLivraison(LocalDate dateLivraison) {
		this.dateLivraison = dateLivraison;
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
		return "Project [name=" + name + ", dateStartDev=" + dateStartDev + ", dateEndDev=" + dateEndDev
				+ ", nbRemainingDevDay=" + nbRemainingDevDay + ", nbRemainingManagementDay=" + nbRemainingManagementDay
				+ ", efficiency=" + efficiency + "]";
	}

}
