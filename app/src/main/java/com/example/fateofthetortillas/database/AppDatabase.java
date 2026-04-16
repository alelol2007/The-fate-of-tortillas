package com.example.fateofthetortillas.database;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.fateofthetortillas.crewMembers.Crew;

@Database(entities = {Crew.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CrewDao crewDao();
}