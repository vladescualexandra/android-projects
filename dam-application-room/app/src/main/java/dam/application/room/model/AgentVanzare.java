package dam.application.room.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "agenti")
public class AgentVanzare implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "name")
    private String nume;// EditText

    @ColumnInfo(name = "tarif")
    private int tarif; // SeekBar

    @ColumnInfo(name = "domeniu")
    private String domeniu; // RadioButton

    @ColumnInfo(name = "data")
    private String data;// DatePicker

    @ColumnInfo(name = "timp")
    private String timp;// TimePicker

    @ColumnInfo(name = "salariu")
    private int salariu; // Spinner

    @ColumnInfo(name = "particular")
    private boolean particular; // CheckBox

    public AgentVanzare() {
    }

    @Ignore
    public AgentVanzare(String nume, int tarif, String domeniu, String data, String timp, int salariu, boolean particular) {
        this.nume = nume;
        this.tarif = tarif;
        this.domeniu = domeniu;
        this.data = data;
        this.timp = timp;
        this.salariu = salariu;
        this.particular = particular;

    }

    public AgentVanzare(long id, String nume, int tarif, String domeniu, String data, String timp, int salariu, boolean particular) {
        this.id = id;
        this.nume = nume;
        this.tarif = tarif;
        this.domeniu = domeniu;
        this.data = data;
        this.timp = timp;
        this.salariu = salariu;
        this.particular = particular;
    }

    @Override
    public String toString() {
        return "AgentVanzare{" +
                "nume='" + nume + '\'' +
                ", tarif=" + tarif +
                ", domeniu='" + domeniu + '\'' +
                ", data='" + data + '\'' +
                ", timp='" + timp + '\'' +
                ", salariu=" + salariu +
                ", particular=" + particular +
                '}';
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getTarif() {
        return tarif;
    }

    public void setTarif(int tarif) {
        this.tarif = tarif;
    }

    public String getDomeniu() {
        return domeniu;
    }

    public void setDomeniu(String domeniu) {
        this.domeniu = domeniu;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTimp() {
        return timp;
    }

    public void setTimp(String timp) {
        this.timp = timp;
    }

    public int getSalariu() {
        return salariu;
    }

    public void setSalariu(int salariu) {
        this.salariu = salariu;
    }

    public boolean isParticular() {
        return particular;
    }

    public void setParticular(boolean particular) {
        this.particular = particular;
    }

}
