public class Employe {

    private string lastName;
    private string firsName;
    private Date dateReadyToWork;

    public Employe(){

    }

    public Employe(string lastName, string firsName, Date dateReadyToWork) {
        this.lastName = lastName;
        this.firsName = firsName;
        this.dateReadyToWork = dateReadyToWork;
    }


    public string getLastName() {
        return lastName;
    }

    public void setLastName(string lastName) {
        this.lastName = lastName;
    }

    public string getFirsName() {
        return firsName;
    }

    public void setFirsName(string firsName) {
        this.firsName = firsName;
    }

    public Date getDateReadyToWork() {
        return dateReadyToWork;
    }

    public void setDateReadyToWork(Date dateReadyToWork) {
        this.dateReadyToWork = dateReadyToWork;
    }



}
