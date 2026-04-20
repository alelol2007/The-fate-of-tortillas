package com.example.fateofthetortillas.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fateofthetortillas.crewMembers.BaseCrewMember;
import com.example.fateofthetortillas.crewMembers.Crew;

import java.util.List;

@Dao
public interface CrewDao {
    @Query("SELECT * FROM crew LIMIT 1")
    Crew getCrew();

    @Query("SELECT * FROM crew")
    List<Crew> getAll();

    @Insert
    void insert(Crew crew);

    @Update
    void update(BaseCrewMember crewMember);

    @Update
    void update(Crew crew);

    @Delete
    void delete(Crew crew);
}
