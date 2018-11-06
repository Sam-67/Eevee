package Domain;

public enum Roles {
    PDG("PDG"),
    Chef("Chef de projet"),
    Dev("Devellopeur"),
    Tech("Responsable technique");

    private String name;

    private Roles(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

