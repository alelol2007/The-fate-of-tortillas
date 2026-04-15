package com.example.fateofthetortillas.model;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
@Database(entities = {Crew.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CrewDao crewDao();
}

