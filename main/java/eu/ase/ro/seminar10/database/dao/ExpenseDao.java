package eu.ase.ro.seminar10.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import eu.ase.ro.seminar10.database.model.Expense;

@Dao
public interface ExpenseDao {

    @Query("select * from expenses")
    List<Expense> getAll();

    @Insert
    long insert(Expense expense);

    @Update
    int update(Expense expense);

    @Delete
    int delete(Expense expense);
}
