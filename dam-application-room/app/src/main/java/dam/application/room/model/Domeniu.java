package dam.application.room.model;

public enum Domeniu {
    DOMENIU_1("Domeniu-1"),
    DOMENIU_2("Domeniu-2");

    String nume;

    Domeniu(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return nume;
    }
}
