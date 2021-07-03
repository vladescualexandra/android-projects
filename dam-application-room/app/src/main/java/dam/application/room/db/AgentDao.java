package dam.application.room.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dam.application.room.model.AgentVanzare;

@Dao
public interface AgentDao {

    @Query("SELECT * from agenti WHERE name LIKE :filtru")
    List<AgentVanzare> filtruNume(String filtru);

    @Query("SELECT * from agenti WHERE tarif = :filtru")
    List<AgentVanzare> filtruTarif(int filtru);

    @Query("SELECT * from agenti WHERE salariu = :filtru")
    List<AgentVanzare> filtruSalariu(int filtru);


    @Query("SELECT * from agenti")
    List<AgentVanzare> getAll();

    @Insert
    long insert(AgentVanzare agent);

    @Delete
    int delete(AgentVanzare agent);

    @Update
    int update(AgentVanzare agent);
}
