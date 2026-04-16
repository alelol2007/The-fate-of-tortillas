package com.example.fateofthetortillas.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fateofthetortillas.crewMembers.Crew;

@Dao
public interface CrewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCrew(Crew crew);
    @Query("SELECT * FROM crew LIMIT 1")
    Crew getCrew();

    @Update
    void updateCrew(Crew crew);
}
