package com.example.fateofthetortillas.database;

import androidx.room.TypeConverter;

import com.example.fateofthetortillas.crewMembers.BaseCrewMember;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static String fromCrewList(List<BaseCrewMember> list) {
        if(list == null) return null;
        return new Gson().toJson(list);
    }
    @TypeConverter
    public static List<BaseCrewMember> toCrewList(String json) {
        if (json == null) return null;
        Type type = new TypeToken<List<BaseCrewMember>>() {}.getType();
        return new Gson().fromJson(json, type);
    }
}
