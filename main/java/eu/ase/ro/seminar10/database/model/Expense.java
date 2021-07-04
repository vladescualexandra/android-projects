package eu.ase.ro.seminar10.database.model;

import java.io.Serializable;
import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import eu.ase.ro.seminar10.util.DateConverter;

@Entity(tableName = "expenses")
public class Expense implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "amount")
    private Double amount;
    @ColumnInfo(name = "description")
    private String description;

    public Expense(long id, Date date, String category, Double amount, String description) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    @Ignore
    public Expense(Date date, String category, Double amount, String description) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Date: " + DateConverter.fromDate(date) +
                " Amount: " + amount +
                " Category: " + category +
                " Description: " + (description != null ? description : "-");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
