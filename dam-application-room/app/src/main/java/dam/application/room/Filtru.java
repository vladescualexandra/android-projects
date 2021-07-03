package dam.application.room;

public enum Filtru {

    NUME("Nume"),

    TARIF("Tarif"),

    SALARIU("Salariu");

    private final String nume;

    Filtru(String nume) {
        this.nume = nume;
    }

    @Override
    public final String toString() {
        return nume;
    }
}
