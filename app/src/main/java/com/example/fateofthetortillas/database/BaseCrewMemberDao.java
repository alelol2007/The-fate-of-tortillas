package com.example.fateofthetortillas.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fateofthetortillas.crewMembers.BaseCrewMember;

import java.util.List;

@Dao
public interface BaseCrewMemberDao {
    @Query("SELECT * FROM crew_table")
    List<BaseCrewMember> getAll();

    @Insert
    void insert(BaseCrewMember member);

    @Update
    void update(BaseCrewMember member);

    @Delete
    void delete(BaseCrewMember member);
}
