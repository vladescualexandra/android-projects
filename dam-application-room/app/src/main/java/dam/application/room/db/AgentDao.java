package dam.application.room.db;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import dam.application.room.model.AgentVanzare;

public interface AgentDao {

    @Query("SELECT * from agenti")
    List<AgentVanzare> getAll();

    @Insert
    long insert(AgentVanzare agent);
}
